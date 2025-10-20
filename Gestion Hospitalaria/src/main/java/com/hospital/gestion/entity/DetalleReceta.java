package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "detallereceta")
public class DetalleReceta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleReceta")
    private Long idDetalleReceta;
    
    @NotNull(message = "La receta médica es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idReceta", nullable = false)
    private RecetaMedica recetaMedica;
    
    @NotBlank(message = "El medicamento es obligatorio")
    @Size(max = 200, message = "El medicamento no puede exceder 200 caracteres")
    @Column(name = "medicamento", nullable = false, length = 200)
    private String medicamento;
    
    @NotBlank(message = "La dosis es obligatoria")
    @Size(max = 100, message = "La dosis no puede exceder 100 caracteres")
    @Column(name = "dosis", nullable = false, length = 100)
    private String dosis;
    
    @NotBlank(message = "La frecuencia es obligatoria")
    @Size(max = 100, message = "La frecuencia no puede exceder 100 caracteres")
    @Column(name = "frecuencia", nullable = false, length = 100)
    private String frecuencia;
    
    @NotBlank(message = "La duración es obligatoria")
    @Size(max = 100, message = "La duración no puede exceder 100 caracteres")
    @Column(name = "duracion", nullable = false, length = 100)
    private String duracion;
    
    // Constructores
    public DetalleReceta() {}
    
    // Getters y Setters
    public Long getIdDetalleReceta() { return idDetalleReceta; }
    public void setIdDetalleReceta(Long idDetalleReceta) { this.idDetalleReceta = idDetalleReceta; }
    
    public RecetaMedica getRecetaMedica() { return recetaMedica; }
    public void setRecetaMedica(RecetaMedica recetaMedica) { this.recetaMedica = recetaMedica; }
    
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
}