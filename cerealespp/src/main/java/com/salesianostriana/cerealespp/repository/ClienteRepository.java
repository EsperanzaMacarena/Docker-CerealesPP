/**
 * 
 */
package com.salesianostriana.cerealespp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salesianostriana.cerealespp.model.Cliente;
/**
 * Interfaz que implementa capa de abstracción entre la capa de acceso a datos de un Cliente y la capa de lógica de negocios (servicios). 
 * @author Esperanza M Escacena M
 *
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	List<Cliente> findByNomEmpresaContainingIgnoreCase(String nomEmpresa);
	
	
}
