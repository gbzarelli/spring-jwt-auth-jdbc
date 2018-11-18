package br.com.helpdev.springjwtauthjdbc.security.jwt;

import br.com.helpdev.springjwtauthjdbc.model.AuthRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Classe responsavel em filtrar o path de login definido no WebSecurityConfig, realiza a conversão da entrada
 * http em objeto, constroi e realiza a autenticação.
 * <p>
 * Class responsible for filtering the login path defined in WebSecurityConfig, performs the conversion of the
 * http entry into the object, builds and performs the authentication.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    /**
     * Método responsável em tratar a entrada da requisição no path configurado.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Authentication
     * @throws AuthenticationException erro de autenticação
     * @throws IOException             erro na leitura ou escrita de dados
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        AuthRequestModel credentials = new ObjectMapper()
                .readValue(request.getInputStream(), AuthRequestModel.class);

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    /**
     * Esse método é chamado após uma autenticação realizada com sucesso. O Token é gerado nesse momento.
     *
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param filterChain FilterChain
     * @param auth        Authentication
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) {

        JWTAuth.addAuthentication(response, auth);
    }

}
