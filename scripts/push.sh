#!/bin/bash

cd ..

APP=`pwd`

echo "Application local directory: $APP"

LOCAL_DIST=$APP/dist/RSSAgregate.war
LOCAL_WEB=$APP/build/web
LOCAL_CONFIG=$APP/config/$1
CONFIG_FILE=$LOCAL_CONFIG/configuration

PERSISTENCE=$LOCAL_WEB/WEB-INF/classes/META-INF/persistence.xml
CONTEXT=$LOCAL_WEB/META-INF/context.xml
LOG4J_PROPERTIES=$LOCAL_WEB/WEB-INF/classes/log4j.properties
LOG4J=$LOCAL_WEB/WEB-INF/classes/log4j.xml

source $CONFIG_FILE
SSH=$USER@$SERVER

echo "Installing configuration files..."

cp $LOCAL_CONFIG/persistence.xml $PERSISTENCE
cp $LOCAL_CONFIG/log4j.properties $LOG4J_PROPERTIES
cp $LOCAL_CONFIG/log4j.xml $LOG4J
cp $LOCAL_CONFIG/context.xml $CONTEXT

echo "Installing configuration files... OK"

echo "Pushing the application..."

rsync -avzr $LOCAL_DIST $SSH:$REMOTE_DIST
rsync -avzr $LOCAL_WEB/* $SSH:$REMOTE_WEB

echo "Pushing the application... OK"


