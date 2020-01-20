#!/bin/sh
# Used:
# - https://hub.kubeapps.com/charts/bitnami/kafka
# - https://hub.kubeapps.com/charts/stable/kafka-manager
# Ref:
# - https://bitnami.com/stack/kafka/helm
# - https://hub.kubeapps.com/charts/incubator/kafka

kubectl create ns kafka

helm delete --purge kafka
helm install bitnami/kafka -n kafka --namespace kafka \
  --set service.type=NodePort

kubectl delete deploy kafka-manager -n kafka
helm delete --purge kafka-manager
helm install stable/kafka-manager -n kafka-manager --namespace kafka \
  --set zkHosts=kafka-zookeeper:2181 \    # https://github.com/yahoo/kafka-manager/issues/430
  --set service.type=NodePort \
  --set ingress.enabled=true 

# helm ls
# helm status kafka