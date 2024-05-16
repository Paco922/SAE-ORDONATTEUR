package vue;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;


import javafx.geometry.Pos;
import modele.Position;
import modele.Temple;
import static vue.ConstantesCanva.*;


public class LectureScenario {
    /*
    La methode lecture prend en param un fichier contenant un scenario et retourne une collection de donnée
     */

    // Les variables champs
    private Position position;
    private int couleur; // Assuming couleur is an int representing color
    private int cristal; // Assuming cristal is an int
    public static Collection<Temple> lecture(File fichierScenario) {

        // Remplacer la liste par un type de collection plus adapté
        Map<Position, Temple> templesDuScenario = new HashMap<>();

        try {
            Scanner scanner = new Scanner(fichierScenario);
            Temple temple;
            while (scanner.hasNext()) {
                // LARGEUR_CANVAS = 31 HAUTEUR_CANVA = 31
                // permet de traiter tout les scénarios proposés
                int posX = scanner.nextInt() + LARGEUR_CANVA / (2 * CARRE);
                // Pourquoi ajouter LARGEUR_CANVAS/(2*CARRE)?

                int posY = scanner.nextInt() + HAUTEUR_CANVA / (2 * CARRE);

                int couleur = scanner.nextInt();
                int cristal = scanner.nextInt();
                temple = new Temple(new Position(posX, posY), couleur, cristal);
                templesDuScenario.put(temple.getPosition(), temple);
            }
            System.out.println(templesDuScenario);


            scanner.close();
        }
       catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
       }
       return (Collection<Temple>) templesDuScenario;
    }

    public String toString() {
        return "Temple{" +
                "position=" + position +
                ", couleur=" + couleur +
                ", cristal=" + cristal +
                '}';
    }



}
