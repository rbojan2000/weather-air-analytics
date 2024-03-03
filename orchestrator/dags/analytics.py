from datetime import datetime
from airflow import DAG
from airflow.providers.ssh.operators.ssh import SSHOperator
from config import default_args

with DAG(
    dag_id='hourly-pollutant-cities-rank-by-max-pollutant-concetration',
    default_args=default_args,
    params={
         "pollutant": "pm10",
    },
    description='Submit job to Spark cluster',
    start_date=datetime(2024, 3, 1, 2),
    schedule_interval='@daily'
) as dag:
    
    spark_submit_command = (
        "cd ../opt/spark && "
        "export JAVA_HOME=/usr/local/openjdk-11/ && "
        "bin/spark-submit --class Runner "
        "../app/target/scala-2.12/air-meteo-insights-0.1.jar "
    )


    job_options = {
        "--job": dag.dag_id,
        "--pollutant": dag.params["pollutant"]
    }

    command_with_options = spark_submit_command + " ".join(f"{key} {value}" for key, value in job_options.items())

    task_submit_job = SSHOperator(
        task_id='submit_hourly-pollutant-cities-rank-by-max-pollutant-concetration_job_to_spark_cluster',
        ssh_conn_id="ssh_con",
        command=command_with_options
    )


with DAG(
    dag_id='hourly-pollutant-metrics',
    default_args=default_args,
    params={
         "pollutant": "pm10",
    },
    description='Submit job to Spark cluster',
    start_date=datetime(2024, 3, 1, 2),
    schedule_interval='@daily'
) as dag:
    
    spark_submit_command = (
        "cd ../opt/spark && "
        "export JAVA_HOME=/usr/local/openjdk-11/ && "
        "bin/spark-submit --class Runner "
        "../app/target/scala-2.12/air-meteo-insights-0.1.jar "
    )


    job_options = {
        "--job": dag.dag_id,
        "--pollutant": dag.params["pollutant"]
    }

    command_with_options = spark_submit_command + " ".join(f"{key} {value}" for key, value in job_options.items())

    task_submit_job = SSHOperator(
        task_id='submit_hourly-pollutant-metrics_job_to_spark_cluster',
        ssh_conn_id="ssh_con",
        command=command_with_options
    )
    