#!/bin/sh

# to fix - as for now. SSH init must be done in dockerfile
/etc/init.d/ssh start

#default jenkins startup
tini -- /usr/local/bin/jenkins.sh

