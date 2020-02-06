package com.salesianostriana.cerealespp.service;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.salesianostriana.cerealespp.model.LineaPedido;
import com.salesianostriana.cerealespp.service.LineaPedidoServicio;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineaPedidoServicioTest {
	@Autowired
	LineaPedidoServicio lps;
	/**
	 * Calcula el precio total de una LineaPedido.
	 * El precio es calculado satisfactoriamente.
	 */
	@Test
	public void test_calcularPrecioTotalLinea() {
		LineaPedido prueba= new LineaPedido(26,null,100,null);
		assertEquals(2600,lps.calcularPrecioTotalLinea(prueba),0.001);
	}
	
}
