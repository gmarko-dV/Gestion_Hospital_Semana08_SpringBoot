package com.hospital.gestion.service;

import com.hospital.gestion.entity.Especialidad;
import com.hospital.gestion.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EspecialidadService {
    
    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    // Crear una nueva especialidad
    public Especialidad crearEspecialidad(Especialidad especialidad) {
        // Verificar si el nombre ya existe
        if (especialidadRepository.existsByNombre(especialidad.getNombre())) {
            throw new RuntimeException("Ya existe una especialidad con el nombre: " + especialidad.getNombre());
        }
        return especialidadRepository.save(especialidad);
    }
    
    // Obtener todas las especialidades
    public List<Especialidad> obtenerTodasLasEspecialidades() {
        return especialidadRepository.findAll();
    }
    
    // Obtener especialidad por ID
    public Optional<Especialidad> obtenerEspecialidadPorId(Long id) {
        return especialidadRepository.findById(id);
    }
    
    // Obtener especialidad por nombre
    public Optional<Especialidad> obtenerEspecialidadPorNombre(String nombre) {
        return especialidadRepository.findByNombre(nombre);
    }
    
    // Buscar especialidades por nombre
    public List<Especialidad> buscarEspecialidadesPorNombre(String nombre) {
        return especialidadRepository.findByNombreContaining(nombre);
    }
    
    // Actualizar especialidad
    public Especialidad actualizarEspecialidad(Especialidad especialidad) {
        if (!especialidadRepository.existsById(especialidad.getIdEspecialidad())) {
            throw new RuntimeException("No se encontró la especialidad con ID: " + especialidad.getIdEspecialidad());
        }
        return especialidadRepository.save(especialidad);
    }
    
    // Eliminar especialidad
    public void eliminarEspecialidad(Long id) {
        if (!especialidadRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la especialidad con ID: " + id);
        }
        especialidadRepository.deleteById(id);
    }
    
    // Verificar si existe una especialidad por nombre
    public boolean existeNombre(String nombre) {
        return especialidadRepository.existsByNombre(nombre);
    }
}
