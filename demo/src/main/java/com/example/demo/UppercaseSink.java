package com.example.demo;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Profile("sink")
@Configuration
public class UppercaseSink {
    private FluxSink<String> sink = null;

	@Bean
	public Consumer<String> uppercase_in() {
        return value -> sink.next(value);
	}

	@Bean
	public Supplier<Flux<String>> uppercase_out() {
		return () -> Flux.create(sink-> {
			UppercaseSink.this.sink = sink;
		});
	}
}