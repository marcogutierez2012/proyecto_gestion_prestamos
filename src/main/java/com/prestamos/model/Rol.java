package com.prestamos.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="rol")
public class Rol {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int idRol;
	private String descripcion;
	private  int estado;
	



}
