FROM node:12.7-alpine as build
ENV PROJECT_DIR=/usr/src/app
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./ $PROJECT_DIR

RUN npm install
RUN npm run build


FROM mcr.microsoft.com/java/tomcat:8-zulu-alpine-tomcat-9
ENV PROJECT_DIR=/usr/src/app
RUN rm -rf /usr/local/tomcat/webapps/*
RUN mkdir -p /usr/local/tomcat/webapps/alibrary

COPY --from=0 $PROJECT_DIR/dist/alibrary /usr/local/tomcat/webapps/alibrary

EXPOSE 80
