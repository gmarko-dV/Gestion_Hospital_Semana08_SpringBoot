package com.hospital.gestion.repository;

import com.hospital.gestion.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    
    // Buscar por número
    Optional<Habitacion> findByNumero(String numero);
    
    // Buscar por tipo
    List<Habitacion> findByTipo(Habitacion.TipoHabitacion tipo);
    
    // Buscar por estado
    List<Habitacion> findByEstado(Habitacion.EstadoHabitacion estado);
    
    // Buscar habitaciones disponibles por tipo
    @Query("SELECT h FROM Habitacion h WHERE h.tipo = :tipo AND h.estado = 'disponible'")
    List<Habitacion> findDisponiblesByTipo(@Param("tipo") Habitacion.TipoHabitacion tipo);
    
    // Verificar si existe una habitación por número
    boolean existsByNumero(String numero);
    
    // Contar habitaciones por estado
    long countByEstado(Habitacion.EstadoHabitacion estado);
}
