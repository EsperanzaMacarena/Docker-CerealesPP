package com.salesianostriana.cerealespp.formbean;

import com.salesianostriana.cerealespp.model.EstadoPedido;

import lombok.Data;

import lombok.NoArgsConstructor;
/**
 * FormBean que recogerá un EstadoPedido de un formulario para poder modificar el EstadoPedido de un pedido.
 * @author Esperanza M Escacena M
 *
 */
@Data @NoArgsConstructor
public class FormBeanEstado {
	private EstadoPedido estado;
	private String descripcion;
}
