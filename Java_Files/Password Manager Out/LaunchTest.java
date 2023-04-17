import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class LaunchTest {
    public static void main(String args[]) throws IOException, InterruptedException {
      BufferedReader br = new BufferedReader(new FileReader("Launch1.txt"));
        for (String line; (line = br.readLine()) != null;) {
            System.out.print(line);
        }
        br.close();  
        Thread.sleep(3000);
        BufferedReader br2 = new BufferedReader(new FileReader("Launch2.txt"));
        for (String line; (line = br2.readLine()) != null;) {
            System.out.print(line);
        }
        br2.close();
        Thread.sleep(3000);
        String Options[] = {"New Password", "Edit Password", "Delete Password"};
        SelectionMenu SM = new SelectionMenu(Options , "Select Desired Option", "e", TerminalUtils.Colors.CYAN);
        SM.run();
        BufferedReader br3 = new BufferedReader(new FileReader("End.txt"));
        for (String line; (line = br3.readLine()) != null;) {
            System.out.print(line);
        }
        br3.close();
    }
}