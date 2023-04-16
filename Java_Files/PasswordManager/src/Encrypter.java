import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;
public class Encrypter
{  
    private final static Logger consolelog = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void log_message(String message)
    {
        consolelog.log(Level.INFO, message);
    }

    public static void main(String[] args)
    {
        XMLFormatter xmlFormatter = new XMLFormatter();
        LogRecord logRecord = new LogRecord(Level.INFO, "Logrecord message to be printed in xml file..");
        FileHandler filelog;
        try {
            filelog = new FileHandler("logrecordxml.xml");
            filelog.setFormatter(xmlFormatter);
            filelog.publish(logRecord);
            filelog.flush();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Encrypter e0 = new Encrypter();
        e0.log_message("HELL YEAH");
    }
}
