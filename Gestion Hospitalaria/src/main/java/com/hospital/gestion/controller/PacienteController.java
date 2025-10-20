package com.hospital.gestion.controller;

import com.hospital.gestion.entity.Paciente;
import com.hospital.gestion.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    @GetMapping
    public String listarPacientes(Model model) {
        try {
            List<Paciente> pacientes = pacienteService.obtenerTodosLosPacientes();
            model.addAttribute("pacientes", pacientes);
            model.addAttribute("mensaje", "Se encontraron " + pacientes.size() + " pacientes");
            return "pacientes/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar pacientes: " + e.getMessage());
            model.addAttribute("pacientes", List.of());
            return "pacientes/lista";
        }
    }
}
