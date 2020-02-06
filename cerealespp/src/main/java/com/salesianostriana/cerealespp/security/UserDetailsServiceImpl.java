/**
 * 
 */
package com.salesianostriana.cerealespp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.salesianostriana.cerealespp.model.Usuario;
import com.salesianostriana.cerealespp.service.UsuarioServicio;

/**
 * Clase que gestiona la información del usuario actual.
 * @author Esperanza M Escacena M
 *
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	UsuarioServicio usuarioServicio;
	/**
	 * Constructor del servicio de UserDetails.
	 * @param servicio Clase Servicio de Usuario.
	 */
	public UserDetailsServiceImpl(UsuarioServicio servicio) {
		this.usuarioServicio = servicio;
	}
	/**
	 * {@inheritDoc}
	 * Método que localiza al usuario por su username (email). 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		Usuario usuario = usuarioServicio.buscarPorEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		} 
		return usuario;	
	}
}
