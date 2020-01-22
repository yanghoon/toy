#!/bin/bash
# Ref:
# - https://github.com/rancher/k3d/wiki/Examples:-Expose-Services

k3d del
k3d create --workers 2 --publish 8080:80 --publish 9092:30092 --wait 0
# docker ps > PORTS
sleep 5

export KUBECONFIG="$(k3d get-kubeconfig --name='k3s-default')"

cd "`dirname \"$0\"`"
bash tiller.sh
# bash kafka.sh