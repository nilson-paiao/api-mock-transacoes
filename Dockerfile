FROM debian:stretch
##MAINTAINER Nilson Paião
##LABEL Description="Docker api-mock-transacoes"

#Instala dependencias
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt update
RUN apt install -y default-jdk curl unzip zip wget git
RUN curl -s  "https://get.sdkman.io" | bash

#Instala Gradle
RUN /bin/bash -c "source ${HOME}/.sdkman/bin/sdkman-init.sh"
RUN /bin/bash -l -c "sdk install gradle 6.4"

#Instala Glassfish
RUN wget 'http://download.oracle.com/glassfish/5.0.1/release/glassfish-5.0.1.zip'
RUN unzip glassfish-5.0.1.zip -d /opt
RUN rm glassfish-5.0.1.zip

#Baixa e realiza build do projeto "api-mock-transacoes"
RUN git clone https://github.com/nilson-paiao/api-mock-transacoes.git /home/api-mock-transacoes
WORKDIR "/home/api-mock-transacoes/"
RUN /bin/bash -l -c "gradle build"

#Faz deploy do projeto
RUN cp /home/api-mock-transacoes/build/libs/api-mock-transacoes-1.0.war /opt/glassfish5/glassfish/domains/domain1/autodeploy/
CMD /opt/glassfish5/bin/./asadmin start-domain --verbose