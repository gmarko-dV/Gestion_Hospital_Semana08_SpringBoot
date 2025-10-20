package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bitacora")
public class Bitacora {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBitacora")
    private Long idBitacora;
    
    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @NotBlank(message = "La acción es obligatoria")
    @Size(max = 200, message = "La acción no puede exceder 200 caracteres")
    @Column(name = "accion", nullable = false, length = 200)
    private String accion;
    
    @CreationTimestamp
    @Column(name = "fechaHora", nullable = false, updatable = false)
    private LocalDateTime fechaHora;
    
    @Column(name = "detalles", columnDefinition = "TEXT")
    private String detalles;
    
    // Constructores
    public Bitacora() {}
    
    // Getters y Setters
    public Long getIdBitacora() { return idBitacora; }
    public void setIdBitacora(Long idBitacora) { this.idBitacora = idBitacora; }
    
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    
    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }
}