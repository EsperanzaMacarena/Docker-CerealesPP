package com.salesianostriana.cerealespp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que modela un  producto.
 * @author Esperanza M Escacena M
 * 
 */
@Data @NoArgsConstructor
@Entity 
@Table(name="Producto")
public class Producto {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nombre;
	private boolean procesado;
	private double precio;
	private int stock;
	private String fileUrl;

	/**
	 * Constructor de un Producto con todos sus atributos, salvo el id.
	 * @param nombre Nombre del producto.
	 * @param procesado Indica si es de categoría procesado o no procesado.
	 * @param precio Precio del producto.
	 * @param stock Cantidad disponible en el almacén.
	 * @param fileUrl ruta de la imagen.
	 */
	public Producto(String nombre, boolean procesado, double precio, int stock,String fileUrl) {
		this.nombre = nombre;
		this.procesado = procesado;
		this.precio = precio;
		this.stock=stock;
		this.fileUrl=fileUrl;
	}	
	
}
