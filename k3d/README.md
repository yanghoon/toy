# Kubernetes (k3d)
## Usage
```bash
$ bash k3d.sh   # and tiller.sh

$ cd app && bash kafka.sh

$ cd monitoring && bash prometheus.sh
$ cd monitoring && bash grafana.sh

$ cd auth && bash keycloak.sh
```

## Issues
### Helm
https://github.com/helm/helm/issues/6374#issuecomment-533185074

## Middleware
### Kafka
https://bitnami.com/stack/kafka/helm