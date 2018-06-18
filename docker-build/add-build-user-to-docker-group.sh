#!/bin/sh
set -e

BUILDUSER="build"

ls /var/run/

DOCKER_GID=$(ls -aln /var/run/docker.sock  | awk '{print $4}')

if ! getent group $DOCKER_GID; then
	echo creating docker group $DOCKER_GID
	addgroup --gid $DOCKER_GID docker
fi

if ! getent group $GID; then
	echo creating $BUILDUSER group $GID
	addgroup --gid $GID $BUILDUSER
fi

if ! getent passwd $BUILDUSER; then
	echo useradd -N --gid $GID -u $UID $BUILDUSER
	useradd -N --gid $GID -u $UID $BUILDUSER
fi

DOCKER_GROUP=$(ls -al /var/run/docker.sock  | awk '{print $4}')
if ! id -nG "$BUILDUSER" | grep -qw "$DOCKER_GROUP"; then
	adduser $BUILDUSER $DOCKER_GROUP
fi

# else default to run whatever the user wanted like "bash" or "sh"
exec "$@"
