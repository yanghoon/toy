# Kubernetes (k3d)
## Usage
```bash
$ bash k3d.sh   # and tiller.sh

$ cd app && bash kafka.sh

$ cd monitoring && bash prometheus.sh
$ cd monitoring && bash grafana.sh

$ cd auth && bash keycloak.sh
```
### ArgoCD
```bash
## Setup
$ cd gitops/argocd && bash argo.sh

## Config DNS
## - https://github.com/coredns/coredns/issues/1260
## - kube-system/coredns
# Corefile: |
#   rewrite name regex (.*)\.local traefik.kube-system.svc.cluster.local
```

## Issues
### Helm
https://github.com/helm/helm/issues/6374#issuecomment-533185074

## Middleware
### Kafka
https://bitnami.com/stack/kafka/helm