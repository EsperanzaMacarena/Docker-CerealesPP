/**
 * 
 */
package com.salesianostriana.cerealespp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salesianostriana.cerealespp.model.LineaPedido;
import com.salesianostriana.cerealespp.model.Producto;

/**
 * Interfaz que implementa capa de abstracción entre la capa de acceso a datos de una LineaPedido y la capa de lógica de negocios (servicios). 
 * @author Esperanza M Escacena M
 *
 */
public interface LineaPedidoRepository extends JpaRepository<LineaPedido, Long> {
	LineaPedido findByProductoAnadido(Producto p);
}
