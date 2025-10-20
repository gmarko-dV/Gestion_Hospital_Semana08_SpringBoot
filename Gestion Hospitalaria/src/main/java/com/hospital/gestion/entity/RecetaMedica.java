package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recetamedica")
public class RecetaMedica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceta")
    private Long idReceta;
    
    @NotNull(message = "La consulta es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConsulta", nullable = false)
    private Consulta consulta;
    
    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;
    
    @CreationTimestamp
    @Column(name = "fechaEmision", nullable = false, updatable = false)
    private LocalDateTime fechaEmision;
    
    // Relaciones
    @OneToMany(mappedBy = "recetaMedica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleReceta> detallesReceta;
    
    // Constructores
    public RecetaMedica() {}
    
    // Getters y Setters
    public Long getIdReceta() { return idReceta; }
    public void setIdReceta(Long idReceta) { this.idReceta = idReceta; }
    
    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
    
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    
    public List<DetalleReceta> getDetallesReceta() { return detallesReceta; }
    public void setDetallesReceta(List<DetalleReceta> detallesReceta) { this.detallesReceta = detallesReceta; }
}