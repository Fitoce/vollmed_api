package med.voll.api.infra.security;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;

@Component
public class SecurityFilter extends  OncePerRequestFilter {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterchain) throws IOException, ServletException {
		System.out.println("este es el inicio del filtro");
		
		//obtener el token del header
		
		var authHeader = request.getHeader("Authorization");
		
		//if(token == null || token == "") {                    de esta manera filtra todo  el if de abajo es la inversion de este metodo de if
		//	throw new RuntimeException("El token enviado no es valido");
		
		if(authHeader != null) {
			var token = authHeader.replace("Bearer ", "");
			var subject = tokenService.getSubjet(token);  //aqui extrae el nombre de usuario  extrac username
			if(subject != null) {
				//Token valido
				var usuario = usuarioRepository.findByLogin(subject);
				var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities()); //forzamos inicio de sesion
				SecurityContextHolder.getContext().setAuthentication(authentication); //setear manualmente esa autenticacion
			}
			
		}
		filterchain.doFilter(request, response);
		
	}
	
	

}
