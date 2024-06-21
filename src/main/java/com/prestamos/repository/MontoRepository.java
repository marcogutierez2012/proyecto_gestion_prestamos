package com.prestamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.model.Monto;

public interface MontoRepository extends JpaRepository<Monto, Integer> {

	
	
}
