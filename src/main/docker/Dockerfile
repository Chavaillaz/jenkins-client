FROM jenkins/jenkins:lts-jdk17

COPY --chown=jenkins:jenkins init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/

# Plugins
COPY --chown=jenkins:jenkins plugins.yml /usr/share/jenkins/ref/plugins.yml
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.yml

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false