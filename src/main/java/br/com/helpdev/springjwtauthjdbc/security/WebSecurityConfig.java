package br.com.helpdev.springjwtauthjdbc.security;

import br.com.helpdev.springjwtauthjdbc.security.jwt.JWTAuthenticationFilter;
import br.com.helpdev.springjwtauthjdbc.security.jwt.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * Classe principal das configurações de segurança das requisições.
 * Main class of security settings for requests.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    /**
     * AutoWired contructor
     * <p>
     * Os parametros construidos automaticamentes, a definição do DataSource e do PasswordEncoder foi definida no
     * Application do sistema com a anotação @Bean
     * <p>
     * The parameters built automatically, the DataSource and PasswordEncoder definition were defined in the
     * System Application with the @Bean annotation
     *
     * @param dataSource
     * @param passwordEncoder
     */
    @Autowired
    public WebSecurityConfig(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método para configuar o modo de autenticação, configurar os usuarios e permissões;
     * Method to configure the authentication mode, configure the users and permissions;
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Cria um usuario em memoria.
        //Creates a user in memory
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(passwordEncoder.encode("xCvSDs$46gG"))
                .roles("ADMIN", "USER");

        //-----

        //Configura banco de dados de usuarios para realizar autenticações;
        //Configure user database to perform authentication;
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?");
    }

    /**
     * Método de configuração das requisições. Determina permissões de entrada e regras de validações
     * Method of configuration of the requisitions. Determines inbound permissions and validation rules
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                //Libera a segurança do método POST em '/login'
                //Releases security of method POST in '/login'
                .antMatchers(HttpMethod.POST, "/login").permitAll()

                //Libera a segurança do método GET em '/open'
                //Releases security of method GET in '/open'
                .antMatchers(HttpMethod.GET, "/open").permitAll()

                //Define que tudo que tiver em '/admin' só podera ser acessado por usuario que tiver pemissao ADMIN
                //Sets that everything in '/admin' can only be accessed by user who has ADMIN
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                //.antMatchers("/admin").hasRole("ADMIN")//Automatic using prefix *ROLE*

                //Define que o restante deve ser autenticado
                //Sets that rest to be authenticated
                .anyRequest().authenticated()
                .and()

                //Filtra requisições de login (converte entrada para validação)
                //Filters login requests (converts input for validation)
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)

                //Filtra as demais requisições para verificar a presença do JWT no header
                //Filters the others requests to verify presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)

                //Desabilita o CSRF?!
                .csrf().disable();
    }
}
