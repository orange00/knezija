package knezija.configs;

import knezija.services.IUserManager;
import knezija.services.UserManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public IUserManager userManager() {
		return new UserManager();
	}

}
