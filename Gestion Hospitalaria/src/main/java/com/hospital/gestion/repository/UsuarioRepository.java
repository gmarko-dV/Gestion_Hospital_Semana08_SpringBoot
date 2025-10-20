package com.hospital.gestion.repository;

import com.hospital.gestion.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Buscar por nombre de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
    // Buscar por rol
    List<Usuario> findByRol(Usuario.RolUsuario rol);
    
    // Buscar usuarios activos
    List<Usuario> findByEstado(Usuario.EstadoUsuario estado);
    
    // Buscar por rol y estado
    List<Usuario> findByRolAndEstado(Usuario.RolUsuario rol, Usuario.EstadoUsuario estado);
    
    // Verificar si existe un nombre de usuario
    boolean existsByNombreUsuario(String nombreUsuario);
    
    // Buscar usuarios por nombre que contenga
    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario LIKE %:nombre%")
    List<Usuario> findByNombreUsuarioContaining(@Param("nombre") String nombre);
}
