package dk.innotech.user.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("dk.innotech.user.repositories")
public class JpaConfiguration {
}
