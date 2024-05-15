#!/bin/bash

cd ../splm-web || exit
npm run build

cd ../splm || exit
docker-compose --profile prod build
docker save splm-app -o ./pkg/splm_app.tar

# docker load -i splm_app.tar
# docker load -i redis.tar
# docker load -i mysql.tar
# docker images
# docker-compose up --profile prod up -d