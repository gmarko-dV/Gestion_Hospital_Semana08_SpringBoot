package com.hospital.gestion.repository;

import com.hospital.gestion.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Buscar por DNI
    Optional<Paciente> findByDni(String dni);
    
    // Buscar por nombre o apellido
    @Query("SELECT p FROM Paciente p WHERE p.nombres LIKE %:nombre% OR p.apellidos LIKE %:nombre%")
    List<Paciente> findByNombreContaining(@Param("nombre") String nombre);
    
    // Buscar pacientes activos
    List<Paciente> findByEstado(Paciente.EstadoPaciente estado);
    
    // Verificar si existe un DNI
    boolean existsByDni(String dni);
    
    // Buscar por sexo
    List<Paciente> findBySexo(Paciente.Sexo sexo);
}
