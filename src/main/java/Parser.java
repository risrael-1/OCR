import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Parser {
    public static void main(String args[]) {
        try {
            // Le fichier d'entrée
            FileInputStream file = new FileInputStream("src/main/java/text.txt");
            Scanner scanner = new Scanner(file);

            //renvoie true s'il y a une autre ligne à lire
            while(scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
