FROM centos:7
ENV container docker

# from official doc
RUN (cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == \
systemd-tmpfiles-setup.service ] || rm -f $i; done); \
rm -f /lib/systemd/system/multi-user.target.wants/*;\
rm -f /etc/systemd/system/*.wants/*;\
rm -f /lib/systemd/system/local-fs.target.wants/*; \
rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
rm -f /lib/systemd/system/basic.target.wants/*;\
rm -f /lib/systemd/system/anaconda.target.wants/*;


RUN yum -y update; yum clean all;
RUN yum -y install httpd openssh-server passwd openssh-clients sudo;

RUN systemctl enable httpd.service

RUN ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key -N ''

RUN echo root:root | chpasswd

VOLUME [ "/sys/fs/cgroup" ]
EXPOSE 22
EXPOSE 80
CMD ["/usr/sbin/init"]