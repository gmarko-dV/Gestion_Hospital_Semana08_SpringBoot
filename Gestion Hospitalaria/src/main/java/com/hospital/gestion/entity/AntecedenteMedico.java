package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "antecedentemedico")
public class AntecedenteMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAntecedente")
    private Long idAntecedente;
    
    @NotNull(message = "La historia clínica es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHistoria", nullable = false)
    private HistoriaClinica historiaClinica;
    
    @NotNull(message = "El tipo de antecedente es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAntecedente tipo;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;
    
    @CreationTimestamp
    @Column(name = "fechaRegistro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    public enum TipoAntecedente {
        alergias, enfermedades_previas, cirugias, otros
    }
    
    // Constructores
    public AntecedenteMedico() {}
    
    // Getters y Setters
    public Long getIdAntecedente() { return idAntecedente; }
    public void setIdAntecedente(Long idAntecedente) { this.idAntecedente = idAntecedente; }
    
    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }
    public void setHistoriaClinica(HistoriaClinica historiaClinica) { this.historiaClinica = historiaClinica; }
    
    public TipoAntecedente getTipo() { return tipo; }
    public void setTipo(TipoAntecedente tipo) { this.tipo = tipo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}