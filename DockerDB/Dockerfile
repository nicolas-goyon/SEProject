FROM mcr.microsoft.com/mssql/server:2022-latest


# Create app directory
USER root
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy initialization scripts
COPY . /usr/src/app

# Grant permissions for the run-initialization script to be executable
RUN chmod +x /usr/src/app/run-initialization.sh


# Set environment variables
ENV MSSQL_SA_PASSWORD YourStrong@Passw0rd
ENV ACCEPT_EULA Y
ENV MSSQL_PID Express

# Expose SQL Server port
EXPOSE 1433

# Run Microsoft SQl Server and initialization script (at the same time)
# Note: If you want to start MsSQL only (without initialization script) you can comment bellow line out, CMD entry from base image will be taken
CMD /bin/bash ./entrypoint.sh
