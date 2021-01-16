package dk.innotech.user.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("dk.innotech.user.repositories")
@EnableJpaAuditing
public class JpaConfiguration {
}
