import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Database {
    // NOTE :
    // Public methods may not throw
    // DO NOT MAKE THEM THROW EXCEPTIONS
    // In fact, how about handling exceptions inside the method itself properly?
    // you ever think about that?
    final String DATABASE_NAME = "database.dat";
    String[] passwords = getPasswordsFromDB();

    /*
     * Public
     * Functions
     * Section
     */
    public void storetoDB(String encryptedString) {
        appendToDataBase(encryptedString);
    }

    public String getPasswordFromDB(String encryptedString) {
        String temp = "";
        try {
            temp = getFromDataBase(encryptedString);
        } catch (IOException e) {
            System.out.println("Couldn't get password from database");
        }
        return temp;
    }

    public String[] getPasswordsFromDB() {
        ArrayList<String> passwords_temp = new ArrayList<String>();
        String passwords[] = {};
        RandomAccessFile file = null;
        file = getAccessFile("rw");
        while (file != null) {
            try {
                passwords_temp.add(file.readUTF());
            } catch (IOException e) {
                break;
            }
        }
        return passwords_temp.toArray(passwords);

    }

    public void modifyPassword(String oldpassword, String newPassword) {
        deletePassword(oldpassword);
        appendToDataBase(newPassword);
    }

    public void deletePassword(String password) {
        RandomAccessFile file = getAccessFile("rw");
        ArrayList<String> strings = new ArrayList<String>();
        String string_arr[] = {};
        String currString = "";
        try (StringIterator stringIterator = new StringIterator()) {
            while (stringIterator.hasNext()) {
                currString = stringIterator.next();
                if (!currString.equals(password))
                    strings.add(currString);
            }
            file.setLength(0); // delete the contents of the file
            string_arr = strings.toArray(string_arr);
            for (int i = 0; i < string_arr.length; i++)
                file.writeUTF(string_arr[i]); // rewrite the bytes into the file, excluding the password
        } catch (IOException e) {
            System.out.println("Couldn't get file length.");
        }

    }

    /*
     * Private
     * Code
     * Section
     */

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
            file = getAccessFile("r");
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

    private String getFromDataBase(String pass) throws IOException {
        // try with resources to automatically close the file
        try (StringIterator stringIterator = new StringIterator()) {
            String match = "";
            while (stringIterator.hasNext()) {
                match = stringIterator.next();
                if (match.equals(pass))
                    break;
            }
            return match;
        }
    }

}
