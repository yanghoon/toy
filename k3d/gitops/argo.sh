#!/bin/bash
# https://argoproj.github.io/argo-cd/getting_started/
# https://argoproj.github.io/argo-cd/operator-manual/ingress/#option-2-multiple-ingress-objects-and-hosts
# https://github.com/argoproj/argo-cd/issues/1265#issuecomment-473086856

curl -OL https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

export KUBECONFIG=$(k3d get-kubeconfig)
ns=argo
kubectl create namespace $ns
kubectl delete -n $ns -f install.yaml
kubectl apply  -n $ns -f install.yaml
kubectl delete -n $ns -f ingress.yaml
kubectl apply  -n $ns -f ingress.yaml

kubectl patch clusterrolebinding -n $ns -p '[{"op": "replace", "path": "/subjects/0/namespace", "value": "'$ns'"}]' --type json argocd-application-controller 
kubectl patch clusterrolebinding -n $ns -p '[{"op": "replace", "path": "/subjects/0/namespace", "value": "'$ns'"}]' --type json argocd-server

# kubectl get pods -n $ns -l app.kubernetes.io/name=argocd-server -o custom-columns=:metadata.name --no-headers=true > .password
kubectl wait pod -n $ns -l app.kubernetes.io/name=argocd-server --for=condition=Ready --timeout 180s
kubectl get  pod -n $ns -l app.kubernetes.io/name=argocd-server | grep Running | cut -d' ' -f1 > .password
kubectl patch deploy argocd-server -n $ns -p '[{"op": "add", "path": "/spec/template/spec/containers/0/command/-", "value": "--insecure"}]' --type json

kubectl wait pod -n $ns -l app.kubernetes.io/name=argocd-server --for=condition=Ready --timeout 180s
which argocd
argocd login argocd.local:8080 --grpc-web --name admin --password $(cat .password)
argocd account update-password --grpc-web --current-password $(cat .password)