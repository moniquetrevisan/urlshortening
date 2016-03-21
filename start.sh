#!/bin/sh

#start.sh
cd /usr/lib/hsqldb/lib
java -cp hsqldb.jar org.hsqldb.server.Server --database.0 file:urlshortening --dbname.0

/usr/lib/tomcat/bin/catalina.sh