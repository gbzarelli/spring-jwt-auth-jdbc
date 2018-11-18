package br.com.helpdev.springjwtauthjdbc.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Classe que filtra as requisições para garantir que elas estejam autenticadas.
 * <p>
 * Class that filters the requests to ensure they are authenticated.
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    /**
     * Verifica se há autenticação no request.
     *
     * @param request     ServletRequest
     * @param response    ServletResponse
     * @param filterChain FilterChain
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = JWTAuth
                .getAuthentication((HttpServletRequest) request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
