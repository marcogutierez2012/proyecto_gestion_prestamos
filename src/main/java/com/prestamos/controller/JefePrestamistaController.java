package com.prestamos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;
import com.prestamos.repository.UsuarioRepository;
import com.prestamos.repository.ZonaRepository;

import java.util.List;

@Controller
@RequestMapping("/prestamista")
public class JefePrestamistaController {
	
	@Autowired
	private ZonaRepository zonaRepo;
	
	@Autowired
	private UsuarioRepository usuRepo;
	
	@Autowired
	private BCryptPasswordEncoder encriptar;
	
	@GetMapping("/listado")
	public String homeJefePrestamista(@ModelAttribute Usuario usuario, Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario1 = usuRepo.findByNombres(username);

		Integer idUsuario = usuario1.getIdUsuario();
		usuario.setIdUsuarioLider(idUsuario);


		//List<Usuario> lstprestamistas = usuRepo.findAll();
		List<Usuario> lstprestamistas = usuRepo.findByIdUsuarioLiderAndEstado(idUsuario, 0);

		model.addAttribute("lstPrestamistas", lstprestamistas);
		return "prestamista-list";
	}
	
	@GetMapping("/vistaregistrar")
	public String vistaRegistrar(Model model) {
		model.addAttribute("lstZona", zonaRepo.findAll());
		return "nuevoJefe";
	}
	
	@PostMapping("/registrar")
	public String registrar(@ModelAttribute Usuario usuario) {
		Rol rol = new Rol();
		rol.setIdRol(5);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuarioJefe = usuRepo.findByNombres(username);
		
		usuario.setEstado(0);
		usuario.setIdRol(rol);
		usuario.setPassword(encriptar.encode(usuario.getPassword()));
		usuario.setIdUsuarioLider(usuarioJefe.getIdUsuario());
		
		usuRepo.save(usuario);
		return "redirect:/jefepresmista/listado";
	}
	
	@GetMapping("/vistaactualizar/{idUsuario}")
	public String vistaActualizar(Model model, @PathVariable int idUsuario) {
		Usuario usuario = usuRepo.findByIdUsuario(idUsuario);
		model.addAttribute("prestamista", usuario);
		model.addAttribute("lstZona", zonaRepo.findAll());
		return "prestamista-Actualizar";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(@ModelAttribute Usuario usuario) {
		Rol rol = new Rol();
		rol.setIdRol(5);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuarioJefe = usuRepo.findByNombres(username);
		Usuario usuarioPrestamista = usuRepo.findByIdUsuario(usuario.getIdUsuario());
		
		usuario.setEstado(0);
		usuario.setIdRol(rol);
		usuario.setPassword(usuarioPrestamista.getPassword());
		usuario.setIdUsuarioLider(usuarioJefe.getIdUsuario());
		
		usuRepo.save(usuario);
		
		return "redirect:/jefepresmista/listado";
	}
	
	@GetMapping("/eliminar/{idUsuario}")
	public String eliminar(@PathVariable int idUsuario) {
		Usuario usuario = usuRepo.findByIdUsuario(idUsuario);
		usuario.setEstado(1);
		usuRepo.save(usuario);
		return "redirect:/prestamista/listado";
	}
}
