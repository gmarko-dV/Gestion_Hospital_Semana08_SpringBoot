package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "diagnostico")
public class Diagnostico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDiagnostico")
    private Long idDiagnostico;
    
    @NotNull(message = "La consulta es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConsulta", nullable = false)
    private Consulta consulta;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;
    
    @NotNull(message = "El tipo de diagnóstico es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoDiagnostico tipo;
    
    @CreationTimestamp
    @Column(name = "fechaDiagnostico", nullable = false, updatable = false)
    private LocalDateTime fechaDiagnostico;
    
    public enum TipoDiagnostico {
        presuntivo, definitivo
    }
    
    // Constructores
    public Diagnostico() {}
    
    // Getters y Setters
    public Long getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(Long idDiagnostico) { this.idDiagnostico = idDiagnostico; }
    
    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public TipoDiagnostico getTipo() { return tipo; }
    public void setTipo(TipoDiagnostico tipo) { this.tipo = tipo; }
    
    public LocalDateTime getFechaDiagnostico() { return fechaDiagnostico; }
    public void setFechaDiagnostico(LocalDateTime fechaDiagnostico) { this.fechaDiagnostico = fechaDiagnostico; }
}