package com.example.rbac.grafana;

import com.example.rbac.retrofit.BasicAuthInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class GrafanaConfig {
    private Logger log = LoggerFactory.getLogger(GrafanaConfig.class);

    @Bean
    public Retrofit retrofit() {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .modules(new JavaTimeModule())
                .build();;
        Converter.Factory converterFactory = JacksonConverterFactory.create(mapper);
        // CallAdapter.Factory adapterFactory = RxJava2CallAdapterFactory.create();

        String url = "http://grafana.local:8080";
        String username = "admin";
        String password = "admin";
        // String token = "eyJrIjoiMHN3cUVPTUtPRUFVbXFPOFV5ZzZmT3FCc3JSRHI3VUMiLCJuIjoiY29uc29sZSIsImlkIjoxfQ==";
        // Interceptor interceptor = new CompositeInterceptor().token(token).body();
        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger(){
			public void log(String message) { log.debug(message); }
        };
        OkHttpClient client =
            new OkHttpClient.Builder()
                // .addInterceptor(new BearerInterceptor(token))
                .addInterceptor(new BasicAuthInterceptor(username, password))
                .addInterceptor(new HttpLoggingInterceptor(logger).setLevel(Level.BODY))
                .build();

        return new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converterFactory)
            // .addCallAdapterFactory(adapterFactory)
            .client(client)
            .build();
    }

    @Bean
    public GrafanaService grafanaService(Retrofit retrofit){
        return retrofit.create(GrafanaService.class);
    }
}