package com.prestamos.service;

import com.prestamos.model.Zona;
import com.prestamos.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaServiceImpl implements ZonaService{

    @Autowired
    private ZonaRepository zonaRepository;

    @Override
    public List<Zona> obtenerTodos() {
        return zonaRepository.findAll();
    }

    @Override
    public Zona obtenerPorId(Integer id) {
        return null;
    }

    @Override
    public void guardar(Zona zona) {

    }

    @Override
    public void actualizar(Zona zona) {

    }

    @Override
    public void eliminar(Integer id) {

    }
}
