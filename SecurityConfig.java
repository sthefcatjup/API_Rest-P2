import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/tarefas/**", "/colaboradores/**").permitAll() // Permitir acesso sem autenticação
            .anyRequest().authenticated() // Todos os outros endpoints exigem autenticação
            .and()
            .httpBasic(); // Usando autenticação básica (Você pode trocar por JWT, por exemplo)
    }
}
