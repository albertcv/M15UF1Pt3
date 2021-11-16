# Practica_2_M15_uf1
## Descripción
Sofware dedicado para la gestions de cadenas de ARN y ARN.
Incorpora funciones como:
  - Dar la vuelta a una cadena de ADN
  - Encontrar la base nitrogenada más pequeña.
  - Encontrar la base nitrogenada más grande.
  - Hacer un recuento y mostrarlo.
  - Convertir ADN a ARN.
  - Convertir ARN a ADN.
## Funcionamiento
Una vez este en funcionamiento la clase principal, nos encontraremos con un mensaje de si queremos empezar importando Los usuarios desde un archivo json. En caso afirmativo pedira la ubicación del archivo y si este esta encriptado(Si esta encriptado necesitamos el token pertinente o la importación se generara con errores).

El primer menu que vemos es el del login. Existen dos tipos de usuarios en esta aplicación:
  - Administradores: Aquellos que gestionan los usuarios.
  - Usuarios: Aquellos que pueden usar las funcionalidades de gestión de cadenas.

El administrador es capaz de eliminar, crear, actualizar y ver a otros usuarios (No se le permite ver las contraseñas a no ser que el mismo cree a los usuarios) hay diferentes formas de crear usuarios, puedes crear un solo usuario o bien usar un archivo json para importarlo; es posible migrar usuarios de una aplicación a otra usando la función json export, la cual te permite crear un json con las contraseñas encriptadas (importante guardarte el token para desencriptar). Por cada archivo json exportado se crear un token diferente.
La funcion de actualizar te permite actualizar los diferente parametros de un solo usuario, lo unico que necesitas es el identificador e-mail.
Es posible borrar un usuarios solo con el email.
Hay diferentes maneras de mostras los diferentes usuarios: usando el email, el nombre, o el nombre y el apellido; tambien puedes pedir mostrar todos los usuarios cargados.

Para mantener la persistencia de los usuarios usamos un archivo llamado archivo.bin, es de vital importancia no borrar este archivo o perderemos a todos los usuarios. Para evitar este problema es posible exportar los usuarios en un json y guardarlos como una copia de seguridad. O guardar directamente el archivo.bin, El problema de esta opcion es que el token que usa para encriptar es privado, ya que es un archivo interno.
