import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Database {
    // NOTE :
    // Public methods may not throw
    // DO NOT MAKE THEM THROW EXCEPTIONS
    // In fact, how about handling exceptions inside the method itself properly?
    // you ever think about that?
    final String DATABASE_NAME = "database.dat";
    HashMap<String, String> passwordsMap = getPasswordsFromDB();

    /*
     * Public
     * Functions
     * Section
     */

    /**
     * @param encryptedString : a string with that represents an app and its
     *                        associated password. (Example :
     *                        "firefox:somepassword")
     */
    public void storetoDB(String encryptedString) {
        appendToDataBase(encryptPassword(encryptedString));
    }

    public String getPasswordFromDB(String associatedApp) {
        return NewEncrypter.decrypt(passwordsMap.get(associatedApp));
    }

    public HashMap<String, String> getPasswordsFromDB() {
        // should be ok to not change
        HashMap<String, String> associationsMap = new HashMap<String, String>();
        String currStringPair[];
        try (StringIterator stringIterator = new StringIterator()) {
            while (stringIterator.hasNext()) {
                currStringPair = stringIterator.next().split(":");
                associationsMap.put(currStringPair[0], currStringPair[1]);
            }
        }
        return associationsMap;

    }

    public void modifyPassword(String oldpassword, String newPassword) {
        passwordsMap.replace(oldpassword, NewEncrypter.encrypt(newPassword));
        deserializePasswords();

    }

    public void deletePassword(String password) {
        passwordsMap.remove(password);
        deserializePasswords();
    }

    /*
     * Private
     * Code
     * Section
     */

    private static String encryptPassword(String passwordPair) {
        String split[] = passwordPair.split(":"), app = split[0], pass = split[1];
        return app + ":" + NewEncrypter.encrypt(pass);
    }

    private static String decryptPassword(String passwordPair) {
        // encrypting an encrypted password reverses it.
        return encryptPassword(passwordPair);
    }

    private void deserializePasswords() {
        RandomAccessFile file = getAccessFile("rw");
        try {
            file.setLength(0); // delete all passwords in the file temporarily
            for (Map.Entry<String, String> entry : passwordsMap.entrySet()) {
                file.writeUTF(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Couldn't delete file contents");
        }
    }

    private RandomAccessFile getAccessFile(String mode) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(DATABASE_NAME, mode);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't open file");
        }
        return file;
    }

    // custom iterator to make getting strings from classes less annoying
    private class StringIterator implements Iterator<String>, AutoCloseable {
        private RandomAccessFile file;
        private String currString;

        public String getCurrString() {
            return currString;
        }

        public void advanceCurrString() {
            try {
                currString = file.readUTF();
            } catch (IOException e) {
                // do nothing when an EOF exception is thrown
                currString = null;
            }
        }

        public StringIterator() {
            file = getAccessFile("rw"); // add write mode to create db file if it doesn't already exist.
        }

        @Override
        public boolean hasNext() {
            advanceCurrString();
            return getCurrString() != null;
        }

        @Override
        public String next() {
            return getCurrString();
        }

        @Override
        public void close() {
            try {
                file.close();
            } catch (IOException e) {
                System.out.println("Couldn't close file.");
            }
        }
    }

    private void appendToDataBase(String password) {
        // append to a dat file
        RandomAccessFile file = getAccessFile("rw");
        try {
            if (file.length() > 0)
                file.seek(file.length());
            file.writeUTF(password);
        } catch (IOException e) {
            System.out.println("Couldn't add new password.");
        }
    }

}
