package org.isobit.nosql.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DateTimeProvider {

    public Instant now() {
        return Instant.now();
    }
}
