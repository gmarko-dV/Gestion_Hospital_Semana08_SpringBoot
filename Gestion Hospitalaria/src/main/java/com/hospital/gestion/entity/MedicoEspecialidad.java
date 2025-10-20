package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "medicoespecialidad")
public class MedicoEspecialidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMedicoEsp")
    private Long idMedicoEsp;
    
    @NotNull(message = "El médico es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMedico", nullable = false)
    private Medico medico;
    
    @NotNull(message = "La especialidad es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEspecialidad", nullable = false)
    private Especialidad especialidad;
    
    @Column(name = "fechaAsignacion")
    private LocalDate fechaAsignacion = LocalDate.now();
    
    // Constructores
    public MedicoEspecialidad() {}
    
    public MedicoEspecialidad(Medico medico, Especialidad especialidad) {
        this.medico = medico;
        this.especialidad = especialidad;
    }
    
    // Getters y Setters
    public Long getIdMedicoEsp() { return idMedicoEsp; }
    public void setIdMedicoEsp(Long idMedicoEsp) { this.idMedicoEsp = idMedicoEsp; }
    
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    
    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }
    
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
}