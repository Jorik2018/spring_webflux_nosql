package org.isobit.nosql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.nio.file.Path;
import org.isobit.nosql.cassandra.DataStaxAstraProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

//https://www.baeldung.com/spring-data-cassandra-tutorial
//https://github.com/DataStax-Examples/spring-k8s-cassandra-microservices/blob/master/microservice-spring-data/src/main/java/com/datastax/examples/SpringDataCassandraConfiguration.java

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "org.isobit.nosql.mongodb")
//@EnableConfigurationProperties (DataStaxAstraProperties.class)
public class NoSQLApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoSQLApplication.class, args);
	}

	/*@Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle)
		.withAuthCredentials("ywCmubBIPEbQvZxDYazrbxkH","ODahyq0GUMtH7hzcf6dOmDJGQcb0u49rXASl9+m0clAuh9az41DykZ1tJnfXMi4f9q5+9HzL029W+n4prWvFNv3HD,oFh3l7f0uQIj3QAHLNY7XJXKxEUbpWey6e2gSD")
		.withKeyspace("main");
    }
	*/
}
