import java.io.IOException;

public class DatabaseTest {
    public static void main(String[] args) {
        String str = "ass", options[] = { "Option1", "Option2", "Option3", "Option4" };
        Database db = new Database();
        SelectionMenu s = new SelectionMenu(options, "Select an option from the menu below", "e",
                TerminalUtils.Colors.CYAN);
        s.run();
        // db.storetoDB(s);
        // db.storetoDB("asshole");
        System.out.println("Exited menu.");
        // try {
        // for (String string : db.getPasswords()) {
        // System.out.println("The password is : " + string);
        // }
        // } catch (IOException e) {
        // System.out.println("Couldn't get passwords");
        // }
    }
}
