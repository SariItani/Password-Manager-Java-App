import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TerminalUtils {

    public static void setClipboard(String dataString) {
        StringSelection selection = new StringSelection(dataString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static String styleString(String str, String style) {
        // style here is any member of the enum
        return style + str + Styles.RESET;
    }

    public static void printFile(String fileName) {
        try (BufferedReader buf = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = buf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Couldn't find or open file.");
        }

    }

    public class Styles {
        // Common colors
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String MAGENTA = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";

        // Text formatting escape codes
        public static final String RESET = "\u001B[0m";
        public static final String BOLD = "\u001B[1m";
        public static final String ITALIC = "\u001B[3m";
        public static final String UNDERLINE = "\u001B[4m";
        public static final String CLEAR = "\033[H\033[2J";
    }

}
