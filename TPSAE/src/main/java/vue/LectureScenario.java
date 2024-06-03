package vue;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

import modele.Position;
import modele.Temple;
import static vue.ConstantesCanva.*;

/**
 * Classe pour lire les fichiers de scénario et créer des objets Temple basés sur les données du fichier.
 */
public class LectureScenario {
    // Variables d'instance pour stocker les informations d'un temple
    private Position position;
    private int couleur;
    private int cristal;

    /**
     * Lit un fichier de scénario et crée une carte des temples.
     *
     * @param fichierScenario le fichier de scénario à lire.
     * @return un HashMap avec les positions comme clés et les temples comme valeurs représentant le scénario.
     */
    public static HashMap<Position, Temple> lecture(File fichierScenario) {
        HashMap<Position, Temple> templesDuScenario = new HashMap<>();

        try {
            Scanner scanner = new Scanner(fichierScenario);
            while (scanner.hasNext()) {
                // Lecture des coordonnées et ajustement selon les dimensions du canevas
                int posX = scanner.nextInt() + LARGEUR_CANVAS / (2 * CARRE);
                int posY = scanner.nextInt() + LARGEUR_CANVAS / (2 * CARRE);
                int couleur = scanner.nextInt();
                int cristal = scanner.nextInt();
                Temple temple = new Temple(new Position(posX, posY), couleur, cristal);
                templesDuScenario.put(temple.getPosition(), temple);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return templesDuScenario;
    }

    /**
     * Retourne une représentation sous forme de chaîne de la classe LectureScenario.
     *
     * @return une chaîne décrivant l'objet LectureScenario.
     */
    @Override
    public String toString() {
        return "LectureScenario{" +
                "position=" + position +
                ", couleur=" + couleur +
                ", cristal=" + cristal +
                '}';
    }
}
