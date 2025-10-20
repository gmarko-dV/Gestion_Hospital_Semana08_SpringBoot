package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "cita")
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCita")
    private Long idCita;
    
    @NotNull(message = "El paciente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;
    
    @NotNull(message = "El médico es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMedico", nullable = false)
    private Medico medico;
    
    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    @Column(name = "hora", nullable = false)
    private LocalTime hora;
    
    @Size(max = 200, message = "El motivo no puede exceder 200 caracteres")
    @Column(name = "motivo", length = 200)
    private String motivo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCita estado = EstadoCita.programada;
    
    @CreationTimestamp
    @Column(name = "fechaRegistro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    // Relaciones
    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;
    
    public enum EstadoCita {
        programada, atendida, cancelada
    }
    
    // Constructores
    public Cita() {}
    
    public Cita(Paciente paciente, Medico medico, LocalDate fecha, LocalTime hora, String motivo) {
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }
    
    // Getters y Setters
    public Long getIdCita() { return idCita; }
    public void setIdCita(Long idCita) { this.idCita = idCita; }
    
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    
    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public List<Consulta> getConsultas() { return consultas; }
    public void setConsultas(List<Consulta> consultas) { this.consultas = consultas; }
}