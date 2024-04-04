#!/bin/bash

if [ "$1" == "--streams" ]; then
    if [ "$2" == "--start" ]; then
        docker network ls | grep streams_nw >/dev/null || docker network create --driver bridge streams_nw
        docker compose -f streams-insights/infrastructure/docker-compose.yml up -d
    elif [ "$2" == "--stop" ]; then
        docker compose -f streams-insights/infrastructure/docker-compose.yml stop
    else
        echo "Invalid argument provided"
        echo "Usage: ./run.sh --streams --start/--stop"
    fi

elif [ "$1" == "--batch" ]; then
    if [ "$2" == "--start" ]; then
        docker network ls | grep infrastructure_nw >/dev/null || docker network create --driver bridge infrastructure_nw
        docker compose -f orchestrator/docker-compose.yaml up -d
        docker compose -f etl/infrastructure/docker-compose.yml up -d
    elif [ "$2" == "--stop" ]; then
        docker compose -f orchestrator/docker-compose.yaml stop
        docker compose -f etl/infrastructure/docker-compose.yml stop
    else
        echo "Invalid argument provided"
        echo "Usage: ./run.sh --batch --start/--stop"
    fi

elif [ "$1" == "--visualization" ]; then
    if [ "$2" == "--start" ]; then
        docker network ls | grep infrastructure_nw >/dev/null || docker network create --driver bridge infrastructure_nw
        docker compose -f visualization/infrastructure/docker-compose.yml up -d
    elif [ "$2" == "--stop" ]; then
        docker compose -f visualization/infrastructure/docker-compose.yml stop
    else
        echo "Invalid argument provided"
        echo "Usage: ./run.sh --visualization --start/--stop"
    fi

elif [ "$1" == "--stop-all" ]; then
        docker compose -f streams-insights/infrastructure/docker-compose.yml stop
        docker compose -f visualization/infrastructure/docker-compose.yml stop
        docker compose -f orchestrator/docker-compose.yaml stop
        docker compose -f etl/infrastructure/docker-compose.yml stop
    
else
    echo "Invalid argument provided"
    echo "Usage: ./run.sh --streams/--batch/--visualization --start/--stop"
    echo "--------------------------------------"
    echo "Stop all Docker containers"
    echo "./run.sh --stop-all"    
fi
