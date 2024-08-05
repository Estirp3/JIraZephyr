[![Maven Central](https://img.shields.io/maven-central/v/io.github.bonigarcia/webdrivermanager.svg)]()
[![Java](https://img.shields.io/badge/JavaJDK-v11.0-gren)]()
[![github](https://img.shields.io/badge/Git__Patricio_Calderon-GitHub-black)](https://github.com/Estirp3)



### Version - Actualizado 
```properties
Version:2.0
Observaciones: El jar realiza la ejecución de las pruebas modificando su estado.
Evidencia: Se sube la ultima evidencia sacada desde la ejecución, formato png.
Api: Trabajamos directamente con las apis de jira zephyr
```
# Integración JIra Zephyr
_Este Jar ejecuta el resultado de las pruebas automatizadas en cucumber, desde el archivo cucumber.json_ <br>
_estas pruebas quedan registradas en Jira-Zephyr._ <br>

### Pre-requisitos | Creación Jar 📋
_En el S.O debe tener instalado lo siguinte:_
```
Maven
Java_JDK 
```
## Para poder crear el jar de forma local

* Despues de descargar el proyecto de gitLab
* Abrir el proyecto - en el apartado derecho "MAVEN" al ingresar presionar Zephyr
* presionar la carpeta Plugins, dar click en assembly y dar doble click assembly:assembly

## Ubicación del Jar 🚀
_Se crea la carpeta TARGET, dentro de esta se crea el 2 JAR con los siguientes nombres:_
## Zephyr-2.0.jar
_Este jar carece de todas las dependencias, como es un complemento de maven y SurfinReport se crea de forma automatica_
## Zephyr-2.0-jar-with-dependencies.jar
_Este jar trae todas las dependencias que contiene el proyecto, este es el jar funcional con las API de Zephyr y Jira_
###  Actualidad del Jar y Utilizacion de este:
_Dentro del proyecto que se utilice el Jar, se debe crear un archvio tipo properties <br> el archivo creado debe contener lo siguiente_

```properties
keyProyecto = Se debe escribir la sigla del proyecto en que queremos trabajar, QA Automation Squad=(QAT)  
version = Se debe escribir la version exacta donde se dejaran las pruebas ejecutadas por la automatizacion 
ciclo = Se debe escribir el ciclo exacto donde quedaran las pruebas ejecutadas por la automatizacion 
carpeta = Nombre de la carpeta que se creeara en el ciclo de zephyr    
url = https://jira.atlasian.net
nuevaCarpeta = Siempore debe ir el valor de SI
ruta = Es el path donde debe ir a busacar el reporte 
archivo = en nombre del archivo con su extension

```
### Report cucumber

_Se debe poder optener el reporte tipo json de cucumber desde la version 5.x.x_