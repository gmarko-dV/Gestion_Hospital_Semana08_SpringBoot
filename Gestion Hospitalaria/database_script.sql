-- =============================================
-- SCRIPT DE BASE DE DATOS - SISTEMA GESTIÓN HOSPITALARIA
-- Base de datos: gestion_hospitalaria
-- Usuario: root
-- Contraseña: 123456
-- =============================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS gestion_hospitalaria;
USE gestion_hospitalaria;

-- =============================================
-- 1. MÓDULO DE PACIENTES
-- =============================================

-- Tabla Paciente
CREATE TABLE Paciente (
    idPaciente INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    sexo ENUM('M', 'F') NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(15),
    correo VARCHAR(100),
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla HistoriaClinica
CREATE TABLE HistoriaClinica (
    idHistoria INT AUTO_INCREMENT PRIMARY KEY,
    idPaciente INT NOT NULL,
    fechaApertura DATE NOT NULL,
    observaciones TEXT,
    FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente) ON DELETE CASCADE
);

-- Tabla AntecedenteMedico
CREATE TABLE AntecedenteMedico (
    idAntecedente INT AUTO_INCREMENT PRIMARY KEY,
    idHistoria INT NOT NULL,
    tipo ENUM('alergias', 'enfermedades_previas', 'cirugias', 'otros') NOT NULL,
    descripcion TEXT NOT NULL,
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idHistoria) REFERENCES HistoriaClinica(idHistoria) ON DELETE CASCADE
);

-- =============================================
-- 2. MÓDULO DE MÉDICOS Y ESPECIALIDADES
-- =============================================

-- Tabla Especialidad
CREATE TABLE Especialidad (
    idEspecialidad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

-- Tabla Medico
CREATE TABLE Medico (
    idMedico INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    colegiatura VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    correo VARCHAR(100),
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla MedicoEspecialidad (relación muchos a muchos)
CREATE TABLE MedicoEspecialidad (
    idMedicoEsp INT AUTO_INCREMENT PRIMARY KEY,
    idMedico INT NOT NULL,
    idEspecialidad INT NOT NULL,
    fechaAsignacion DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (idMedico) REFERENCES Medico(idMedico) ON DELETE CASCADE,
    FOREIGN KEY (idEspecialidad) REFERENCES Especialidad(idEspecialidad) ON DELETE CASCADE,
    UNIQUE KEY unique_medico_especialidad (idMedico, idEspecialidad)
);

-- =============================================
-- 3. MÓDULO DE CITAS MÉDICAS
-- =============================================

-- Tabla Cita
CREATE TABLE Cita (
    idCita INT AUTO_INCREMENT PRIMARY KEY,
    idPaciente INT NOT NULL,
    idMedico INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    motivo VARCHAR(200),
    estado ENUM('programada', 'atendida', 'cancelada') DEFAULT 'programada',
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente) ON DELETE CASCADE,
    FOREIGN KEY (idMedico) REFERENCES Medico(idMedico) ON DELETE CASCADE
);

-- =============================================
-- 4. MÓDULO DE CONSULTAS Y DIAGNÓSTICOS
-- =============================================

-- Tabla Consulta
CREATE TABLE Consulta (
    idConsulta INT AUTO_INCREMENT PRIMARY KEY,
    idCita INT NOT NULL,
    idMedico INT NOT NULL,
    idPaciente INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    motivoConsulta VARCHAR(200),
    observaciones TEXT,
    FOREIGN KEY (idCita) REFERENCES Cita(idCita) ON DELETE CASCADE,
    FOREIGN KEY (idMedico) REFERENCES Medico(idMedico) ON DELETE CASCADE,
    FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente) ON DELETE CASCADE
);

-- Tabla Diagnostico
CREATE TABLE Diagnostico (
    idDiagnostico INT AUTO_INCREMENT PRIMARY KEY,
    idConsulta INT NOT NULL,
    descripcion TEXT NOT NULL,
    tipo ENUM('presuntivo', 'definitivo') NOT NULL,
    fechaDiagnostico TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idConsulta) REFERENCES Consulta(idConsulta) ON DELETE CASCADE
);

