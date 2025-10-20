package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Entity
@Table(name = "habitacion")
public class Habitacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHabitacion")
    private Long idHabitacion;
    
    @NotBlank(message = "El número de habitación es obligatorio")
    @Column(name = "numero", nullable = false, unique = true, length = 10)
    private String numero;
    
    @NotNull(message = "El tipo de habitación es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoHabitacion tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoHabitacion estado = EstadoHabitacion.disponible;
    
    @Positive(message = "La capacidad debe ser mayor a 0")
    @Column(name = "capacidad")
    private Integer capacidad = 1;
    
    // Relaciones
    @OneToMany(mappedBy = "habitacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hospitalizacion> hospitalizaciones;
    
    public enum TipoHabitacion {
        UCI, general, emergencia
    }
    
    public enum EstadoHabitacion {
        disponible, ocupada, mantenimiento
    }
    
    // Constructores
    public Habitacion() {}
    
    // Getters y Setters
    public Long getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(Long idHabitacion) { this.idHabitacion = idHabitacion; }
    
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    
    public TipoHabitacion getTipo() { return tipo; }
    public void setTipo(TipoHabitacion tipo) { this.tipo = tipo; }
    
    public EstadoHabitacion getEstado() { return estado; }
    public void setEstado(EstadoHabitacion estado) { this.estado = estado; }
    
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
    
    public List<Hospitalizacion> getHospitalizaciones() { return hospitalizaciones; }
    public void setHospitalizaciones(List<Hospitalizacion> hospitalizaciones) { this.hospitalizaciones = hospitalizaciones; }
}