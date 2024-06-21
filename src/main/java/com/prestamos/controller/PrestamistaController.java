package com.prestamos.controller;

import com.prestamos.model.Cuota;
import com.prestamos.model.Rol;
import com.prestamos.model.Solicitud;
import com.prestamos.model.Usuario;
import com.prestamos.model.Zona;
import com.prestamos.repository.CuotaRepository;
import com.prestamos.repository.SolicitudRepository;
import com.prestamos.repository.UsuarioRepository;
import com.prestamos.repository.ZonaRepository;
import com.prestamos.service.PrestamistaService;
import com.prestamos.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
public class PrestamistaController {

	@Autowired
	private PrestamistaService prestamistaService;

	@Autowired
	private RolService rolService;

	@Autowired
	private ZonaRepository zonarepo;

	@Autowired
	private UsuarioRepository usurepo;
	
	@Autowired
	private SolicitudRepository solrepo;

	
	@Autowired
	private CuotaRepository cuotarepo;

	/*@GetMapping("prestamista-list")
	public String mostrarTodos(Model model) {
		List<Rol> roles = rolService.obtenerTodos();
		List<Usuario> prestamistas = prestamistaService.obtenerTodos();
		model.addAttribute("roles", roles);
		model.addAttribute("prestamistas", prestamistas);
		return "prestamista-list";
	}*/
	@GetMapping("prestamista-list")
	public String mostrarTodos(Model model) {
		List<Rol> roles = rolService.obtenerTodos();

		// Obtener el usuario autenticado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Usuario usuarioAutenticado = usurepo.findByUsername(username);

		// Obtener el idUsuarioLider del usuario autenticado
		Integer idUsuarioLider = usuarioAutenticado.getIdUsuario();

		// Obtener todos los prestamistas
		List<Usuario> prestamistas = prestamistaService.obtenerTodos();
		List<Usuario> prestamistasFiltrados = new ArrayList<>();

		// Filtrar los prestamistas que tienen el rol con ID 5 y el idUsuarioLider correspondiente al usuario autenticado
		for (Usuario prestamista : prestamistas) {
			if (prestamista.getIdRol().getIdRol() == 5 && prestamista.getIdUsuarioLider() != null && prestamista.getIdUsuarioLider() == idUsuarioLider) {
				prestamistasFiltrados.add(prestamista);
			}
		}

		model.addAttribute("roles", roles);
		model.addAttribute("prestamistas", prestamistasFiltrados);
		return "";
	}

	@GetMapping("/prestamista-crear")
	public String mostrarFormularioCrear(Model model) {
			// Obtener el nombre de usuario del contexto de seguridad
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();

			// Obtener el usuario actual
			Usuario usuario = usurepo.findByUsername(username);

			Rol rolPrestatario = rolService.obtenerRolPrestatario(5);

			// Poner el usuario en el modelo
			// Agregar otros atributos necesarios al modelo
			model.addAttribute("nombreUsuario", username);
			model.addAttribute("idRol", rolPrestatario);
			model.addAttribute("idZona", usuario.getIdZona().getIdZona());
			model.addAttribute("listzonas", zonarepo.findAll());

			model.addAttribute("prestamista", new Usuario());

			return "prestamista-crear";

	}

	@PostMapping("/prestamista/crear")

		public String crearPrestamista (@ModelAttribute Usuario usuario, @RequestParam("idZona") int idZona, Model model){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario jefeprestamista = usurepo.findByUsername(username);

		// Encriptar la contraseña
		String passwordEncriptado = encriptarPassword(usuario.getPassword());
		usuario.setPassword(passwordEncriptado);

		int idPrestamista = jefeprestamista.getIdUsuario();
		usuario.setIdUsuarioLider(idPrestamista);

		// Obtener el rol del prestatario
		Rol rolJefePrestatario = rolService.obtenerRolPrestatario(5);
		// Asignar el rol al usuario
		usuario.setIdRol(rolJefePrestatario);



			usurepo.save(usuario);

			return "redirect:/prestamista-crear?registroExitoso";


		}

