/**
 * 
 */
package com.salesianostriana.cerealespp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.salesianostriana.cerealespp.model.Producto;

/**
 * Interfaz que implementa capa de abstracción entre la capa de acceso a datos de un Producto y la capa de lógica de negocios (servicios). 
 * @author Esperanza M Escacena M
 *
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	/**
	 * Busca si el String que se le pasa como parámetro lo contiene el nombre del producto, ignorando si es mayús o minús.
	 * @param nombre Dato tipo String por el que se busca el producto.
	 * @return Lista de productos que contengan el parámetro: nombre.
	 */
	List <Producto> findByNombreContainingIgnoreCase(String nombre);
	/**
	 * Busca los productos procesados que tengan stock mayor a un número tipo int.
	 * @param vacio Número de tipo int.
	 * @return Lista de productos procesados con stock mayor a un número.
	 */
	List <Producto> findByProcesadoTrueAndStockGreaterThan(int vacio);
	/**
	 * Busca los productos no procesados que tengan stock mayor a un número tipo int.
	 * @param vacio Número de tipo int.
	 * @return Lista de productos no procesados con stock mayor a un número.
	 */
	List <Producto> findByProcesadoFalseAndStockGreaterThan(int vacio);

}
