#!/bin/bash
export KUBECONFIG=$(k3d get-kubeconfig)

kubectl create serviceaccount -n kube-system tiller
kubectl create clusterrolebinding crb-tiller-cluster-admin --clusterrole=cluster-admin --serviceaccount=kube-system:tiller

# helm init --output yaml --service-account tiller > tiller.yaml
# vi tiller.yaml
#  - appVersion: apps/v1
#  - spec.selector.matchLabels: {...}
kubectl apply -f - <<EOF
# apiVersion: extensions/v1beta1
apiVersion: apps/v1
kind: Deployment
metadata:
  creationTimestamp: null
  labels:
    app: helm
    name: tiller
  name: tiller-deploy
  namespace: kube-system
spec:
  replicas: 1
  strategy: {}
  selector:
    matchLabels:
      app: helm
      name: tiller
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: helm
        name: tiller
    spec:
      containers:
      - env:
        - name: TILLER_NAMESPACE
          value: kube-system
        - name: TILLER_HISTORY_MAX
          value: "0"
        image: gcr.io/kubernetes-helm/tiller:v2.16.9
        imagePullPolicy: IfNotPresent
        livenessProbe:
          httpGet:
            path: /liveness
            port: 44135
          initialDelaySeconds: 1
          timeoutSeconds: 1
        name: tiller
        ports:
        - containerPort: 44134
          name: tiller
        - containerPort: 44135
          name: http
        readinessProbe:
          httpGet:
            path: /readiness
            port: 44135
          initialDelaySeconds: 1
          timeoutSeconds: 1
        resources: {}
      serviceAccountName: tiller
status: {}
EOF

# helm version