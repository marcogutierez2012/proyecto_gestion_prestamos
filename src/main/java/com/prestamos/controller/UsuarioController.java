package com.prestamos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;
import com.prestamos.repository.RolRepository;
import com.prestamos.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

	@Autowired
	UsuarioRepository usurepo;
	
	@Autowired
	RolRepository rolrepo;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	

	@GetMapping("/login")
	public String login() {	
		return "login";
	}
	
	
	
	@GetMapping("/registrarUser")
	public String mostrarFormularioRegistro(Model model) {
	    model.addAttribute("usuario", new Usuario());
	    
	    // Buscar el rol de "Prestamista" en la base de datos por su nombre
	    Rol prestamista = rolrepo.findByDescripcion("Prestamista");
	    
	    // Verificar si se encontró el rol de "Prestamista"
	    if (prestamista != null) {
	        // Si se encontró el rol de "Prestamista", buscar los usuarios asociados a ese rol
	        List<Usuario> prestamistas = usurepo.findByIdRol(prestamista);

	        // Verificar si la lista de prestamistas no está vacía
	        if (!prestamistas.isEmpty()) {
	            // Agregar la lista de usuarios al modelo para ser utilizada en la vista
	            model.addAttribute("listprestamista", prestamistas);
	            
	            // Imprimir los datos de los prestamistas en la consola
	            for (Usuario pres : prestamistas) {
	    	        System.out.println("IDPRESTAMISTA : " + pres.getIdUsuario());
	    	        System.out.println("NOMBRE PRESTAMISTA : " + pres.getNombres());
	    	        System.out.println("IDZONA : " + pres.getIdZona().getIdZona());
	    	        System.out.println("NOMBREZONA : " + pres.getIdZona().getDescripcion());
	    	        System.out.println("USUARIOLIDER : " + pres.getIdUsuarioLider());
	    	        System.out.println("----");
	    	    }
	        } else {
	            // Si la lista de prestamistas está vacía, manejar la situación apropiadamente
	            model.addAttribute("error", "No hay prestamistas disponibles");
	        }
	    } else {
	        // Si no se encontró el rol de "Prestamista", manejar la situación apropiadamente
	        model.addAttribute("error", "No se encontró el rol de 'Prestamista'");
	    }
	    
	   
	    return "registrarUser";
	}

	  	@GetMapping("/buscarUsuario")
	    @ResponseBody
	    public ResponseEntity<Usuario> buscarUsuario(@RequestParam("selectPrestamista") int selectPrestamista) {
	        
		  
		  
		  Usuario usuario = usurepo.findByIdUsuario(selectPrestamista);
	        
	        if (usuario != null) {
	        	return ResponseEntity.ok(usuario);
	        } else {
	        	return ResponseEntity.notFound().build();
	        }
	    }

	  	
	  	@PostMapping("/guardar")
	  	public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult result, RedirectAttributes redirectAttrs) {
	  	    // Verificar si hay errores en la validación del formulario
	  	    if (result.hasErrors()) {
	  	        // Si hay errores, agregar un atributo flash para mostrar el mensaje de error en la misma página
	  	        redirectAttrs.addFlashAttribute("error", "Usuario o contraseña inválidos");
	  	    } else {
	  	        // Encriptar la contraseña antes de guardarla en la base de datos
	  	        String contraseñaEncriptada = passwordEncoder.encode(usuario.getPassword());
	  	        usuario.setPassword(contraseñaEncriptada);
	  	      // Buscar el rol de "Prestamista" en la base de datos por su nombre
	  		    Rol prestamista = rolrepo.findByDescripcion("Prestatario");
	  		    usuario.setIdRol(prestamista);
	  	   // Obtener el rol predeterminado de alguna manera
	  	     
	  	        
	  	        // Guardar el usuario en la base de datos
	  	        usurepo.save(usuario);

	  	        // Agregar un atributo flash para mostrar el mensaje de éxito en la misma página
	  	        redirectAttrs.addFlashAttribute("registroExitoso", true);
	  	    }
	  	    
	  	    // Redireccionar a la página de registro
	  	    return "redirect:/registrarUser";
	  	}


	  	

	@GetMapping("/home")
	public String home(Model model, HttpSession session) {
	   
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
		
	    Usuario usuario = usurepo.findByUsername(username);
	    
	    Rol r = usuario.getIdRol();
	    	
		String desrol = r.getDescripcion();
		
		session.setAttribute("rol", desrol);
			
		String rol = (String) session.getAttribute("rol");
		
		model.addAttribute("rol", rol);
		
		return "home";
	}
	
	
}
