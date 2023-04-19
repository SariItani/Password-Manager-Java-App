
public class SelectionMenu {
    public String[] options;
    public String prompt;
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
        this.hilightColor = TerminalUtils.Styles.CYAN;
    }

    public SelectionMenu(String[] options, String promptString) {
        this.options = options;
        this.prompt = promptString;
        this.hilightColor = TerminalUtils.Styles.CYAN;
    }

    public SelectionMenu(String[] options, String prompt, String exitStr, String hilightColor) {
        this.options = options;
        this.prompt = prompt;
        this.hilightColor = hilightColor;
    }

    public int run() {
        while (true) {
            // Print out the menu options
            System.out.print(TerminalUtils.Styles.CLEAR); // clear the console
            System.out.println(prompt + "(press enter to exit menu and confirm selection)");
            System.out.println("To navigate the menu, use the up and down arrow keys\n\n");
            for (int i = 0; i < options.length; i++) {
                if (i == selectedOptionIndex) {
                    System.out.println("> " + TerminalUtils
                            .styleString(TerminalUtils.styleString(options[i], hilightColor),
                                    TerminalUtils.Styles.UNDERLINE));
                } else {
                    System.out.println("  " + options[i]);
                }
            }

            KeystrokeDetector.KEYS key = KeystrokeDetector.readKey();
            switch (key) {
                case ARROW_UP:
                    if (selectedOptionIndex > 0)
                        selectedOptionIndex--;
                    break;

                case ARROW_DOWN:
                    if (selectedOptionIndex < options.length - 1)
                        selectedOptionIndex++;
                    break;
                case ENTER:
                    return selectedOptionIndex;
                case UNRECOGNIZED:
                    System.out.println("Unrecognized Input!");
                    break;
            }

        }
    }
}
