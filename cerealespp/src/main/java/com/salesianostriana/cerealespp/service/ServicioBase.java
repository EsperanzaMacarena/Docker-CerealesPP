/**
 * 
 */
package com.salesianostriana.cerealespp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Esperanza M Escacena M
 * Clase Crud que sirve de base para todas nuestras entidades. Para ello extendemos la interfaz
 * JpaRepository, y utlizamos sus métodos y los de sus superinterfaces, como CrudRepository
 */

public abstract class ServicioBase <T, ID, R extends JpaRepository<T, ID>> {
	@Autowired	
	protected R repositorio;
	/**
	 * Guardamos una nueva entidad
	 * @param t Objeto.
	 * @return Objeto.
	 */
	public T save(T t) {
	
		return repositorio.save(t);
	}
	/**
	 * Buscamos una entidad por su id
	 * @param id Id del objeto
	 * @return Objeto.
	 */
	public T findById(ID id) {
		return repositorio.findById(id).orElse(null);
	}
	/**
	 * Obtenemos una lista de todas las entidades
	 * @return Lista con todos los objetos.
	 */
	public List<T> findAll() {
		return repositorio.findAll();
	}
	/**
	 * Editamos una entidad
	 * @param t Objeto
	 * @return Objeto editado.
	 */
	public T edit(T t) {
		return repositorio.save(t);
	}
	/**
	 * Eliminamos una entidad.
	 * @param t Objeto a eliminar.
	 */
	public void delete(T t) {
		repositorio.delete(t);
	}
	/**
	 * Eliminar una entidad por su id
	 * @param id Id del objeto a eliminar.
	 */
	public void deleteById(ID id) {
		repositorio.deleteById(id);
	}
	
	
}
