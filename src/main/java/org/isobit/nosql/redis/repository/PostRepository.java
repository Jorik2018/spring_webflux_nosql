package org.isobit.nosql.redis.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.isobit.nosql.redis.entity.Post;

@Repository
@RequiredArgsConstructor
public class PostRepository {
	
    private final ReactiveRedisOperations<String, Post> reactiveRedisOperations;

    public Flux<Post> findAll(){
        return this.reactiveRedisOperations.opsForList().range("posts", 0, -1);
    }

    public Mono<Post> findById(String id) {
        return this.findAll().filter(p -> p.getId().equals(id)).last();
    }


    public Mono<Long> save(Post post){
        return this.reactiveRedisOperations.opsForList().rightPush("posts", post);
    }

    public Mono<Boolean> deleteAll() {
        return this.reactiveRedisOperations.opsForList().delete("posts");
    }

}