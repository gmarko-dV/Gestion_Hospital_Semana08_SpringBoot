package com.hospital.gestion.repository;

import com.hospital.gestion.entity.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    
    // Buscar por paciente
    List<HistoriaClinica> findByPacienteIdPaciente(Long pacienteId);
    
    // Buscar la historia clínica principal de un paciente
    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente.idPaciente = :pacienteId ORDER BY h.fechaApertura ASC")
    List<HistoriaClinica> findByPacienteIdOrderByFechaApertura(@Param("pacienteId") Long pacienteId);
    
    // Obtener la primera historia clínica de un paciente
    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente.idPaciente = :pacienteId ORDER BY h.fechaApertura ASC LIMIT 1")
    Optional<HistoriaClinica> findFirstByPacienteIdOrderByFechaApertura(@Param("pacienteId") Long pacienteId);
}
