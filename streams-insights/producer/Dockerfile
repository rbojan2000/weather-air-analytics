FROM adoptopenjdk:11-jdk-hotspot

RUN apt-get update && apt-get install -y curl

# Install SBT
RUN curl -L -o sbt-1.9.8.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-1.9.8.deb && \
    dpkg -i sbt-1.9.8.deb && \
    apt-get update && \
    apt-get install -y sbt && \
    rm sbt-1.9.8.deb

RUN apt-get install -y netcat

COPY . /app
WORKDIR /app
RUN sbt assembly
