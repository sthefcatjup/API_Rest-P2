import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aqui você poderia buscar o usuário no banco de dados
        if ("admin".equals(username)) {
            return User.withUsername(username)
                       .password("{noop}admin123") // {noop} para não usar codificação de senha
                       .roles("USER")
                       .build();
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}
