package com.prestamos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="prestamos")
public class Prestamos {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPrestamos;
	
	@ManyToOne
	@JoinColumn(name="id_monto")
	Monto idMonto;
	
	private int duracionDias;
	
	private int interes;
	
}
