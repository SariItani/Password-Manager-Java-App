import java.util.Base64;
import java.util.Scanner;

public class LaunchTest {

    public static void main(String args[]) throws InterruptedException {
        final String LAUNCH1 = "IF9fX18gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBfXyAgICAgDQovXCAgX2BcICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgL1wgXCAgICANClwgXCBcTFwgXCBfXyAgICAgIF9fX18gICAgX19fXyAgX18gIF9fICBfXyAgICBfX18gICBfIF9fICBcX1wgXCAgIA0KIFwgXCAsX18vJ19fYFwgICAvJyxfX1wgIC8nLF9fXC9cIFwvXCBcL1wgXCAgLyBfX2BcL1xgJ19fXC8nX2AgXCAgDQogIFwgXCBcL1wgXExcLlxfL1xfXywgYFwvXF9fLCBgXCBcIFxfLyBcXy8gXC9cIFxMXCBcIFwgXC8vXCBcTFwgXCANCiAgIFwgXF9cIFxfXy8uXF9cL1xfX19fL1wvXF9fX18vXCBcX19feF9fXy8nXCBcX19fXy9cIFxfXFwgXF9fXyxfXA0KICAgIFwvXy9cL19fL1wvXy9cL19fXy8gIFwvX19fLyAgXC9fXy8vX18vICAgXC9fX18vICBcL18vIFwvX18sXyAvDQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICANCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIA0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgDQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICANCiAgX19fIF9fXyAgICAgIF9fICAgICAgX19fICAgICAgX18gICAgICAgX18gICAgICBfXyAgIF8gX18gICAgICAgIA0KLycgX19gIF9fYFwgIC8nX19gXCAgLycgXyBgXCAgLydfX2BcICAgLydfIGBcICAvJ19fYFwvXGAnX19cICAgICAgDQovXCBcL1wgXC9cIFwvXCBcTFwuXF8vXCBcL1wgXC9cIFxMXC5cXy9cIFxMXCBcL1wgIF9fL1wgXCBcLyAgICAgICANClwgXF9cIFxfXCBcX1wgXF9fLy5cX1wgXF9cIFxfXCBcX18vLlxfXCBcX19fXyBcIFxfX19fXFwgXF9cICAgICAgIA0KIFwvXy9cL18vXC9fL1wvX18vXC9fL1wvXy9cL18vXC9fXy9cL18vXC9fX19MXCBcL19fX18vIFwvXy8gICAgICAgDQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIC9cX19fXy8gICAgICAgICAgICAgICAgICANCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgXF8vX18vICAgICAgICAgICAgICAgICAgIA0KIF9fICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIF9fICAgICAgICAgICAgICAgICAgICAgDQovXCBcICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAvXCBcICAgICAgICAgICAgICAgICAgICANClwgXCBcICAgICAgICAgX18gICAgIF9fICBfXyAgICBfX18gICAgIF9fX1wgXCBcX19fICAgICAgICAgICAgICAgIA0KIFwgXCBcICBfXyAgLydfX2BcICAvXCBcL1wgXCAvJyBfIGBcICAvJ19fX1wgXCAgXyBgXCAgICAgICAgICAgICAgDQogIFwgXCBcTFwgXC9cIFxMXC5cX1wgXCBcX1wgXC9cIFwvXCBcL1wgXF9fL1wgXCBcIFwgXCAgICAgICAgICAgICANCiAgIFwgXF9fX18vXCBcX18vLlxfXFwgXF9fX18vXCBcX1wgXF9cIFxfX19fXFwgXF9cIFxfXCAgICAgICAgICAgIA0KICAgIFwvX19fLyAgXC9fXy9cL18vIFwvX19fLyAgXC9fL1wvXy9cL19fX18vIFwvXy9cL18vICAgICAgICAgICAgDQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICANCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIA==";
        final String END = "DQogIF9fX19fX18gXyAgICAgICAgICAgICAgICAgXyAgICAgICAgICAgICAgICAgICAgICAgICAgIF9fICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBfICAgICAgICAgICAgICAgICAgXyAgIF8gDQogfF9fICAgX198IHwgICAgICAgICAgICAgICB8IHwgICAgICAgICAgICAgICAgICAgICAgICAgLyBffCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHwgfCAgICAgICAgICAgICAgICB8IHwgfCB8DQogICAgfCB8ICB8IHxfXyAgIF9fIF8gXyBfXyB8IHwgX18gIF8gICBfICBfX18gIF8gICBfICB8IHxfIF9fXyAgXyBfXyAgIF8gICBfICBfX18gIF8gICBfIF8gX18gIHwgfF8gXyBfXyBfICAgXyBfX198IHxffCB8DQogICAgfCB8ICB8ICdfIFwgLyBfYCB8ICdfIFx8IHwvIC8gfCB8IHwgfC8gXyBcfCB8IHwgfCB8ICBfLyBfIFx8ICdfX3wgfCB8IHwgfC8gXyBcfCB8IHwgfCAnX198IHwgX198ICdfX3wgfCB8IC8gX198IF9ffCB8DQogICAgfCB8ICB8IHwgfCB8IChffCB8IHwgfCB8ICAgPCAgfCB8X3wgfCAoXykgfCB8X3wgfCB8IHx8IChfKSB8IHwgICAgfCB8X3wgfCAoXykgfCB8X3wgfCB8ICAgIHwgfF98IHwgIHwgfF98IFxfXyBcIHxffF98DQogICAgfF98ICB8X3wgfF98XF9fLF98X3wgfF98X3xcX1wgIFxfXywgfFxfX18vIFxfXyxffCB8X3wgXF9fXy98X3wgICAgIFxfXywgfFxfX18vIFxfXyxffF98ICAgICBcX198X3wgICBcX18sX3xfX18vXF9fKF8pDQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBfXy8gfCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBfXy8gfCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgDQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHxfX18vICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHxfX18vICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgDQo=";
        final String LOADING = "IF9fXyAgICAgICBfX19fX19fXyAgX19fX19fX18gIF9fX19fX19fICBfX18gIF9fX19fX19fICAgX19fX19fX18gICAgICAgICAgICAgICAgICAgICAgICAgICAgICANCnxcICBcICAgICB8XCAgIF9fICBcfFwgICBfXyAgXHxcICAgX19fIFx8XCAgXHxcICAgX19fICBcfFwgICBfX19fXCAgICAgICAgICAgICAgICAgICAgICAgICAgICAgDQpcIFwgIFwgICAgXCBcICBcfFwgIFwgXCAgXHxcICBcIFwgIFxffFwgXCBcICBcIFwgIFxcIFwgIFwgXCAgXF9fX3wgICAgICAgICAgICAgICAgICAgICAgICAgICAgIA0KIFwgXCAgXCAgICBcIFwgIFxcXCAgXCBcICAgX18gIFwgXCAgXCBcXCBcIFwgIFwgXCAgXFwgXCAgXCBcICBcICBfX18gICAgICAgICAgICAgICAgICAgICAgICAgICANCiAgXCBcICBcX19fX1wgXCAgXFxcICBcIFwgIFwgXCAgXCBcICBcX1xcIFwgXCAgXCBcICBcXCBcICBcIFwgIFx8XCAgXCAgICAgIF9fXyBfX18gX19fIF9fXyBfX18gDQogICBcIFxfX19fX19fXCBcX19fX19fX1wgXF9fXCBcX19cIFxfX19fX19fXCBcX19cIFxfX1xcIFxfX1wgXF9fX19fX19cICAgIHxcX19cXF9fXFxfX1xcX19cXF9fXA0KICAgIFx8X19fX19fX3xcfF9fX19fX198XHxfX3xcfF9ffFx8X19fX19fX3xcfF9ffFx8X198IFx8X198XHxfX19fX19ffCAgICBcfF9fXHxfX1x8X19cfF9fXHxfX3wNCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgDQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIA0KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA=";
        System.out.println(getFromBase64(LAUNCH1));
        Thread.sleep(3000);
        System.out.print(TerminalUtils.Styles.CLEAR); // Clear the terminal before loading screen
        char[] loadingString = getFromBase64(LOADING).toCharArray();
        for (int i = 0; i < loadingString.length; i++) {
            System.out.print(loadingString[i]);
            Thread.sleep(5);
        }
        //sami part:
        String Options[] = {"New Password", "Copy to Clipboard", "Modify Password", "Delete Password", "List All Password"};
        String passwords[];
        Database DB = new Database();
        Scanner scan = new Scanner(System.in);
        String password, NewPass; int ind;
        SelectionMenu SM = new SelectionMenu(Options, "Select Desired Option", "e", TerminalUtils.Styles.CYAN);
        SM.run();
        switch (SM.options[])
        {
            //New Pass
            case SM.options[1]:
            {System.out.println("Input Desired Password:");
            password = scan.nextLine();
            DB.storetoDB(password); 
            }
            //Copy to Clipboard
            case SM.options[2]:
            {
                System.out.println("Which password do you desire?:");
                ind = scan.nextInt();
                passwords = DB.getPasswordsFromDB();
                for (int i = 0; i < passwords.length; i++)
                System.out.println("The password is : " + passwords[i]); */
            }
            //Modify Pass
            case SM.options[3]:
            {
                System.out.println("Which password would you like to modify?:");
                password = scan.nextLine();
                System.out.println("Insert Modification");
                NewPass = scan.nextLine();
                DB.modifyPassword(password, NewPass);
            }
            //Delete Pass
            case SM.options[4]:
            {    
                System.out.println("Which password would like to omit?:");
                password = scan.nextLine();
                DB.deletePassword(password);
            }
            //List All
            case SM.options[5]:
            {
                System.out.println("Your stored passwords are:");
                for(int i = 0; i < passwords.length; i++)
                {
                    System.out.println(passwords[i]);
                }
            }
        }
        
        getFromBase64(END);
    }

    private static String getFromBase64(String asciiString) {
        return new String(Base64.getDecoder().decode(asciiString));
    }
}