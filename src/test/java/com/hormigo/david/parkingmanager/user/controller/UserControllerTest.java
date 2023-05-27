package com.hormigo.david.parkingmanager.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.service.UserService;
import com.hormigo.david.parkingmanager.user.service.UserServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void testPositive() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("da@correo.es", "David", "Hormigo", "Ramírez", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenReturn(new User("da@correo.es","David","Hormigo","Ramírez",Role.PROFESSOR));
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().isCreated());
    }


    @Test
    public void testSingleUserRead() throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        User user = new User("dhorram948@g.educaand.es","David","Hormigo","Ramírez",Role.PROFESSOR);
        ArrayList<User> usuariso = new ArrayList<>();
        usuariso.add(user);
        String json = mapper.writeValueAsString(usuariso);
        json = "{ \"_embedded\": {\"userList\":" + json + "}}";
        when(userService.getAll()).thenReturn(usuariso);
        this.mockMvc.perform(get("/api/users"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
                    

    }


    @Test
    public void testLeerUsuarios() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> listaUsuario = new ArrayList<>();
        listaUsuario.add(new User("pablojaviermu@hotmail.com", "Pablo Javier", "Muñoz", Role.STUDENT));
        listaUsuario.add(new User("Daniel@ns.com", "Dani", "García", Role.STUDENT));
        String json = mapper.writeValueAsString(listaUsuario);
        json = "{\"_embedded\": {\"userList\": " +json+"}}";
        when(userService.getAll()).thenReturn(listaUsuario);
        this.mockMvc.perform(get("/api/users"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
    }

    //correoRepetido
    @Test
    public void testCrearUsuariosMal1() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("prueba@prueba.com","prueba","prueba",Role.STUDENT);
        String json = mapper.writeValueAsString(user);
        when(userService.register(any(UserDao.class))).thenThrow(UserExistsException.class);
        this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json("Ya existe alguien registrado con este correo"));
        
    }

    //corrreoNulo
    @Test
    public void testCrearUsuariosMal2() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("","prueba","prueba",Role.STUDENT);
        String json = mapper.writeValueAsString(user);
        when(userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("falta correo"));
    }

    //nombreNulo
    @Test
    public void testCrearUsuariosMal3() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("prueba@prueba.com",null,"prueba",Role.STUDENT);
        String json = mapper.writeValueAsString(user);
        when(userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("falta nombre"));
    }

    @Test
    public void testBorrarUsuarios() throws Exception{
        User user = new User("prueba@prueba.com","prueba","prueba",Role.STUDENT);
        when(userService.getUser(2)).thenReturn(Optional.of(user));
        this.mockMvc.perform(delete("/api/users/2"))
                    .andDo(print())
                    .andExpect(status().is(204));
    }

    @Test 
    //no me funciona...
    public void testModificarUsuarios() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("prueba@prueba.com","prueba","prueba",Role.STUDENT);
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", "cambio");
        updatedData.put("lastName1", "cambio");
        updatedData.put("lastName2", "cambio");
        updatedData.put("role", "STUDENT");
        user.setName("cambio");
        String json = mapper.writeValueAsString(user);
        String updatedDataJson = mapper.writeValueAsString(updatedData);
        when(userService.updateUser(2, updatedData)).thenReturn(user);
        String json2 = mapper.writeValueAsString(user);
        String updateDataJson2 = mapper.writeValueAsString(updatedData);
        when(userService.updateUser(2, updatedData)).thenReturn(user);
        this.mockMvc.perform(patch("/api/users/2").contentType(MediaType.APPLICATION_JSON).content(updateDataJson))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));

    }
}
