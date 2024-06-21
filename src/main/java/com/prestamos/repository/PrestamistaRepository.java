package com.prestamos.repository;

import com.prestamos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamistaRepository extends JpaRepository<Usuario, Integer> {

    //@Query("SELECT u FROM Usuario u WHERE u.idRol = :idRol")
   //List<Usuario> findByRolId(Integer idRol);

	@Query("SELECT u FROM Usuario u JOIN u.idRol r WHERE u.nombres LIKE %:nombres% AND r.idRol = :idRol")
    List<Usuario> findByNombresAndRolId(String nombres, Integer idRol);
    
    List<Usuario> findByIdRolDescripcionAndIdUsuarioLiderAndEstado(String rol, int usuariolider, int estado);

    @Query("SELECT u FROM Usuario u JOIN u.idRol r WHERE " +
            "(:nombres is null or u.nombres like %:nombres%) AND " +
            "(:apePaterno is null or u.apePaterno like %:apePaterno%) AND " +
            "(:apeMaterno is null or u.apeMaterno like %:apeMaterno%) AND " +
            "(:dni is null or u.dni like %:dni%) AND " +
            "(r.idRol = :idRol)")
    List<Usuario> findByAttributes(String nombres,
                                String apePaterno,
                                String apeMaterno,
                                String dni,
                                Integer idRol);

    @Query("SELECT u FROM Usuario u JOIN u.idRol r WHERE " +
            "(:nombres is null or u.nombres like %:nombres%) AND " +
            "(:apePaterno is null or u.apePaterno like %:apePaterno%) AND " +
            "(:apeMaterno is null or u.apeMaterno like %:apeMaterno%) AND " +
            "(:dni is null or u.dni like %:dni%) AND " +
            "(u.idUsuarioLider = :idUsuario) AND" +
            "(r.idRol = :idRol)")
    List<Usuario> findByAttributesP(String nombres,
                                   String apePaterno,
                                   String apeMaterno,
                                   String dni,
                                   Integer idRol,
                                    Integer idUsuario);


    @Modifying
    @Query("UPDATE Usuario u SET u.estado = 1 WHERE u.idUsuario = :idUsuario")
    void cambiarEstado(@Param("idUsuario") int idUsuario);
}
