/**
 * 
 */
package com.salesianostriana.cerealespp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum que contiene los diferentes estados por los que pasa un pedido.
 * @author Esperanza M Escacena M
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum EstadoPedido {
	PAGADO ("Pagado"),
	PROCESADO ("Procesado"),
	ENVIADO ("Enviado"),
	TRANSITO ("En tránsito"),
	ENTREGADO ("Entregado");
	private String descripcion;
	
}
