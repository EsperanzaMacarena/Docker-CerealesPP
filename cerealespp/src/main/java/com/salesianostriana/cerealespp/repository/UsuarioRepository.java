package com.salesianostriana.cerealespp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.cerealespp.model.Usuario;
/**
 * Interfaz que implementa capa de abstracción entre la capa de acceso a datos de un Usuario y la capa de lógica de negocios (servicios). 
 * @author Esperanza M Escacena M
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	/**
	 * Busca a un usuario por su email.
	 * @param email Dato tipo String por el que se busca a un usuario.
	 * @return El usuario cuyo email concuerda con el parámetro.
	 */
	Usuario findFirstByEmail(String email);
}
