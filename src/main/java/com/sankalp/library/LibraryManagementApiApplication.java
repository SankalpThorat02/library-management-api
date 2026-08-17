package com.sankalp.library;

import com.sankalp.library.entity.User;
import com.sankalp.library.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class LibraryManagementApiApplication implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public  LibraryManagementApiApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApiApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername("sankalp");

        if(optionalUser.isEmpty()) {
            User user = new User();
            user.setUsername("sankalp");

            user.setPassword(passwordEncoder.encode("hello123"));

            userRepository.save(user);
        }
    }
}
