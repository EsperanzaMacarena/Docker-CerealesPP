package com.salesianostriana.cerealespp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.cerealespp.formbean.FormBeanLineaPedido;
import com.salesianostriana.cerealespp.formbean.FormBeanProducto;
import com.salesianostriana.cerealespp.model.Cliente;
import com.salesianostriana.cerealespp.model.LineaPedido;
import com.salesianostriana.cerealespp.model.Pedido;
import com.salesianostriana.cerealespp.model.Producto;
import com.salesianostriana.cerealespp.service.ClienteServicio;
import com.salesianostriana.cerealespp.service.LineaPedidoServicio;
import com.salesianostriana.cerealespp.service.PedidoServicios;
import com.salesianostriana.cerealespp.service.ProductoServicios;
import com.salesianostriana.cerealespp.service.UsuarioServicio;

/**
 * Esta clase gestiona las peticiones realizadas por cualquier tipo de usuario
 * (Administrador, Cliente, anónimo).
 * 
 * @author Esperanza M Escacena M
 *
 */

@Controller

public class AppController {
	@Autowired
	private ClienteServicio cs;
	@Autowired
	private ProductoServicios ps;
	@Autowired
	private UsuarioServicio us;
	@Autowired
	private PedidoServicios peds;
	@Autowired
	private LineaPedidoServicio lps;
	private boolean errorEmailSignUp;

	/**
	 * Muestra la página de inicio.
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de inicio.
	 */
	@GetMapping("/")
	public String getInicio(Model model, Principal p) {

		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("buscarProducto", new FormBeanProducto());
		return "pagEstaticas/inicio";
	}

	/**
	 * Muestra la página de Términos y condiciones.
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de Términos y condiciones.
	 */
	@GetMapping("/terminos")
	public String getTerminosCondiciones(Model model, Principal p) {
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("buscarProducto", new FormBeanProducto());
		return "pagEstaticas/terminos";
	}

	/**
	 * Muestra la página de protección de datos
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de Política de protección de datos.
	 */
	@GetMapping("/proteccionDatos")
	public String getProteccionDatos(Model model, Principal p) {
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("buscarProducto", new FormBeanProducto());
		return "pagEstaticas/proteccionDatos";
	}

	/**
	 * Muestra la plantilla de contacto, donde se muestra un formulario (dummy)
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de contacto.
	 */
	@GetMapping("/contactUs")
	public String getContactanos(Model model, Principal p) {
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("buscarProducto", new FormBeanProducto());

		return "pagEstaticas/contactUs";
	}
	/**
	 * Recoge los datos de la plantilla de contacto.
	 * 
	 * @return Redirecciona al inicio.
	 */
	@PostMapping("/contactUs/submit")
	public String enviarFormContacto() {
		return "redirect:/";
	}

	/**
	 * Muestra la plantilla de preguntas frecuentes
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de FAQ.
	 */
	@GetMapping("/faq")
	public String getFaq(Model model, Principal p) {
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("buscarProducto", new FormBeanProducto());

		return "pagEstaticas/faq";
	}

	/**
	 * Muestra la plantilla sobre nosotros
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla sobre nosotros.
	 */
	@GetMapping("/aboutUs")
	public String getSobreNosotros(Model model, Principal p) {
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("buscarProducto", new FormBeanProducto());

		return "pagEstaticas/aboutUs";
	}
	

