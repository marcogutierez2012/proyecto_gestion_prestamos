package com.prestamos.service;

import com.prestamos.model.Usuario;

import java.util.List;

public interface PrestamistaService {


    List<Usuario> obtenerTodos();
    Usuario obtenerPorId(Integer id);
    void guardar(Usuario usuario);
    void actualizar(Usuario usuario);
    void eliminar(Integer id);

    List<Usuario> buscarPorNombreYRol(String nombres);
    List<Usuario> buscarPorAtributos(String nombres, String apePaterno, String apeMaterno, String dni);
    List<Usuario> buscarPorAtributosP(String nombres, String apePaterno, String apeMaterno, String dni, Integer idUsuario);

    void cambiarEstado(int idUsuario);

}
