#!/bin/bash

# First, build and package the maven application
mvn clean compile install package

# Second, kill all possible running processes listening in on the designated ports
PORTS=(12345 12346)
for PORT in "${PORTS[@]}"; do
    echo "Checking port ${PORT}..."
    PIDS=$(lsof -i tcp:${PORT} -t)
    if [ -n "${PIDS}" ]; then
        echo "Processes listening on port ${PORT}:"
        lsof -i tcp:${PORT}

        echo "Killing processes listening on port ${PORT}..."
        kill -9 ${PIDS}
        echo "Processes listening on port ${PORT} killed."
    else
        echo "No processes listening on port ${PORT}."
    fi
done


# Third, create the docker image
CONTAINER_NAME="contained-tcp-hello-world"
sudo docker build -t ${CONTAINER_NAME} .

# Finally, run the docker image
sudo docker run -p 12345:12345 -p 12346:12346 ${CONTAINER_NAME}