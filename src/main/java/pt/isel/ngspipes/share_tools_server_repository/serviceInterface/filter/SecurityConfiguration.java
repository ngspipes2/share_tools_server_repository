package pt.isel.ngspipes.share_tools_server_repository.serviceInterface.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_tools_server_repository.serviceInterface.config.Routes;

import javax.sql.DataSource;

@CrossOrigin
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()

            .antMatchers(HttpMethod.OPTIONS).permitAll()

            .antMatchers(HttpMethod.GET, Routes.GET_ALL_TOOLS_REPOSITORIES).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_TOOLS_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(HttpMethod.POST, Routes.INSERT_TOOLS_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.UPDATE_TOOLS_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.DELETE_TOOLS_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_TOOLS_REPOSITORIES_OF_USER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())

            .antMatchers(Routes.GET_TOOLS_REPOSITORY_IMAGE).permitAll()
            .antMatchers(Routes.UPDATE_TOOLS_REPOSITORY_IMAGE).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.DELETE_TOOLS_REPOSITORY_IMAGE).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())

            .and().httpBasic()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, true from _user where username=?")
            .authoritiesByUsernameQuery("select username, concat('ROLE_', role) from _user where username=?")
            .passwordEncoder(passwordEncoder);
    }

}
