import java.util.logging.Logger;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;

public class Encrypter
{
    // Private Entities

    // Logging:
    private final static Logger consolelog = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static XMLFormatter xmlFormatter = new XMLFormatter();

    // Encrypting/Decrypting:
    private SecretKey key;
    private final int KEY_SIZE = 128;
    private final int T_LEN = 128;
    private Cipher encryptionCipher;

    // Private Methods
    private String encode(byte[] data)
    {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data)
    {
        return Base64.getDecoder().decode(data);
    }

    // Public Methods
    
    // Logging:
    public void log_message(String message)
    {
        consolelog.log(Level.INFO, message);
    }

    public void log_file(String record) throws SecurityException, IOException
    {
        LogRecord logRecord = new LogRecord(Level.INFO, record);
        FileHandler filelog = new FileHandler("logrecordxml.xml");
        filelog.setFormatter(xmlFormatter);
        filelog.publish(logRecord);
        filelog.flush();
    }

    // Encrypting/Decrypting:
    public void init() throws NoSuchAlgorithmException
    {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    public String encrypt(String message) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {
        byte[] messageInBytes = message.getBytes();
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        byte[] messageInBytes = decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, SecurityException, IOException
    {
        String logRecord = "\n", message = "";
        Encrypter encrypter = new Encrypter();
        message = "Created the encrypter object\n";
        logRecord = logRecord.concat(message);
        encrypter.log_message(message);

        encrypter.init();
        message = "initialized the encrypter\n";
        logRecord = logRecord.concat(message);
        encrypter.log_message(message);

        String encryptedMessage = encrypter.encrypt("MyFabulousPasswordUncrackableYourMamaIsABanana");
        message = "Encrypted to : " + encryptedMessage + "\n";
        logRecord = logRecord.concat(message);
        encrypter.log_message(message);
        
        String decryptedMessage = encrypter.decrypt(encryptedMessage);
        message = "Decrypted to : "+ decryptedMessage + "\n";
        logRecord = logRecord.concat(message);
        encrypter.log_message(message);

        encrypter.log_file(logRecord);
    }
}
