#!/bin/bash

kubectl delete ns monitoring
kubectl create ns monitoring

helm delete prometheus --purge
helm install stable/prometheus -n prometheus --namespace monitoring -f - <<EOF
server:
  ingress:
    enabled: true
    hosts:
    - prometheus.local
EOF

# http://prometheus.local:8080/api/v1/query_range?query=node_memory_Active_bytes&start=1580700340.772&end=1580703940.772&step=14