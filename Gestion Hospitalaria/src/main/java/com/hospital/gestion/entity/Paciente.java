package com.hospital.gestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "paciente")
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPaciente")
    private Long idPaciente;
    
    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    @Column(name = "dni", unique = true, nullable = false, length = 8)
    private String dni;
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden exceder 100 caracteres")
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @NotNull(message = "El sexo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;
    
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Column(name = "direccion", length = 200)
    private String direccion;
    
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @Email(message = "El formato del correo no es válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    @Column(name = "correo", length = 100)
    private String correo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPaciente estado = EstadoPaciente.activo;
    
    @CreationTimestamp
    @Column(name = "fechaRegistro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;
    
    // Relaciones
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoriaClinica> historiasClinicas;
    
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cita> citas;
    
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hospitalizacion> hospitalizaciones;
    
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Factura> facturas;
    
    public enum Sexo {
        M, F
    }
    
    public enum EstadoPaciente {
        activo, inactivo
    }
    
    // Constructores
    public Paciente() {}
    
    public Paciente(String dni, String nombres, String apellidos, LocalDate fechaNacimiento, Sexo sexo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
    }
    
    // Getters y Setters
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public EstadoPaciente getEstado() { return estado; }
    public void setEstado(EstadoPaciente estado) { this.estado = estado; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public List<HistoriaClinica> getHistoriasClinicas() { return historiasClinicas; }
    public void setHistoriasClinicas(List<HistoriaClinica> historiasClinicas) { this.historiasClinicas = historiasClinicas; }
    
    public List<Cita> getCitas() { return citas; }
    public void setCitas(List<Cita> citas) { this.citas = citas; }
    
    public List<Hospitalizacion> getHospitalizaciones() { return hospitalizaciones; }
    public void setHospitalizaciones(List<Hospitalizacion> hospitalizaciones) { this.hospitalizaciones = hospitalizaciones; }
    
    public List<Factura> getFacturas() { return facturas; }
    public void setFacturas(List<Factura> facturas) { this.facturas = facturas; }
    
    // Método helper para obtener el nombre completo
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}