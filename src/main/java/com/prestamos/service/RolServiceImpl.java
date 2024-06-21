package com.prestamos.service;

import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;
import com.prestamos.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    @Override
    public Rol obtenerPorId(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public void guardar(Rol rol) {
        rolRepository.save(rol);

    }

    @Override
    public void actualizar(Rol rol) {
        rolRepository.save(rol);

    }

    @Override
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Rol obtenerRolPrestatario(int idRol) {
        return rolRepository.findByIdRol(idRol);
    }
}
