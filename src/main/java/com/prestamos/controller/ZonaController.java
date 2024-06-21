package com.prestamos.controller;

import com.prestamos.model.Rol;
import com.prestamos.model.Zona;
import com.prestamos.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("zonas")
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @GetMapping
    public String mostrarTodos(Model model) {
        List<Zona> zonas = zonaService.obtenerTodos();
        model.addAttribute("zonas", zonas);
        return "zonas-list";
    }

}
