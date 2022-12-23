package org.isobit.cassandra;

import org.springframework.boot.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.datastax.oss.driver.api.core.CqlSession;

//@Configuration
public class Config {

  public @Bean CqlSession session() {
    return CqlSession.builder().withKeyspace("main").build();
  }
}