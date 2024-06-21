package com.prestamos.model;



import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int idUsuario;
	private String username;
	private String nombres;
	private String apePaterno;
	private String apeMaterno;
	private String password;
	private String email;
	private String telefono;
	private String dni;
	private int estado;
	
	@ManyToOne
	@JoinColumn(name="id_rol")
	Rol idRol;
	
	@ManyToOne
	@JoinColumn(name="id_zona")
	Zona idZona;
	
	// Campo para el ID del líder
    @Column(name = "id_usuario_lider")
    private Integer idUsuarioLider;


}