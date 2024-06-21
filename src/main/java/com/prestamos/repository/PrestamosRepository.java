package com.prestamos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prestamos.model.Prestamos;

@Repository
public interface PrestamosRepository extends JpaRepository<Prestamos, Integer> {
	
    @Query(value = "SELECT p.duracion_dias, " +
            "ROUND(m.monto1 + (m.monto1 * (p.interes / 100)), 2) AS monto1_calculado, " +
            "ROUND(m.monto2 + (m.monto2 * (p.interes / 100)), 2) AS monto2_calculado, " +
            "ROUND(m.monto3 + (m.monto3 * (p.interes / 100)), 2) AS monto3_calculado, " +
            "ROUND(m.monto4 + (m.monto4 * (p.interes / 100)), 2) AS monto4_calculado, " +
            "ROUND(m.monto5 + (m.monto5 * (p.interes / 100)), 2) AS monto5_calculado, " +
            "p.interes " + // Agrega la columna "interes" a la consulta
            "FROM prestamos p " +
            "INNER JOIN monto m ON p.id_monto = m.id_monto", nativeQuery = true)
    List<Object[]> obtenerCalculoMontos();
}

