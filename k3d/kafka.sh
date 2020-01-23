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
  --set service.type=NodePort \
  --set service.nodePort=30092 \
  --set advertisedListeners='PLAINTEXT://kafka:9092'

kubectl delete deploy kafka-manager -n kafka
helm delete --purge kafka-manager

# --set zkHosts >> https://github.com/yahoo/kafka-manager/issues/430
helm install stable/kafka-manager -n kafka-manager --namespace kafka \
  --set zkHosts=kafka-zookeeper:2181 \
  --set service.type=NodePort \
  --set ingress.enabled=true 

# helm ls
# helm status kafka

# $ vi /etc/hosts
# 127.0.0.1	kafka
# 127.0.0.1	kafka-manager.local
# 
# kafka-console-producer --broker-list      localhost:9092 --topic test
# kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning
# 
# kafka-console-producer --broker-list      localhost:9092 --topic uppercase-in-0
# kafka-console-consumer --bootstrap-server localhost:9092 --topic uppercase-out-0
# kafka-console-consumer --bootstrap-server localhost:9092 --topic uppercase-out-0 --from-beginning

# Error-Producer:
#   - [2020-01-20 17:03:55,816] ERROR Error when sending message to topic test with key: null, value: 1 bytes with error: (org.apache.kafka.clients.producer.internals.ErrorLoggingCallback)
#     org.apache.kafka.common.errors.TimeoutException: Topic test not present in metadata after 60000 ms.
#   - Solution: Expose NodePort instead ContainerPort. Only Pods deployed on Master Node can expose ContainerPorts.
#
#   - [2020-01-21 11:06:40,133] WARN [Producer clientId=console-producer] Error connecting to node kafka-0.kafka-headless.kafka.svc.cluster.local:9092 (id: 1001 rack: null) (org.apache.kafka.clients.NetworkClient)
#     java.net.UnknownHostException: kafka-0.kafka-headless.kafka.svc.cluster.local
#       at java.net.InetAddress.getAllByName0(InetAddress.java:1280)
#       at java.net.InetAddress.getAllByName(InetAddress.java:1192)
#       at java.net.InetAddress.getAllByName(InetAddress.java:1126)
#       at org.apache.kafka.clients.ClientUtils.resolve(ClientUtils.java:104)
#       at org.apache.kafka.clients.ClusterConnectionStates$NodeConnectionState.currentAddress(ClusterConnectionStates.java:403)
#       at org.apache.kafka.clients.ClusterConnectionStates$NodeConnectionState.access$200(ClusterConnectionStates.java:363)
#       at org.apache.kafka.clients.ClusterConnectionStates.currentAddress(ClusterConnectionStates.java:151)
#       at org.apache.kafka.clients.NetworkClient.initiateConnect(NetworkClient.java:943)
#       at org.apache.kafka.clients.NetworkClient.access$600(NetworkClient.java:68)
#       at org.apache.kafka.clients.NetworkClient$DefaultMetadataUpdater.maybeUpdate(NetworkClient.java:1114)
#       at org.apache.kafka.clients.NetworkClient$DefaultMetadataUpdater.maybeUpdate(NetworkClient.java:1005)
#       at org.apache.kafka.clients.NetworkClient.poll(NetworkClient.java:537)
#       at org.apache.kafka.clients.producer.internals.Sender.runOnce(Sender.java:335)
#       at org.apache.kafka.clients.producer.internals.Sender.run(Sender.java:244)
#   - Solution: Change advertisedListeners from kafka-0.kafka-headless.kafka.svc.cluster.local to localhost