package com.example.demo.prometheus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@ConditionalOnBean(PrometheusTemplate.class)
@ConditionalOnProperty("prometheus.restful")
@Component
@RestController
public class PrometheusController {
    @Autowired(required = false)
    private PrometheusTemplate template;

    @GetMapping("{path:query|query_range}/{metric}")
    @ResponseBody
    public Object queryAny(
            @RequestParam(name="s", required=false, defaultValue="default") final String server,
            @PathVariable final String metric,
            @PathVariable final String path) throws RestClientException {

        Object res = null;
        if("query".equals(path))
            res = template.query(server, metric);
        else if("query_range".equals(path))
            res = template.queryRange(server, metric);
		return res;
    }
}