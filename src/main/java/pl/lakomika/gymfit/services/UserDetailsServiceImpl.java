package pl.lakomika.gymfit.services;


import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lakomika.gymfit.entity.MyUserDetails;
import pl.lakomika.gymfit.repository.UserAppRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAppRepository userAppRepository;

    public UserDetailsServiceImpl(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userAppRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono nazwy użytkownika: " + username));

        return MyUserDetails.build(user);
    }
}
