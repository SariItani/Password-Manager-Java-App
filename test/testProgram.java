import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class testProgram
{
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {
        String logRecord = "\n", message = "";
        Database db = new Database(); //Every password entering the db must be encrypted...
        Encrypter ec = new Encrypter();
        ec.init();
        String[] options = {"add new password", "view certain password", "delete password", "modify password"};
        SelectionMenu sm = new SelectionMenu(options);
        TerminalUtils tu = new TerminalUtils();

        String password = "MyFabulousPasswordIfYouBreakIBreakYourBack";
        message = "Taken a password from user";
        ec.log_message(message);
        logRecord = logRecord.concat(message).concat("\n");

        db.storetoDB(ec.encrypt(password));
        message = "Saved " + password + " to database as " + ec.encrypt(password);
        ec.log_message(message);
        logRecord = logRecord.concat(message).concat("\n");

        db.deletePassword(password);
        message = "Deleted " + password;
        ec.log_message(message);
        logRecord = logRecord.concat(message).concat("\n");

        try {
            ec.log_file(logRecord);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
