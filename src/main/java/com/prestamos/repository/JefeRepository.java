package com.prestamos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prestamos.model.Usuario;

public interface JefeRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByEmail(String email);
	
	Usuario findByTelefono(String telefono);
	
	Usuario findByDni(String dni);
	
	Usuario findByUsername(String username);
	
	Usuario findByIdRolDescripcion(String descripcion);
	
	List<Usuario> findByIdRolDescripcionAndEstado(String descripcion, int estado);
	
	@Query("SELECT u FROM Usuario u " +
	           "JOIN u.idRol r " +
	           "WHERE (LOWER(u.dni) LIKE LOWER(CONCAT('%', :search, '%')) " +
	           "       OR LOWER(u.nombres) LIKE LOWER(CONCAT('%', :search, '%')) " +
	           "       OR LOWER(u.apePaterno) LIKE LOWER(CONCAT('%', :search, '%'))) " +
	           "AND LOWER(r.descripcion) = LOWER(:rolDescripcion) " +
	           "AND u.estado = :estado")
	 List<Usuario> findBySearchAndIdRolDescripcionAndEstado(@Param("search") String search,
	                                                         @Param("rolDescripcion") String rolDescripcion,
	                                                         @Param("estado") int estado);
	
}
