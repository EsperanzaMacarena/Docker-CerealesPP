package com.salesianostriana.cerealespp.service;

import org.springframework.stereotype.Service;

import com.salesianostriana.cerealespp.model.LineaPedido;
import com.salesianostriana.cerealespp.repository.LineaPedidoRepository;

/**
 * Clase de lógica de negocio que concierne a una LineaPedido.
 * 
 * @author Esperanza M Escacena M
 *
 */
@Service
public class LineaPedidoServicio extends ServicioBase<LineaPedido, Long, LineaPedidoRepository> {
	/**
	 * Calcula el precio de la línea de pedido multiplicando el precio unitario de
	 * un producto por la cantidad añadida a la cesta.
	 * 
	 * @param lp LineaPedido a calcular.
	 * @return precio total de la LineaPedido.
	 */
	public double calcularPrecioTotalLinea(LineaPedido lp) {
		return lp.getCantidad() * lp.getPrecioUnidad();
	}

}
