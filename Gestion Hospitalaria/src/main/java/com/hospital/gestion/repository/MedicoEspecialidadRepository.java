package com.hospital.gestion.repository;

import com.hospital.gestion.entity.MedicoEspecialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoEspecialidadRepository extends JpaRepository<MedicoEspecialidad, Long> {
    
    // Buscar por médico
    List<MedicoEspecialidad> findByMedicoIdMedico(Long medicoId);
    
    // Buscar por especialidad
    List<MedicoEspecialidad> findByEspecialidadIdEspecialidad(Long especialidadId);
    
    // Buscar asignación específica
    @Query("SELECT me FROM MedicoEspecialidad me WHERE me.medico.idMedico = :medicoId AND me.especialidad.idEspecialidad = :especialidadId")
    Optional<MedicoEspecialidad> findByMedicoIdAndEspecialidadId(@Param("medicoId") Long medicoId, @Param("especialidadId") Long especialidadId);
    
    // Verificar si existe una asignación
    @Query("SELECT COUNT(me) > 0 FROM MedicoEspecialidad me WHERE me.medico.idMedico = :medicoId AND me.especialidad.idEspecialidad = :especialidadId")
    boolean existsByMedicoIdAndEspecialidadId(@Param("medicoId") Long medicoId, @Param("especialidadId") Long especialidadId);
}
