package com.example.demo.prometheus;

import java.net.URI;

import com.example.demo.prometheus.PrometheusProperties.Prometheus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@ConditionalOnBean(PrometheusProperties.class)
@Component
public class PrometheusTemplate {
    private Logger logger = LoggerFactory.getLogger(PrometheusTemplate.class);

    @Autowired
    private PrometheusProperties props;

    @Autowired
    private RestTemplate template;

    public Object queryRange(String server, String metric) throws RestClientException {
        return query(server, metric, "/api/v1/query_range");
    }

    public Object query(String server, String metric) throws RestClientException {
        return query(server, metric, "/api/v1/query");
    }

    // http://prometheus.local:8080/api/v1/query_range?query=node_memory_Active_bytes&start=1580700340.772&end=1580703940.772&step=14
    // http://prometheus.local:8080/api/v1/query?query=node_memory_Active_bytes&start=1580775795.152&end=1580779395.152&step=14
    private Object query(String server, String metric, String path) throws RestClientException {

        Prometheus host = props.getServers().get(server);
        if(host == null)
            throw new IllegalArgumentException("There is no configured prometheus server.");
        
        String alias = host.getMetrics().get(metric);
        boolean senstive = host.getSenstive();
        if (alias == null)
            if(senstive)
                throw new IllegalArgumentException("There is no configured prometheus metrics.");
            else
                alias = metric;
        
        long now = System.currentTimeMillis();
        long millis = now % 1000;
        long end    = now / 1000;
        long start  = end - 3600;

        String prometheusUrl = host.getUrl();
        URI uri = UriComponentsBuilder.fromUriString(prometheusUrl)
            .path(path)
            .queryParam("query", alias)
            .queryParam("start", start + "." + millis)
            .queryParam("end", end + "." + millis)
            .queryParam("step", "14")
            .build()
            .toUri();

        logger.info(uri.toString());

        // Map<?, ?> res = template.getForObject(uri, Map.class);
        PrometheusRes res = template.getForObject(uri, PrometheusRes.class);
        return res;
    }
}