public class DatabaseTest {
    public static void main(String[] args) {
        String str = "pepsi", passwords[];
        Database db = new Database();
        db.storetoDB(str);
        db.storetoDB("man");
        db.storetoDB("asshole");
        passwords = db.getPasswordsFromDB();
        for (int i = 0; i < passwords.length; i++)
            System.out.println("The password is : " + passwords[i]);
        db.deletePassword("asshole");
        db.modifyPassword(str, "cola");
    }
}
