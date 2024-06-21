package com.prestamos.controller;

import java.util.List;

import com.prestamos.model.Zona;
import com.prestamos.repository.CuotaRepository;
import com.prestamos.repository.PrestatarioRepository;
import com.prestamos.repository.UsuarioRepository;
import com.prestamos.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.prestamos.model.Cuota;
import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;
import com.prestamos.service.PrestatarioService;
import com.prestamos.service.RolService;


@Controller
public class PrestatarioController {

	@Autowired
    private PrestatarioService prestatarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestatarioRepository prestatarioRepository;
    
    @Autowired
    private CuotaRepository cuotarepo;

    @GetMapping("prestatario-list")
    public String mostrarTodos(@ModelAttribute Usuario usuario, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario prestamista = usuarioRepository.findByUsername(username);

        Integer idUsuario = prestamista.getIdUsuario();
        usuario.setIdUsuarioLider(idUsuario);


        List<Rol> roles = rolService.obtenerTodos();
        List<Usuario> prestatarios = prestatarioRepository.findByIdRolDescripcionAndIdUsuarioLiderAndEstado("Prestatario", idUsuario, 0);

        model.addAttribute("roles", roles);
        model.addAttribute("prestatarios", prestatarios);
        return "prestatario-list";
    }

    @GetMapping("/prestatario-crear")
    public String mostrarFormularioCrear(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario prestamista = usuarioRepository.findByUsername(username);

        // Obtener la zona del prestamista
        Zona zonaPrestamista = prestamista.getIdZona();

        // Agregar la zona del prestamista al modelo
        model.addAttribute("zonaPrestamista", zonaPrestamista);

        List<Zona> zonas = zonaService.obtenerTodos();

        // Filtrar los roles para obtener solo el de "prestatario"
        Rol rolPrestatario = rolService.obtenerRolPrestatario(6);

        model.addAttribute("nombrePrestamista", username);
        model.addAttribute("zonas", zonas);
        // Agregar el rol al modelo
        model.addAttribute("rolPrestatario", rolPrestatario);
        model.addAttribute("prestatario", new Usuario());
        return "prestatario-crear";
    }

    @PostMapping("/prestatario/crear")
    public String crearPrestamista(@ModelAttribute Usuario usuario, @RequestParam("idZona") int idZona, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario prestamista = usuarioRepository.findByUsername(username);

        // Encriptar la contraseña
        String passwordEncriptado = encriptarPassword(usuario.getPassword());
        usuario.setPassword(passwordEncriptado);


        int idPrestamista = prestamista.getIdUsuario();
        usuario.setIdUsuarioLider(idPrestamista);

        // Obtener el rol del prestatario
        Rol rolPrestatario = rolService.obtenerRolPrestatario(6);
        // Asignar el rol al usuario
        usuario.setIdRol(rolPrestatario);

        // Asignar el ID de la zona seleccionada al usuario
        /*Zona zonaSeleccionada = new Zona();
        zonaSeleccionada.setIdZona(idZona);
        usuario.setIdZona(zonaSeleccionada);*/



        prestatarioService.guardar(usuario);
        return "redirect:/prestatario-list";
    }

    // Método para encriptar la contraseña
    private String encriptarPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    @GetMapping("/prestatario/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Usuario usuario = prestatarioService.obtenerPorId(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario prestamista = usuarioRepository.findByUsername(username);

        // Obtener la zona del prestamista
        Zona zonaPrestamista = prestamista.getIdZona();

        // Agregar la zona del prestamista al modelo
        model.addAttribute("zonaPrestamista", zonaPrestamista);

        //List<Zona> zonas = zonaService.obtenerTodos();

        // Filtrar los roles para obtener solo el de "prestatario"
        Rol rolPrestatario = rolService.obtenerRolPrestatario(6);

        model.addAttribute("nombrePrestamista", username);
        //model.addAttribute("zonas", zonas);
        // Agregar el rol al modelo
        model.addAttribute("rolPrestatario", rolPrestatario);
        model.addAttribute("prestatario", new Usuario());
        model.addAttribute("usuario", usuario);
        return "prestatario-actualizar";
    }

