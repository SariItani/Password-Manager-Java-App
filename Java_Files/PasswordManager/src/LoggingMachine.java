import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;

public class LoggingMachine
{
    // Private Entities
    private final static Logger consolelog = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static XMLFormatter xmlFormatter = new XMLFormatter();
    private String logrecord = "\n", message = "";
    
    // Private Methods


    // Public Methods

    // Setters and Getters

    // Message
    public void setMessage(String message)
    {
        this.message = message;
    }
    public String getMessage()
    {
        return this.message;
    }

    // logrecord
    public void setLogRecord(String logRecord)
    {
        this.logrecord = logRecord;
    }
    public String getLogRecord()
    {
        return this.logrecord;
    }

    // Useful utils
    public void log_message(String message)
    {
        setMessage(message);
        consolelog.log(Level.INFO, this.message);
    }

    public void record(String message)
    {
        String logrecord = getLogRecord();
        logrecord = logrecord.concat(message).concat("\n");
        setLogRecord(logrecord);
    }

    public void log_file() throws SecurityException, IOException
    {
        LogRecord logRecord = new LogRecord(Level.INFO, getLogRecord());
        FileHandler filelog = new FileHandler("RuntimeLog.xml");
        filelog.setFormatter(xmlFormatter);
        filelog.publish(logRecord);
        filelog.flush();
    }
    
}
