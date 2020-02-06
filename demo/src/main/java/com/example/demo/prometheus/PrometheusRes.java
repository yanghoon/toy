package com.example.demo.prometheus;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * https://stackoverflow.com/questions/47542485/jackson-deserialize-plain-json-array-to-a-single-java-object
 * 
 * https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations
 * https://stackoverflow.com/questions/52542662/cant-deserialize-instant-after-serialization-with-jackson
 * https://github.com/FasterXML/jackson-modules-java8/tree/master/datetime
 * https://github.com/FasterXML/jackson-modules-java8/wiki
 */
public class PrometheusRes {
    public String status;
    public PrometheusData data;
    
    public static class PrometheusData {
        public String resultType;
        public List<PrometheusResult> result;
    }

    /*
     * @JsonAlias({"value", "values"})
     */
    public static class PrometheusResult {
        public Map<String, String> metric;

        @JsonInclude(Include.NON_NULL)
        public PrometheusValue value;

        @JsonInclude(Include.NON_NULL)
        public List<PrometheusValue> values;
    }

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    public static class PrometheusValue {
        @JsonProperty
        public Double time;
        @JsonProperty
        public String val;
    }
}