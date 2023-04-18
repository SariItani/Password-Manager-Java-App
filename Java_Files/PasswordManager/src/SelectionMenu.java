
import java.io.Console;

public class SelectionMenu {
    public String[] options;
    public String prompt;
    public String exitStr;
    public String hilightColor;
    private int selectedOptionIndex;

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Shorthand for setOptions(options) and setPrompt(prompt)
     */
    public void setMenu(String[] options, String prompt) {
        setOptions(options);
        setPrompt(prompt);
    }

    public SelectionMenu(String[] options) {
        this.options = options;
        this.prompt = "Select an option from the menu below";
        this.exitStr = "e";
        this.hilightColor = TerminalUtils.Styles.CYAN;
    }

    public SelectionMenu(String[] options, String promptString) {
        this.options = options;
        this.prompt = promptString;
        this.exitStr = "e";
        this.hilightColor = TerminalUtils.Styles.CYAN;
    }

    public SelectionMenu(String[] options, String prompt, String exitStr, String hilightColor) {
        this.options = options;
        this.prompt = prompt;
        this.exitStr = exitStr;
        this.hilightColor = hilightColor;
    }

    public int run() {
        while (true) {
            // Print out the menu options
            System.out.print(TerminalUtils.Styles.CLEAR); // clear the console
            System.out.println(prompt + "( write " + exitStr + " and press enter to exit menu and confirm selection)");
            System.out.println("To navigate the menu, use the up and down arrow keys and immediately press enter\n\n");
            for (int i = 0; i < options.length; i++) {
                if (i == selectedOptionIndex) {
                    System.out.println("> " + TerminalUtils
                            .styleString(TerminalUtils.styleString(options[i], hilightColor),
                                    TerminalUtils.Styles.UNDERLINE));
                } else {
                    System.out.println("  " + options[i]);
                }
            }

            // Read input from the user
            Console scanner = System.console();
            String input = new String(scanner.readPassword(""));

            if (input.equals(exitStr)) {
                break;
            }
            // Handle the input
            if (input.equals("w")) {

                if (selectedOptionIndex > 0) {
                    selectedOptionIndex--;
                }
            } else if (input.equals("s")) {

                if (selectedOptionIndex < options.length - 1) {
                    selectedOptionIndex++;
                }
            } else if (input.equals("\n")) {
                return selectedOptionIndex;
            } else {
                System.out.println("Invalid Input!");
            }
        }
        return selectedOptionIndex;
    }
}
