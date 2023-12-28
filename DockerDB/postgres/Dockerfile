FROM postgres:latest

# Create app directory
USER root
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy init.sql to the default initialization directory
COPY ./init.sql /docker-entrypoint-initdb.d/init.sql

# Expose PostgreSQL port
EXPOSE 5432
