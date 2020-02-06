/**
 * 
 */
package com.salesianostriana.cerealespp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salesianostriana.cerealespp.model.DatosMaestros;

/**
 * Interfaz que implementa capa de abstracción entre la capa de acceso a datos de los DatosMaestros y la capa de lógica de negocios (servicios). 
 * @author Esperanza M Escacena M
 *
 */
public interface DatosMaestrosRepository extends JpaRepository<DatosMaestros, Long> {

}