	/**
	 * Muestra la plantilla de cesta en la que listará los productos añadidos a un
	 * pedido no consolidado o si no hay productos un aviso. Los usuarios no
	 * registrados pueden acceder a la cesta pero para añadir y comprar deben estar
	 * registrados, con lo cual solo podrán visualizarla con el aviso de "No hay
	 * productos en la cesta"
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de cesta.
	 */
	@GetMapping("/cesta")
	public String mostrarProductosAnadidos(Model model, Principal p) {
		if (p != null) {
			Cliente c=cs.findById(us.buscarUsuarioLogged(p).getId());

			Pedido pedido = peds.buscarPedidoNoConsolidado(c);
		
			if(pedido==null) {
				peds.crearPedido(c);
				pedido = peds.buscarPedidoNoConsolidado(c);
				System.out.println(pedido);
				System.out.println(pedido.getLinea());
			}
			System.out.println(pedido);
			
			if(pedido.getLinea()!=null) {
				if(!pedido.getLinea().isEmpty()) {
					for(LineaPedido l: pedido.getLinea()) {
						l.getProductoAnadido().setFileUrl("/images/" + l.getProductoAnadido().getFileUrl());
					}
				}
			}
			
			
			model.addAttribute("pedido", pedido);
			model.addAttribute("lineaPedido", new FormBeanLineaPedido());
			
		}
		
		model.addAttribute("buscarProducto", new FormBeanProducto());
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));

		return "pagEstaticas/cesta";
	}

	/**
	 * Recoge los datos de una línea de pedido añadida en la cesta desde la
	 * plantilla de cesta cuando el usuario modifica la cantidad del producto que
	 * desea comprar.
	 * 
	 * @param id    Id del pedido
	 * @param model Modelo
	 * @param form  Formbean que recoge información sobre la cantidad de la línea de
	 *              pedido.
	 * @param p     Usuario logueado en ese momento.
	 * @return Redirecciona a la plantilla de cesta.
	 */
	@PostMapping("/cesta/modificar/{id}")
	public String modificarCesta(@PathVariable("id") Long id, Model model,
			@ModelAttribute("lineaPedido") FormBeanLineaPedido form, Principal p) {
		Pedido pedido = lps.findById(id).getPedido();
		Cliente c = cs.findById(us.buscarUsuarioLogged(p).getId());
		peds.anadirLineaPedido(pedido, lps.findById(id).getProductoAnadido(), form.getCantidad(), c);
		peds.calcularGastosEnvio(pedido);
		peds.actualizarPedido(pedido);
		return "redirect:/cesta";
	}

	/**
	 * Borra una línea de pedido en la Plantilla cesta
	 * 
	 * @param id id de la línea de pedido.
	 * @return Redirecciona a la plantilla de cesta.
	 */
	@GetMapping("/cesta/borrar/{id}")
	public String borrar(@PathVariable("id") long id) {
		Pedido p = lps.findById(id).getPedido();
		peds.eliminarLineaPedido(p, lps.findById(id));
		peds.calcularGastosEnvio(p);
		peds.actualizarPedido(p);
		return "redirect:/cesta";
	}

	/**
	 * Gestiona el formulario de registro.
	 * 
	 * @param model Modelo
	 * @return Plantilla de registro.
	 */
	@GetMapping("/signup")
	public String registrarse(Model model) {
		Cliente c = new Cliente();
		model.addAttribute("formularioRegistro", c);
		model.addAttribute("buscarProducto", new FormBeanProducto());
		if (errorEmailSignUp) {
			model.addAttribute("errorEmail", errorEmailSignUp);
			errorEmailSignUp=false;
		}
		return "user/signup";
	}

	/**
	 * Recoge la información del formulario de registro y guarda en la BBDD el nuevo
	 * usuario (Cliente).
	 * 
	 * @param c     Cliente nuevo.
	 * @param model Modelo
	 * @return Redirecciona al inicio.
	 */
	@PostMapping("/signup/submit")
	public String submit(@ModelAttribute("formularioRegistro") Cliente c, Model model) {
		if (us.comprobarEmail(c.getEmail())) {
			errorEmailSignUp = true;
			return "redirect:/signup";
		} else {
			errorEmailSignUp = false;
			BCryptPasswordEncoder encriptar=new BCryptPasswordEncoder();
			c.setPassword(encriptar.encode(c.getPassword()));
			cs.save(c);
			return "redirect:/";
		}

	}

	/**
	 * Lista los productos procesados con stock mayor o igual a 25. También crea o
	 * busca un pedido no consolidado del cliente logueado.
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento
	 * @return Plantilla que lista los productos.
	 */
	@GetMapping("/procesados")
	public String getProductosProcesados(Model model, Principal p) {
		model.addAttribute("buscarProducto", new FormBeanProducto());
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		if (p != null) {
			peds.crearPedido(cs.findById(us.buscarUsuarioLogged(p).getId()));
		}
		model.addAttribute("lineaPedido", new FormBeanLineaPedido());
		model.addAttribute("p", ps.buscarProductosProcesadosConStock());
		return "pagEstaticas/productos";
	}

	/**
	 * Recoge la línea de pedido añadida a la cesta para añadirlo al pedido. Una vez
	 * añadida, actualiza los datos sobre el transporte y el precio total del
	 * pedido.
	 * 
	 * @param id    Id del pedido
	 * @param model Modelo
	 * @param form  FormBean de la línea de pedido que recoge la cantidad.
	 * @param p     Usuario logueado en ese momento.
	 * @return Redirecciona a la plantilla de productos, que listará los productos
	 *         procesados o no en función del tipo de producto añadido.
	 */
	@PostMapping("/producto/cesta/{id}")
	public String productoProcesadoACarrito(@PathVariable("id") Long id, Model model,
			@ModelAttribute("lineaPedido") FormBeanLineaPedido form, Principal p) {
		Cliente c = cs.findById(us.buscarUsuarioLogged(p).getId());
		Producto prod = ps.findById(id);
		Pedido noConsolidado = peds.buscarPedidoNoConsolidado(c);
		peds.anadirLineaPedido(noConsolidado, prod, form.getCantidad(), c);
		System.out.println("PEDIDOOOOO"+noConsolidado);
		peds.calcularGastosEnvio(noConsolidado);
		peds.actualizarPedido(noConsolidado);
		if (prod.isProcesado()) {
			return "redirect:/procesados";
		} else {
			return "redirect:/noprocesados";
		}

	}

	/**
	 * Lista los productos no procesados con stock mayor o igual a 25. También crea
	 * o busca un pedido no consolidado del cliente logueado.
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento
	 * @return Plantilla que lista los productos.
	 */
	@GetMapping("/noprocesados")
	public String getProductosNoProcesados(Model model, Principal p) {
		model.addAttribute("buscarProducto", new FormBeanProducto());

		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		if (p != null) {
			peds.crearPedido(cs.findById(us.buscarUsuarioLogged(p).getId()));
		}
		model.addAttribute("lineaPedido", new FormBeanLineaPedido());

		model.addAttribute("p", ps.buscarProductoNoProcesadoConStock());

		return "pagEstaticas/productos";
	}

	/**
	 * Recoge la palabra con la que se quiere buscar un producto. Se buscara
	 * ignorando minús. y mayús. y deberá contenerla.
	 * 
	 * @param fbp   FormBean que recoge el nombre de un producto.
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla que lista los resultados. Si no encuentra, avisará de ello.
	 */
	@PostMapping("/buscar")
	public String listarResultadosBusquedaProductos(@ModelAttribute("buscarProducto") FormBeanProducto fbp, Model model,
			Principal p) {
		List<Producto> encontrados = ps.buscarProductoNombre(fbp.getNombre());
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));

		model.addAttribute("lineaPedido", new FormBeanLineaPedido());
		model.addAttribute("productos", encontrados);
		return "pagEstaticas/busquedaProductos";
	}

}
