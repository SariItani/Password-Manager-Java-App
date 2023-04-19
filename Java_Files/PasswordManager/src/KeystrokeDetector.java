
import java.io.IOException;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
/*
 * The basic idea is to somehow enable a terminal's "raw" mode
 * and disable echoing of characters in the standard input.
 * Once this is done, get the integer representation of specific characters.
 */

public class KeystrokeDetector {
    private static final int MAX_BYTES = 1024;
    private static int originalMode;

    public enum KEYS {
        ARROW_UP, ARROW_DOWN, ENTER, UNRECOGNIZED
    }

    public static KEYS readKey() {
        KEYS key = null;
        try {
            setRawMode();
            // Listen to user keystrokes
            byte[] bytes = new byte[MAX_BYTES];
            int numBytes = System.in.read(bytes);
            // Process each keystroke
            for (int i = 0; i < numBytes; i++) {
                bytes[i] &= 0xff; // mask with 0xff because bytes are signed, but characters are not...
            }

            if (isEnterKeyPressed(numBytes, bytes)) { // Enter key
                key = KEYS.ENTER;
            } else if (isUpArrowKeyPressed(numBytes, bytes)) { // Escape key
                key = KEYS.ARROW_UP;
            } else if (isDownArrowKeyPressed(numBytes, bytes))
                key = KEYS.ARROW_DOWN;
            else
                key = KEYS.UNRECOGNIZED;

            restoreMode();// Restore terminal's default mode
        } catch (IOException e) {
            System.out.println("Couldn't read key from stdin");
        }
        return key;

    }

    public static boolean isEnterKeyPressed(int numBytes, byte[] bytes) {
        return numBytes == 1 && bytes[0] == 13;
    }

    public static boolean isUpArrowKeyPressed(int numBytes, byte[] bytes) {
        return numBytes == 3 && bytes[0] == 27 && bytes[1] == 91 && bytes[2] == 65;
    }

    public static boolean isDownArrowKeyPressed(int numBytes, byte[] bytes) {
        return numBytes == 3 && bytes[0] == 27 && bytes[1] == 91 && bytes[2] == 66;
    }

    private static void restoreMode() {
        if (isWindows())
            resetModeWindows();
        else
            restoreModeLinux();

    }

    private static void setRawMode() {
        if (isWindows())
            setRawModeWindows();
        else
            setRawModeLinux();

    }

    public static boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows");
    }

    public static boolean isUnix() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("nix") || osName.contains("nux") || osName.contains("aix");
    }

    public static int byteArrToInt(byte[] bytes, int index) {
        return (int) bytes[index];
    }

    public static void setRawModeWindows() {
        // Set the console mode to raw on windows
        Kernel32 kernel32 = Kernel32.INSTANCE;
        HANDLE consoleHandle = kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        IntByReference mptr = new IntByReference();
        if (!kernel32.GetConsoleMode(consoleHandle, mptr)) {
            System.out.println("Couldn't get console mode");
        }
        // Get the original console mode
        originalMode = mptr.getValue();
        mptr.setValue(mptr.getValue() | Kernel32.ENABLE_VIRTUAL_TERMINAL_INPUT); // allow arrow keys to be sent to the
                                                                                 // stdin.
        kernel32.SetConsoleMode(consoleHandle,
                mptr.getValue()
                        & ~(Kernel32.ENABLE_LINE_INPUT | Kernel32.ENABLE_ECHO_INPUT | Kernel32.ENABLE_PROCESSED_INPUT));
    }

    public static void resetModeWindows() {
        // Reset the console mode on windows
        Kernel32 kernel32 = Kernel32.INSTANCE;
        HANDLE consoleHandle = kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (!kernel32.SetConsoleMode(consoleHandle, originalMode)) {
            System.err.println("Failed to reset console mode");
        }
    }

    private static void setRawModeLinux() {
        String[] cmd = { "/bin/sh", "-c", "stty raw -echo </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println("Couldn't set the terminal to raw mode.");
        }
    }

    private static void restoreModeLinux() {
        String[] cmd = { "/bin/sh", "-c", "stty sane </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println("Couldn't restore terminal mode.");
        }
    }
}
