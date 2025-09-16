
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FileComparator {
    public static boolean compareFiles(String file1Path, String file2Path, int bufferSize) {
        Path path1 = Paths.get(file1Path);
        Path path2 = Paths.get(file2Path);

        try {
            if (!Files.exists(path1) || !Files.exists(path2)) {
                System.err.println("Error! could not open files.\n");
                System.exit(1);
            }
            if (Files.size(path1) != Files.size(path2)) {
                return false;
            }

            try (FileInputStream f1 = new FileInputStream(file1Path);
                    FileInputStream f2 = new FileInputStream(file2Path)) {

                byte[] buffer1 = new byte[bufferSize];
                byte[] buffer2 = new byte[bufferSize];
                int bytesRead1, bytesRead2;

                while ((bytesRead1 = f1.read(buffer1)) != -1) {
                    bytesRead2 = f2.read(buffer2);

                    if (bytesRead1 != bytesRead2) {
                        return false;
                    }

                    if (!Arrays.equals(Arrays.copyOf(buffer1, bytesRead1), Arrays.copyOf(buffer2, bytesRead2))) {
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String file1Path, file2Path;
        int bufferSize = 4096;
        Scanner scanner = new Scanner(System.in);

        if (args.length >= 3) {
            file1Path = args[0];
            file2Path = args[1];
            try {
                bufferSize = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid buffer size. Using default 4096 bytes.\n");
            }
        } else {
            System.out.print("Enter path for first file: ");
            file1Path = scanner.nextLine();

            System.out.print("Enter path for second file: ");
            file2Path = scanner.nextLine();

            System.out.print("Enter buffer size in bytes (default 4096): ");
            try {
                bufferSize = scanner.nextInt();
                if (bufferSize == 0) {
                    System.err.println("Invalid buffer size. Using default 4096 bytes.\n");
                    bufferSize = 4096;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid buffer size. Using default 4096 bytes.\n");
                bufferSize = 4096;
            }
        }

        long startTime = System.nanoTime();
        boolean result = compareFiles(file1Path, file2Path, bufferSize);
        long endTime = System.nanoTime();

        if (result) {
            System.out.println("Files are identical.");
        } else {
            System.out.println("Files are not identical.\n");
        }

        double duration = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Comparison took %.4f seconds.%n", duration);
        scanner.close();
    }
}
