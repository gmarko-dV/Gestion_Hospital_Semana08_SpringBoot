package com.hospital.gestion.service;

import com.hospital.gestion.entity.Paciente;
import com.hospital.gestion.entity.HistoriaClinica;
import com.hospital.gestion.repository.PacienteRepository;
import com.hospital.gestion.repository.HistoriaClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;
    
    // Crear un nuevo paciente con historia clínica automática
    public Paciente crearPaciente(Paciente paciente) {
        // Verificar si el DNI ya existe
        if (pacienteRepository.existsByDni(paciente.getDni())) {
            throw new RuntimeException("Ya existe un paciente con el DNI: " + paciente.getDni());
        }
        
        // Guardar el paciente
        Paciente pacienteGuardado = pacienteRepository.save(paciente);
        
        // Crear historia clínica automáticamente
        HistoriaClinica historiaClinica = new HistoriaClinica();
        historiaClinica.setPaciente(pacienteGuardado);
        historiaClinica.setFechaApertura(LocalDate.now());
        historiaClinica.setObservaciones("Historia clínica creada automáticamente");
        historiaClinicaRepository.save(historiaClinica);
        
        return pacienteGuardado;
    }
    
    // Obtener todos los pacientes
    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll();
    }
    
    // Obtener pacientes activos
    public List<Paciente> obtenerPacientesActivos() {
        return pacienteRepository.findByEstado(Paciente.EstadoPaciente.activo);
    }
    
    // Obtener paciente por ID
    public Optional<Paciente> obtenerPacientePorId(Long id) {
        return pacienteRepository.findById(id);
    }
    
    // Obtener paciente por DNI
    public Optional<Paciente> obtenerPacientePorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }
    
    // Buscar pacientes por nombre
    public List<Paciente> buscarPacientesPorNombre(String nombre) {
        return pacienteRepository.findByNombreContaining(nombre);
    }
    
    // Actualizar paciente
    public Paciente actualizarPaciente(Paciente paciente) {
        if (!pacienteRepository.existsById(paciente.getIdPaciente())) {
            throw new RuntimeException("No se encontró el paciente con ID: " + paciente.getIdPaciente());
        }
        return pacienteRepository.save(paciente);
    }
    
    // Desactivar paciente (cambio de estado)
    public void desactivarPaciente(Long id) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            paciente.setEstado(Paciente.EstadoPaciente.inactivo);
            pacienteRepository.save(paciente);
        } else {
            throw new RuntimeException("No se encontró el paciente con ID: " + id);
        }
    }
    
    // Activar paciente
    public void activarPaciente(Long id) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            paciente.setEstado(Paciente.EstadoPaciente.activo);
            pacienteRepository.save(paciente);
        } else {
            throw new RuntimeException("No se encontró el paciente con ID: " + id);
        }
    }
    
    // Eliminar paciente (solo si no tiene citas o hospitalizaciones)
    public void eliminarPaciente(Long id) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            // Verificar si tiene citas o hospitalizaciones activas
            if (!paciente.getCitas().isEmpty() || !paciente.getHospitalizaciones().isEmpty()) {
                throw new RuntimeException("No se puede eliminar el paciente porque tiene citas o hospitalizaciones asociadas");
            }
            pacienteRepository.delete(paciente);
        } else {
            throw new RuntimeException("No se encontró el paciente con ID: " + id);
        }
    }
    
    // Verificar si existe un DNI
    public boolean existeDni(String dni) {
        return pacienteRepository.existsByDni(dni);
    }
}
