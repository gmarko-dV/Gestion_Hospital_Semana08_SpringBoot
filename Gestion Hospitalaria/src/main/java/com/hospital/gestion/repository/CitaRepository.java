package com.hospital.gestion.repository;

import com.hospital.gestion.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    // Buscar citas por paciente
    List<Cita> findByPacienteIdPaciente(Long pacienteId);
    
    // Buscar citas por médico
    List<Cita> findByMedicoIdMedico(Long medicoId);
    
    // Buscar citas por fecha
    List<Cita> findByFecha(LocalDate fecha);
    
    // Buscar citas por estado
    List<Cita> findByEstado(Cita.EstadoCita estado);
    
    // Buscar citas por paciente y estado
    List<Cita> findByPacienteIdPacienteAndEstado(Long pacienteId, Cita.EstadoCita estado);
    
    // Buscar citas por médico y fecha
    List<Cita> findByMedicoIdMedicoAndFecha(Long medicoId, LocalDate fecha);
    
    // Buscar citas entre fechas
    @Query("SELECT c FROM Cita c WHERE c.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Cita> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    // Buscar citas del día
    @Query("SELECT c FROM Cita c WHERE c.fecha = :fecha ORDER BY c.hora")
    List<Cita> findCitasDelDia(@Param("fecha") LocalDate fecha);
}
