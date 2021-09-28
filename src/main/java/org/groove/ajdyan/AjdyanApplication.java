package org.groove.ajdyan;

import org.groove.ajdyan.entity.authority.Authority;
import org.groove.ajdyan.entity.authority.AuthorityRepository;
import org.groove.ajdyan.entity.user.User;
import org.groove.ajdyan.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
@Controller
@Configuration
@EnableWebMvc
public class AjdyanApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	public static void main(String[] args) {
		SpringApplication.run(AjdyanApplication.class, args);
	}


	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/view/");
		bean.setSuffix(".jsp");
		return bean;
	}

	@Override
	public void run(String... args) throws Exception {
		Authority admin = new Authority("ROLE_ADMIN");
		Authority user = new Authority("ROLE_USER");
		User aj = new User("ajheflin", passwordEncoder.encode("testpass"), "AJ Heflin", true);
		User rachel = new User("rachelhester", passwordEncoder.encode("testpass2"), "Rachel Hester", true);
		user.addUser(aj);
		user.addUser(rachel);
		admin.addUser(aj);
		authorityRepository.save(user);
		authorityRepository.save(admin);
		userRepository.save(aj);
		userRepository.save(rachel);
	}
}
