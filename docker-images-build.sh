#!/usr/bin/env bash

docker login -u $DOCKER_USER -p $DOCKER_PASS

export TAG="latest"

# CONFIG SERVICE
export CONFIG=turukin/d-echo-config
docker build -t $CONFIG:$COMMIT ./config
docker tag $CONFIG:$COMMIT $CONFIG:$TAG
docker push $CONFIG:$TAG
echo "Has been pushed: $CONFIG:$TAG"

# DISCOVERY SERVICE
export DISCOVERY=turukin/d-echo-eureka
docker build -t $DISCOVERY:$COMMIT ./discovery-service
docker tag $DISCOVERY:$COMMIT $DISCOVERY:$TAG
docker push $DISCOVERY:$TAG
echo "Has been pushed: $DISCOVERY:$TAG"

# ECHO SERVICE
export ECHO=turukin/d-echo-echo
docker build -t $ECHO:$COMMIT ./echo-service
docker tag $ECHO:$COMMIT $ECHO:$TAG
docker push $ECHO:$TAG
echo "Has been pushed: $ECHO:$TAG"

#GATEWAY
export GATEWAY=turukin/d-echo-gateway
docker build -t $GATEWAY:$COMMIT ./gateway
docker tag $GATEWAY:$COMMIT $GATEWAY:$TAG
docker push $GATEWAY:$TAG
echo "Has been pushed: $GATEWAY:$TAG"

#AUTH
export AUTH=turukin/d-echo-auth
docker build -t $AUTH:$COMMIT ./auth
docker tag $AUTH:$COMMIT $AUTH:$TAG
docker push $AUTH:$TAG
echo "Has been pushed: $AUTH:$TAG"

