# ArgoCD
## Setup
Web Console - http://argocd.local:8080
```bash
cd argocd

bash argo.sh
#   ...
# WARNING: server is not configured with TLS. Proceed (y/n)? y
# Username: admin
# 'admin' logged in successfully
# Context 'admin' updated
# *** Enter new password: 
# *** Confirm new password: 
# Password updated
# Context 'admin' updated
```

## Deploy Applications
```
cd ../apps
kubectl apply -f . -n argo
```

## Add new Application
### [with Helm Dependencies](helm/README.md)
1. Create a Helm Wrapper Chart for Dependencies (/helm/new-chart)
2. Create ArgoCD Application YAML (/apps)
3. Deploy Application via `kubectl apply -f new-chart.yaml -n argo`