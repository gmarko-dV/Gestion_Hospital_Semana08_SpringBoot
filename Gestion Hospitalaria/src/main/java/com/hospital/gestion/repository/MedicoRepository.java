package com.hospital.gestion.repository;

import com.hospital.gestion.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
    // Buscar por colegiatura
    Optional<Medico> findByColegiatura(String colegiatura);
    
    // Buscar por nombre o apellido
    @Query("SELECT m FROM Medico m WHERE m.nombres LIKE %:nombre% OR m.apellidos LIKE %:nombre%")
    List<Medico> findByNombreContaining(@Param("nombre") String nombre);
    
    // Buscar médicos activos
    List<Medico> findByEstado(Medico.EstadoMedico estado);
    
    // Verificar si existe una colegiatura
    boolean existsByColegiatura(String colegiatura);
    
    // Buscar médicos por especialidad
    @Query("SELECT DISTINCT m FROM Medico m JOIN m.medicoEspecialidades me JOIN me.especialidad e WHERE e.idEspecialidad = :idEspecialidad")
    List<Medico> findByEspecialidad(@Param("idEspecialidad") Long idEspecialidad);
}
