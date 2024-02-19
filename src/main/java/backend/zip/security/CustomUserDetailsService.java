package backend.zip.security;

import backend.zip.domain.user.User;
import backend.zip.global.exception.token.CustomUsernameNotFoundException;
import backend.zip.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static backend.zip.global.status.ErrorStatus.AUTHENTICATION_FAILED;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(
                () -> new CustomUsernameNotFoundException(AUTHENTICATION_FAILED));

        return new CustomUserDetails(user);
    }
}
