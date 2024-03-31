#!/bin/bash

if [ "$1" == "--streams" ]; then
    if [ "$2" == "--start" ]; then
        docker network ls | grep streams_nw >/dev/null || docker network create --driver bridge streams_nw
        docker compose -f streams-insights/infrastructure/docker-compose.yml up -d
        docker compose -f visualization/infrastructure/docker-compose.yml up -d
    elif [ "$2" == "--stop" ]; then
        docker compose -f streams-insights/infrastructure/docker-compose.yml down
        docker compose -f visualization/infrastructure/docker-compose.yml down
    else
        echo "Invalid argument provided"
        echo "Usage: ./run.sh --streams --start/--stop"
    fi

elif [ "$1" == "--batch" ]; then
    if [ "$2" == "--start" ]; then
        docker network ls | grep infrastructure_nw >/dev/null || docker network create --driver bridge infrastructure_nw
        docker compose -f orchestrator/docker-compose.yml up -d
        docker compose -f air-meteo-insights/infrastructure/docker-compose.yml up -d
    elif [ "$2" == "--stop" ]; then
        docker compose -f orchestrator/docker-compose.yml down
        docker compose -f air-meteo-insights/infrastructure/docker-compose.yml down
    else
        echo "Invalid argument provided"
        echo "Usage: ./run.sh --streams --start/--stop"
    fi
else
    echo "Invalid argument provided"
    echo "Usage: ./run.sh --streams/--batch --start/--stop"
fi
