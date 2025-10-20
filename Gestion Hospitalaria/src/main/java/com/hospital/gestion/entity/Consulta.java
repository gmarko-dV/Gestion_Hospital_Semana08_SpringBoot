package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "consulta")
public class Consulta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConsulta")
    private Long idConsulta;
    
    @NotNull(message = "La cita es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCita", nullable = false)
    private Cita cita;
    
    @NotNull(message = "El médico es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMedico", nullable = false)
    private Medico medico;
    
    @NotNull(message = "El paciente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;
    
    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    @Column(name = "hora", nullable = false)
    private LocalTime hora;
    
    @Size(max = 200, message = "El motivo de consulta no puede exceder 200 caracteres")
    @Column(name = "motivoConsulta", length = 200)
    private String motivoConsulta;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    // Relaciones
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Diagnostico> diagnosticos;
    
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecetaMedica> recetasMedicas;
    
    // Constructores
    public Consulta() {}
    
    // Getters y Setters
    public Long getIdConsulta() { return idConsulta; }
    public void setIdConsulta(Long idConsulta) { this.idConsulta = idConsulta; }
    
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
    
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    
    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public List<Diagnostico> getDiagnosticos() { return diagnosticos; }
    public void setDiagnosticos(List<Diagnostico> diagnosticos) { this.diagnosticos = diagnosticos; }
    
    public List<RecetaMedica> getRecetasMedicas() { return recetasMedicas; }
    public void setRecetasMedicas(List<RecetaMedica> recetasMedicas) { this.recetasMedicas = recetasMedicas; }
}