# Sistema de Gestión Hospitalaria

Sistema web desarrollado con Spring Boot para la gestión integral de un hospital, incluyendo pacientes, médicos, citas, hospitalización y facturación.

## 🏥 Características Principales

### Módulos Implementados
- **Gestión de Pacientes**: Registro, edición y consulta de pacientes
- **Gestión de Médicos**: Administración del personal médico y especialidades
- **Gestión de Citas**: Programación y seguimiento de citas médicas
- **Historia Clínica**: Expedientes médicos completos
- **Antecedentes Médicos**: Registro de alergias y enfermedades previas
- **Hospitalización**: Control de habitaciones y pacientes internados
- **Facturación**: Gestión de cobros y servicios
- **Seguridad**: Sistema de usuarios y roles

## 🛠️ Tecnologías Utilizadas

- **Backend**: Spring Boot 3.5.6
- **Base de Datos**: MySQL 8.0
- **Frontend**: Thymeleaf + Bootstrap 5
- **ORM**: JPA/Hibernate
- **Seguridad**: Spring Security
- **Validación**: Bean Validation
- **Java**: 17

## 📋 Requisitos Previos

1. **Java 17** o superior
2. **MySQL 8.0** o superior
3. **Maven 3.6** o superior
4. **IDE** (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Instalación y Configuración

### 1. Clonar el Proyecto
```bash
git clone <url-del-repositorio>
cd Gestion Hospitalaria
```

### 2. Configurar la Base de Datos

#### Crear la base de datos en MySQL:
```sql
CREATE DATABASE gestion_hospitalaria;
```

#### Ejecutar el script de base de datos:
```bash
mysql -u root -p gestion_hospitalaria < database_script.sql
```

### 3. Configurar las Credenciales

Editar el archivo `src/main/resources/application.properties`:
```properties
# Configuración de la base de datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_hospitalaria?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456
```

### 4. Ejecutar la Aplicación

#### Opción 1: Desde el IDE
- Abrir el proyecto en tu IDE
- Ejecutar la clase `GestionHospitalariaApplication.java`

#### Opción 2: Desde la línea de comandos
```bash
mvn spring-boot:run
```

#### Opción 3: Compilar y ejecutar JAR
```bash
mvn clean package
java -jar target/gestion-hospitalaria-0.0.1-SNAPSHOT.jar
```

### 5. Acceder a la Aplicación

Abrir el navegador y acceder a:
```
http://localhost:8080
```

## 📱 Uso del Sistema

### Página Principal
- Dashboard con estadísticas generales
- Acceso rápido a todas las funcionalidades

### Gestión de Pacientes
- **Ver pacientes**: Lista de todos los pacientes activos
- **Nuevo paciente**: Formulario de registro con validaciones
- **Editar paciente**: Modificar datos existentes
- **Desactivar paciente**: Cambiar estado a inactivo

### Gestión de Médicos
- **Ver médicos**: Lista del personal médico
- **Nuevo médico**: Registro con colegiatura
- **Editar médico**: Actualizar información
- **Asignar especialidades**: Relacionar médico con especialidades

### Gestión de Citas
- **Ver citas**: Lista de todas las citas programadas
- **Nueva cita**: Programar cita entre paciente y médico
- **Atender cita**: Marcar como atendida
- **Cancelar cita**: Cancelar cita programada

## 🗄️ Estructura de la Base de Datos

### Tablas Principales
- `Paciente`: Información personal de pacientes
- `Medico`: Datos del personal médico
- `Especialidad`: Especialidades médicas
- `Cita`: Citas programadas
- `Consulta`: Registro de consultas médicas
- `HistoriaClinica`: Expedientes médicos
- `Habitacion`: Control de habitaciones
- `Factura`: Facturación de servicios
- `Usuario`: Sistema de usuarios
- `Bitacora`: Registro de actividades

## 🔧 Configuración Adicional

### Cambiar Puerto de la Aplicación
En `application.properties`:
```properties
server.port=8081
```

### Configurar Seguridad
El sistema incluye Spring Security básico. Para desarrollo, las credenciales son:
- Usuario: `admin`
- Contraseña: `admin123`

### Personalizar Validaciones
Las validaciones están en las entidades JPA usando Bean Validation:
- `@NotNull`: Campo obligatorio
- `@NotBlank`: No puede estar vacío
- `@Size`: Longitud máxima
- `@Email`: Formato de correo válido

## 📊 Funcionalidades Implementadas

### ✅ Completadas
- [x] Configuración del proyecto Spring Boot
- [x] Entidades JPA para todas las tablas
- [x] Repositorios con consultas personalizadas
- [x] Servicios de negocio con validaciones
- [x] Controladores web con Thymeleaf
- [x] Interfaz de usuario responsive
- [x] Gestión completa de pacientes
- [x] Gestión básica de médicos
- [x] Sistema de citas médicas
- [x] Validaciones de formularios
- [x] Manejo de errores y mensajes





