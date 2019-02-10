FROM jenkins/jenkins:2.162

USER root

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV JENKINS_GROOVY_INIT_FOLDER="/usr/share/jenkins/ref/init.groovy.d"

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN xargs /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# create configs
COPY configs/ $JENKINS_GROOVY_INIT_FOLDER

#install ssh, maven
RUN apt-get update; 
RUN apt-get install -y passwd openssh-client openssh-server maven sudo systemd

# set juser as admin
RUN useradd juser -s /bin/bash -g sudo -m
RUN echo juser:juser | chpasswd

#revert to normal - todo in the future
# USER jenkins

EXPOSE 22
EXPOSE 80

COPY startup.sh /usr/local/bin/custom_startup.sh
ENTRYPOINT ["/usr/local/bin/custom_startup.sh"]