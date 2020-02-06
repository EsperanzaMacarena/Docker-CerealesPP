/**
 * 
 */
package com.salesianostriana.cerealespp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que modela una línea de pedido cuando se añade un producto a la cesta.
 * @author Esperanza M Escacena M
 * 
 */
@Data @NoArgsConstructor
@Entity
@Table(name="LineaPedido")
public class LineaPedido {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int cantidad;
	@ManyToOne 
	private Producto productoAnadido;
	private double precioUnidad;
	@ManyToOne
	private Pedido pedido;
	/**
	 * Constructor de una Línea de pedido con todos sus atributos salvo el id.
	 * @param cantidad Número de un producto que añade a la cesta.
	 * @param productoAnadido El producto añadido a la cesta.
	 * @param precioUnidad El precio al que se compró el producto.
	 * @param p Pedido al que pertenece
	 */
	public LineaPedido(int cantidad, Producto productoAnadido, double precioUnidad, Pedido p) {
		this.cantidad = cantidad;
		this.productoAnadido = productoAnadido;
		this. precioUnidad=precioUnidad;
		this.pedido=p;
	}	
}