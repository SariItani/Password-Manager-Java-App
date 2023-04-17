public class SelectionMenuTest {

    public static void main(String[] args) {

        String options[] = { "Option1", "Option2", "Option3", "Option4" };
        SelectionMenu s = new SelectionMenu(options, "Select an option from the menu below", "e",
                TerminalUtils.Styles.CYAN);

        // note that the selection represents the actual array index of the options
        // array that you specify
        int selection = s.run();
        System.out.println("Selected option " + selection);
        System.out.println("Exited menu.");
    }
}
