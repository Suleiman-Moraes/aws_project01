FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY target/aws_project01-0.0.1.jar aws_project01-0.0.1.jar
ENV JAVA_OPTS=""
ENV SPRING_PROFILE="default"

ENTRYPOINT exec java $JAVA_OPTS \
	-Djava.security.egd=file:/dev/./urandom \
	-Duser.timezone=Brazil/East \
	-Dspring.profiles.active=$SPRING_PROFILE \
	-jar aws_project01-0.0.1.jar --trace