package com.salesianostriana.cerealespp.model;


import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que especifica que este usuario es una persona física y es administrador.
 * @author Esperanza M Escacena M

 */

@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="Administrador")
public class Administrador extends Usuario{
	/**
	 * Constructor de un Administrador con los atributos de su superclase, salvo el id.
	 * @param nombre Nombre del Administrador.
	 * @param apellidos Apellidos del Administrador.
	 * @param password Contraseña del Administrador.
	 * @param email Email del Administrador que se utilizará como nombre de usuario para poder hacer logIn.
	 */
	public Administrador(String nombre, String apellidos, String password, String email) {
		super(nombre, apellidos, password, email);
		// TODO Auto-generated constructor stub
	}
	/**
	 * {@inheritDoc}
	 * Método que le otorga un rol.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
}
