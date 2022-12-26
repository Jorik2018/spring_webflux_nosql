package org.isobit.nosql.cassandra;

import java.net.InetSocketAddress;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Path;
import java.time.Duration;
import org.springframework.data.cassandra.core.convert.*;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.mapping.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DataCenterReplication;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator;
import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;
import javax.annotation.PostConstruct;
import org.isobit.nosql.cassandra.DataStaxAstraProperties;

@Configuration
@EnableConfigurationProperties(CassandraProperties.class)
//@EnableConfigurationProperties (DataStaxAstraProperties.class)
@EnableReactiveCassandraRepositories
public class SpringDataCassandraConfiguration extends AbstractReactiveCassandraConfiguration {
	
    @Autowired
    private CassandraProperties cassandraProperties;

    @Value("${datastax.astra.secure-connect-bundle:none}")
    private String astraSecureConnectBundle;

    @Value("${spring.data.cassandra.username:cassandra}")
    private String username;

    @Value("${spring.data.cassandra.password:cassandra}")
    private String password;

	@Value("${spring.data.cassandra.keyspace-name:main}")
    private String keyspaceName;
	
    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

	/*@Bean
    public CassandraMappingContext cassandraMapping() 
      throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }*/
	/*@Bean
public CassandraMappingContext mappingContext() throws ClassNotFoundException {
    CassandraMappingContext mappingContext= new CassandraMappingContext();
    mappingContext.setInitialEntitySet(getInitialEntitySet());
    return mappingContext;
}*/
	
    /*@Override
    protected String getLocalDataCenter() {
        return cassandraProperties.getLocalDatacenter();
    }

    @Override
    protected int getPort() {
        return cassandraProperties.getPort();
    }*/

	/*@Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        //Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(Paths.get(astraSecureConnectBundle))
		.withAuthCredentials(username, password)
		.withKeyspace(keyspaceName);
    }
	*/
	
	@Bean
    public org.springframework.boot.autoconfigure.cassandra.DriverConfigLoaderBuilderCustomizer defaultProfile(){
        return builder -> builder.withString(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, "20 seconds")
		.withString(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, "20 seconds")
		.withString(DefaultDriverOption.REQUEST_TIMEOUT, "20 seconds").build();
    }
	
    @Override
    protected SessionBuilderConfigurer getSessionBuilderConfigurer() {
		return new SessionBuilderConfigurer() {
            @Override
            public CqlSessionBuilder configure(CqlSessionBuilder cqlSessionBuilder) {
				System.out.println("astraSecureConnectBundle==============="+astraSecureConnectBundle);
        
                if (!astraSecureConnectBundle.equals("none")) {
                    return cqlSessionBuilder
					.withConfigLoader(DriverConfigLoader.programmaticBuilder()
                                // Resolves the timeout query 'SELECT * FROM system_schema.tables' timed out after PT2S
                                .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofMillis(60000))
                                .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofMillis(60000))
                                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(15000))
                                .build())
                            .withCloudSecureConnectBundle(Paths.get(astraSecureConnectBundle))
                            .withAuthCredentials(username, password).withKeyspace(keyspaceName);
                }
                else{
                    return cqlSessionBuilder
                            .addContactPoint(new InetSocketAddress(
                                    cassandraProperties.getContactPoints().get(0),
                                    cassandraProperties.getPort()))
                            .withAuthCredentials(username, password);
                }
            }
        };
    }
/*
    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        if (astraSecureConnectBundle.equals("none")) {
            return Arrays.asList(CreateKeyspaceSpecification
                    .createKeyspace(getKeyspaceName())
                    .ifNotExists(true)
                    .withNetworkReplication(DataCenterReplication.of(getLocalDataCenter(), 1))
                    .with(KeyspaceOption.DURABLE_WRITES));
        }
        return Arrays.asList();
    }
	
	@Bean
public CassandraConverter converter() throws ClassNotFoundException
{
    return new MappingCassandraConverter(mappingContext());
}*/

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    /*@Override
    protected KeyspacePopulator keyspacePopulator() {
        ResourceKeyspacePopulator keyspacePopulate = new ResourceKeyspacePopulator();
        keyspacePopulate.setSeparator(";");
        keyspacePopulate.setScripts(new ClassPathResource("orders-schema.cql"));
        return keyspacePopulate;
    }*/

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{ SpringDataCassandraConfiguration.class.getPackageName() };
    }

}