-- Tabla RecetaMedica
CREATE TABLE RecetaMedica (
    idReceta INT AUTO_INCREMENT PRIMARY KEY,
    idConsulta INT NOT NULL,
    indicaciones TEXT,
    fechaEmision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idConsulta) REFERENCES Consulta(idConsulta) ON DELETE CASCADE
);

-- Tabla DetalleReceta
CREATE TABLE DetalleReceta (
    idDetalleReceta INT AUTO_INCREMENT PRIMARY KEY,
    idReceta INT NOT NULL,
    medicamento VARCHAR(200) NOT NULL,
    dosis VARCHAR(100) NOT NULL,
    frecuencia VARCHAR(100) NOT NULL,
    duracion VARCHAR(100) NOT NULL,
    FOREIGN KEY (idReceta) REFERENCES RecetaMedica(idReceta) ON DELETE CASCADE
);

-- =============================================
-- 5. MÓDULO DE HOSPITALIZACIÓN
-- =============================================

-- Tabla Habitacion
CREATE TABLE Habitacion (
    idHabitacion INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(10) NOT NULL UNIQUE,
    tipo ENUM('UCI', 'general', 'emergencia') NOT NULL,
    estado ENUM('disponible', 'ocupada', 'mantenimiento') DEFAULT 'disponible',
    capacidad INT DEFAULT 1
);

-- Tabla Hospitalizacion
CREATE TABLE Hospitalizacion (
    idHosp INT AUTO_INCREMENT PRIMARY KEY,
    idPaciente INT NOT NULL,
    idHabitacion INT NOT NULL,
    fechaIngreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fechaAlta TIMESTAMP NULL,
    diagnosticoIngreso TEXT,
    estado ENUM('activo', 'dado de alta') DEFAULT 'activo',
    FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente) ON DELETE CASCADE,
    FOREIGN KEY (idHabitacion) REFERENCES Habitacion(idHabitacion) ON DELETE CASCADE
);

-- =============================================
-- 6. MÓDULO DE FACTURACIÓN
-- =============================================

-- Tabla Factura
CREATE TABLE Factura (
    idFactura INT AUTO_INCREMENT PRIMARY KEY,
    idPaciente INT NOT NULL,
    fechaEmision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    estado ENUM('pendiente', 'pagado') DEFAULT 'pendiente',
    FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente) ON DELETE CASCADE
);

-- Tabla DetalleFactura
CREATE TABLE DetalleFactura (
    idDetalleFactura INT AUTO_INCREMENT PRIMARY KEY,
    idFactura INT NOT NULL,
    concepto ENUM('consulta', 'medicamento', 'procedimiento', 'hospitalizacion', 'laboratorio') NOT NULL,
    descripcion VARCHAR(200),
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idFactura) REFERENCES Factura(idFactura) ON DELETE CASCADE
);

-- =============================================
-- 7. MÓDULO DE ADMINISTRACIÓN Y SEGURIDAD
-- =============================================

-- Tabla Usuario
CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombreUsuario VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('admin', 'medico', 'recepcionista', 'enfermera') NOT NULL,
    estado ENUM('activo', 'inactivo') DEFAULT 'activo',
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Bitacora
CREATE TABLE Bitacora (
    idBitacora INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    accion VARCHAR(200) NOT NULL,
    fechaHora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    detalles TEXT,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario) ON DELETE CASCADE
);

-- =============================================
-- INSERTAR DATOS DE EJEMPLO
-- =============================================

-- Insertar especialidades
INSERT INTO Especialidad (nombre, descripcion) VALUES
('Medicina General', 'Atención médica general y preventiva'),
('Cardiología', 'Especialidad en enfermedades del corazón'),
('Pediatría', 'Atención médica para niños y adolescentes'),
('Ginecología', 'Especialidad en salud femenina'),
('Traumatología', 'Especialidad en huesos y articulaciones'),
('Neurología', 'Especialidad en sistema nervioso');

