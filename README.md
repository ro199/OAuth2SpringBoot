# OAuth2SpringBoot
# Proyecto Backend con Spring Boot

Este proyecto es una aplicación backend desarrollada en Java utilizando el framework Spring Boot. La aplicación utiliza una base de datos PostgreSQL para la persistencia de datos y expone una API REST.

## Tecnologías Utilizadas

- Lenguaje: Java
- Framework: Spring Boot
- Persistencia de Datos: PostgreSQL
- Autenticación: OAuth 2.0
- Documentación de la API: Swagger-OpenAPI

## Requisitos

Asegúrate de tener instaladas las siguientes herramientas antes de continuar:

- Java Development Kit (JDK) 17
- PostgreSQL
- Maven

## Configuración

1. Clona este repositorio a tu máquina local.

```bash
git clone https://github.com/ro199/OAuth2SpringBoot.git
cd OAuth2SpringBoot

## Antes de Empezar

### Restaurar la Base de Datos
Antes de iniciar la aplicación, asegúrate de restaurar la base de datos utilizando el archivo SQL proporcionado en el archivo `KruggerEmployedBackUp.sql`. Esto establecerá la estructura y los datos iniciales necesarios para el funcionamiento de la aplicación.

### Importar y Ejecutar los Proyectos
Para poner en marcha la aplicación, sigue estos pasos:

1. Abre tu IDE IntelliJ.
2. Importa los proyectos `authorization-server` y `resource-server`.
3. Ejecuta primero el proyecto `authorization-server`.
4. Luego, inicia el proyecto `resource-server`. 

La generación de autorización y autenticación se indicará en la documentación entregada.

## Instrucciones Adicionales

### Documentación de la API
La documentación de la API se encuentra disponible en Swagger-OpenAPI. Una vez que la aplicación esté en funcionamiento, puedes acceder a la documentación utilizando tu navegador web. La URL típica de la documentación es:

