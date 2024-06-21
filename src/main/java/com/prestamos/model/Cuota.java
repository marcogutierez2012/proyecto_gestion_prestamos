package com.prestamos.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="cuota")
@Getter
@Setter
public class Cuota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCuota;
	private int numeroCuota;
	private double montoCuota;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaPago;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaVencimiento;
	private String estado;
	
	@ManyToOne
	@JoinColumn(name="id_solicitud")
	private Solicitud idSolicitud;
	
	
}
