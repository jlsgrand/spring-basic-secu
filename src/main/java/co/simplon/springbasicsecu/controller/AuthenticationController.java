package co.simplon.springbasicsecu.controller;

import co.simplon.springbasicsecu.model.AppUser;
import co.simplon.springbasicsecu.model.Role;
import co.simplon.springbasicsecu.repository.AppUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;

    private final AppUserRepository appUserRepository;

    public AuthenticationController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Point d'entrée API permettant de créer un compte utilisateur standard (pouvant lire les citations)
     *
     * @param newUser l'utilisateur à créer
     * @return l'utilisateur nouvellement créé si le username n'est pas déjà existant
     */
    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody AppUser newUser) {
        Optional<AppUser> existingUser = appUserRepository.findByUsernameIgnoreCase(newUser.getUsername());

        // Pour faire simple on renvoie juste un code d'erreur 400, mais il faudrait renvoyer plus d'information pour que l'utilisateur comprenne le pb.
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            // On encode le mot de passe pour éviter de l'avoir en clair dans la BDD
            String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
            // On crée un nouvel utilisateur avec le role reader par défaut et le mot de passe hashé
            newUser = new AppUser(newUser.getUsername(), encryptedPassword, Collections.singletonList(Role.ROLE_USER));
            // On sauve l'utilisateur
            appUserRepository.save(newUser);
            return ResponseEntity.ok().build();
        }
    }
}
