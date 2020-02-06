/**
 * 
 */
package com.salesianostriana.cerealespp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.salesianostriana.cerealespp.formbean.FormBeanEstado;
import com.salesianostriana.cerealespp.formbean.UploadFormBean;
import com.salesianostriana.cerealespp.model.Cliente;
import com.salesianostriana.cerealespp.model.EstadoPedido;
import com.salesianostriana.cerealespp.model.Pedido;
import com.salesianostriana.cerealespp.model.Producto;
import com.salesianostriana.cerealespp.service.ClienteServicio;
import com.salesianostriana.cerealespp.service.LineaPedidoServicio;
import com.salesianostriana.cerealespp.service.PedidoServicios;
import com.salesianostriana.cerealespp.service.ProductoServicios;

/**
 * Esta clase gestiona las peticiones del usuario Administrador.
 * 
 * @author Esperanza M Escacena M
 *
 */
@Controller
public class AdminController {
	@Autowired
	ClienteServicio cs;
	@Autowired
	private PedidoServicios ps;
	@Autowired
	private ProductoServicios prod;
	

	/**
	 * Lista a todos los clientes. Es la página de inicio del panel del
	 * Administrador.
	 * 
	 * @param model Modelo
	 * @return Plantilla HTML en la que tiene acceso solamente el administrador y
	 *         muestra la lista de clientes con información básica.
	 */
	@GetMapping({ "/admin", "/admin/clientes" })
	public String listarClientes(Model model) {
		model.addAttribute("clientesEncontrados", cs.findAll());
		return "admin/adminClientesListar";
	}

	/**
	 * Obtiene la información de un cliente en concreto y su historial de pedidos
	 * confirmados.
	 * 
	 * @param id    Id del Cliente a visualizar.
	 * @param model Modelo
	 * @return Plantilla HTML en la que tiene acceso solamente el administrador y
	 *                  muestra la información del cliente y una lista de los
	 *                  pedidos consolidados.
	 */
	@GetMapping("/admin/cliente/{id}")
	public String getDetalleCliente(@PathVariable("id") long id, Model model) {
		Cliente c = cs.findById(id);
		model.addAttribute("detalleCliente", c);
		model.addAttribute("listaPedido", ps.buscarPedidoConsolidados(c));
		return "admin/detalleCliente";

	}

	/**
	 * Lista de todos los pedidos realizados en la aplicación.
	 * 
	 * @param model Modelo
	 * @return Plantilla HTML en la que tiene acceso solamente el administrador y
	 *         muestra una lista de pedidos con información básica.
	 */
	@GetMapping("/admin/pedidos")
	public String getPedidos(Model model) {
		model.addAttribute("pedidos", ps.listarPedidosConsolidados());
		return "admin/adminPedidos";
	}

	/**
	 * Obtiene la información de un pedido en concreto por su ID.
	 * 
	 * @param id    Id del pedido.
	 * @param model Modelo
	 * @return Plantilla HTML en la que tiene acceso solamente el administrador y
	 *         muestra la información de un pedido en concreto.
	 */
	@GetMapping("/admin/pedido/{id}")
	public String getDetallePedidoCliente(@PathVariable("id") long id, Model model) {
		Pedido p = ps.findById(id);
	
		if (p != null) {
			model.addAttribute("pedido", p);
			model.addAttribute("lineaPedido", p.getLinea());
			model.addAttribute("pedidoS", ps);
			model.addAttribute("lineaS", new LineaPedidoServicio());
			model.addAttribute("estado", EstadoPedido.values());
			model.addAttribute("formbeanEstado", new FormBeanEstado());
			return "admin/detallePedido";
		} else {
			return "redirect:/admin/cliente";
		}
	}

	/**
	 * Lista todos los productos ofertados en la aplicación.
	 * 
	 * @param model Modelo
	 * @return Plantilla HTML en la que tiene acceso solamente el administrador y
	 *         muestra todos los productos y su información básica.
	 */
	@GetMapping("/admin/productos")
	public String getProductos(Model model) {
		model.addAttribute("producto",  prod.list());
		return "admin/adminProductos";
	}

