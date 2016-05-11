package knezija.configs;

import javax.servlet.Filter;

import knezija.services.IUserManager;
import knezija.services.UserManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import filters.TabsFilter;

@Configuration
public class AppConfig {

	@Bean
	public IUserManager userManager() {
		return new UserManager();
	}

	@Bean
	public Filter tabsFilter() {
		return new TabsFilter();
	}
}
