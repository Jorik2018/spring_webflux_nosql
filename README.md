SET PATH=H:\java\jdk-17.0.5+8\bin;%PATH%
SET JAVA_HOME=H:\java\jdk-17.0.5+8

https://lankydan.dev/2017/12/11/reactive-streams-with-spring-data-cassandra

DESCRIBE KEYSPACES;


REDIS
https://developer.redis.com/develop/java/spring/rate-limiting/fixed-window/reactive/
https://hantsy.github.io/spring-reactive-sample/data/data-redis.html
https://github.com/spring-guides/gs-spring-data-reactive-redis/tree/main/complete/src/main/java/hello
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Connection;

public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://default:Pd0El4JYknPfDI7KUPfuU2rVxBHxCsLB@redis-15567.c302.asia-northeast1-1.gce.cloud.redislabs.com:15567");
        Connection connection = jedis.getConnection();
    }
}

(Username: "default")
https://app.redislabs.com/
Pd0El4JYknPfDI7KUPfuU2rVxBHxCsLB