# Init
## Manifests
```bash
$ kubectl run console-rbac \
    --labels='app=console-rbac' \
    --image=yoon5/console-rbac:latest \
    --env SPRING_PROFILES_ACTIVE=dev \
    --port=8080 \
    --dry-run \
    -o yaml > deploy/deploy.yaml
$ kubectl create svc clusterip console-rbac \
    --tcp=80:8080 \
    --dry-run \
    -o yaml > deploy/service.yaml
```

## Jib
- https://github.com/docker/docker-credential-helpers
```bash
$ brew install docker-credential-helper
$ vi ~/.docker/config.json
...
  "credsStore": "osxkeychain"  # from 'desktop'
...
$ vi build.gradle
jib {
  to {
    ...
    credHelper = "osxkeychain"
    ...
  }
}

$ gradle jib
$ docker images | grep console-rbac
yoon5/console-rbac                                    0.0.1-SNAPSHOT      729f020ea794        50 years ago        120MB
yoon5/console-rbac                                    latest              729f020ea794        50 years ago        120MB

## ERR: Docker Hub Credential
> Task :jib FAILED

Containerizing application to yoon5/console-rbac, yoon5/console-rbac:0.0.1-SNAPSHOT...
Base image 'openjdk:8-jre-alpine' does not use a specific image digest - build may not be reproducible
Executing tasks:
[===                           ] 10.0% complete
> pulling base image manifest
> building dependencies layer
> building resources layer
> building classes layer


FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':jib'.
> com.google.cloud.tools.jib.plugins.common.BuildStepsExecutionException: The system does not have docker-credential-pass CLI

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 1s
3 actionable tasks: 1 executed, 2 up-to-date
```

## Skaffold
```bash
$ brew install skaffold
$ skaffold version   # v.1.4.0

# $ skaffold init --XXenableJibInit --analyze | jq
# {
#   "builders": [
#     {
#       "name": "Jib Gradle Plugin",
#       "payload": {
#         "image": "yoon5/console-rbac",
#         "path": "build.gradle"
#       }
#     }
#   ],
#   "images": [
#     {
#       "name": "console-rbac",
#       "foundMatch": false
#     }
#   ]
# }

$ skaffold init --XXenableJibInit
apiVersion: skaffold/v2alpha4
kind: Config
metadata:
  name: rbac
build:
  artifacts:
  - image: yoon5/console-rbac
    jib: {}
deploy:
  kubectl:
    manifests:
    - deploy/deploy.yaml
    - deploy/ingress.yaml
    - deploy/service.yaml

Do you want to write this configuration to skaffold.yaml? [y/n]: y
Configuration skaffold.yaml was written
You can now run [skaffold build] to build the artifacts
or [skaffold run] to build and deploy
or [skaffold dev] to enter development mode, with auto-redeploy
```

# Build
```bash
$ kubectl create ns console

$ skaffold build
$ skaffold dev -n console
```

- https://skaffold.dev/docs/pipeline-stages/init/
- https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin#authentication-methods
- https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin#using-docker-credential-helpers
- https://www.baeldung.com/jib-dockerizing