    @PostMapping("/prestatario/editar/{id}")
    public String actualizarPrestamista(@PathVariable Integer id, @ModelAttribute Usuario usuario, @RequestParam("idZona") int idZona, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario prestamista = usuarioRepository.findByUsername(username);

        // Encriptar la contraseña
        String passwordEncriptado = encriptarPassword(usuario.getPassword());
        usuario.setPassword(passwordEncriptado);


        int idPrestamista = prestamista.getIdUsuario();
        usuario.setIdUsuarioLider(idPrestamista);

        // Obtener el rol del prestatario
        Rol rolPrestatario = rolService.obtenerRolPrestatario(6);
        // Asignar el rol al usuario
        usuario.setIdRol(rolPrestatario);

        // Asignar el ID de la zona seleccionada al usuario
        /*Zona zonaSeleccionada = new Zona();
        zonaSeleccionada.setIdZona(idZona);
        usuario.setIdZona(zonaSeleccionada);*/



        prestatarioService.guardar(usuario);
        return "redirect:/prestatario-list";
    }
    
    @GetMapping("/prestatario/eliminar/{id}")
    public String eliminarPrestamista(@PathVariable Integer id) {
    	Usuario eliminar = usuarioRepository.findByIdUsuario(id);
        eliminar.setEstado(1);
        usuarioRepository.save(eliminar);
        return "redirect:/prestatario-list";
    }

    @GetMapping("/prestatario-search")
    public String buscarPorNombreYRol(String nombres, Model model) {
        List<Usuario> personas = prestatarioService.buscarPorNombreYRol(nombres);
        model.addAttribute("personas", personas);
        return "prestatario-search"; // nombre de la plantilla Thymeleaf
    }

    @GetMapping("/prestatario-search1")
    public String buscarPorAtributos(
            String nombres,
            String apePaterno,
            String apeMaterno,
            String email,
            String telefono,
            String dni,
            Model model) {

        List<Usuario> usuarios = prestatarioService.buscarPorAtributos(nombres, apePaterno, apeMaterno, email, telefono, dni);
        model.addAttribute("usuarios", usuarios);
        return "prestatario-search";
    }

    @GetMapping("/prestatario-search2")
    public String buscarPorAtributosP(
            String nombres,
            String apePaterno,
            String apeMaterno,
            String email,
            String telefono,
            String dni,
            Model model) {

        // Obtener el nombre del prestamista logueado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Obtener el prestamista logueado desde la base de datos
        Usuario prestamista = usuarioRepository.findByUsername(username);
        Integer idUsuario = prestamista.getIdUsuario();

        List<Usuario> usuarios = prestatarioService.buscarPorAtributosP(nombres, apePaterno, apeMaterno, email, telefono, dni, idUsuario);
        model.addAttribute("usuarios", usuarios);
        return "prestatario-search";
    }

    @PostMapping("/prestatario/cambiarEstado/{idUsuario}")
    public String cambiarEstado(@PathVariable int idUsuario) {
        prestatarioService.cambiarEstado(idUsuario); // Cambiar el estado de la entidad
        return "redirect:/prestatario-list"; // Redirigir a la página de listado
    }
	
	@GetMapping("/cuotas-list-prestatario-pendiente")
	public String listaCuotasPrestatario(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();		
	    Usuario usuario = usuarioRepository.findByUsername(username);
		
		int idPrestatario = usuario.getIdUsuario();
		
		List<Cuota> cuotas = cuotarepo.findByIdSolicitudIdPrestatarioIdUsuarioAndEstado(idPrestatario, "PENDIENTE");
		
		model.addAttribute("lstCuotas", cuotas);
	    
		return "cuotas-list-prestatario-pendiente";
	}
}
