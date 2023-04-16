import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;
public class Encrypter
{  
    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void log_message(String message)
    {
        log.log(Level.INFO, message);
    }

    public static void main(String[] args)
    {
        XMLFormatter xmlFormatter = new XMLFormatter();
        LogRecord logRecord = new LogRecord(Level.INFO, "Logrecord message to be printed in xml file..");
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("logrecordxml.xml");
            fileHandler.setFormatter(xmlFormatter);
            fileHandler.publish(logRecord);
            fileHandler.flush();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Encrypter e0 = new Encrypter();
        e0.log_message("HELL YEAH");
    }
}
