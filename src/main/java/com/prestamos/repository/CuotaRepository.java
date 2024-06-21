package com.prestamos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prestamos.model.Cuota;

public interface CuotaRepository extends JpaRepository<Cuota, Integer> {

	List<Cuota> findByIdSolicitudIdPrestamistaIdUsuarioAndEstado(int idPrestamista, String estado);
	
	List<Cuota> findByIdSolicitudIdPrestatarioIdUsuarioAndEstado(int idPrestatario, String estado);
	
	
	
}
