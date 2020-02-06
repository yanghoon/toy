package com.example.demo.stream;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Profile("processor")
@Configuration
public class UppercaseProcessor /* implements Consumer<String>, Supplier<Flux<String>> */ {
    // private EmitterProcessor<String> processor = EmitterProcessor.create();
    // private FluxSink<String> sink = processor.sink();

	// @Override
	// public void accept(String value) {
    //     sink.next(value);
	// }

	// @Override
	// public Flux<String> get() {
	// 	return processor;
	// }

	EmitterProcessor<String> processor = EmitterProcessor.create();
	@Bean
	public Consumer<String> uppercase_in() {
		// DEFAULT-QUEUE-NAME: uppercase_in-in-0 (read)
		FluxSink<String> sink = processor.sink();
		return value -> {
			System.out.format(">> %s\n", value);
			sink.next(value.toUpperCase());
		    // processor.onNext(value);
		};
	}
	@Bean
	public Supplier<Flux<String>> uppercase_out() {
		// DEFAULT-QUEUE_NAME: uppercase_out-out-0 (write)
		// FluxSink<String> sink = processor.sink();
		// sink.next("asdf");
		return () -> processor.log();
	}
}