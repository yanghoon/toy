#!/bin/bash
# https://argoproj.github.io/argo-cd/getting_started/
# https://argoproj.github.io/argo-cd/operator-manual/ingress/#option-2-multiple-ingress-objects-and-hosts

curl -OL https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

export KUBECONFIG=$(k3d get-kubeconfig)
kubectl create namespace argo
kubectl delete -n argo -f install.yaml
kubectl apply -n argo -f install.yaml
kubectl delete -n argo -f ingress.yaml
kubectl apply -n argo -f ingress.yaml

# kubectl get pods -n argo -l app.kubernetes.io/name=argocd-server -o custom-columns=:metadata.name --no-headers=true > .password
kubectl wait pod -n argo -l app.kubernetes.io/name=argocd-server --for=condition=Ready --timeout 180s
kubectl get  pod -n argo -l app.kubernetes.io/name=argocd-server | grep Running | cut -d' ' -f1 > .password
kubectl patch deploy argocd-server -n argo -p '[{"op": "add", "path": "/spec/template/spec/containers/0/command/-", "value": "--insecure"}]' --type json

kubectl wait pod -n argo -l app.kubernetes.io/name=argocd-server --for=condition=Ready --timeout 180s
which argocd
argocd login argocd.local:8080 --grpc-web --name admin --password $(cat .password)
argocd account update-password --grpc-web --current-password $(cat .password)