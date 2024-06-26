from datetime import datetime
from airflow import DAG
from airflow.providers.ssh.operators.ssh import SSHOperator
from datetime import timedelta

default_args = {
    'owner': 'Bojan Radovic',
    'retries': 3,
    'retry_delay': timedelta(minutes=2)
}

with DAG(
    dag_id='convert-to-delta',
    default_args=default_args,
    params={
         "city": "Moscow",
         "start_date": "2022-07-29",
         "end_date": "2023-12-09",
         "data_type": "historical_air_quality"
    },
    description='Submit job to Spark cluster',
    start_date=datetime(2024, 3, 1, 2),
    schedule_interval='@daily'
) as dag:
    
    spark_submit_command = (
        "cd ../opt/spark && "
        "export JAVA_HOME=/usr/local/openjdk-11/ && "
        "bin/spark-submit --class Runner "
        "../app/transformation/target/scala-2.12/transformation-0.1.jar "
    )


    job_options = {
        "--job": dag.dag_id,
        "--city": dag.params["city"],
        "--data-type": dag.params["data_type"],
        "--start-date": dag.params["start_date"],
        "--end-date": dag.params["end_date"],
    }

    command_with_options = spark_submit_command + " ".join(f"{key} {value}" for key, value in job_options.items())

    task_submit_job = SSHOperator(
        task_id='submit_convert-to-delta_job_to_spark_cluster',
        ssh_conn_id="ssh_con",
        command=command_with_options
    )


with DAG(
    dag_id='convert-all-to-delta',
    default_args=default_args,
    params={
         "start_date": "2022-07-29",
         "end_date": "2023-12-09",
         "data_type": "historical_air_quality"
    },
    description='Submit job to Spark cluster',
    start_date=datetime(2024, 3, 1, 2),
    schedule_interval='@daily'
) as dag:
    
    spark_submit_command = (
        "cd ../opt/spark && "
        "export JAVA_HOME=/usr/local/openjdk-11/ && "
        "bin/spark-submit --class Runner "
        "../app/transformation/target/scala-2.12/transformation-0.1.jar "
    )

    job_options = {
        "--job": dag.dag_id,
        "--data-type": dag.params["data_type"],
        "--start-date": dag.params["start_date"],
        "--end-date": dag.params["end_date"],
    }

    command_with_options = spark_submit_command + " ".join(f"{key} {value}" for key, value in job_options.items())

    task_submit_job = SSHOperator(
        task_id='submit_convert-all-to-delta_job_to_spark_cluster',
        ssh_conn_id="ssh_con",
        command=command_with_options
    )
