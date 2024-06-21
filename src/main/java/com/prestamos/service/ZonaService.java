package com.prestamos.service;

import com.prestamos.model.Zona;

import java.util.List;

public interface ZonaService {
    List<Zona> obtenerTodos();
    Zona obtenerPorId(Integer id);
    void guardar(Zona zona);
    void actualizar(Zona zona);
    void eliminar(Integer id);
}
