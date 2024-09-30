# Use the Selenium standalone Chrome image as the base
FROM selenium/standalone-chrome:latest
# Install OpenJDK 21
USER root
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://download.java.net/java/GA/jdk21/latest/binaries/openjdk-21_linux-x64_bin.tar.gz && \
    tar -xvf openjdk-21_linux-x64_bin.tar.gz && \
    mv jdk-21 /usr/local/ && \
    ln -s /usr/local/jdk-21/bin/java /usr/local/bin/java && \
    rm openjdk-21_linux-x64_bin.tar.gz
# Define build-time arguments and labels
ARG SOURCE
ARG COMMIT_HASH
ARG COMMIT_ID
ARG BUILD_TIME
LABEL source=${SOURCE}
LABEL commit_hash=${COMMIT_HASH}
LABEL commit_id=${COMMIT_ID}
LABEL build_time=${BUILD_TIME}
# Define build-time arguments for user and group
ARG container_user=mosip
ARG container_user_group=mosip
ARG container_user_uid=1001
ARG container_user_gid=1001
# Set working directory for the user
ENV work_dir=/home/${container_user}/
ARG KUBECTL_VERSION=1.22.9
# Create user and download kubectl
RUN groupadd -g ${container_user_gid} ${container_user_group} \
    && useradd -u ${container_user_uid} -g ${container_user_group} -s /bin/bash -m ${container_user} -d ${work_dir} \
    && chown -R ${container_user}:${container_user} /home/${container_user}/  \
    && curl -LO "https://storage.googleapis.com/kubernetes-release/release/v${KUBECTL_VERSION}/bin/linux/amd64/kubectl" \
    && chmod +x kubectl \
    && mv kubectl /usr/local/bin/
# Switch to the specified user for the subsequent commands
USER ${container_user_uid}:${container_user_gid}
# Copy files
COPY --chown=${container_user_uid}:${container_user} --chmod=771 ./entrypoint.sh ${work_dir}/entrypoint.sh
COPY --chown=${container_user}:${container_user} ./src/main/resources/ ${work_dir}/resources/
COPY --chown=${container_user_uid}:${container_user} ./target/*.jar ${work_dir}
# Set working directory
WORKDIR /home/${container_user}/
# Entrypoint for the container
ENTRYPOINT ["/bin/bash", "-c", "./entrypoint.sh"]