-- Insertar médicos
INSERT INTO Medico (nombres, apellidos, colegiatura, telefono, correo) VALUES
('Carlos', 'Mendoza', 'CM12345', '987654321', 'carlos.mendoza@hospital.com'),
('Ana', 'García', 'AG67890', '987654322', 'ana.garcia@hospital.com'),
('Luis', 'Rodríguez', 'LR11111', '987654323', 'luis.rodriguez@hospital.com'),
('María', 'López', 'ML22222', '987654324', 'maria.lopez@hospital.com');

-- Asignar especialidades a médicos
INSERT INTO MedicoEspecialidad (idMedico, idEspecialidad) VALUES
(1, 1), -- Carlos Mendoza - Medicina General
(1, 2), -- Carlos Mendoza - Cardiología
(2, 3), -- Ana García - Pediatría
(3, 4), -- Luis Rodríguez - Ginecología
(4, 5), -- María López - Traumatología
(4, 6); -- María López - Neurología

-- Insertar pacientes
INSERT INTO Paciente (dni, nombres, apellidos, fechaNacimiento, sexo, direccion, telefono, correo) VALUES
('12345678', 'Juan', 'Pérez', '1985-03-15', 'M', 'Av. Principal 123', '999888777', 'juan.perez@email.com'),
('87654321', 'María', 'González', '1990-07-22', 'F', 'Calle Secundaria 456', '999888778', 'maria.gonzalez@email.com'),
('11223344', 'Pedro', 'Martínez', '1978-11-08', 'M', 'Jr. Libertad 789', '999888779', 'pedro.martinez@email.com');

-- Crear historias clínicas para los pacientes
INSERT INTO HistoriaClinica (idPaciente, fechaApertura, observaciones) VALUES
(1, '2024-01-15', 'Paciente nuevo, sin antecedentes conocidos'),
(2, '2024-01-16', 'Paciente con antecedentes de hipertensión'),
(3, '2024-01-17', 'Paciente con alergia a penicilina');

-- Insertar antecedentes médicos
INSERT INTO AntecedenteMedico (idHistoria, tipo, descripcion) VALUES
(2, 'enfermedades_previas', 'Hipertensión arterial desde 2020'),
(3, 'alergias', 'Alergia severa a penicilina y derivados');

-- Insertar habitaciones
INSERT INTO Habitacion (numero, tipo, estado) VALUES
('101', 'general', 'disponible'),
('102', 'general', 'disponible'),
('201', 'UCI', 'disponible'),
('301', 'emergencia', 'disponible'),
('103', 'general', 'mantenimiento');

-- Insertar usuarios del sistema
INSERT INTO Usuario (nombreUsuario, contraseña, rol) VALUES
('admin', 'admin123', 'admin'),
('recepcionista1', 'recepcion123', 'recepcionista'),
('medico1', 'medico123', 'medico'),
('enfermera1', 'enfermera123', 'enfermera');

-- Insertar citas de ejemplo
INSERT INTO Cita (idPaciente, idMedico, fecha, hora, motivo, estado) VALUES
(1, 1, '2024-02-01', '09:00:00', 'Consulta general', 'programada'),
(2, 2, '2024-02-01', '10:30:00', 'Control pediátrico', 'programada'),
(3, 1, '2024-02-02', '14:00:00', 'Revisión cardiológica', 'programada');

-- =============================================
-- ÍNDICES PARA MEJORAR RENDIMIENTO
-- =============================================

-- Índices en campos de búsqueda frecuente
CREATE INDEX idx_paciente_dni ON Paciente(dni);
CREATE INDEX idx_paciente_nombres ON Paciente(nombres, apellidos);
CREATE INDEX idx_cita_fecha ON Cita(fecha);
CREATE INDEX idx_cita_estado ON Cita(estado);
CREATE INDEX idx_medico_especialidad ON MedicoEspecialidad(idEspecialidad);
CREATE INDEX idx_habitacion_tipo ON Habitacion(tipo);
CREATE INDEX idx_habitacion_estado ON Habitacion(estado);
CREATE INDEX idx_factura_estado ON Factura(estado);
CREATE INDEX idx_bitacora_fecha ON Bitacora(fechaHora);

