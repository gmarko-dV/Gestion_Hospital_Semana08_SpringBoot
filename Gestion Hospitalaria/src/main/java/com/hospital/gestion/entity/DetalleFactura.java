package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "detallefactura")
public class DetalleFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleFactura")
    private Long idDetalleFactura;
    
    @NotNull(message = "La factura es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFactura", nullable = false)
    private Factura factura;
    
    @NotNull(message = "El concepto es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "concepto", nullable = false)
    private ConceptoFactura concepto;
    
    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    
    public enum ConceptoFactura {
        consulta, medicamento, procedimiento, hospitalizacion, laboratorio
    }
    
    // Constructores
    public DetalleFactura() {}
    
    // Getters y Setters
    public Long getIdDetalleFactura() { return idDetalleFactura; }
    public void setIdDetalleFactura(Long idDetalleFactura) { this.idDetalleFactura = idDetalleFactura; }
    
    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }
    
    public ConceptoFactura getConcepto() { return concepto; }
    public void setConcepto(ConceptoFactura concepto) { this.concepto = concepto; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
}