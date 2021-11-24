package co.simplon.springbasicsecu;

import co.simplon.springbasicsecu.model.AppUser;
import co.simplon.springbasicsecu.model.Role;
import co.simplon.springbasicsecu.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class SpringBasicSecuApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBasicSecuApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            appUserRepository.save(new AppUser("admin", passwordEncoder.encode("admin"), List.of(Role.ROLE_ADMIN)));
            appUserRepository.save(new AppUser("it_user", passwordEncoder.encode("it_user"), List.of(Role.ROLE_IT_USER)));
            appUserRepository.save(new AppUser("user", passwordEncoder.encode("user"), List.of(Role.ROLE_USER)));
        };
    }
}
