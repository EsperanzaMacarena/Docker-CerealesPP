package com.salesianostriana.cerealespp.service;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.salesianostriana.cerealespp.model.Cliente;
import com.salesianostriana.cerealespp.model.EstadoPedido;
import com.salesianostriana.cerealespp.model.LineaPedido;
import com.salesianostriana.cerealespp.model.Pedido;
import com.salesianostriana.cerealespp.model.Producto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PedidoServiciosTest {
	@Autowired
	PedidoServicios ps;
	@Autowired
	ClienteServicio cs;
	@Autowired
	ProductoServicios prs;
	@Autowired
	LineaPedidoServicio lps;
	@Autowired
	DatosMaestrosServicio dms;
	/**
	 * Busca los pedidos consolidados de un cliente.
	 * Encuentra el pedido consolidado dado satisfactoriamente.
	 */ 
	@Test
	public void test_buscarPedidoConsolidado() {
		Cliente c = new Cliente();
		cs.save(c);
		Pedido p = new Pedido(LocalDate.now(), true, c, 50, 50, 50, EstadoPedido.ENTREGADO, null);
		ps.save(p);
		List<Pedido> pedidosEsperados = new ArrayList<Pedido>();
		pedidosEsperados.add(p);
		assertEquals(true, ps.buscarPedidoConsolidados(c).contains(p));
	}
	/**
	 * Busca el pedido no consolidado.
	 * Encuentra el pedido no consolidado dado satisfactoriamente.
	 */
	@Test
	public void test_buscarPedidoNoConsolidado() {
		Cliente c = new Cliente();
		cs.save(c);
		Pedido p = new Pedido(LocalDate.now(), false, c, 100, 50, 50, EstadoPedido.ENTREGADO, null);
		ps.save(p);
		assertEquals(p, ps.buscarPedidoNoConsolidado(c));
	}
	/**
	 * Elimina un Pedido.
	 * Elimina el pedido dado satisfactoriamente.
	 */
	@Test
	public void test_cancelarPedido() {
		Pedido p = new Pedido(LocalDate.now(), false, null, 100, 50, 50, EstadoPedido.ENTREGADO, null);
		ps.save(p);
		ps.cancelarPedido(p.getId());
		assertEquals(false, ps.findAll().contains(p));
	}
	/**
	 * Crea un pedido si no hay un pedido no consolidado en la lista de búsqueda de pedidos no consolidados.
	 * Crea el pedido satisfactoriamente.
	 */
	@Test
	public void test_crearPedido() {
		Cliente c = new Cliente();
		cs.save(c);
		Pedido p = new Pedido(LocalDate.now(), true, c, 100, 50, 50, EstadoPedido.ENTREGADO, null);
		Pedido p2 = new Pedido(LocalDate.now(), true, c, 100, 50, 50, EstadoPedido.ENTREGADO, null);
		ps.save(p);
		ps.save(p2);
		ps.crearPedido(c);
		assertEquals(false, ps.buscarPedidoNoConsolidado(c).isConsolidado());
	}
	/**
	 * Se prueba el método test_crearPedidoPrueba de forma contraria, que no lo cree, que lo rescate.
	 * Rescata satisfactoriamente el pedido no consolidado del cliente.
	 */
	@Test
	public void test_crearPedidoPrueba2() {
		Cliente c = new Cliente();
		cs.save(c);
		Pedido p = new Pedido(LocalDate.now(), false, c, 100, 50, 50, EstadoPedido.ENTREGADO, null);
		Pedido p2 = new Pedido(LocalDate.now(), true, c, 100, 50, 50, EstadoPedido.ENTREGADO, null);
		ps.save(p);
		ps.save(p2);
		ps.crearPedido(c);
		assertEquals(p, ps.buscarPedidoNoConsolidado(c));
	}
	/**
	 * Comprueba que no se añada una LineaPedido con un producto ya existente en la lista de LineaPedidos de un Pedido pero sí que edite la LineaPedido existente.
	 * Edita la LineaPedido con el producto dado ya existente satisfactoriamente.
	 * 
	 */
	@Test
	public void test_anadirLineaPedido() {
		Cliente c = new Cliente();
		Pedido p = new Pedido(LocalDate.now(), false, c, 100, 50, 50, EstadoPedido.ENTREGADO,
				new ArrayList<LineaPedido>());
		Producto pr = new Producto();
		LineaPedido lp = new LineaPedido(26, pr, pr.getPrecio(), p);
		cs.save(c);
		ps.save(p);
		prs.save(pr);
		lps.save(lp);
		p.anadirLinea(lp);
		ps.edit(p);
		ps.anadirLineaPedido(p, pr, 52, c);
		lp.setCantidad(52);
		assertEquals(52, p.getLinea().get(p.getLinea().indexOf(lp)).getCantidad());
		
	}
	/**
	 * Elimina una LineaPedido en un Pedido.
	 * Elimina satisfactoriamente la LineaPedido.
	 */
	@Test
	public void test_eliminarLineaPedido() {
		Cliente c = new Cliente();
		Pedido p = new Pedido(LocalDate.now(), false, c, 100, 50, 50, EstadoPedido.ENTREGADO,
				new ArrayList<LineaPedido>());
		Producto pr = new Producto();
		LineaPedido lp = new LineaPedido(26, pr, pr.getPrecio(), p);
		cs.save(c);
		ps.save(p);
		prs.save(pr);
		lps.save(lp);
		p.anadirLinea(lp);
		ps.edit(p);
		ps.eliminarLineaPedido(p,lp);
		assertEquals(false, p.getLinea().contains(lp));
	}
	/**
	 * Actualiza los atributos precioTotal y precioSubtotal de un Pedido.
	 * Actualiza las cantidades satisfactoriamente.
	 */
	@Test
	public void test_actualizarPedido() {
		Cliente c = new Cliente();
		Pedido p = new Pedido(LocalDate.now(), false, c, 0, 0, 0, EstadoPedido.ENTREGADO, new ArrayList<LineaPedido>());
		LineaPedido lp = new LineaPedido(1, null, 100, p);
		LineaPedido lp2 = new LineaPedido(2, null, 50, p);
		cs.save(c);
		ps.save(p);
		lps.save(lp);
		lps.save(lp2);
		p.anadirLinea(lp);
		p.anadirLinea(lp2);
		ps.edit(p);
		ps.actualizarPedido(p);
		assertEquals(200, p.getPrecioTotal(), 0.001);
		assertEquals(200, p.getPrecioSubtotal(), 0.001);
	}
	
}
