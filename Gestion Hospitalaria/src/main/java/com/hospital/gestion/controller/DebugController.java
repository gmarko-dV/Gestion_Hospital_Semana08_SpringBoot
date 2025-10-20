package com.hospital.gestion.controller;

import com.hospital.gestion.entity.Paciente;
import com.hospital.gestion.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DebugController {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @GetMapping("/debug/pacientes")
    @ResponseBody
    public String debugPacientes() {
        try {
            List<Paciente> pacientes = pacienteRepository.findAll();
            return "Total de pacientes: " + pacientes.size() + "\n" +
                   "Pacientes: " + pacientes.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage() + "\n" +
                   "Stack trace: " + e.getStackTrace()[0].toString();
        }
    }
    
    @GetMapping("/debug/test")
    @ResponseBody
    public String test() {
        return "Debug controller funcionando correctamente";
    }
}
