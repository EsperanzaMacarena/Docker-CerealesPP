/**
 * 
 */
package com.salesianostriana.cerealespp.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Clase que modela un Pedido.
 * @author Esperanza M Escacena M
 * 
 */
@Data @NoArgsConstructor
@Entity 
@Table(name="Pedido")
public class Pedido {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;
	private boolean consolidado;
	@ManyToOne
	private Cliente cliente;
	private double precioTransporte;
	private double precioSubtotal;
	private double precioTotal;
	private EstadoPedido estado;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="pedido")
	private List<LineaPedido> linea;
	/**
	 * Constructor de un Pedido con todos sus atributos, salvo el id.
	 * @param fecha Fecha de compra del pedido.
	 * @param consolidado Si el pedido ha sido pagado o no
	 * @param cliente Cliente al que pertence
	 * @param precioTransporte Precio de los gastos de envío
	 * @param precioSubtotal Precio total de las LineasPedido
	 * @param precioTotal Precio total del pedido
	 * @param estado Estado del pedido
	 * @param linea Listado de las LineasPedido
	 */
	public Pedido(LocalDate fecha, boolean consolidado, Cliente cliente, double precioTransporte,
			double precioSubtotal, double precioTotal, EstadoPedido estado, List<LineaPedido> linea) {
		super();
		this.fecha = fecha;
		this.consolidado =consolidado;
		this.cliente = cliente;
		this.precioTransporte = precioTransporte;
		this.precioSubtotal = precioSubtotal;
		this.precioTotal = precioTotal;
		this.estado = estado;
		this.linea = linea;
	}
	
	
	/**
	 * Método helper que añade una LineaPedido en la lista linea de un Pedido.
	 * @param lp LineaPedido a añadir.
	 */
	public void anadirLinea(LineaPedido lp) {
		if (lp != null) {
			lp.setPedido(this);
			this.getLinea().add(lp);
		}
	}
	/**
	 * Método helper que elimina una LineaPedido en la lista de un Pedido.
	 * @param lp LineaPedido a eliminar.
	 */
	public void eliminarLinea(LineaPedido lp) {
		if (lp != null) {
			lp.setPedido(null);
			this.getLinea().remove(lp);
		}
	}
	
}
