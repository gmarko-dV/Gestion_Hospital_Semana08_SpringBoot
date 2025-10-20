package com.hospital.gestion.service;

import com.hospital.gestion.entity.Cita;
import com.hospital.gestion.entity.Paciente;
import com.hospital.gestion.entity.Medico;
import com.hospital.gestion.repository.CitaRepository;
import com.hospital.gestion.repository.PacienteRepository;
import com.hospital.gestion.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CitaService {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    // Crear una nueva cita
    public Cita crearCita(Cita cita) {
        // Verificar que el paciente existe y está activo
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(cita.getPaciente().getIdPaciente());
        if (pacienteOpt.isEmpty()) {
            throw new RuntimeException("No se encontró el paciente con ID: " + cita.getPaciente().getIdPaciente());
        }
        if (pacienteOpt.get().getEstado() != Paciente.EstadoPaciente.activo) {
            throw new RuntimeException("El paciente no está activo");
        }
        
        // Verificar que el médico existe y está activo
        Optional<Medico> medicoOpt = medicoRepository.findById(cita.getMedico().getIdMedico());
        if (medicoOpt.isEmpty()) {
            throw new RuntimeException("No se encontró el médico con ID: " + cita.getMedico().getIdMedico());
        }
        if (medicoOpt.get().getEstado() != Medico.EstadoMedico.activo) {
            throw new RuntimeException("El médico no está activo");
        }
        
        // Verificar disponibilidad del médico en la fecha y hora
        List<Cita> citasExistentes = citaRepository.findByMedicoIdMedicoAndFecha(cita.getMedico().getIdMedico(), cita.getFecha());
        for (Cita citaExistente : citasExistentes) {
            if (citaExistente.getHora().equals(cita.getHora()) && 
                citaExistente.getEstado() == Cita.EstadoCita.programada) {
                throw new RuntimeException("El médico ya tiene una cita programada en esa fecha y hora");
            }
        }
        
        return citaRepository.save(cita);
    }
    
    // Obtener todas las citas
    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }
    
    // Obtener citas por paciente
    public List<Cita> obtenerCitasPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteIdPaciente(pacienteId);
    }
    
    // Obtener citas por médico
    public List<Cita> obtenerCitasPorMedico(Long medicoId) {
        return citaRepository.findByMedicoIdMedico(medicoId);
    }
    
    // Obtener citas por fecha
    public List<Cita> obtenerCitasPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha);
    }
    
    // Obtener citas por estado
    public List<Cita> obtenerCitasPorEstado(Cita.EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }
    
    // Obtener citas del día
    public List<Cita> obtenerCitasDelDia() {
        return citaRepository.findCitasDelDia(LocalDate.now());
    }
    
    // Obtener cita por ID
    public Optional<Cita> obtenerCitaPorId(Long id) {
        return citaRepository.findById(id);
    }
    
    // Actualizar cita
    public Cita actualizarCita(Cita cita) {
        if (!citaRepository.existsById(cita.getIdCita())) {
            throw new RuntimeException("No se encontró la cita con ID: " + cita.getIdCita());
        }
        return citaRepository.save(cita);
    }
    
    // Cancelar cita
    public void cancelarCita(Long id) {
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            if (cita.getEstado() == Cita.EstadoCita.atendida) {
                throw new RuntimeException("No se puede cancelar una cita ya atendida");
            }
            cita.setEstado(Cita.EstadoCita.cancelada);
            citaRepository.save(cita);
        } else {
            throw new RuntimeException("No se encontró la cita con ID: " + id);
        }
    }
    
    // Marcar cita como atendida
    public void marcarCitaComoAtendida(Long id) {
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            if (cita.getEstado() == Cita.EstadoCita.cancelada) {
                throw new RuntimeException("No se puede marcar como atendida una cita cancelada");
            }
            cita.setEstado(Cita.EstadoCita.atendida);
            citaRepository.save(cita);
        } else {
            throw new RuntimeException("No se encontró la cita con ID: " + id);
        }
    }
    
    // Reprogramar cita
    public Cita reprogramarCita(Long id, LocalDate nuevaFecha, LocalTime nuevaHora) {
        Optional<Cita> citaOpt = citaRepository.findById(id);
        if (citaOpt.isPresent()) {
            Cita cita = citaOpt.get();
            if (cita.getEstado() == Cita.EstadoCita.atendida) {
                throw new RuntimeException("No se puede reprogramar una cita ya atendida");
            }
            
            // Verificar disponibilidad del médico en la nueva fecha y hora
            List<Cita> citasExistentes = citaRepository.findByMedicoIdMedicoAndFecha(cita.getMedico().getIdMedico(), nuevaFecha);
            for (Cita citaExistente : citasExistentes) {
                if (citaExistente.getHora().equals(nuevaHora) && 
                    citaExistente.getEstado() == Cita.EstadoCita.programada &&
                    !citaExistente.getIdCita().equals(id)) {
                    throw new RuntimeException("El médico ya tiene una cita programada en esa fecha y hora");
                }
            }
            
            cita.setFecha(nuevaFecha);
            cita.setHora(nuevaHora);
            return citaRepository.save(cita);
        } else {
            throw new RuntimeException("No se encontró la cita con ID: " + id);
        }
    }
}
