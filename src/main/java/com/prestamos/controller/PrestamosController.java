package com.prestamos.controller;

import java.util.Date;
import java.util.List;

import com.prestamos.model.Solicitud;
import com.prestamos.model.Usuario;
import com.prestamos.repository.SolicitudRepository;
import com.prestamos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.prestamos.repository.PrestamosRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PrestamosController {
	

	@Autowired
	private PrestamosRepository prepo;

	@Autowired
	private SolicitudRepository solrepo;

	@Autowired
	private UsuarioRepository usurepo;


	
	@GetMapping("/prestamos")

	public String cargar(Model model) {
		
		model.addAttribute("solicitud", new Solicitud());
		
	    List<Object[]> resultados = prepo.obtenerCalculoMontos();
	    

	    
	    // Imprimir los resultados y el interés
	    for (Object[] resultado : resultados) {
	        Integer dias = (Integer) resultado[0];
	        Double monto1 = (Double) resultado[1];
	        Double monto2 = (Double) resultado[2];
	        Double monto3 = (Double) resultado[3];
	        Double monto4 = (Double) resultado[4];
	        Double monto5 = (Double) resultado[5];
	        Integer interes =(Integer) resultado[6];
	        
	        System.out.println("Días: " + dias);
	        System.out.println("Monto 1: " + monto1);
	        System.out.println("Monto 2: " + monto2);
	        System.out.println("Monto 3: " + monto3);
	        System.out.println("Monto 4: " + monto4);
	        System.out.println("Monto 5: " + monto5);
	        System.out.println("Interes : " + interes);
	    }

	    
	    // Agregar resultados e interés al modelo
	    model.addAttribute("resultados", resultados);
	 
	    
	    return "prestamos";

	}


	//metodo para registrar solicitud
	@PostMapping("/solicitarprestamo")
	public String solicitarPrestamo(@ModelAttribute Solicitud solicitud,
									@RequestParam("fecInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecInicio,
									@RequestParam("fecFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecFin) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = usurepo.findByUsername(username);
		int idPrestamista = usuario.getIdUsuarioLider();
		Usuario prestamista = usurepo.findByIdUsuario(idPrestamista);

		Date fechaActual = new Date();

		solicitud.setFecRegistro(fechaActual);
		solicitud.setFecInicio(fecInicio);
		solicitud.setFecFin(fecFin);
		solicitud.setEstado("PENDIENTE");
		solicitud.setIdPrestatario(usuario);
		solicitud.setIdPrestamista(prestamista);
		solrepo.save(solicitud);
		return "redirect:/historial-prestamo?solicitudExitosa";
	}

	//Listado de solicitudes de prestatario
	@GetMapping("/historial-prestamo")
	public String historialPrestamos(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();		
	    Usuario usuario = usurepo.findByUsername(username);

		int idUsuario = usuario.getIdUsuario();
		
		List<Solicitud> solicitudes = solrepo.findByIdPrestatarioIdUsuario(idUsuario);
		
		model.addAttribute("lstSolicitudes", solicitudes);
		
		return "historial-prestamo";
	}
}
