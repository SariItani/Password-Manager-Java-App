$FILE="./Java_Files/PasswordManager/src/NewEncrypter.java"
$line = (Select-String "private static final String KEY" $FILE).LineNumber
$PATTERN='".*"'
# actually introduce the random key

$RANDOM_KEY=$(New-Guid)

$RANDOM_KEY=$(python3 -c "import re; print(re.escape("${RANDOM_KEY}"))")

(Get-Content $FILE)[$line-1] = (Get-Content $FILE)[$line-1] -replace $PATTERN, ""$RANDOM_KEY""

Set-Content $FILE (Get-Content $FILE)