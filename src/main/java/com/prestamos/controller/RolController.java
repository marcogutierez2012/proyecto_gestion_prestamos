package com.prestamos.controller;

import com.prestamos.model.Rol;
import com.prestamos.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;


    @GetMapping
    public String mostrarTodos(Model model) {
        List<Rol> roles = rolService.obtenerTodos();
        model.addAttribute("roles", roles);
        return "roles-list";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("rol", new Rol());
        return "formulario-rol";
    }

    @PostMapping("/crear")
    public String crearRol(@ModelAttribute Rol rol) {
        rolService.guardar(rol);
        return "redirect:/roles";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Rol rol = rolService.obtenerPorId(id);
        model.addAttribute("rol", rol);
        return "formulario-rol";
    }

    @PostMapping("/editar/{id}")
    public String actualizarRol(@PathVariable Integer id, @ModelAttribute Rol rol) {
        rol.setIdRol(id); // Asegurarse de que el rol tenga el ID correcto
        rolService.actualizar(rol);
        return "redirect:/roles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRol(@PathVariable Integer id) {
        rolService.eliminar(id);
        return "redirect:/roles";
    }
}
