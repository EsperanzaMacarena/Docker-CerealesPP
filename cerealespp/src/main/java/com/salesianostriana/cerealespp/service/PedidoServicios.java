/**
 * 
 */
package com.salesianostriana.cerealespp.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.salesianostriana.cerealespp.model.Cliente;
import com.salesianostriana.cerealespp.model.EstadoPedido;
import com.salesianostriana.cerealespp.model.LineaPedido;
import com.salesianostriana.cerealespp.model.Pedido;
import com.salesianostriana.cerealespp.model.Producto;
import com.salesianostriana.cerealespp.repository.PedidoRepository;

/**
 * Clase de lógica de negocio que concierne a un Pedido.
 * 
 * @author Esperanza M Escacena M
 *
 */
@Service
public class PedidoServicios extends ServicioBase<Pedido, Long, PedidoRepository> {
	@Autowired
	LineaPedidoServicio lps;
	@Autowired
	ProductoServicios ps;
	@Autowired
	DatosMaestrosServicio dms;

	/**
	 * Busca los pedidos confirmados, pagados.
	 * @param c Cliente para buscar el pedido.
	 * @return Pedidos confirmados.
	 */
	public List<Pedido> buscarPedidoConsolidados(Cliente c) {
		return this.repositorio.findByClienteAndConsolidadoTrue(c);
	}

	/**
	 * Busca el pedido no consolidado de un Cliente por el id del Cliente..
	 * 
	 * @param c Cliente para buscar el pedido.
	 * @return Pedido no consolidado.
	 */
	public Pedido buscarPedidoNoConsolidado(Cliente c) {
		return this.repositorio.findByClienteAndConsolidadoFalse(c);
	}

	/**
	 * Elimina un pedido.
	 * 
	 * @param id ID del pedido.
	 */
	public void cancelarPedido(Long id) {
		this.repositorio.delete(findById(id));
	}

	/**
	 * Crea un pedido no consolidado si el cliente no tiene ninguno.
	 * @param c Cliente al que hay que crearle un pedido no consolidado si no tiene ninguno.
	 */
	public void crearPedido(Cliente c) {
		if (this.repositorio.findByClienteAndConsolidadoFalse(c) == null) {
			Pedido p = new Pedido();
			p.setCliente(c);
			p.setConsolidado(false);
			this.save(p);
		
		}
	}

	/**
	 * Añade una LineaPedido a un pedido. Si ya existe una LineaPedido con el mismo
	 * producto, no la añade, si no que modifica la existente.
	 * 
	 * @param pedido   Pedido a la que añadir la LineaPedido
	 * @param p        Producto para poder buscar la LineaPedido y comprobar si ya
	 *                 existe esa linea.
	 * @param cantidad Cantidad de producto de la LineaPedido, para crearla o
	 *                 modificarla.
	 * @param c        Cliente para poder buscar
	 */
	public void anadirLineaPedido(Pedido pedido, Producto p, int cantidad, Cliente c) {
		
		if (pedido.getLinea().stream().anyMatch(lp -> lp.getProductoAnadido().equals(p))) {
			LineaPedido aux = pedido.getLinea()
					.stream()
					.filter(lp -> lp.getProductoAnadido().equals(p)).findFirst()
					.get();
			aux.setCantidad(cantidad);
			lps.edit(aux);
		} else {
			LineaPedido lp = new LineaPedido(cantidad, p, p.getPrecio(), pedido);
			lps.save(lp);
			pedido.anadirLinea(lp);
			this.edit(pedido);
		}

	}

	/**
	 * Elimina una LineaPedido en el Pedido.
	 * 
	 * @param pedido Pedido en el cual hay que borrar una LineaPedido.
	 * @param lp     LineaPedido a borrar.
	 */
	public void eliminarLineaPedido(Pedido pedido, LineaPedido lp) {
		pedido.eliminarLinea(lp);
		this.edit(pedido);
		lps.delete(lp);
	}

	/**
	 * Actualiza el precioTotal y precioSubTotal de un pedido cuando se añade una
	 * Lineapedido.
	 * 
	 * @param p Pedido a actualizar.
	 */
	public void actualizarPedido(Pedido p) {
		p.setPrecioSubtotal(p.getLinea()
				.stream()
				.mapToDouble(lp -> lps.calcularPrecioTotalLinea(lp))
				.sum());
		p.setPrecioTotal(p.getPrecioSubtotal() + p.getPrecioTransporte());
		this.edit(p);
	}

	/**
	 * Confirma un pedido (consolidado), seteando la fecha de compra, el estado y
	 * modifica el stock del producto.
	 * 
	 * @param p Pedido a confirmar
	 */
	public void confirmarPedido(Pedido p) {
		p.setFecha(LocalDate.now());
		p.setConsolidado(true);
		p.setEstado(EstadoPedido.PAGADO);
		ps.modificarStock(p);
		this.edit(p);
	}

	/**
	 * Calcula los gastos de envío en función del número de toneladas compradas. Hay
	 * que tener en cuenta que 26 toneladas es un camión, por lo que se cobra gastos
	 * de envío por cada camión cargado.
	 * 
	 * @param p Pedido al que hay que calcularle los gastos de envío.
	 */
	public void calcularGastosEnvio(Pedido p) {
		double precio = 0;
		double pGastosEnvio = dms.findById(3026L).getPrecioCamion();
		for (LineaPedido lp : p.getLinea()) {

			switch (lp.getCantidad()) {
			case 26:
				precio += pGastosEnvio;
				break;
			case 52:
				precio += pGastosEnvio * 2;
				break;
			case 78:
				precio += pGastosEnvio * 3;
				break;
			case 104:
				precio += pGastosEnvio * 4;
				break;
			case 130:
				precio += pGastosEnvio * 5;
				break;
			case 156:
				precio += pGastosEnvio * 6;
				break;
			case 182:
				precio += pGastosEnvio * 7;
				break;
			case 208:
				precio += pGastosEnvio * 8;
				break;
			}
		}
		p.setPrecioTransporte(precio);
		this.edit(p);
	}
	/**
	 * Lista todos los pedidos consolidados.
	 * @return Lista de todos los pedidos consolidados.
	 */
	public List<Pedido> listarPedidosConsolidados() {
		return this.repositorio.findByConsolidadoTrue();
	}
}
