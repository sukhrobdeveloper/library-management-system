package sukhrob.developer.rest_api.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sukhrob.developer.rest_api.entities.Role;
import sukhrob.developer.rest_api.entities.User;
import sukhrob.developer.rest_api.payload.PermissionEnum;
import sukhrob.developer.rest_api.repo.RoleRepository;
import sukhrob.developer.rest_api.repo.UserRepository;
import sukhrob.developer.rest_api.utilities.AppConstant;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${DataLoaderMode")
    private String mode;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always")) {
            Role admin = roleRepository.save(new Role(
                    AppConstant.ADMIN,
                    "bu admin",
                    new HashSet<>(Arrays.asList(PermissionEnum.values()))
            ));
            Role user = roleRepository.save(new Role(
                    AppConstant.USER,
                    "bu user",
                    new HashSet<>()
            ));
            User adminUser = userRepository.save(new User(
                    "admin",
                    "adminov",
                    "sukhrob_dev",
                    "s@gmail.com",
                    passwordEncoder.encode("sukhrob_1234"),
                    admin,
                    null,
                    true
            ));
        }

    }
}
