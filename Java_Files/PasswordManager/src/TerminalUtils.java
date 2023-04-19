import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    private static final String WINDOWS_UP_ARROW = "\u001b[A";
    private static final String WINDOWS_DOWN_ARROW = "\u001b[B";
    private static final String WINDOWS_ENTER = "\r";
    private static final String UNIX_UP_ARROW = "\033[A";
    private static final String UNIX_DOWN_ARROW = "\033[B";
    private static final String UNIX_ENTER = "\n";

    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().contains("win");
    }

    public static boolean isUnix() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().contains("nix") || os.toLowerCase().contains("nux")
                || os.toLowerCase().contains("aix") || os.toLowerCase().contains("mac");
    }

    public static String getUpArrow() {
        return isWindows() ? WINDOWS_UP_ARROW : UNIX_UP_ARROW;
    }

    public static String getDownArrow() {
        return isWindows() ? WINDOWS_DOWN_ARROW : UNIX_DOWN_ARROW;
    }

    public static String getEnterKey() {
        return isWindows() ? WINDOWS_ENTER : UNIX_ENTER;
    }

    public static char readTerminalInput() throws IOException {
        if (isWindows()) {
            int input = System.in.read();
            if (input == 0 || input == 224) { // handle special keys in Windows
                input = System.in.read();
                switch (input) {
                    case 72:
                        return 'A'; // up arrow
                    case 80:
                        return 'B'; // down arrow
                    default:
                        return (char) input;
                }
            } else {
                return (char) input;
            }
        } else if (isUnix()) {
            byte[] input = new byte[3];
            System.in.read(input);
            if (new String(input).equals(getUpArrow())) {
                return 'A'; // up arrow
            } else if (new String(input).equals(getDownArrow())) {
                return 'B'; // down arrow
            } else {
                return new String(input).charAt(0);
            }
        } else {
            throw new RuntimeException("Unsupported operating system.");
        }
    }

    public static boolean isEnterKeyPressed() throws IOException {
        if (System.console() == null) {
            throw new RuntimeException("No console available");
        }

        // Get the file descriptor for the standard input stream
        int fd = System.in.read();
        if (fd == -1) {
            // End of file
            return false;
        } else {
            // Check if the input is a newline character
            return (char) fd == '\n';
        }
    }

}
