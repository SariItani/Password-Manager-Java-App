import java.io.*;
import java.util.ArrayList;

public class Database {

    final String DATABASE_NAME = "database.dat";

    public void storetoDB(String encryptedString) {
        try {
            appendToDataBase(DATABASE_NAME, encryptedString);
        } catch (IOException e) {
            System.out.println("Couldn't store password in database");
        }
    }

    public String getPasswordfromDB(String encryptedString) {
        String temp = "";
        try {
            temp = getFromDataBase(DATABASE_NAME, encryptedString);
        } catch (IOException e) {
            System.out.println("Couldn't get password from database");
        }
        return temp;
    }

    public String[] getPasswords() throws IOException {
        ArrayList<String> passwords_temp = new ArrayList<String>();
        String passwords[] = {};
        RandomAccessFile file = new RandomAccessFile(DATABASE_NAME, "r");
        while (true) {
            try {
                passwords_temp.add(file.readUTF());
            } catch (EOFException e) {
                break;
            }

        }
        file.close();
        return passwords_temp.toArray(passwords);

    }

    private static void appendToDataBase(String filename, String data)
            throws IOException {
        RandomAccessFile file = new RandomAccessFile(filename, "rw");
        if (file.length() > 0)
            file.seek(file.length()); // append to a dat file
        file.writeUTF(data);
        file.close();
    }

    private static String getFromDataBase(String filename, String pass) throws IOException {
        RandomAccessFile file = new RandomAccessFile(filename, "r");
        String match = "";
        while (true) {
            match = file.readUTF();
            if (match.equals(pass))
                break;

        }
        file.close();
        return match;
    }
}
