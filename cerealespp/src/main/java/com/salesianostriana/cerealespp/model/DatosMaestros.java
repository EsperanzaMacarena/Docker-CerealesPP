/**
 * 
 */
package com.salesianostriana.cerealespp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que contiene datos maestros de la empresa, como el precio del transporte de la mercancía.
 * @author Esperanza M Escacena M
 *
 */

@Data @NoArgsConstructor
@Entity	
@Table(name="DatosMaestros")
public class DatosMaestros {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private double precioCamion;//Precio por kilómetro
	
}
