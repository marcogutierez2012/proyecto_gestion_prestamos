package com.prestamos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.prestamos.model.Cuota;
import com.prestamos.model.Usuario;
import com.prestamos.repository.CuotaRepository;
import com.prestamos.repository.UsuarioRepository;

@Controller
public class CuotaController {

	@Autowired
	private CuotaRepository cuotarepo;
	
	@Autowired
	private UsuarioRepository usurepo;
	
	@GetMapping("/cuotas-list-prestamista")
	public String listarCuotasPrestamista(Model model) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    
	    Usuario usuario = usurepo.findByUsername(username);
		
	    int idPrestamista = usuario.getIdUsuario();
	    
		List<Cuota> listacuotas = cuotarepo.findByIdSolicitudIdPrestamistaIdUsuarioAndEstado(idPrestamista, "PENDIENTE");
		
		model.addAttribute("lstCuotas", listacuotas);
		return "cuotas-list-prestamista";
	}
	
}
