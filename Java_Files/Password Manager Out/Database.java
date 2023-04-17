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
        RandomAccessFile file = getAccessFile("w");
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        Byte[] bytes_arr = {};
        try (StringIterator stringIterator = new StringIterator()) {
            while (stringIterator.hasNext()) {
                byte[] temp = stringIterator.next().getBytes();
                if (new String(temp).equals(password))
                    for (int i = 0; i < temp.length; i++) {
                        bytes.add(temp[i]);
                    }
                file.setLength(0); // delete the contents of the file
                bytes_arr = bytes.toArray(bytes_arr);
                for (int i = 0; i < bytes_arr.length; i++)
                    file.writeByte(bytes_arr[i].byteValue()); // rewrite the bytes into the file, excluding the password
            }
        } catch (IOException e) {
            System.out.println("Couldn't get file length for some reason.");
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
        private long position;
        private long length;

        public StringIterator() {
            try {
                file = getAccessFile("r");
                position = 0;
                length = file.length();
            } catch (IOException e) {
                System.out.println("Couldn't construct string iterator.");
            }
        }

        @Override
        public boolean hasNext() {
            return position < length;
        }

        @Override
        public String next() {
            try {
                String str = file.readUTF();
                position = file.getFilePointer();
                return str;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
