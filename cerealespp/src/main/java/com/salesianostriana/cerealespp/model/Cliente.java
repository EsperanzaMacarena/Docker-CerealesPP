/**
 * 
 */
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
 * Clase que modela un objeto Cliente.
 * @author Esperanza M Escacena M
 */

@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="Cliente")
public class Cliente extends Usuario {
	
	private String nomEmpresa;
	private String cif;
	private String telefono;
	private String direccionSocial;
	private String cpSocial;
	private String localidadSocial;
	private String provinciaSocial;
	private String direccionEnvio;
	private String cpEnvio;
	private String localidadEnvio;
	private String provinciaEnvio;
	
	/**
	 * @param nombre Nombre de la persona de contacto de la empresa, que es nuestro verdadero cliente.
	 * @param apellidos Apellidos de la persona de contacto de la empresa.
	 * @param contrasena Contraseña de la cuenta de usuario Cliente de la empresa.
	 * @param email Email de la empresa que actuará también de nombre de usuario.
	 * @param nomEmpresa Nombre de la Empresa
	 * @param cif Número de Identificación Fiscal de la empresa
	 * @param telefono Número de teléfono de contacto de la empresa
	 * @param direccionSocial Dirección social de la empresa
	 * @param cpSocial Código postal de la dirección social de la empresa
	 * @param localidadSocial Localidad de la dirección social de la empresa
	 * @param provinciaSocial Provincia de la dirección social de la empresa
	 * @param direccionEnvio Dirección a dónde el cliente desea recibir la mercancía
	 * @param cpEnvio Código postal de la dirección de envío.
	 * @param localidadEnvio Localidad de la dirección de envío.
	 * @param provinciaEnvio Provincia de la dirección de envío.
	 */
	public Cliente( String nombre, String apellidos, String contrasena, String email, String nomEmpresa, String cif,
			String telefono, String direccionSocial, String cpSocial, String localidadSocial, String provinciaSocial,
			String direccionEnvio, String cpEnvio, String localidadEnvio, String provinciaEnvio) {
		super(nombre, apellidos,contrasena, email);
	
		this.nomEmpresa = nomEmpresa;
		this.cif = cif;
		this.telefono = telefono;
		this.direccionSocial = direccionSocial;
		this.cpSocial = cpSocial;
		this.localidadSocial = localidadSocial;
		this.provinciaSocial = provinciaSocial;
		this.direccionEnvio = direccionEnvio;
		this.cpEnvio = cpEnvio;
		this.localidadEnvio = localidadEnvio;
		this.provinciaEnvio = provinciaEnvio;
	}
	/**
	 * {@inheritDoc}
	 * Método que le otorga un rol.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	
	
}
