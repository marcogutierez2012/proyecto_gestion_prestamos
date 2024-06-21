package com.prestamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.model.Zona;

public interface ZonaRepository extends JpaRepository<Zona,Integer> {
	
}
