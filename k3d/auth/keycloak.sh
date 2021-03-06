#!/bin/bash
export KUBECONFIG=$(k3d get-kubeconfig)

# kubectl delete ns keycloak
kubectl create ns keycloak

# kubectl delete secret generic realm-secret -n keycloak
# kubectl create secret generic realm-secret -n keycloak --from-file=realm-export-master.json

helm repo add codecentric https://codecentric.github.io/helm-charts
helm repo update

helm delete keycloak --purge
helm install codecentric/keycloak -n keycloak --namespace keycloak --version 6.2.0 -f - <<EOF
keycloak:
  serviceAccount:
    create: true
    # create: false
    # name: 

  username: keycloak
  password: keycloak
  # existingSecret: 
  # existingSecretKey: password

  # resources: {}

  # extraVolumes: |-
    # - name: realm-secret
    #   secret:
    #     secretName: realm-secret
    # - name: theme
    #   emptyDir: {}
  # extraVolumeMounts: |-
    # - name: realm-secret
    #   mountPath: /realm/
    #   readOnly: true
    # - name: theme
    #   mountPath: /opt/jboss/keycloak/themes/company
  # extraArgs: -Dkeycloak.import=/realm/realm-export-master.json
  # extraInitContainers: |
  #   - name: theme-provider
  #     image: repo/custom-theme-img:latest
  #     imagePullPolicy: IfNotPresent
  #     command:
  #       - sh
  #     args:
  #       - -c
  #       - echo "Copying theme..." && cp -R /theme-company/* /theme
  #     volumeMounts:
  #       - name: theme
  #         mountPath: /theme

  ingress:
    enabled: true
    hosts:
    - keycloak.local  # add to /etc/hosts

  persistence:
    deployPostgres: true
    dbVendor: postgres  # also mysql, mariadb
    # existingSecret:
    # existingSecretKey:

# Error: apiVersion "monitoring.coreos.com/v1" in keycloak/templates/servicemonitor.yaml is not available
# prometheus:
#   operator:
#     enabled: true
EOF

# https://github.com/codecentric/helm-charts
# https://github.com/codecentric/helm-charts/tree/master/charts/keycloak
# https://github.com/codecentric/helm-charts/tree/master/charts/keycloak#setting-a-custom-realm
# https://github.com/codecentric/helm-charts/tree/master/charts/keycloak#providing-a-custom-theme