		@GetMapping("/prestamista/editar/{id}")
		public String mostrarFormularioEditar (@PathVariable Integer id, Model model){
			Usuario usuario = prestamistaService.obtenerPorId(id);


			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			Usuario jefeprestamista = usurepo.findByUsername(username);

			// Obtener la zona del prestamista
			Zona zonaJefePrestamista = jefeprestamista.getIdZona();

			// Agregar la zona del prestamista al modelo
			model.addAttribute("zonaJefePrestamista", zonaJefePrestamista);

			Rol rolJefePrestamista = rolService.obtenerRolPrestatario(5);

			model.addAttribute("nombreJefePrestamista", username);

			// Agregar el rol al modelo
			model.addAttribute("rolJefePrestamista", rolJefePrestamista);

			model.addAttribute("usuario", usuario);

			
			return "prestamista-Actualizar";
		}



	@PostMapping("/prestamista/editar/{id}")
		public String actualizarPrestamista (@PathVariable Integer id, @ModelAttribute Usuario usuario){
			usuario.setIdUsuario(id);// Asegurarse de que el rol tenga el ID correcto

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String nombreJefePrestamista = auth.getName();
			Usuario jefeprestamista = usurepo.findByNombres(nombreJefePrestamista);

			// Encriptar la contraseña
			String passwordEncriptado = encriptarPassword(usuario.getPassword());
			usuario.setPassword(passwordEncriptado);

			int idPrestamista = jefeprestamista.getIdUsuario();
			usuario.setIdUsuarioLider(idPrestamista);

			// Obtener el rol del prestatario
			Rol rolJefePrestatario = rolService.obtenerRolPrestatario(5);
			// Asignar el rol al usuario
			usuario.setIdRol(rolJefePrestatario);

			prestamistaService.actualizar(usuario);
			return "redirect:/prestamista/listado";
		}

	// Método para encriptar la contraseña
	private String encriptarPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

		@GetMapping("/eliminar/{id}")
		public String eliminarPrestamista (@PathVariable Integer id){
			prestamistaService.eliminar(id);
			return "redirect:/prestamistas";
		}
		
	    @GetMapping("/prestamista-search")
	    public String buscarPorNombreYRol(String nombres, Model model) {
	        List<Usuario> personas = prestamistaService.buscarPorNombreYRol(nombres);
	        model.addAttribute("personas", personas);
	        return "prestamista-search"; // nombre de la plantilla Thymeleaf
	    }
	    
	    @GetMapping("/prestamista-search1")
	    public String buscarPorAtributos(
	            String nombres,
	            String apePaterno,
	            String apeMaterno,
	            String dni,
	            Model model) {

	        List<Usuario> usuarios = prestamistaService.buscarPorAtributos(nombres, apePaterno, apeMaterno, dni);
	        model.addAttribute("usuarios", usuarios);
	        return "prestamista-search";
	    }
		
	    @GetMapping("/prestamista-search2")
	    public String buscarPorAtributosP(
	            String nombres,
	            String apePaterno,
	            String apeMaterno,
	            String dni,
	            Model model) {

	        // Obtener el nombre del prestamista logueado
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        String username = auth.getName();

	        // Obtener el prestamista logueado desde la base de datos
	        Usuario jefeprestamista = usurepo.findByUsername(username);
	        Integer idUsuario = jefeprestamista.getIdUsuario();

	        List<Usuario> usuarios = prestamistaService.buscarPorAtributosP(nombres, apePaterno, apeMaterno, dni, idUsuario);
	        model.addAttribute("usuarios", usuarios);
	        return "prestamista-search";
	    }

	    @PostMapping("/prestamista/cambiarEstado/{idUsuario}")
	    public String cambiarEstado(@PathVariable int idUsuario) {
	        prestamistaService.cambiarEstado(idUsuario); // Cambiar el estado de la entidad
	        return "redirect:/prestamista-list"; // Redirigir a la página de listado
	    }
	    
		@GetMapping("/solicitudes-prestamo")
		public String verSolicitudesPrestamos(Model model) {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String username = auth.getName();		
		    Usuario usuario = usurepo.findByUsername(username);

			int idUsuario = usuario.getIdUsuario();
			
			List<Solicitud> solicitudes = solrepo.findByIdPrestamistaIdUsuarioAndEstado(idUsuario, "PENDIENTE");
			
			model.addAttribute("lstSolicitudes", solicitudes);
			return "solicitudes-prestamo";
		}
		
