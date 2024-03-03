from datetime import datetime, timedelta
from airflow import DAG
from airflow.providers.ssh.operators.ssh import SSHOperator

default_args = {
    'owner': 'Bojan Radovic',
    'retries': 5,
    'retry_delay': timedelta(minutes=2)
}

with DAG(
    dag_id='submit_job',
    default_args=default_args,
    description='dss',
    start_date=datetime(2024, 3, 1, 2),
    schedule_interval='@daily'

) as dag:
    task44 = SSHOperator(
        task_id= 'dock',
        ssh_conn_id="ssh_con",
        command="cd ../opt/spark && export JAVA_HOME=/usr/local/openjdk-11/ && bin/spark-submit --class Runner ../app/target/scala-2.12/air-meteo-insights-0.1.jar -j dssdsd -c London -dt historical_air_quality -sd 2022-07-29 -ed 2023-12-09"
    )
