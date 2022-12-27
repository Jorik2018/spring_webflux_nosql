package org.isobit.nosql.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import reactor.core.publisher.Mono;
import org.isobit.nosql.redis.entity.Post;
import org.isobit.nosql.redis.repository.PostRepository;
import java.io.IOException;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

	//Reactive

    @Bean
	@Primary
	//@Qualifier("reactive-redis")
    public ReactiveRedisConnectionFactory reactiveConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("redis-15567.c302.asia-northeast1-1.gce.cloud.redislabs.com");
        configuration.setPort(15567);
		configuration.setUsername("default");
		configuration.setPassword(org.springframework.data.redis.connection.RedisPassword.of("Pd0El4JYknPfDI7KUPfuU2rVxBHxCsLB"));
        return new LettuceConnectionFactory(configuration);
		/*LettuceConnectionFactory connectionFactory=new LettuceConnectionFactory("redis-15567.c302.asia-northeast1-1.gce.cloud.redislabs.com", 15567);
		connectionFactory.setUsername("default");
		connectionFactory.setPassword("Pd0El4JYknPfDI7KUPfuU2rVxBHxCsLB");
        return connectionFactory;*/
    }
	
	@Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("redis-15567.c302.asia-northeast1-1.gce.cloud.redislabs.com");
        configuration.setPort(15567);
		configuration.setUsername("default");
		configuration.setPassword(org.springframework.data.redis.connection.RedisPassword.of("Pd0El4JYknPfDI7KUPfuU2rVxBHxCsLB"));
        return new JedisConnectionFactory(configuration);
    }

	
//    @Bean
//    public ReactiveRedisConnectionFactory lettuceConnectionFactory() {
//
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//            .useSsl().and()
//            .commandTimeout(Duration.ofSeconds(2))
//            .shutdownTimeout(Duration.ZERO)
//            .build();
//
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379), clientConfig);
//    }
	
	@Bean
    public ReactiveRedisTemplate<String, Post> reactiveRedisTemplate(@Qualifier("reactiveConnectionFactory") ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<String, Post>(
            factory,
            RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer(Post.class))
        );
    }

	
	@Bean
    public ReactiveRedisMessageListenerContainer redisMessageListenerContainer(PostRepository posts,@Qualifier("reactiveConnectionFactory") ReactiveRedisConnectionFactory reactiveConnectionFactory) {
        ReactiveRedisMessageListenerContainer container = new ReactiveRedisMessageListenerContainer(reactiveConnectionFactory);
        ObjectMapper objectMapper = new ObjectMapper();
        container.receive(ChannelTopic.of("posts"))
			.map(p -> p.getMessage())
			.map(m -> {
				try {
					Post post = objectMapper.readValue(m, Post.class);
					post.setId(UUID.randomUUID().toString());
					return post;
				} catch (IOException e) {
					return null;
				}
			})
			.switchIfEmpty(Mono.error(new IllegalArgumentException()))
			.flatMap(p -> posts.save(p))
			.subscribe(c -> System.out.println(" count:" + c), null, () -> System.out.println("saving post."));
        return container;
    }
	
	//No reactive
	

    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

}