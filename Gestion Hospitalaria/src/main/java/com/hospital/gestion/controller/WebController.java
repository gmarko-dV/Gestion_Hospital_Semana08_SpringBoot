package com.hospital.gestion.controller;

import com.hospital.gestion.entity.Paciente;
import com.hospital.gestion.entity.Medico;
import com.hospital.gestion.entity.Cita;
import com.hospital.gestion.entity.Especialidad;
import com.hospital.gestion.service.PacienteService;
import com.hospital.gestion.service.MedicoService;
import com.hospital.gestion.service.CitaService;
import com.hospital.gestion.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class WebController {
    
    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private MedicoService medicoService;
    
    @Autowired
    private CitaService citaService;
    
    @Autowired
    private EspecialidadService especialidadService;
    
    // Página principal
    @GetMapping("/dashboard")
    public String index(Model model) {
        try {
            model.addAttribute("totalPacientes", pacienteService.obtenerPacientesActivos().size());
            model.addAttribute("totalMedicos", medicoService.obtenerMedicosActivos().size());
            model.addAttribute("citasHoy", citaService.obtenerCitasDelDia().size());
        } catch (Exception e) {
            model.addAttribute("totalPacientes", 0);
            model.addAttribute("totalMedicos", 0);
            model.addAttribute("citasHoy", 0);
            model.addAttribute("error", "Error al cargar estadísticas: " + e.getMessage());
        }
        return "index";
    }
    
    // ========== GESTIÓN DE PACIENTES ==========
    // Los métodos de pacientes se han movido a PacienteController
    
    // ========== GESTIÓN DE MÉDICOS ==========
    
    @GetMapping("/medicos")
    public String listarMedicos(Model model) {
        List<Medico> medicos = medicoService.obtenerMedicosActivos();
        model.addAttribute("medicos", medicos);
        return "medicos/lista";
    }
    
    @GetMapping("/medicos/nuevo")
    public String mostrarFormularioMedico(Model model) {
        model.addAttribute("medico", new Medico());
        List<Especialidad> especialidades = especialidadService.obtenerTodasLasEspecialidades();
        model.addAttribute("especialidades", especialidades);
        return "medicos/formulario";
    }
    
    @PostMapping("/medicos/guardar")
    public String guardarMedico(@ModelAttribute Medico medico, RedirectAttributes redirectAttributes) {
        try {
            medicoService.crearMedico(medico);
            redirectAttributes.addFlashAttribute("mensaje", "Médico registrado exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al registrar médico: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/medicos";
    }
    
    // ========== GESTIÓN DE CITAS ==========
    
    @GetMapping("/citas")
    public String listarCitas(Model model) {
        List<Cita> citas = citaService.obtenerTodasLasCitas();
        model.addAttribute("citas", citas);
        return "citas/lista";
    }
    
    @GetMapping("/citas/nueva")
    public String mostrarFormularioCita(Model model) {
        model.addAttribute("cita", new Cita());
        List<Paciente> pacientes = pacienteService.obtenerPacientesActivos();
        List<Medico> medicos = medicoService.obtenerMedicosActivos();
        model.addAttribute("pacientes", pacientes);
        model.addAttribute("medicos", medicos);
        return "citas/formulario";
    }
    
    @PostMapping("/citas/guardar")
    public String guardarCita(@ModelAttribute Cita cita, RedirectAttributes redirectAttributes) {
        try {
            citaService.crearCita(cita);
            redirectAttributes.addFlashAttribute("mensaje", "Cita programada exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al programar cita: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/citas";
    }
    
    @GetMapping("/citas/cancelar/{id}")
    public String cancelarCita(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            citaService.cancelarCita(id);
            redirectAttributes.addFlashAttribute("mensaje", "Cita cancelada exitosamente");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al cancelar cita: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/citas";
    }
    
    @GetMapping("/citas/atender/{id}")
    public String atenderCita(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            citaService.marcarCitaComoAtendida(id);
            redirectAttributes.addFlashAttribute("mensaje", "Cita marcada como atendida");
            redirectAttributes.addFlashAttribute("tipo", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al marcar cita como atendida: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipo", "danger");
        }
        return "redirect:/citas";
    }
}
