package com.prestamos.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;

	public MyUserDetails(Usuario usuario) {
		this.usuario = usuario;
	}


	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	    // Obtenemos el rol del usuario
	    Rol rol = usuario.getIdRol();
	    // Añadimos el rol como autoridad si no es nulo
	    if (rol != null) {
	        authorities.add(new SimpleGrantedAuthority(rol.getDescripcion()));
	    }
	    return authorities;
	}


	@Override
	public String getPassword() {
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		return usuario.getUsername();
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}