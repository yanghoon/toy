package com.example.rbac.grafana;

import com.example.rbac.console.CompanyService.Integrations;
import com.example.rbac.console.CompanyService.Tool;
import com.example.rbac.impl.keycloak.KeycloakConfig.IntegrationProperties;
import com.example.rbac.retrofit.BasicAuthInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Iterables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class GrafanaConfig {
    private Logger log = LoggerFactory.getLogger(GrafanaConfig.class);

    @Autowired
    private IntegrationProperties props;

    @Value("${test-realm}")
    private String realm;

    @Bean
    public Retrofit retrofit() {
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS).modules(new JavaTimeModule()).build();
        Converter.Factory converterFactory = JacksonConverterFactory.create(mapper);
        // CallAdapter.Factory adapterFactory = RxJava2CallAdapterFactory.create();

        Integrations tools = props.getIntegrations(realm);
        Tool grafana = Iterables.find(tools.getTools(), t -> t.getName().equals("grafana"));
        String endpoint = UriComponentsBuilder.fromHttpUrl(grafana.getEndpoint()).replacePath("/").build().toString();
        String url = endpoint;
        String username = grafana.getAttributes().get("username");
        String password = grafana.getAttributes().get("password");
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