package com.prestamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.model.Rol;


public interface RolRepository extends JpaRepository<Rol,Integer>  {

	Rol  findByIdRol(int idRol);
	Rol  findByDescripcion(String descripcion);
}
