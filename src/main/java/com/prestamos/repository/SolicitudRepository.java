package com.prestamos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prestamos.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {

	Solicitud findByIdSolicitud(int idSolicitud);
	
    List<Solicitud> findByIdPrestatarioIdUsuario(int idPrestatario);
    List<Solicitud> findByIdPrestamistaIdUsuarioAndEstado(int idPrestamista, String estado);

    @Query("SELECT s FROM Solicitud s " +
            "JOIN s.idPrestatario p " +
            "WHERE (:search IS NULL OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:fecha1 IS NULL OR :fecha2 IS NULL OR (s.fecInicio BETWEEN :fecha1 AND :fecha2) OR (s.fecFin BETWEEN :fecha1 AND :fecha2)) " +
            "AND s.idPrestamista.idUsuario = :idPrestamista " +
    		"AND s.estado = 'PENDIENTE'")
    List<Solicitud> findBySearchAndIdPrestatarioNombresAndFecha1AndFecha2AndIdPrestamistaIdUsuario(@Param("search") String search,
                                                                                                   @Param("fecha1") Date fecha1,
                                                                                                   @Param("fecha2") Date fecha2,
                                                                                                   int idPrestamista);
}