package com.salesianostriana.cerealespp.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.salesianostriana.cerealespp.model.Administrador;
import com.salesianostriana.cerealespp.model.Usuario;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServicioTest {
	@Autowired
	UsuarioServicio us;
	/**
	 * Busca por email un usuario.
	 * Encuentra satisfactoriamente al usuario.
	 */
	@Test
	public void test_buscarPorEmail() {
		Usuario usu=new Administrador("prueba","prueba","1234","p@p.com");
		us.save(usu);
		assertEquals(usu, us.buscarPorEmail("p@p.com"));
	}
	/**
	 * Comprueba si el email dado existe ya en la BBDD.
	 * La comprobación es satisfactoria.
	 */
	public void test_comprobarEmail() {
		Usuario usu=new Administrador("prueba","prueba","1234","p@p.com");
		us.save(usu);
		assertEquals(true, us.comprobarEmail("p@p.com"));
	}
	/**
	 * Comprueba si el email dado existe ya en la BBDD. Esta vez se le da un email no salvado en la BBDD.
	 * La comprobación es satisfactoria.
	 */
	public void test_comprobarEmail2() {
		assertEquals(false, us.comprobarEmail("p@p.com"));
	}
}
