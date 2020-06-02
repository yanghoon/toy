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