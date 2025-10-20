package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "historiaclinica")
public class HistoriaClinica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHistoria")
    private Long idHistoria;
    
    @NotNull(message = "El paciente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;
    
    @NotNull(message = "La fecha de apertura es obligatoria")
    @Column(name = "fechaApertura", nullable = false)
    private LocalDate fechaApertura;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    // Relaciones
    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AntecedenteMedico> antecedentesMedicos;
    
    // Constructores
    public HistoriaClinica() {}
    
    public HistoriaClinica(Paciente paciente, LocalDate fechaApertura, String observaciones) {
        this.paciente = paciente;
        this.fechaApertura = fechaApertura;
        this.observaciones = observaciones;
    }
    
    // Getters y Setters
    public Long getIdHistoria() { return idHistoria; }
    public void setIdHistoria(Long idHistoria) { this.idHistoria = idHistoria; }
    
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    
    public LocalDate getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(LocalDate fechaApertura) { this.fechaApertura = fechaApertura; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public List<AntecedenteMedico> getAntecedentesMedicos() { return antecedentesMedicos; }
    public void setAntecedentesMedicos(List<AntecedenteMedico> antecedentesMedicos) { this.antecedentesMedicos = antecedentesMedicos; }
}