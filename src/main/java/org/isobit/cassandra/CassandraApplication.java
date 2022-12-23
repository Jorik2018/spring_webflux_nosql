package org.isobit.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.nio.file.Path;
import org.isobit.cassandra.DataStaxAstraProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "org.isobit.cassandra.dao")
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class CassandraApplication {

	public static void main(String[] args) {
		SpringApplication.run(CassandraApplication.class, args);
	}

	@Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle)
		.withAuthCredentials("ywCmubBIPEbQvZxDYazrbxkH","ODahyq0GUMtH7hzcf6dOmDJGQcb0u49rXASl9+m0clAuh9az41DykZ1tJnfXMi4f9q5+9HzL029W+n4prWvFNv3HD,oFh3l7f0uQIj3QAHLNY7XJXKxEUbpWey6e2gSD")
		.withKeyspace("main");
    }
	
}
