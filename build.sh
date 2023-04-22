#!/bin/bash
# get the project from github
git clone https://github.com/SariItani/Password-Manager-Java-App.git
# generate the manifest file
printf "Main-Class: TerminalInterface\nClass-Path: bin/\n" > Manifest.txt
DIR=Password-Manager-Java-App
CLASSPATH=~/Downloads/jna-5.13.0.jar:~/Downloads/jna-platform-5.13.0.jar:$CLASSPATH
mv Manifest.txt $DIR/
cd $DIR
# generate a random key before building the files
./generate_key.sh
# build it after randomizing the key
javac -d Java_Files/PasswordManager/bin Java_Files/PasswordManager/src/*.java
jar cvfm PasswordManager.jar Manifest.txt -C Java_Files/PasswordManager/bin .
java -jar PasswordManager.jar
