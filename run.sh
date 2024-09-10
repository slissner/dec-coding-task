#!/usr/bin/env bash

JAR_PATH="./server/build/libs/api-0.0.1-SNAPSHOT.jar"

if [ ! -f "$JAR_PATH" ]; then
  echo "Compiling jar file"
  (cd ./server && ./gradlew build -x test)
fi

if [ ! -d "./client/node_modules" ]; then
  echo "Installing npm packages"
  (cd ./client && npm ci)
fi

echo "Starting Docker Compose..."
docker-compose -f "./docker-compose.yml" up -d

echo "Starting Spring Boot server..."
java -jar ${JAR_PATH} &

SPRING_PID=$!

echo "Starting Vue.js app..."
npm run dev --prefix ./client &

VUE_PID=$!

trap "kill $SPRING_PID $VUE_PID; docker-compose -f './docker-compose.yml' down" INT EXIT

# Wait for both processes to finish
wait $SPRING_PID
wait $VUE_PID
