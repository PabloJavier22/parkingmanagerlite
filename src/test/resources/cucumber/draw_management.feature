# language: es
Característica: Gestion de sorteos
  
  Escenario: Navegación a la lista de sorteos
    Dado un usuario esta en la pagina inicial
    Cuando el usuario hace click sobre el botón de Sorteos
    Entonces esta en la pagina de lista de sorteos

  Escenario: Navegacion a la lista de creación de sorteo
    Dado un usuario esta en la pagina lista de sorteos
    Y el usuario hace click sobre el botón de crear sorteo

  Escenario: Comprobar que el formulario de creación de sorteo tiene todos los elementos
    Dado un usuario esta en la pagina creación de sorteo
    
  Escenario: Crear un sorteo correctamente
    Dado un usuario esta en la pagina creación de sorteo
    Y el usuario hace click sobre el botón enviar nuevo sorteo
    Entonces esta en la pagina de lista de sorteos
    Y se ha persistido el sorteo en la base de datos
