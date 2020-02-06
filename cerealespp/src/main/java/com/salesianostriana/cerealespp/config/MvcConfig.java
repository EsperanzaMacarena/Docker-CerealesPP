package com.salesianostriana.cerealespp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Esta clase contiene las configuraciones de MVC sobre las rutas estáticas y controllers de login.
 * @author Esperanza M Escacena M
 *
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	   /**
	    * {@inheritDoc}
	    * @param registry Objeto que maneja registros de recursos estáticos.
	    */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/images/**")
			.addResourceLocations("file:files/");	
	    registry
	    	.addResourceHandler("/webjars/**")
	    	.addResourceLocations("classpath:/META-INF/resources/webjars/");
	    registry
    	.addResourceHandler("/css/**")
    	.addResourceLocations("classpath:/static/css/");
	    registry
    	.addResourceHandler("/img/**")
    	.addResourceLocations("classpath:/static/img/");
	    registry
    	.addResourceHandler("/js/**")
    	.addResourceLocations("classpath:/static/js/");
			
	}
	
	/**
	 * {@inheritDoc}
	 * @param registry Objeto que añade un controlador preconfigurado.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/");
		registry.addViewController("/acceso-denegado");
	}
	
	

}
