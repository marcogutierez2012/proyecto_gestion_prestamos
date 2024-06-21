package com.prestamos.service;

import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;

import java.util.List;


public interface RolService{

    List<Rol> obtenerTodos();
    Rol obtenerPorId(Integer id);
    void guardar(Rol rol);
    void actualizar(Rol rol);
    void eliminar(Integer id);
    Rol obtenerRolPrestatario(int idRol);
}
