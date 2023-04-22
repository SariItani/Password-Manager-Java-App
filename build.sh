#!/bin/bash
# get the project from github as well as the required libs
git clone https://github.com/SariItani/Password-Manager-Java-App.git
curl https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.13.0/jna-5.13.0.jar --output jna-5.13.0.jar
curl https://repo1.maven.org/maven2/net/java/dev/jna/jna-platform/5.13.0/jna-platform-5.13.0.jar --output jna-platform-5.13.0.jar
# generate the manifest file
printf "Main-Class: TerminalInterface\nClass-Path: bin/\n" > Manifest.txt
DIR=Password-Manager-Java-App
CLASSPATH=./jna-5.13.0.jar:./jna-platform-5.13.0.jar:$CLASSPATH
# generate a random key before building the files
./$DIR/generate_key.sh
# build it after randomizing the key
javac -d $DIR/Java_Files/PasswordManager/bin $DIR/Java_Files/PasswordManager/src/*.java
jar cvfm PasswordManager.jar Manifest.txt -C $DIR/Java_Files/PasswordManager/bin .
# delete the source files
rm -rf $DIR
# then run the program for the first time.
java -jar PasswordManager.jar