-- =============================================
-- VISTAS ÚTILES PARA CONSULTAS FRECUENTES
-- =============================================

-- Vista de pacientes con sus historias clínicas
CREATE VIEW vista_pacientes_completos AS
SELECT 
    p.idPaciente,
    p.dni,
    p.nombres,
    p.apellidos,
    p.fechaNacimiento,
    p.sexo,
    p.telefono,
    p.correo,
    hc.fechaApertura as fecha_historia_clinica
FROM Paciente p
LEFT JOIN HistoriaClinica hc ON p.idPaciente = hc.idPaciente
WHERE p.estado = 'activo';

-- Vista de médicos con sus especialidades
CREATE VIEW vista_medicos_especialidades AS
SELECT 
    m.idMedico,
    m.nombres,
    m.apellidos,
    m.colegiatura,
    GROUP_CONCAT(e.nombre SEPARATOR ', ') as especialidades
FROM Medico m
LEFT JOIN MedicoEspecialidad me ON m.idMedico = me.idMedico
LEFT JOIN Especialidad e ON me.idEspecialidad = e.idEspecialidad
WHERE m.estado = 'activo'
GROUP BY m.idMedico, m.nombres, m.apellidos, m.colegiatura;

-- Vista de citas con información completa
CREATE VIEW vista_citas_completas AS
SELECT 
    c.idCita,
    p.nombres as paciente_nombre,
    p.apellidos as paciente_apellido,
    p.dni as paciente_dni,
    m.nombres as medico_nombre,
    m.apellidos as medico_apellido,
    c.fecha,
    c.hora,
    c.motivo,
    c.estado
FROM Cita c
JOIN Paciente p ON c.idPaciente = p.idPaciente
JOIN Medico m ON c.idMedico = m.idMedico;

-- =============================================
-- PROCEDIMIENTOS ALMACENADOS BÁSICOS
-- =============================================

DELIMITER //

-- Procedimiento para crear un nuevo paciente con historia clínica
CREATE PROCEDURE CrearPacienteCompleto(
    IN p_dni VARCHAR(8),
    IN p_nombres VARCHAR(100),
    IN p_apellidos VARCHAR(100),
    IN p_fechaNacimiento DATE,
    IN p_sexo ENUM('M', 'F'),
    IN p_direccion VARCHAR(200),
    IN p_telefono VARCHAR(15),
    IN p_correo VARCHAR(100)
)
BEGIN
    DECLARE v_idPaciente INT;
    
    -- Insertar paciente
    INSERT INTO Paciente (dni, nombres, apellidos, fechaNacimiento, sexo, direccion, telefono, correo)
    VALUES (p_dni, p_nombres, p_apellidos, p_fechaNacimiento, p_sexo, p_direccion, p_telefono, p_correo);
    
    -- Obtener el ID del paciente insertado
    SET v_idPaciente = LAST_INSERT_ID();
    
    -- Crear historia clínica automáticamente
    INSERT INTO HistoriaClinica (idPaciente, fechaApertura, observaciones)
    VALUES (v_idPaciente, CURDATE(), 'Historia clínica creada automáticamente');
    
    SELECT v_idPaciente as idPacienteCreado;
END //

-- Procedimiento para registrar una acción en la bitácora
CREATE PROCEDURE RegistrarBitacora(
    IN p_idUsuario INT,
    IN p_accion VARCHAR(200),
    IN p_detalles TEXT
)
BEGIN
    INSERT INTO Bitacora (idUsuario, accion, detalles)
    VALUES (p_idUsuario, p_accion, p_detalles);
END //

DELIMITER ;

-- =============================================
-- MENSAJE FINAL
-- =============================================

SELECT 'Base de datos gestion_hospitalaria creada exitosamente!' as Mensaje;
SELECT 'Tablas creadas: 12' as Tablas;
SELECT 'Vistas creadas: 3' as Vistas;
SELECT 'Procedimientos creados: 2' as Procedimientos;
SELECT 'Datos de ejemplo insertados' as Datos;
