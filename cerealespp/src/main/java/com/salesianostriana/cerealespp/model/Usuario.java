package com.salesianostriana.cerealespp.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que modela a un Usuario.
 * @author Esperanza M Escacena M
 *
 */

@Data @NoArgsConstructor
@Entity
@Table(name="Usuario")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Usuario implements UserDetails{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nombre;
	private String apellidos;
	private String password;
	@Column(unique=true)
	private String email;
	private boolean cuentaCaducada;
	private boolean cuentaBloqueada;
	private boolean credencialesCaducadas;
	/**
	 * Constructor de un Usuario con todos sus atributos, salvo el id.
	 * @param nombre Nombre del Usuario
	 * @param apellidos Apellidos del Usuario
	 * @param password Contraseña del Usuario
	 * @param email email del Usuario, que funcionará como nombre de usuario.
	 */
	public Usuario(String nombre, String apellidos, String password, String email) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.email = email;
		this.cuentaCaducada = false;
		this.cuentaBloqueada = false;
		this.credencialesCaducadas = false;
	}
	/**
	 * {@inheritDoc}
	 * Método que le otorga un rol.
	 */
	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();
	/**
	 * {@inheritDoc}
	 * Método que obtiene el atributo que funciona como nombre de usuario para el logIn.
	 */
	@Override
	public String getUsername() {
		return email;
	}
	/**
	 * {@inheritDoc}
	 * Método que obtiene el atributo sobre si la cuenta esta expirada o no para hacer logIn.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return !cuentaCaducada;
	}
	/**
	 * {@inheritDoc}
	 * Método que obtiene el atributo sobre si la cuenta esta bloqueada o no para hacer logIn.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return !cuentaBloqueada;
	}

	/**
	 * {@inheritDoc}
	 * Método que obtiene el atributo sobre si las credenciales esta caducada o no para hacer logIn.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return !credencialesCaducadas;
	}

	/**
	 * {@inheritDoc}
	 * Método que obtiene el atributo sobre si la cuenta esta habilitada o no para hacer logIn.
	 */
	@Override
	public boolean isEnabled() {
		return !cuentaBloqueada;
	}
	
	
}