		@GetMapping("/solicitudes-filtrar")
		public String filtrarSolicitudes(Model model, @RequestParam("prestatario") String prestatario, @RequestParam("primeraFecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1,
													  @RequestParam("segundaFecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2) {
			
			//model.addAttribute("cuotas", new Cuota());
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String username = auth.getName();		
		    Usuario usuario = usurepo.findByUsername(username);
		    
		    int idUsuario = usuario.getIdUsuario();
			
			List<Solicitud> solicitudesfiltradas = 
					solrepo.findBySearchAndIdPrestatarioNombresAndFecha1AndFecha2AndIdPrestamistaIdUsuario(prestatario, fecha1, fecha2, idUsuario);
			
			
			model.addAttribute("lstSolicitudes", solicitudesfiltradas);
			
			return "solicitudes-prestamo";
		}
		
		@GetMapping("/cuotas-list-prestamista-pendiente")
		public String listaCuotasPrestamista(Model model) {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    String username = auth.getName();		
		    Usuario usuario = usurepo.findByUsername(username);
			
			int idPrestamista = usuario.getIdUsuario();
			
			List<Cuota> cuotas = cuotarepo.findByIdSolicitudIdPrestamistaIdUsuarioAndEstado(idPrestamista, "PENDIENTE");
			
			model.addAttribute("lstCuotas", cuotas);
		    
			return "cuotas-list-prestamista-pendiente";
		}

		@PostMapping("/registrarprestamo")
		public String registrarPrestamo(@RequestParam("idSolicitud") int idSolicitud,
		                                 @RequestParam("diaslaborales") int nrodiaslaborales,
		                                 @RequestParam("pagodiario") double pagoDiario) {

		    // Obtener la solicitud por su ID
		    Solicitud solicitud = solrepo.findByIdSolicitud(idSolicitud);
		    solicitud.setEstado("APROBADO");
		    solrepo.save(solicitud);

		    // Calcular la fecha de inicio
		    Date fechaInicio = solicitud.getFecInicio();
		    
		    // Inicializar las fechas de pago y vencimiento
		    Date fechaPago = fechaInicio;
		    Date fechaVencimiento = fechaInicio;

		    // Iterar sobre los días laborables para crear las cuotas
		    for (int x = 1; x < nrodiaslaborales+1; x++) {
		        // Verificar si la fecha de pago es un día laborable
		        while (!esDiaLaborable(fechaPago)) {
		            fechaPago = sumarDias(fechaPago, 1);
		            fechaVencimiento = sumarDias(fechaVencimiento, 1);
		        }
		        
		        // Crear la cuota y establecer sus atributos
		        Cuota cuota = new Cuota();
		        cuota.setIdSolicitud(solicitud);
		        cuota.setNumeroCuota(x);
		        cuota.setMontoCuota(pagoDiario);
		        cuota.setFechaPago(fechaPago);
		        cuota.setFechaVencimiento(fechaVencimiento);
		        cuota.setEstado("PENDIENTE");
		        cuotarepo.save(cuota);
		        
		        // Avanzar la fecha de pago y la fecha de vencimiento
		        fechaPago = sumarDias(fechaPago, 1);
		        fechaVencimiento = sumarDias(fechaVencimiento, 1);
		    }
		    
		    return "redirect:/cuotas-list-prestamista-pendiente?prestamoAprobado";
		}

		// Función para verificar si una fecha es un día laborable (no sábado ni domingo)
		private boolean esDiaLaborable(Date fecha) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(fecha);
		    int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
		    return diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY;
		}

		// Función para sumar días a una fecha
		private Date sumarDias(Date fecha, int dias) {
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(fecha);
		    cal.add(Calendar.DAY_OF_MONTH, dias);
		    return cal.getTime();
		}
		
		@PostMapping("/rechazarprestamo")
		public String rechazarPrestamo(@RequestParam("idSolicitud") int idSolicitud) {
			
			Solicitud solicitud = solrepo.findByIdSolicitud(idSolicitud);
			
			solicitud.setEstado("RECHAZADO");
			solrepo.save(solicitud);
			
			return "redirect:/solicitudes-prestamo?prestamoRechazado";
		}
		
	}


	

