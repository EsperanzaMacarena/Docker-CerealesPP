package com.salesianostriana.cerealespp;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.salesianostriana.cerealespp.model.Administrador;
import com.salesianostriana.cerealespp.model.Cliente;
import com.salesianostriana.cerealespp.model.Usuario;
import com.salesianostriana.cerealespp.service.AdministradorServicio;
import com.salesianostriana.cerealespp.service.ClienteServicio;
import com.salesianostriana.cerealespp.service.UsuarioServicio;
/**
 * Main de la aplicación
 * @author Esperanza M Escacena M
 *
 */
@SpringBootApplication
public class CerealesppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CerealesppApplication.class, args);
	}
	/**
	 * Método para encriptar las contraseñas de los usuarios al iniciar el programa
	 * @param us Servicio del Usuario para listar a todos.
	 * @param encriptar Objeto que encripta las contraseñas
	 * @return Contraseñas de usuarios encriptadas.
	 */
	@Bean
	public CommandLineRunner init(UsuarioServicio us, BCryptPasswordEncoder encriptar) {
		return args -> {
			/*Cliente c=cs.findById(1001L);
			c.setPassword(encriptar.encode(c.getPassword()));
			cs.save(c);
			Administrador a=ad.findById(1002L);
			a.setPassword(encriptar.encode(a.getPassword()));
			ad.save(a);
			Cliente c2=cs.findById(1003L);
			c2.setPassword(encriptar.encode(c2.getPassword()));
			cs.save(c2);*/
			
			for(Usuario usu: us.findAll()) {
				usu.setPassword(encriptar.encode(usu.getPassword()));
				us.save(usu);
				System.out.println(usu);
			}
			
			
		};
	}
}
