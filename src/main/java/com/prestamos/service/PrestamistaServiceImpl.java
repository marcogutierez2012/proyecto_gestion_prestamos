package com.prestamos.service;

import com.prestamos.model.Usuario;
import com.prestamos.repository.PrestamistaRepository;
import com.prestamos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamistaServiceImpl implements PrestamistaService{

    @Autowired
    private PrestamistaRepository prestamistaRepository;

    @Override
    public List<Usuario> obtenerTodos() {
        return prestamistaRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        return prestamistaRepository.findById(id).orElse(null);
    }

    @Override
    public void guardar(Usuario usuario) {
        prestamistaRepository.save(usuario);

    }

    @Override
    public void actualizar(Usuario usuario) {
        prestamistaRepository.save(usuario);

    }

    @Override
    public void eliminar(Integer id) {
        prestamistaRepository.deleteById(id);

    }

	@Override
	public List<Usuario> buscarPorNombreYRol(String nombres) {
		Integer idRol = 5;
		return prestamistaRepository.findByNombresAndRolId(nombres, idRol);
	}

	@Override
	public List<Usuario> buscarPorAtributos(String nombres, String apePaterno, String apeMaterno, String dni) {
		Integer idRol = 5; 
        return prestamistaRepository.findByAttributes(nombres, apePaterno, apeMaterno, dni, idRol);
	}

	@Override
	public List<Usuario> buscarPorAtributosP(String nombres, String apePaterno, String apeMaterno, String dni,
			Integer idUsuario) {
		Integer idRol = 5; 
        return prestamistaRepository.findByAttributesP(nombres, apePaterno, apeMaterno, dni, idRol, idUsuario);
	}

	@Override
	public void cambiarEstado(int idUsuario) {
		prestamistaRepository.cambiarEstado(idUsuario);
		
	}

    /*@Override
    public List<Usuario> obtenerUsuariosPorRol(Integer idRol) {
        return prestamistaRepository.findByRolId(idRol);
    }*/
}
