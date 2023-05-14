# language: es

# Archivo: navegacion.feature
# Descripción: Escenarios de prueba para la navegación del usuario

Característica: Navegación del usuario

  Esquema del escenario: Navegar a la página de inicio
    Dado un usuario está en la página inicial
    Entonces se muestra la página de inicio

  Esquema del escenario: Navegar a la lista de usuarios
    Cuando el usuario hace clic sobre el botón de Usuarios
    Entonces se muestran todos los usuarios del sistema

  Esquema del escenario: Navegar a la creación de usuarios
    Cuando el usuario hace clic sobre el botón de crear Usuario
    Entonces se muestra el formulario de creación de usuarios

  Esquema del escenario: Navegar a la lista de sorteos
    Cuando el usuario hace clic sobre el botón de Sorteo
    Entonces se muestra la lista de sorteos del sistema

  Esquema del escenario: Navegar a la creación de sorteos
    Cuando el usuario hace clic sobre el botón de crear Sorteo
    Entonces se muestra el formulario de creación de sorteos
