package com.sankalp.library;

import com.sankalp.library.entity.User;
import com.sankalp.library.repository.UserRepository;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
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
            user.setRole("ADMIN");
            user.setPassword(passwordEncoder.encode("hello123"));

            userRepository.save(user);
        }

        SecretKey key = Jwts.SIG.HS256.key().build();
        String secret = Encoders.BASE64.encode(key.getEncoded());

        System.out.println(secret);
    }
}
