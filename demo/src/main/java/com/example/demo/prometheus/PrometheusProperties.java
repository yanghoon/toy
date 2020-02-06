package com.example.demo.prometheus;

import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/*
 * Not Working !!!
 *   @ConditionalOnProperty(name = "prometheus.servers", havingValue = "")  // not working
 *   https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/condition/ConditionalOnProperty.html
 * 
 * Using @ConditionalOnExpression
 *   https://stackoverflow.com/a/26458255
 */
@Component
@ConditionalOnExpression("'${prometheus.servers}' != ''")
@ConfigurationProperties(prefix = "prometheus")
public class PrometheusProperties {
    @NestedConfigurationProperty
    private Map<String, Prometheus> servers;

    public void setServers(final Map<String, Prometheus> servers) {
        this.servers = servers;
    }

    public Map<String, Prometheus> getServers() {
        return servers;
    }

    public static class Prometheus {
        private String url;
        private boolean senstive = false;
        private Map<String, String> metrics;

        public void setUrl(String url){
            if(!url.startsWith("http"))
                url = "http://" + url;

            this.url = url;
        }
        public String getUrl(){
            return url;
        }

        public void setSenstive(boolean senstive){
            this.senstive = senstive;
        }
        public boolean getSenstive(){
            return this.senstive;
        }

        public void setMetrics(Map<String, String> metrics){
            this.metrics = metrics;
        }
        public Map<String, String> getMetrics(){
            return metrics;
        }
    }
}