package com.salesianostriana.cerealespp.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

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
public class ProductoServiciosTest {
	@Autowired
	PedidoServicios ps;
	@Autowired
	ClienteServicio cs;
	@Autowired
	ProductoServicios prs;
	@Autowired
	LineaPedidoServicio lps;
	/**
	 * Busca un producto por su nombre.
	 * Encuentra el producto por un nombre satisfactoriamente.
	 */
	@Test
	public void test_buscarProductoNombre() {
		Producto pr = new Producto("prueba",false,100,200,null);
		prs.save(pr);
		pr.setFileUrl("/images/null");
		assertEquals(true, prs.buscarProductoNombre("prueba").contains(pr));
	
	}
	/**
	 * Busca los productos de tipo procesado con stock mayor a 25.
	 * Encuentra los productos satisfactoriamente.
	 */
	@Test
	public void test_buscarProductosProcesadosConStock() {
		Producto pr = new Producto("prueba",true,100,200,null);
		prs.save(pr);
		pr.setFileUrl("/images/null");
		assertEquals(true, prs.buscarProductosProcesadosConStock().contains(pr));
	}
	/**
	 * Busca los productos de tipo procesado con stock mayor a 25. Esta vez, pasamos un producto con stock menor o igual a 25.
	 * No lista el producto dado, por lo que lista satisfactoriamente los productos con stock procesados.
	 */
	@Test
	public void test_buscarProductosProcesadosConStock2() {
		Producto pr = new Producto("prueba",true,100,25,null);
		prs.save(pr);
		pr.setFileUrl("/images/null");
		assertEquals(false, prs.buscarProductosProcesadosConStock().contains(pr));
	}
	/**
	 * Busca los productos de tipo no procesado con stock mayor a 25.
	 * Encuentra los productos satisfactoriamente.
	 */
	@Test
	public void test_buscarProductosNoProcesadosConStock() {
		Producto pr = new Producto("prueba",false,100,200,null);
		prs.save(pr);
		pr.setFileUrl("/images/null");
		assertEquals(true, prs.buscarProductoNoProcesadoConStock().contains(pr));
	}
	/**
	 * Busca los productos de tipo no procesado con stock mayor a 25. Esta vez, pasamos un producto con stock menor o igual a 25.
	 * No lista el producto dado, por lo que lista satisfactoriamente los productos con stock no procesados.
	 */
	@Test
	public void test_buscarProductosNoProcesadosConStock2() {
		Producto pr = new Producto("prueba",true,100,25,null);
		prs.save(pr);
		pr.setFileUrl("/images/null");
		assertEquals(false, prs.buscarProductoNoProcesadoConStock().contains(pr));
	}
	/**
	 * Modifica el stock cuando se confirma un pedido (resta las cantidades que han sido compradas por un cliente al confirmar el pedido).
	 * Modifica el stock satisfactoriamente.
	 */
	@Test
	public void test_modificarStock() {
		Cliente c = new Cliente();
		Pedido p = new Pedido(LocalDate.now(), false, c, 100, 50, 50, EstadoPedido.ENTREGADO,
				new ArrayList<LineaPedido>());
		Producto pr = new Producto("prueba",false,100,200,null);
		LineaPedido lp = new LineaPedido(100, pr, pr.getPrecio(), p);
		cs.save(c);
		ps.save(p);
		prs.save(pr);
		lps.save(lp);
		p.anadirLinea(lp);
		ps.edit(p);
		prs.modificarStock(p);
		assertEquals(100, prs.findById(pr.getId()).getStock(),0.001);
	}
	/**
	 * Suma las cantidades de un pedido ya confirmado por que ha sido cancelado el pedido.
	 * Retorna las unidades de producto satisfactoriamente.
	 */
	@Test
	public void test_retornarStockPorCancelacion() {
		Cliente c = new Cliente();
		Pedido p = new Pedido(LocalDate.now(), false, c, 100, 50, 50, EstadoPedido.ENTREGADO,
				new ArrayList<LineaPedido>());
		Producto pr = new Producto("prueba",true,100,200,null);
		LineaPedido lp = new LineaPedido(100, pr, pr.getPrecio(), p);
		cs.save(c);
		ps.save(p);
		prs.save(pr);
		lps.save(lp);
		p.anadirLinea(lp);
		ps.edit(p);
		ps.confirmarPedido(p);
		prs.retornarStockPorCancelacion(p);
		assertEquals(200,prs.findById(pr.getId()).getStock());
	}
	/**
	 * Lista todos los productos
	 * Lista todos los productos satisfactoriamente.
	 */
	@Test
	public void test_list() {
		Producto pr = new Producto("prueba",false,100,200,null);
		prs.save(pr);
		pr.setFileUrl("/images/null");
		assertEquals(true, prs.list().contains(pr));
	}
	
}
