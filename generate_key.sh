#!/bin/sh
FILE="./Java_Files/PasswordManager/src/NewEncrypter.java"
line=$(cat $FILE |  grep -n "private static final String KEY" | cut -d " " -f 1 | sed "s/://")
PATTERN='"\(.*\)"'

# actually introduce the random key
RANDOM_KEY=$(tr -dc 'A-Za-z0-9!#$%&()*+,-./:;<=>?@[\]^_`{|}~' </dev/urandom | head -c 20  ; echo)
RANDOM_KEY=$(python3 -c "import re; print(re.escape(\"${RANDOM_KEY}\"))")

sed -i "${line}s/${PATTERN}/\"$RANDOM_KEY\"/" $FILE

