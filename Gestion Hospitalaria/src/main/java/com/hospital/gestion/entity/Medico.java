package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "medico")
public class Medico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMedico")
    private Long idMedico;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @NotBlank(message = "La colegiatura es obligatoria")
    @Size(max = 20, message = "La colegiatura no puede exceder 20 caracteres")
    @Column(name = "colegiatura", nullable = false, unique = true, length = 20)
    private String colegiatura;
    
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @Email(message = "El formato del correo no es válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    @Column(name = "correo", length = 100)
    private String correo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMedico estado = EstadoMedico.activo;
    
    @CreationTimestamp
    @Column(name = "fechaRegistro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    // Relaciones
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicoEspecialidad> medicoEspecialidades;
    
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cita> citas;
    
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas;
    
    public enum EstadoMedico {
        activo, inactivo
    }
    
    // Constructores
    public Medico() {}
    
    public Medico(String nombres, String apellidos, String colegiatura) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.colegiatura = colegiatura;
    }
    
    // Getters y Setters
    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getColegiatura() { return colegiatura; }
    public void setColegiatura(String colegiatura) { this.colegiatura = colegiatura; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public EstadoMedico getEstado() { return estado; }
    public void setEstado(EstadoMedico estado) { this.estado = estado; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public List<MedicoEspecialidad> getMedicoEspecialidades() { return medicoEspecialidades; }
    public void setMedicoEspecialidades(List<MedicoEspecialidad> medicoEspecialidades) { this.medicoEspecialidades = medicoEspecialidades; }
    
    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
    
    public List<Consulta> getConsultas() { return consultas; }
    public void setConsultas(List<Consulta> consultas) { this.consultas = consultas; }
    
    // Método helper para obtener el nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}