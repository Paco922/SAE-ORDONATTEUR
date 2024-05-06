package vue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.io.File;

import modele.Position;
import modele.Temple;
import static vue.ConstantesCanva.*;


public class LectureScenario {
    /*
    La methode lecture prend en param un fichier contenant un scenario et retourne une collection de donnée
     */
    public static Collection<Temple> lecture(File fichierScenario) {

        Collection<Temple> templesDuScenario = new ArrayList<>();
        // Remplacer la liste par un type de collection plus adapté

        try {
            Scanner scanner = new Scanner(fichierScenario);
            Temple temple;
            while (scanner.hasNext()) {
                // LARGEUR_CANVAS = 31 HAUTEUR_CANVA = 31
                // permet de traiter tout les scénarios proposés
                int posX = scanner.nextInt() + LARGEUR_CANVA / (2 * CARRE);
                // Pourquoi ajouter LARGEUR_CANVAS/(2*CARRE)?

                int posY = scanner.nextInt() + HAUTEUR_CANVAS / (2 * CARRE);

                int couleur = scanner.nextInt();
                int cristal = scanner.nextInt();
                temple = new Temple(new Position(posX, posY), couleur, cristal);
                templesDuScenario.add(temple);
            }

            scanner.close();
        }
       catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
       }
       return templesDuScenario;
}

}
