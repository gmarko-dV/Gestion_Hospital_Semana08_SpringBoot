package com.hospital.gestion.service;

import com.hospital.gestion.entity.Medico;
import com.hospital.gestion.entity.Especialidad;
import com.hospital.gestion.entity.MedicoEspecialidad;
import com.hospital.gestion.repository.MedicoRepository;
import com.hospital.gestion.repository.EspecialidadRepository;
import com.hospital.gestion.repository.MedicoEspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicoService {
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Autowired
    private MedicoEspecialidadRepository medicoEspecialidadRepository;
    
    // Crear un nuevo médico
    public Medico crearMedico(Medico medico) {
        // Verificar si la colegiatura ya existe
        if (medicoRepository.existsByColegiatura(medico.getColegiatura())) {
            throw new RuntimeException("Ya existe un médico con la colegiatura: " + medico.getColegiatura());
        }
        return medicoRepository.save(medico);
    }
    
    // Obtener todos los médicos
    public List<Medico> obtenerTodosLosMedicos() {
        return medicoRepository.findAll();
    }
    
    // Obtener médicos activos
    public List<Medico> obtenerMedicosActivos() {
        return medicoRepository.findByEstado(Medico.EstadoMedico.activo);
    }
    
    // Obtener médico por ID
    public Optional<Medico> obtenerMedicoPorId(Long id) {
        return medicoRepository.findById(id);
    }
    
    // Obtener médico por colegiatura
    public Optional<Medico> obtenerMedicoPorColegiatura(String colegiatura) {
        return medicoRepository.findByColegiatura(colegiatura);
    }
    
    // Buscar médicos por nombre
    public List<Medico> buscarMedicosPorNombre(String nombre) {
        return medicoRepository.findByNombreContaining(nombre);
    }
    
    // Obtener médicos por especialidad
    public List<Medico> obtenerMedicosPorEspecialidad(Long idEspecialidad) {
        return medicoRepository.findByEspecialidad(idEspecialidad);
    }
    
    // Actualizar médico
    public Medico actualizarMedico(Medico medico) {
        if (!medicoRepository.existsById(medico.getIdMedico())) {
            throw new RuntimeException("No se encontró el médico con ID: " + medico.getIdMedico());
        }
        return medicoRepository.save(medico);
    }
    
    // Desactivar médico
    public void desactivarMedico(Long id) {
        Optional<Medico> medicoOpt = medicoRepository.findById(id);
        if (medicoOpt.isPresent()) {
            Medico medico = medicoOpt.get();
            medico.setEstado(Medico.EstadoMedico.inactivo);
            medicoRepository.save(medico);
        } else {
            throw new RuntimeException("No se encontró el médico con ID: " + id);
        }
    }
    
    // Activar médico
    public void activarMedico(Long id) {
        Optional<Medico> medicoOpt = medicoRepository.findById(id);
        if (medicoOpt.isPresent()) {
            Medico medico = medicoOpt.get();
            medico.setEstado(Medico.EstadoMedico.activo);
            medicoRepository.save(medico);
        } else {
            throw new RuntimeException("No se encontró el médico con ID: " + id);
        }
    }
    
    // Asignar especialidad a médico
    public void asignarEspecialidad(Long idMedico, Long idEspecialidad) {
        Optional<Medico> medicoOpt = medicoRepository.findById(idMedico);
        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(idEspecialidad);
        
        if (medicoOpt.isEmpty()) {
            throw new RuntimeException("No se encontró el médico con ID: " + idMedico);
        }
        if (especialidadOpt.isEmpty()) {
            throw new RuntimeException("No se encontró la especialidad con ID: " + idEspecialidad);
        }
        
        // Verificar si ya existe la asignación
        boolean existeAsignacion = medicoEspecialidadRepository.existsByMedicoIdAndEspecialidadId(idMedico, idEspecialidad);
        if (existeAsignacion) {
            throw new RuntimeException("El médico ya tiene asignada esta especialidad");
        }
        
        MedicoEspecialidad medicoEspecialidad = new MedicoEspecialidad();
        medicoEspecialidad.setMedico(medicoOpt.get());
        medicoEspecialidad.setEspecialidad(especialidadOpt.get());
        medicoEspecialidad.setFechaAsignacion(LocalDate.now());
        
        medicoEspecialidadRepository.save(medicoEspecialidad);
    }
    
    // Remover especialidad de médico
    public void removerEspecialidad(Long idMedico, Long idEspecialidad) {
        Optional<MedicoEspecialidad> medicoEspecialidadOpt = 
            medicoEspecialidadRepository.findByMedicoIdAndEspecialidadId(idMedico, idEspecialidad);
        
        if (medicoEspecialidadOpt.isPresent()) {
            medicoEspecialidadRepository.delete(medicoEspecialidadOpt.get());
        } else {
            throw new RuntimeException("No se encontró la asignación de especialidad");
        }
    }
    
    // Verificar si existe una colegiatura
    public boolean existeColegiatura(String colegiatura) {
        return medicoRepository.existsByColegiatura(colegiatura);
    }
}
