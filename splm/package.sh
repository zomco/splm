#!/bin/bash

cd ../splm-web || exit
npm run build

cd ../splm || exit
docker-compose --profile prod build
docker save splm-app > ./pkg/splm_app.tar