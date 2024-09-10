#!/usr/bin/env bash

JAR_PATH="/server/build/libs/api-0.0.1-SNAPSHOT.jar"

if [ ! -f "$JAR_PATH" ]; then
  echo "Compiling jar file"
  ./server/gradlew build -x test
fi

if [ ! -d "/client/node_modules" ]; then
  echo "Installing npm packages"
  (cd client && npm ci)
fi

echo "Starting Spring Boot server..."
java -jar ${JAR_PATH} &

SPRING_PID=$!

cd ./client || exit

echo "Starting Vue.js app..."
npm run dev &

VUE_PID=$!

# Wait for both processes to finish
wait $SPRING_PID
wait $VUE_PID

open http://localhost:5173
