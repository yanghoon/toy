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
```bash
kubectl apply -f config -n argo
```

## Deploy Applications
```
cd ../apps
kubectl apply -f . -n argo
```

## Add new Application
### [with Application(Group)](apps/README.md)
1. Create ArgoCD Application(YAML, using helm or kustomize) in New Directory (/apps/new-group)
2. Create Wrapper Application for All Application in New Dirctory (/apps/new-group-apps.yaml)
3. Deploy Application via `kubectl apply -f apps/new-group-apps.yaml -n argo`

### [with Helm Dependencies](helm/README.md)
1. Create a Helm Wrapper Chart for Dependencies (/helm/new-chart)
2. Create ArgoCD Application YAML (/apps)
3. Deploy Application via `kubectl apply -f new-chart.yaml -n argo`