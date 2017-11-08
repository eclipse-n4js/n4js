FROM openjdk:8u131-jdk

LABEL version="0.1.0" \
      description="Build n4js with Maven. Includes xvfb, node." \
      maintainer="Continuous Delivery Team <staff-build-release@numberfour.eu>" \
      eu.numberfour.dockerimage.name="numberfour/build-n4js" \
      eu.numberfour.dockerimage.github="eclipse/n4js"

ENV DEBIAN_FRONTEND=noninteractive

VOLUME /workspace
WORKDIR /workspace

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"]

#
# Maven installation:
# see https://github.com/carlossg/docker-maven/tree/ecf54b9839caed8aa2bcf9b8f7bb19594634ee89/jdk-8
# and https://hub.docker.com/_/maven/
#
ARG MAVEN_VERSION=3.5.2
ARG USER_HOME_DIR="/root"
ARG SHA=707b1f6e390a65bde4af4cdaf2a24d45fc19a6ded00fff02e91626e3e42ceaff
ARG BASE_URL=https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref && \
    curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
    echo "${SHA}  /tmp/apache-maven.tar.gz" | sha256sum -c - && \
    tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 && \
    rm -f /tmp/apache-maven.tar.gz && \
    ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME   /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ENV MAVEN_OPTS   "-Xms1536m -Xmx2048M"

COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY settings.xml /usr/share/maven/ref/

#
# The remaining part has been inspired by:
# https://github.com/nodejs/docker-node/blob/master/8.9/wheezy/Dockerfile
#

RUN groupadd --gid 1000 node && \
    useradd --uid 1000 --gid node --shell /bin/bash --create-home node

RUN set -ex && \
    for key in \
        6A010C5166006599AA17F08146C2130DFD2497F5 \
        9554F04D7259F04124DE6B476D5A82AC7E37093B \
        94AE36675C464D64BAFA68DD7434390BDBE9B9C5 \
        FD3A5288F042B6850C66B31F09FE44734EB7990E \
        71DCFD284A79C3B38668286BC97EC7A07EDE3FC1 \
        DD8F2338BAE7501E3DD5AC78C273792F7D83545D \
        B9AE9905FFD7803F25714661B63B535A4C206CA9 \
        C4F0DFFF4E8C1A8236409D08E73BC641CC11F4C8 \
        56730D5401028683275BD23C23EFEFE93C4CFFFE \
        77984A986EBC2AA786BC0F66B01FBB92821C587A ; \
    do \
        gpg --keyserver hkp://pgp.mit.edu:80                --recv-keys "$key" || \
        gpg --keyserver hkp://keyserver.pgp.com:80          --recv-keys "$key" || \
        gpg --keyserver hkp://ha.pool.sks-keyservers.net:80 --recv-keys "$key" ; \
  done

#
# Node install
#
ENV NODE_VERSION 8.4.0

RUN ARCH= && dpkgArch="$(dpkg --print-architecture)" && \
    case "${dpkgArch##*-}" in \
        amd64)   ARCH='x64'    ;; \
        ppc64el) ARCH='ppc64le';; \
        *)       echo "unsupported architecture"; exit 1 ;; \
    esac && \
    curl -SLO "https://nodejs.org/dist/v$NODE_VERSION/node-v$NODE_VERSION-linux-$ARCH.tar.xz" && \
    curl -SLO --compressed "https://nodejs.org/dist/v$NODE_VERSION/SHASUMS256.txt.asc"        && \
    gpg --batch --decrypt --output SHASUMS256.txt SHASUMS256.txt.asc                          && \
    grep " node-v$NODE_VERSION-linux-$ARCH.tar.xz\$" SHASUMS256.txt | sha256sum -c -          && \
    tar -xJf "node-v$NODE_VERSION-linux-$ARCH.tar.xz" -C /usr/local --strip-components=1      && \
    rm "node-v$NODE_VERSION-linux-$ARCH.tar.xz" SHASUMS256.txt.asc SHASUMS256.txt             && \
    ln -s /usr/local/bin/node /usr/local/bin/nodejs

#
# Yarn install
#
ENV YARN_VERSION 1.2.1

RUN set -ex && \
    curl -fSLO --compressed "https://yarnpkg.com/downloads/$YARN_VERSION/yarn-v$YARN_VERSION.tar.gz"     && \
    curl -fSLO --compressed "https://yarnpkg.com/downloads/$YARN_VERSION/yarn-v$YARN_VERSION.tar.gz.asc" && \
    gpg --batch --verify yarn-v$YARN_VERSION.tar.gz.asc yarn-v$YARN_VERSION.tar.gz                       && \
    mkdir -p /opt/yarn                                                                                   && \
    tar -xzf yarn-v$YARN_VERSION.tar.gz -C /opt/yarn --strip-components=1                                && \
    ln -s /opt/yarn/bin/yarn /usr/local/bin/yarn                                                         && \
    ln -s /opt/yarn/bin/yarn /usr/local/bin/yarnpkg                                                      && \
    rm yarn-v$YARN_VERSION.tar.gz.asc yarn-v$YARN_VERSION.tar.gz

#
# xvfb install
#
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        xauth \
        xvfb
