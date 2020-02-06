package com.salesianostriana.cerealespp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.salesianostriana.cerealespp.formbean.FormBeanProducto;
import com.salesianostriana.cerealespp.model.Cliente;
import com.salesianostriana.cerealespp.model.Pedido;
import com.salesianostriana.cerealespp.pdf.FacturaPdf;
import com.salesianostriana.cerealespp.service.ClienteServicio;
import com.salesianostriana.cerealespp.service.LineaPedidoServicio;
import com.salesianostriana.cerealespp.service.PedidoServicios;
import com.salesianostriana.cerealespp.service.UsuarioServicio;

@Controller
public class ClienteController {
	@Autowired
	private ClienteServicio cs;
	@Autowired
	private PedidoServicios ps;
	@Autowired
	private FacturaPdf pdf;
	@Autowired
	private UsuarioServicio us;

	/**
	 * Muestra el perfil de un cliente. Muestra la información gracias al objeto
	 * Principal.
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla del perfil de usuario.
	 */
	@GetMapping("/user/perfil")
	public String getPerfil(Model model, Principal p) {
		model.addAttribute("buscarProducto", new FormBeanProducto());

		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		return "user/perfil";
	}

	/**
	 * Muestra un formulario para editar el perfil de un cliente.
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla para editar un usuario.
	 */
	@GetMapping("/user/editarperfil")
	public String editarPerfilUser(Model model, Principal p) {
		model.addAttribute("buscarProducto", new FormBeanProducto());

		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		return "user/editarPerfil";
	}

	/**
	 * Recoge la información de la plantilla de editar usuario y lo guarda (edita)
	 * en la BBDD.
	 * 
	 * @param c     Cliente a editar.
	 * @param model Modelo
	 * @return Redirecciona al perfil del cliente logueado.
	 */
	@PostMapping("/user/editarperfil/submit")
	public String editarPerfilUserSubmit(@ModelAttribute("usuarioLogged") Cliente c, Model model) {
		cs.edit(c);
		return "redirect:/user/perfil";
	}

	/**
	 * Lista los pedidos del usuario logueado
	 * 
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de pedidos (vista usuario).
	 */
	@GetMapping("/user/pedidos")
	public String getPedidosUser(Model model, Principal p) {
		model.addAttribute("buscarProducto", new FormBeanProducto());
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("pedidos", ps.buscarPedidoConsolidados(cs.findById(us.buscarUsuarioLogged(p).getId())));
		return "user/pedidos";
	}

	/**
	 * Muestra la información de un pedido en concreto de un cliente logueado.
	 * 
	 * @param id    Id del pedido a mostrar.
	 * @param model Modelo
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de detalle del pedido (vista usuario).
	 */
	@GetMapping("/user/pedido/{id}")
	public String getDetallePedidoUser(@PathVariable("id") long id, Model model, Principal p) {
		model.addAttribute("buscarProducto", new FormBeanProducto());

		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		Pedido pedido = ps.findById(id);
		
		if (p != null) {
			model.addAttribute("pedido", pedido);

			model.addAttribute("lineaS", new LineaPedidoServicio());

			return "user/detallePedido";
		} else {
			return "redirect:/user/inicio";
		}
	}

	/**
	 * Genera un pdf conviertiéndolo en un Array de byte.
	 * 
	 * @param model Modelo
	 * @param id    ID del pedido
	 * @return PDF del pedido.
	 * @throws IOException Excepción
	 */
	@GetMapping("user/pedido/{id}/print/pdf")
	public ResponseEntity<InputStreamResource> generarPdfPedido(Model model, @PathVariable("id") long id)
			throws IOException {
		Pedido p = ps.findById(id);

		ByteArrayInputStream b = pdf.generarPdf(p);
		HttpHeaders h = new HttpHeaders();
		h.add("Content-Disposition", "inline; filename=pedido.pdf");
		return ResponseEntity.ok().headers(h).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(b));

	}

	/**
	 * Muestra la plantilla de confirmar pedido (pagar)
	 *  
	 * @param model Modelo
	 * @param id    ID del pedido
	 * @param p     Usuario logueado en ese momento.
	 * @return Plantilla de pago.
	 */
	@GetMapping("/user/pago/{id}")
	public String pagar(Model model, @PathVariable("id") long id, Principal p) {
		model.addAttribute("buscarProducto", new FormBeanProducto());
		model.addAttribute("usuarioLogged", us.buscarUsuarioLogged(p));
		model.addAttribute("pedido", ps.findById(id));
		return "user/pago";
	}

	/**
	 * Recoge la información de la tarjeta de crédito de la plantilla de pago y
	 * confirma el pedido.
	 * 
	 * @param id id del pedido a confirmar.
	 * @return Redirige al método que imprime un pdf del pedido.
	 */
	@PostMapping("user/pago/{id}/submit")
	public String submitPagar(@PathVariable("id") long id) {
		System.out.println(ps.findById(id));
		ps.confirmarPedido(ps.findById(id));
		return "redirect:/user/pedido/{id}/print/pdf";
	}

}
