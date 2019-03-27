Proyecto manejo de Excepciones
==============================

La ruta maneja tres tipos de excepcion:

* onException: manejo de excepciones generales para toda la ruta, en este caso, maneja la excepcion NumberFormatException.
* doTry-doCatch: manejo de excepciones para una ruta en especifico, en este caso, se encarga de manejar la excepcion generada cuando un archivo ya existe en el destino.
* errorHandler: puede manejarse como una excepcion para una ruta en especifico o como una excepcion general para todas las rutas, en este caso, se encarga de manejar las excepciones que no sean manejadas por el doTry ni el onException

Para correr el proyecto, desde consola ejecutar:

    mvn clean camel:run

Desde JBoss developer studio, crear un perfil de ejecuciÃ³n:

	clean camel:run
	