## Create New Chart
```bash
chart=keycloak

helm create $chart && cd $chart
rm -rf templates charts
touch requirements.yaml

# $ cat requirements.yaml
# dependencies:
# - name: keycloak
#   version: 6.2.0
#   repository: https://kubernetes-charts.storage.googleapis.com
```

## Create New Application
```yaml
# vi ../apps/keycloak.yaml
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: keycloak  # change your name
  namespace: argo
spec:
  project: default
  source:
    repoURL: https://github.com/yanghoon/toy.git
    path: k3d/gitops/helm/keycloak  # change your dir
    targetRevision: gitops
    helm:
      valueFiles:
      - values.yaml
  destination:
    server: https://kubernetes.default.svc
    namespace: keycloak
  syncPolicy: {}
```