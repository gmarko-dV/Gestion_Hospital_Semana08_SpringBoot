package com.hospital.gestion.repository;

import com.hospital.gestion.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    
    // Buscar por nombre
    Optional<Especialidad> findByNombre(String nombre);
    
    // Buscar por nombre que contenga
    @Query("SELECT e FROM Especialidad e WHERE e.nombre LIKE %:nombre%")
    List<Especialidad> findByNombreContaining(@Param("nombre") String nombre);
    
    // Verificar si existe una especialidad por nombre
    boolean existsByNombre(String nombre);
}
