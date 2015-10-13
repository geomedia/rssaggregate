#!/bin/bash

SERVER=mygrid.huma-num.fr
USER=geomedia
SSH=$USER@$SERVER
WEBAPPS="/sites/geomedia/resource/tomcat/dev/webapps"
REMOTE_DIST=$WEBAPPS/
REMOTE_WEB=$WEBAPPS/RSSAgregate/

echo "Pushing the application..."

cd ..

APP=`pwd`

echo "Application local directory: $APP"

LOCAL_DIST=$APP/dist/RSSAgregate.war
LOCAL_WEB=$APP/build/web
LOCAL_CONFIG=$APP/config/dev

PERSISTENCE=$LOCAL_WEB/WEB-INF/classes/META-INF/persistence.xml
CONTEXT=$LOCAL_WEB/META-INF/context.xml
LOG4J=$LOCAL_WEB/WEB-INF/classes/log4j.properties

echo "Installing configuration files..."

cp $LOCAL_CONFIG/persistence.xml $PERSISTENCE
cp $LOCAL_CONFIG/log4j.properties $LOG4J
cp $LOCAL_CONFIG/context.xml $CONTEXT

echo "Installing configuration files... OK"

echo "Pushing the application..."

rsync -avzr $LOCAL_DIST $SSH:$REMOTE_DIST
rsync -avzr $LOCAL_WEB/* $SSH:$REMOTE_WEB

echo "Pushing the application... OK"


