# Mimacom Technical Challenge

Esta prueba técnica consistirá en crear una pequeña aplicación backend que exponga una API
REST que permita a un usuario gestionar una **lista de tareas** personalizada.

La aplicación debe permitir tanto la **creación** de tareas nuevas, como el **borrado y la edición**
de tareas existentes. Asímismo, una tarea ya realizada debe poder marcarse como **finalizada**.

El candidato tendrá que desarrollar este ejercicio utilizando **Java** y **Spring Framework**,
dejando a su libre elección tanto las versiones a utilizar como el resto de tecnologías que
puedan necesitarse para completar la funcionalidad, como por ejemplo, la tecnología con la
que se va a construir el proyecto (Maven, Gradle, Ant, etc).

Como entregable final, se facilitará el acceso al código fuente original, en el formato elegido por
el candidato, así como los pasos a seguir para poner la aplicación en marcha.


### Installation

Para ahorrar tiempo y trabajo construí una imagen la cual subí al registry de mi cuenta en docker hub que tiene el enviroment necesario para ejecutar la app, **solo es requerido instalar docker en tu ordenador y ejecutar el compose** y seguir las indicaciones que te proporcionaré. veamos como instalar docker en los sistemas operativos. 

### Install Compose on macOS

Docker Desktop para Mac incluye Compose junto con otras aplicaciones de Docker, por lo que los usuarios de Mac no necesitan instalar Compose por separado.

* [Descargar DockerDesktop] - https://desktop.docker.com/mac/stable/Docker.dmg
* Haga doble clic en Docker.dmg para abrir el instalador, luego arrastre el icono de Docker a la carpeta Aplicaciones.
* Haga doble clic en Docker.app en la carpeta Aplicaciones para iniciar Docker. (En el siguiente ejemplo, la carpeta Aplicaciones está en modo de vista de "cuadrícula").
* El menú de Docker en la barra de estado superior indica que Docker Desktop se está ejecutando y es accesible desde una terminal. ¡Felicidades! Ahora está ejecutando con éxito Docker Desktop.

### Install Compose on Windows

Esta instalación incluye Compose junto con otras aplicaciones de Docker, por lo que los usuarios de Windows no necesitan instalar Compose por separado.

* Haga doble clic en Docker Desktop Installer.exe para ejecutar el instalador.

* Si aún no ha descargado el instalador (Docker Desktop Installer.exe), puede obtenerlo de Docker Hub. Por lo general, se descarga en su carpeta de Descargas o puede ejecutarlo desde la barra de descargas recientes en la parte inferior de su navegador web.

* Cuando se le solicite, asegúrese de que la opción Habilitar características de Hyper-V Windows esté seleccionada en la página Configuración.

* Siga las instrucciones del asistente de instalación para autorizar el instalador y continuar con la instalación.

* Cuando la instalación sea exitosa, haga clic en Cerrar para completar el proceso de instalación.

* Si su cuenta de administrador es diferente a su cuenta de usuario, debe agregar el usuario al grupo docker-users. Ejecute Computer Management como administrador y vaya a Usuarios y grupos locales> Grupos> docker-users. Haga clic derecho para agregar el usuario al grupo. Cierre la sesión y vuelva a iniciarla para que los cambios surtan efecto.

### Install Compose on Linux

* Ejecute este comando para descargar la versión estable actual de Docker Compose:
```sh
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```
* Aplicar permisos ejecutables al binario:
```sh
$ sudo chmod +x /usr/local/bin/docker-compose
```
* Test the installation.
```sh
$ docker-compose --version
```

Cualquier inconveniente consultar la documentación https://docs.docker.com/docker-for-mac/install/

### Run project

Una vez instalado Docker Compose en tu sistema operativo haremos el siguiente procedimiento:

* Tomar el siguiente **ComposeFile** - https://github.com/lacartaya/TaskManager/blob/main/src/main/docker/docker-compose.yml y lo colocaremos en la ruta donde tengamos permisos de ejecución.

* Ejecutaremos el siguiente comando para crear dos contenedores:

```sh
$ docker-compose -f docker-compose.yml up -d
```
* Verificamos que los dos containers esten creados:
```sh
$ docker ps

```

* Colocamos la IP y el puerto en nuestro navegador http://0.0.0.0:8080/ la misma se mostrará en este formato:
```sh
luisin/taskmanagerservice  "java -Djava.securit…"   About a minute ago   Up About a minute   **0.0.0.0:8080->8080/tcp**              microservicio

```

* Estaremos en la página de inicio.

### Usuario de la aplicacion

| Usuario | Password |
| ------ | ------ |
| Juan | 12345 |