#!/bin/bash
export KUBECONFIG=$(k3d get-kubeconfig)

# kubectl delete ns grafana
kubectl create ns grafana

kubectl delete deploy grafana -n grafana
helm delete grafana --purge
# helm install stable/grafana -n grafana --namespace grafana --version 4.6.3 -f - <<EOF
# helm fetch stable/grafana --version 3.12.1
# helm template grafana-3.12.1.tgz -n grafana --namespace grafana -f - <<EOF
helm install stable/grafana -n grafana --namespace grafana --version 3.12.0 -f - <<EOF
ingress:
  enabled: true
  hosts:
    - grafana.local

adminUser: admin
adminPassword: admin

dashboards:

datasources:
  datasources.yaml:
    apiVersion: 1
    datasources:
    - name: Prometheus
      type: prometheus
      access: proxy
      url: 'http://prometheus-server:80'
      isDefault: true
      version: 1

grafana.ini:
  log:
    level: debug
  server:
    http_port: 3000
    domain: grafana.local
    root_url: http://%(domain)s:8080/
  auth.generic_oauth:
    name: Keycloak
    enabled: true
    client_id: grafana
    scopes: openid email name groups
    auth_url: http://keycloak.local:8080/auth/realms/master/protocol/openid-connect/auth
    token_url: http://keycloak-http.keycloak/auth/realms/master/protocol/openid-connect/token
    api_url: http://keycloak-http.keycloak/auth/realms/master/protocol/openid-connect/userinfo
    allow_sign_up: true
    # team_ids:
    # allowed_organizations:
    # role_attribute_path: contains(info.groups[*], 'admin') && 'Admin' || contains(info.groups[*], 'editor') && 'Editor' || 'Viewer'
EOF

# https://github.com/helm/charts/tree/master/stable/grafana
# https://github.com/helm/charts/blob/master/stable/grafana/templates/configmap.yaml#L15

# https://grafana.com/docs/grafana/latest/installation/configuration
# https://grafana.com/docs/grafana/latest/auth/generic-oauth/
# https://grafana.com/docs/grafana/latest/administration/provisioning/#example-datasource-config-file

# $ helm install stable/grafana -n grafana --namespace grafana --version 4.6.3 --dry-run
# $ helm install stable/grafana -n grafana --namespace grafana --version 4.0.0 --dry-run
# Error: Chart incompatible with Tiller v2.9.1
# $ helm search stable/grafana -v 3.12.1
# NAME          	CHART VERSION	APP VERSION	DESCRIPTION                                       
# stable/grafana	3.12.1       	6.4.2      	The leading tool for querying and visualizing t...

# ERROR :: in OAuth, invalid (or no) user email ....
# t=2020-02-14T08:32:11+0000 lvl=info msg="Request Completed" logger=context userId=0 orgId=0 uname= method=GET path=/login/generic_oauth status=302 remote_addr=10.42.2.33 time_ms=0 size=321 referer=http://grafana.local:8080/login
# t=2020-02-14T08:32:12+0000 lvl=info msg="state check" logger=oauth queryState=cb5e434f083066336b605b9f0c46a486d382158004b7298fffec4e05ceadc022 cookieState=cb5e434f083066336b605b9f0c46a486d382158004b7298fffec4e05ceadc022
# t=2020-02-14T08:32:12+0000 lvl=dbug msg="OAuthLogin Got token" logger=oauth token="&{AccessToken:eyJhbGci..."
# t=2020-02-14T08:32:12+0000 lvl=dbug msg="No email found in id_token" logger=oauth.generic_oauth json="{\"jti\":\"972e5a5a-...\"email_verified\":false,\"preferred_username\":\"keycloak\"}" data="&{Name: DisplayName: Login: Username: Email: Upn: Attributes:map[]}"
# t=2020-02-14T08:32:12+0000 lvl=eror msg="login.OAuthLogin(get info from generic_oauth)" logger=context userId=0 orgId=0 uname= error="Error getting user info: {\"error\":\"invalid_token\",\"error_description\":\"Token invalid: Invalid token issuer. Expected 'http://keycloak-http.keycloak/auth/realms/master', but was 'http://keycloak.local:8080/auth/realms/master'\"}"
# t=2020-02-14T08:32:12+0000 lvl=eror msg="Request Completed" logger=context userId=0 orgId=0 uname= method=GET path=/login/generic_oauth status=500 remote_addr=10.42.2.33 time_ms=36 size=1750 referer=http://grafana.local:8080/login

# Role Mapping in OAuth2
# https://github.com/grafana/grafana/issues/9766
# https://github.com/grafana/grafana/pull/20300