/**
 * 
 */
package com.salesianostriana.cerealespp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;



/**
 * Esta clase proviene del ejemplo que podemos encontrar en <a href="http://websystique.com/spring-security/spring-security-4-role-based-login-example/">aquí</a>
 * 
 * @author WebSystique
 *
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			System.out.println("Can't redirect");
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/**
	 * Método que extrae los roles del usuario logueado y retorna el URL según el rol
	 * 
	 */
	protected String determineTargetUrl(Authentication authentication) {
		String url = "";

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}

		if (isAdmin(roles)) {
			url = "/";
		} else if (isUser(roles)) {
			url = "/";
		} else {
			url = "/acceso-denegado";
		}

		return url;
	}
	/**
	 * Método que determina si el rol del Usuario es tipo usuario.
	 * @param roles Lista de roles.
	 * @return true si es usuario o false si no lo es.
	 */
	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_USER")) {
			return true;
		}
		return false;
	}
	/**
	 * Método que determina si el rol del Usuario es tipo admin.
	 * @param roles Lista de roles.
	 * @return true si es usuario o false si no lo es.
	 */
	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}

