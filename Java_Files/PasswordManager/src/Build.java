import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Build {
        private static final String FILE_TO_REPLACE = "./Password-Manager-Java-App-main/Java_Files/PasswordManager/src/NewEncrypter.java";
        private static final String SRC_DIR = "./Password-Manager-Java-App-main/Java_Files/PasswordManager/src/";
        private static final String REPLACEMENT_PATTERN = escapeChars("\".*\"");
        private static final String[] URLS = {
                        "https://github.com/SariItani/Password-Manager-Java-App/archive/refs/heads/main.zip",
                        "https://repo1.maven.org/maven2/net/java/dev/jna/jna/5.13.0/jna-5.13.0.jar",
                        "https://repo1.maven.org/maven2/net/java/dev/jna/jna-platform/5.13.0/jna-platform-5.13.0.jar"
        };
        private static final String[] FILES_TO_DELETE = {
                        "main.zip",
                        "jna-5.13.0.jar",
                        "jna-platform-5.13.0.jar",
                        "Password-Manager-Java-App-main",
                        "classes",
                        "Manifest.txt"
        };

        public static void main(String[] args) throws Exception {
                // Pull the GitHub repository using wget as a zip.
                int exitCode;
                for (String string : URLS) {
                        String command = "wget " + string;
                        Process process = Runtime.getRuntime().exec(command);
                        exitCode = process.waitFor();
                        if (exitCode != 0) {
                                System.err.println("Failed to pull GitHub repository");
                                System.exit(1);
                        }
                }

                // unzip file using java
                extractZipFile("main.zip", ".");

                // Replace line 34 with a random string
                Random random = new Random();
                String replacement = "\"" + random.ints(10, 33, 127)
                                .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                                                StringBuilder::append)
                                .toString() + "\"";
                String fileContent = new String(Files.readAllBytes(Paths.get(FILE_TO_REPLACE)),
                                StandardCharsets.UTF_8);
                fileContent = fileContent.replaceAll(REPLACEMENT_PATTERN, replacement);
                Files.write(Paths.get(FILE_TO_REPLACE),
                                fileContent.getBytes(StandardCharsets.UTF_8));

                // // Build the repository using javac
                Process process;

                String envVariableString = System.getProperty("os.name").toLowerCase().contains("windows") ? ";" : ":";
                String execString = String.format(
                                "javac -d ./classes/ -cp ./jna-5.13.0.jar%s./jna-platform-5.13.0.jar%s%s  %sTerminalInterface.java",
                                envVariableString, envVariableString, SRC_DIR, SRC_DIR);

                process = Runtime.getRuntime().exec(execString);
                exitCode = process.waitFor();
                printError(process, exitCode);

                // extract the 2 jar files into the classes folder, and then compile with the
                // main app to make a fat jar.
                extractZipFile("./jna-5.13.0.jar", "./classes/");
                extractZipFile("./jna-platform-5.13.0.jar", "./classes/");
                // generate the Manifest file
                File manifestFile = new File("Manifest.txt");
                manifestFile.createNewFile(); // create it if it doesn't exist
                try (FileOutputStream outputStream = new FileOutputStream("Manifest.txt", false)) {
                        outputStream.write("Main-Class: TerminalInterface\nClass-Path: classes/\n".getBytes());
                }
                ProcessBuilder buildFiles = new ProcessBuilder();
                buildFiles.command("jar", "cvfm", "PasswordManager.jar", "Manifest.txt", "./classes");
                buildFiles.inheritIO();
                buildFiles.start().waitFor();
                // and finally delete all of the temporary folders.
                for (String fileName : FILES_TO_DELETE) {

                        File file = new File(fileName);
                        deleteDir(file);

                }
        }

        public static void extractZipFile(String zipFilePath, String outputFolder) throws IOException {
                try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
                        ZipEntry zipEntry = zipInputStream.getNextEntry();
                        while (zipEntry != null) {
                                String filePath = outputFolder + File.separator + zipEntry.getName();
                                if (!zipEntry.isDirectory()) {
                                        extractFile(zipInputStream, filePath);
                                } else {
                                        File directory = new File(filePath);
                                        directory.mkdirs();
                                }
                                zipEntry = zipInputStream.getNextEntry();
                        }
                }
        }

        private static void extractFile(ZipInputStream zipInputStream, String filePath) throws IOException {
                try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {
                        byte[] buffer = new byte[1024];
                        int count;
                        while ((count = zipInputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, count);
                        }
                }
        }

        public static String escapeChars(String str) {
                if (str != null && str.length() > 0) {
                        return str.replaceAll("[\\W]", "\\\\$0"); // \W designates non-word characters
                }
                return "";
        }

        public static void printError(Process process, int exitCode) throws IOException {
                int curr_byte;
                if (exitCode != 0) {
                        // read what the error is
                        InputStream stream = process.getErrorStream();
                        while ((curr_byte = stream.read()) != -1) {
                                System.out.print((char) curr_byte);
                        }
                        System.exit(1);
                }
        }

        private static void deleteDir(File file) {
                File[] contents = file.listFiles();
                if (contents != null) {
                        for (File f : contents) {
                                if (!Files.isSymbolicLink(f.toPath())) {
                                        deleteDir(f);
                                }
                        }
                }
                file.delete();
        }
}
