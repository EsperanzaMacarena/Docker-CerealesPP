package com.salesianostriana.cerealespp.service;

import java.security.Principal;
import org.springframework.stereotype.Service;
import com.salesianostriana.cerealespp.model.Usuario;
import com.salesianostriana.cerealespp.repository.UsuarioRepository;
/**
 Clase de lógica de negocio que concierne a un Usuario.
 * @author Esperanza M Escacena M
 *
 */
@Service
public class UsuarioServicio extends ServicioBase<Usuario, Long, UsuarioRepository> {
	/**
	 * Busca a un usuario por su email.
	 * @param email Dato tipo String por el que buscar el Usuario.
	 * @return Usuario encontrado.
	 */
	public Usuario buscarPorEmail(String email) {
		return repositorio.findFirstByEmail(email);
	}
	/**
	 * Busca al usuario logueado en ese momento.
	 * @param p representación del usuario logueado.
	 * @return Usuario logueado.
	 */
	public Usuario buscarUsuarioLogged( Principal p) {
		Usuario usuario;
		if(p!=null) {
			usuario=this.buscarPorEmail(p.getName());
			return usuario;
		}else {
			return null;
		}
	}
	/**
	 * Comprueba si el email ingresado en el formulario de registro está ya registrado en la BBDD.
	 * @param email Email ingresado en el formulario.
	 * @return Booleano que indica si ya existe o no en la BBDD.
	 */
	public boolean comprobarEmail(String email){
		if(this.findAll()
				.stream()
				.anyMatch(usu->usu.getEmail().equals(email))) {
			return true;
		}else {
			return false;
		}
	
	}
}
