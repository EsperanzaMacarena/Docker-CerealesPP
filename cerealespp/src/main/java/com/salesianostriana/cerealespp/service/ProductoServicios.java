/**
 * 
 */
package com.salesianostriana.cerealespp.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesianostriana.cerealespp.model.Pedido;
import com.salesianostriana.cerealespp.model.Producto;
import com.salesianostriana.cerealespp.repository.ProductoRepository;
import com.salesianostriana.cerealespp.storage.StorageService;

/**
 * Clase de lógica de negocio que concierne a un Producto.
 * 
 * @author Esperanza M Escacena M
 *
 */
@Service
public class ProductoServicios extends ServicioBase<Producto, Long, ProductoRepository> {
	@Autowired
	private StorageService storageService;

	/**
	 * Busca un Producto por su nombre, ignorando mayús y mínus.
	 * 
	 * @param nomProducto Nombre del producto a buscar
	 * @return Productos que contengan ese nombre.
	 */
	public List<Producto> buscarProductoNombre(String nomProducto) {
		List<Producto> partialResult = this.repositorio.findByNombreContainingIgnoreCase(nomProducto);
		List<Producto> result = new LinkedList<Producto>(partialResult);

		for (int i = 0; i < partialResult.size(); i++) {
			String fileName = partialResult.get(i).getFileUrl();
			result.get(i).setFileUrl("/images/" + fileName);
		}

		return result;

	}

	/**
	 * Busca un Producto por su categoría y disponibilidad
	 * 
	 * @return Lista de productos procesados con stock mayor a 25 toneladas.
	 */
	public List<Producto> buscarProductosProcesadosConStock() {
		List<Producto> partialResult = this.repositorio.findByProcesadoTrueAndStockGreaterThan(25);
		List<Producto> result = new LinkedList<Producto>(partialResult);

		for (int i = 0; i < partialResult.size(); i++) {
			String fileName = partialResult.get(i).getFileUrl();
			result.get(i).setFileUrl("/images/" + fileName);
		}

		return result;

	}

	/**
	 * Busca un Producto por su categoría y disponibilidad
	 * 
	 * @return Lista de productos no procesados con stock mayor a 25 toneladas.
	 */
	public List<Producto> buscarProductoNoProcesadoConStock() {
		List<Producto> partialResult = this.repositorio.findByProcesadoFalseAndStockGreaterThan(25);
		List<Producto> result = new LinkedList<Producto>(partialResult);

		for (int i = 0; i < partialResult.size(); i++) {
			String fileName = partialResult.get(i).getFileUrl();
			result.get(i).setFileUrl("/images/" + fileName);
		}

		return result;

	}

	/**
	 * Modifica el stock de cada producto cada vez que se confirma un Pedido.
	 * Recorre las lineas de pedido del pedido para poder hacer la operación.
	 * 
	 * @param p Pedido confirmado.
	 */
	public void modificarStock(Pedido p) {
		p.getLinea()
			.stream()
			.forEach(lp -> {
				Producto aux = this.findById(lp.getProductoAnadido().getId());
				aux.setStock(aux.getStock() - lp.getCantidad());
				this.edit(aux);
			});
	}

	/**
	 * Modifica el stock de cada producto cada vez que se cancela un Pedido.
	 * 
	 * @param p Pedido a cancelar.
	 */
	public void retornarStockPorCancelacion(Pedido p) {
		p.getLinea()
			.stream()
			.forEach(lp -> {
				Producto aux = this.findById(lp.getProductoAnadido().getId());
				aux.setStock(aux.getStock() + lp.getCantidad());
				this.edit(aux);
			});
	}

	/**
	 * Añade un archivo img al Producto.
	 * 
	 * @param p    Producto al que añadir el archivo
	 * @param file Archivo img.
	 */
	public void add(Producto p, MultipartFile file) {
		String fileName = storageService.store(file);// Guarda la imagen
		// Guardamos nombre de la imagen almacenada en el atributo de la entidad
		p.setFileUrl(fileName);
		// Guardamos la entidad en la base de datos y en ella ya irá el nombre del
		// archivo
		// en la correspondiente propiedad (fileUrl)
		this.save(p);

	}

	/**
	 * Lista el Producto con el archivo.
	 * 
	 * @return Lista de todos los Productos.
	 */
	public List<Producto> list() {
		List<Producto> partialResult = this.findAll();
		List<Producto> result = new LinkedList<Producto>(partialResult);

		for (int i = 0; i < partialResult.size(); i++) {
			String fileName = partialResult.get(i).getFileUrl();
			result.get(i).setFileUrl("/images/" + fileName);
		}

		return result;
	}
}
