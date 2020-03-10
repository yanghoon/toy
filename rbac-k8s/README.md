# Environment
```bash
# Setup
kubectl apply -f env
kubectl apply -f pod-kubectl.yaml

# Clean Up
kubectl delete -f bindings
kubectl delete -f env
```

# Test
## No Permissions
```bash
# Pod
kubectl exec -it kubectl -n console-users -- bash
kubectl version
kubectl get pod   # Fail: Forbidden(403)
kubectl get sa    # Fail: Forbidden(403)
kubectl get node  # Fail: Forbidden(403)
exit
```

## Binding ClusterRole: pods[get,list]
```bash
# Local
kubectl apply -f bindings/crb-built-in-view.yaml
kubectl replace --force -f pod-kubectl.yaml

# Pod
kubectl exec -it kubectl -n console-users -- bash
kubectl get pod   # Success: (--all-namespaces)
kubectl get sa    # Fail: Forbidden(403)
kubectl get node  # Fail: Forbidden(403)
```

## Binding Role: serviceaccounts[*], nodes[*]
```bash
# Local
kubectl apply -f bindings/rb-proj-admin.yaml
kubectl replace --force -f pod-kubectl.yaml

# Pod
kubectl exec -it kubectl -n console-users -- bash
kubectl get pod   # Success: (--all-namespaces)
kubectl get sa    # Success
kubectl get node  # Fail: Forbidden(403)
```

## Binding ClusterRole: nodes[*]
```bash
# Local
kubectl apply -f bindings/crb-built-in-nodes.yaml
kubectl replace --force -f pod-kubectl.yaml

# Pod
kubectl exec -it kubectl -n console-users -- bash
kubectl get pod   # Success: (--all-namespaces)
kubectl get sa    # Success
kubectl get node  # Sucdess
```