# base image
FROM nginx

ARG SOURCE
ARG COMMIT_HASH
ARG COMMIT_ID
ARG BUILD_TIME
LABEL source=${SOURCE}
LABEL commit_hash=${COMMIT_HASH}
LABEL commit_id=${COMMIT_ID}
LABEL build_time=${BUILD_TIME}

ENV base_path=/usr/share/nginx/html

ENV i18n_path=${base_path}/assets/i18n

ENV entity_spec_path=${base_path}/assets/entity-spec

ENV master_template_path=${base_path}/templates

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user=mosip
ARG container_user_group=mosip
ARG container_user_uid=1001
ARG container_user_gid=1001

# can be passed during Docker build as build time environment for artifactory URL
ARG artifactory_url

# environment variable to pass artifactory url, at docker runtime
ENV artifactory_url_env=${artifactory_url}

# set working directory for the user
WORKDIR /home/${container_user}

ADD ./nginx.conf /etc/nginx/nginx.conf

ADD ./default.conf /etc/nginx/conf.d/

ADD ./download.conf /etc/nginx/include/download.conf

ADD dist ${base_path}

# install the needed packages including wget and a unzip library 
#RUN apt-get update -y && apt-get install -y wget && apt-get install -y unzip

ADD configure_start.sh configure_start.sh

## install packages, create user and change permissions of file inside working dir
RUN apt-get -y update \
&& apt-get install -y unzip wget zip npm \
&& groupadd -g ${container_user_gid} ${container_user_group} \
&& useradd -u ${container_user_uid} -g ${container_user_group} -s /bin/sh -m ${container_user} \
&& mkdir -p /var/run/nginx /var/tmp/nginx \
&& chown -R ${container_user}:${container_user} /home/${container_user} /usr/share/nginx /var/run/nginx /var/tmp/nginx ${base_path}/assets/i18n ${base_path}/assets/entity-spec \
&& mkdir -p ${master_template_path} && chown -R ${container_user}:${container_user} ${master_template_path} \
&& chmod +x configure_start.sh \
&& cd /home/${container_user} 

# select container user for all tasks
USER ${container_user_uid}:${container_user_gid}

EXPOSE 8080

ENTRYPOINT [ "./configure_start.sh" ]

#get the admin i18n bundle zip from artifactory
CMD echo "starting nginx" ; \
    nginx ; \
    sleep infinity
	
