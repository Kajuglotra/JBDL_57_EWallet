package org.gfg.UserService;

import org.gfg.UserService.model.User;
import org.gfg.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${service.authority}")
	private String serviceAuthority;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = User.builder().contact("txn-service").
				password(passwordEncoder.encode("txn-service")).email("txnservice@gmail.com").
				authorities(serviceAuthority).
				build();
		userRepository.save(user);
	}
}