	/**
	 * Edita un producto.
	 * 
	 * @param id    ID del producto en concreto.
	 * @param model Modelo
	 * @return Plantilla HTML en la que tiene acceso solamente el administrador y
	 *         muestra un formulario mediante el cual puede modificar cualquier
	 *         atributo del producto.
	 */
	@GetMapping("/admin/producto/editar/{id}")
	public String getProducto(@PathVariable("id") long id, Model model) {
		Producto p = prod.findById(id);

		if (p != null) {
			model.addAttribute("prodEditado", p);
			return "admin/detalleProducto";
		} else {
			return "redirect:/admin/productos";
		}
	}

	/**
	 * Obtiene la información recogida en el método getProducto para poder editar de
	 * forma efectiva el producto.
	 * @param id id del producto.
	 * @param p     Producto editado.
	 * @param file  Archivo de imagen a insertar en el producto si es que se edita.
	 * @return Retorna a la plantilla donde se listan todos los productos (vista
	 *         administrador).
	 */
	@PostMapping("/admin/producto/editar/{id}/submit")
	public String submitEditarProducto(@PathVariable("id") long id, @ModelAttribute("prodEditado") Producto p,
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {

			prod.add(p, file);
		} else {
			p.setFileUrl(prod.findById(id).getFileUrl());
			prod.edit(p);
		}

		return "redirect:/admin/productos";
	}

	/**
	 * Añade un producto a la base de datos.
	 * 
	 * @param model Modelo
	 * @return Plantilla HTML en la que tiene acceso solamente el administrador y
	 *         muestra un formulario para añadir un producto.
	 */
	@GetMapping("/admin/anadirproducto")
	public String addProducto(Model model) {
		model.addAttribute("productoNuevo", new UploadFormBean());
		return "admin/anadirProducto";
	}

	/**
	 * Recoge los datos del método addProducto y añade el nuevo producto a la base
	 * de datos.
	 * 
	 * @param p     Producto nuevo
	 * @param file  Archivo de imagen del producto nuevo
	 * @return Retorna a la plantilla donde se listan todos los productos (vista
	 *         administrador).
	 */
	@PostMapping("/admin/producto/nuevo/submit")
	public String submitNuevoProducto(@ModelAttribute("productoNuevo") UploadFormBean p,
			@RequestParam("file") MultipartFile file) {
		Producto producto = new Producto();
		producto.setNombre(p.getNombre());
		producto.setPrecio(p.getPrecio());
		producto.setProcesado(p.isProcesado());
		producto.setStock(p.getStock());
		prod.add(producto, file);
		return "redirect:/admin/productos";
	}

	/**
	 * Cancela/elimina un pedido. Solo puede ser realizado por el administrador.
	 * 
	 * @param id ID del pedido a cancelar.
	 * @return Retorna a la plantilla donde se listan todos los pedidos (vista
	 *         administrador).
	 */
	@GetMapping("/admin/pedido/{id}/cancelar")
	public String borrarPedido(@PathVariable("id") long id) {
		prod.retornarStockPorCancelacion(ps.findById(id));
		ps.cancelarPedido(id);
		return "redirect:/admin/pedidos";
	}

	/**
	 * Cambia el estado de un pedido. Solo puede se realizado por el administrador.
	 * 
	 * @param id id del pedido del que se va a modificar su estado de pedido.
	 * @param e  Nuevo estado
	 * @return Retorna a la plantilla del pedido en detalle (vista administrador).
	 */
	@PostMapping("/admin/pedido/{id}/submit")
	public String cambiarEstadoPedido(@PathVariable("id") long id, @ModelAttribute("formbeanEstado") FormBeanEstado e) {
		Pedido p = ps.findById(id);
		p.setEstado(e.getEstado());
		ps.edit(p);
		return "redirect:/admin/pedido/" + id;
	}
}
