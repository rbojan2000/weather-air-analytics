# orchestrator

***Orchestrator*** utilizes Apache Airflow for managing the scheduling and coordination of tasks within the project.

## Infrastructure Setup
Running ```bash docker compose up``` will start the following services:
 - `postgres`: Starts the PostgreSQL database used as the backend for Airflow to store metadata and execution-related information
 - `airflow-webserver`: Launches the web server serving as the user interface for Airflow. Users can view, manage, and trigger various tasks and workflows here.
 - `airflow-scheduler`: Initiates the scheduler, responsible for planning and distributing task execution within Airflow according to defined schedules
 - `airflow-triggerer`: Runs a specialized service controlling triggers and task execution according to defined rules and schedules.
 - `airflow-init`: A service for initialization, conducting initial setup and configuration, including resource availability checks and directory permissions setup.
 - `airflow-cli`: Provides access to the Airflow command-line interface for managing and executing various operations and tasks via the command line.


## Submitting Spark Jobs

The current approach for submitting Spark jobs in the project involves:
1. Establishing an SSH connection to the container hosting the Spark master node.
2. Submitting the desired job.


## Usage
After starting the services using docker-compose up, users can access the Airflow web interface(`localhost:8080`) to manage tasks and workflows. Additionally, the Airflow command-line interface (`airflow-cli`) provides options for managing and executing tasks via the command line.
