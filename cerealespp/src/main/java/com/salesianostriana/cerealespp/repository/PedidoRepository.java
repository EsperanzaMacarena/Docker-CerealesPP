/**
 * 
 */
package com.salesianostriana.cerealespp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.cerealespp.model.Cliente;
import com.salesianostriana.cerealespp.model.Pedido;

/**
 * Interfaz que implementa capa de abstracción entre la capa de acceso a datos de un Pedido y la capa de lógica de negocios (servicios). 
 * @author Esperanza M Escacena M
 *
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido>  findByClienteAndConsolidadoTrue(Cliente c);
	Pedido findByClienteAndConsolidadoFalse(Cliente c);
	List<Pedido> findByConsolidadoTrue();
	
}
