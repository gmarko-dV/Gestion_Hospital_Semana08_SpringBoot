package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "hospitalizacion")
public class Hospitalizacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHosp")
    private Long idHosp;
    
    @NotNull(message = "El paciente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;
    
    @NotNull(message = "La habitación es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHabitacion", nullable = false)
    private Habitacion habitacion;
    
    @CreationTimestamp
    @Column(name = "fechaIngreso", nullable = false, updatable = false)
    private LocalDateTime fechaIngreso;
    
    @Column(name = "fechaAlta")
    private LocalDateTime fechaAlta;
    
    @Column(name = "diagnosticoIngreso", columnDefinition = "TEXT")
    private String diagnosticoIngreso;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoHospitalizacion estado = EstadoHospitalizacion.activo;
    
    public enum EstadoHospitalizacion {
        activo, dado_de_alta
    }
    
    // Constructores
    public Hospitalizacion() {}
    
    // Getters y Setters
    public Long getIdHosp() { return idHosp; }
    public void setIdHosp(Long idHosp) { this.idHosp = idHosp; }
    
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    
    public Habitacion getHabitacion() { return habitacion; }
    public void setHabitacion(Habitacion habitacion) { this.habitacion = habitacion; }
    
    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDateTime fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    
    public LocalDateTime getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDateTime fechaAlta) { this.fechaAlta = fechaAlta; }
    
    public String getDiagnosticoIngreso() { return diagnosticoIngreso; }
    public void setDiagnosticoIngreso(String diagnosticoIngreso) { this.diagnosticoIngreso = diagnosticoIngreso; }
    
    public EstadoHospitalizacion getEstado() { return estado; }
    public void setEstado(EstadoHospitalizacion estado) { this.estado = estado; }
}