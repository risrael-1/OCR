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
                //System.out.println(scanner.nextLine());
                String input = scanner.nextLine();
                //System.out.print(input);

                String[] arrayInput = input.split("");
                //System.out.print(arrayInput.length);
                String[][] result = new String[27][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 27; j++) {
                        result[j][i] = arrayInput[j];
                    }
                }

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 27; j++) {
                        System.out.print(result[j][i]);
                    }
                    System.out.println("");
                }

            }
            scanner.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
