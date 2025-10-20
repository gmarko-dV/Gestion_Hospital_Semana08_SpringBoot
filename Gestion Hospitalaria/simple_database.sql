-- Script simple para crear la base de datos
USE gestion_hospitalaria;

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

-- Tabla MedicoEspecialidad
CREATE TABLE MedicoEspecialidad (
    idMedicoEsp INT AUTO_INCREMENT PRIMARY KEY,
    idMedico INT NOT NULL,
    idEspecialidad INT NOT NULL,
    fechaAsignacion DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (idMedico) REFERENCES Medico(idMedico) ON DELETE CASCADE,
    FOREIGN KEY (idEspecialidad) REFERENCES Especialidad(idEspecialidad) ON DELETE CASCADE,
    UNIQUE KEY unique_medico_especialidad (idMedico, idEspecialidad)
);

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

-- Tabla Usuario
CREATE TABLE Usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombreUsuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
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

-- Insertar datos de ejemplo
INSERT INTO Especialidad (nombre, descripcion) VALUES
('Medicina General', 'Atencion medica general y preventiva'),
('Cardiologia', 'Especialidad en enfermedades del corazon'),
('Pediatria', 'Atencion medica para ninos y adolescentes'),
('Ginecologia', 'Especialidad en salud femenina'),
('Traumatologia', 'Especialidad en huesos y articulaciones'),
('Neurologia', 'Especialidad en sistema nervioso');

INSERT INTO Medico (nombres, apellidos, colegiatura, telefono, correo) VALUES
('Carlos', 'Mendoza', 'CM12345', '987654321', 'carlos.mendoza@hospital.com'),
('Ana', 'Garcia', 'AG67890', '987654322', 'ana.garcia@hospital.com'),
('Luis', 'Rodriguez', 'LR11111', '987654323', 'luis.rodriguez@hospital.com'),
('Maria', 'Lopez', 'ML22222', '987654324', 'maria.lopez@hospital.com');

INSERT INTO MedicoEspecialidad (idMedico, idEspecialidad) VALUES
(1, 1), (1, 2), (2, 3), (3, 4), (4, 5), (4, 6);

INSERT INTO Paciente (dni, nombres, apellidos, fechaNacimiento, sexo, direccion, telefono, correo) VALUES
('12345678', 'Juan', 'Perez', '1985-03-15', 'M', 'Av. Principal 123', '999888777', 'juan.perez@email.com'),
('87654321', 'Maria', 'Gonzalez', '1990-07-22', 'F', 'Calle Secundaria 456', '999888778', 'maria.gonzalez@email.com'),
('11223344', 'Pedro', 'Martinez', '1978-11-08', 'M', 'Jr. Libertad 789', '999888779', 'pedro.martinez@email.com');

INSERT INTO HistoriaClinica (idPaciente, fechaApertura, observaciones) VALUES
(1, '2024-01-15', 'Paciente nuevo, sin antecedentes conocidos'),
(2, '2024-01-16', 'Paciente con antecedentes de hipertension'),
(3, '2024-01-17', 'Paciente con alergia a penicilina');

INSERT INTO AntecedenteMedico (idHistoria, tipo, descripcion) VALUES
(2, 'enfermedades_previas', 'Hipertension arterial desde 2020'),
(3, 'alergias', 'Alergia severa a penicilina y derivados');

INSERT INTO Habitacion (numero, tipo, estado) VALUES
('101', 'general', 'disponible'),
('102', 'general', 'disponible'),
('201', 'UCI', 'disponible'),
('301', 'emergencia', 'disponible'),
('103', 'general', 'mantenimiento');

INSERT INTO Usuario (nombreUsuario, contrasena, rol) VALUES
('admin', 'admin123', 'admin'),
('recepcionista1', 'recepcion123', 'recepcionista'),
('medico1', 'medico123', 'medico'),
('enfermera1', 'enfermera123', 'enfermera');

INSERT INTO Cita (idPaciente, idMedico, fecha, hora, motivo, estado) VALUES
(1, 1, '2024-02-01', '09:00:00', 'Consulta general', 'programada'),
(2, 2, '2024-02-01', '10:30:00', 'Control pediatrico', 'programada'),
(3, 1, '2024-02-02', '14:00:00', 'Revision cardiologica', 'programada');
