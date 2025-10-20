package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "especialidad")
public class Especialidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEspecialidad")
    private Long idEspecialidad;
    
    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    // Relaciones
    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicoEspecialidad> medicoEspecialidades;
    
    // Constructores
    public Especialidad() {}
    
    public Especialidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public Long getIdEspecialidad() { return idEspecialidad; }
    public void setIdEspecialidad(Long idEspecialidad) { this.idEspecialidad = idEspecialidad; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public List<MedicoEspecialidad> getMedicoEspecialidades() { return medicoEspecialidades; }
    public void setMedicoEspecialidades(List<MedicoEspecialidad> medicoEspecialidades) { this.medicoEspecialidades = medicoEspecialidades; }
}