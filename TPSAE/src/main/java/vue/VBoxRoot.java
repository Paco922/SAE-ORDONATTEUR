package vue;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import modele.Position;
import java.io.File;
import java.util.Scanner;
import java.io.File;
import java.util.Scanner;

import java.util.Timer;
import java.util.TimerTask;

import static vue.ConstantesCanva.*;

public class VBoxRoot extends VBox {
    private Label labelNombreDePas;
    private Canvas canvasCarte;
    private static GraphicsContext graphicsContext2D;
    private Position positionApprenti;
    private boolean enMouvement = false;

    public VBoxRoot() {
        // Position de départ de l'apprenti
        positionApprenti = new Position(LARGEUR_CANVA / (CARRE * 2), HAUTEUR_CANVA / (CARRE * 2));

        // Création de l'étiquette affichant le nombre de pas
        labelNombreDePas = new Label("Nombre de pas : 0");

        // Initialisation du canvas et de son contexte graphique
        canvasCarte = new Canvas();
        canvasCarte.setWidth(LARGEUR_CANVA);
        canvasCarte.setHeight(HAUTEUR_CANVA);
        graphicsContext2D = canvasCarte.getGraphicsContext2D();

        // Dessin des carrés de la grille
        graphicsContext2D.setStroke(COULEUR_GRILLE);
        for (int i = 0; i < LARGEUR_CANVA; i += CARRE) {
            for (int j = 0; j < HAUTEUR_CANVA; j += CARRE) {
                graphicsContext2D.strokeRect(i, j, CARRE, CARRE);
            }
        }

        // Ajout de l'étiquette et du canvas à la racine
        this.getChildren().add(labelNombreDePas);
        VBox.setMargin(labelNombreDePas, new Insets(30));
        this.getChildren().add(canvasCarte);
        VBox.setMargin(canvasCarte, new Insets(30));

        // Dessin des numéros de colonnes
        int numCol = 1;
        graphicsContext2D.setFill(COULEUR_GRILLE);
        for (int i = CARRE; i < LARGEUR_CANVA; i += CARRE) {
            graphicsContext2D.fillText(Integer.toString(numCol), i + CARRE / 3, CARRE / 2);
            numCol++;
        }

        // Dessin des numéros de lignes
        int numLigne = 1;
        for (int i = CARRE; i < HAUTEUR_CANVA; i += CARRE) {
            graphicsContext2D.fillText(Integer.toString(numLigne), CARRE / 3, i + CARRE / 2);
            numLigne++;
        }

        // Dessin de l'apprenti
        graphicsContext2D.setFill(COULEUR_APPRENTI);
        graphicsContext2D.fillOval(positionApprenti.getAbscisse() * CARRE,
                positionApprenti.getOrdonnee() * CARRE, LARGEUR_OVALE, HAUTEUR_OVALE);

        // Gestion des clics sur le canvas
        canvasCarte.setOnMouseClicked(event -> {
            if (!enMouvement) {
                enMouvement = true;

                int abscisse = (int) event.getX() / CARRE;
                int ordonnee = (int) event.getY() / CARRE;
                Position positionCliquee = new Position(abscisse, ordonnee);
                System.out.println(positionCliquee);
                graphicsContext2D.setFill(COULEUR_POSITION);
                graphicsContext2D.fillRect(abscisse * CARRE, ordonnee * CARRE, CARRE, CARRE);
                deplacementAvecTimer(positionApprenti, positionCliquee);
            }
        });
    }

    /**
     * Méthode de déplacement avec un Timer.
     * Elle déplace l'apprenti vers la position cible.
     *
     * @param positionApprenti Position actuelle de l'apprenti.
     * @param positionCliquee  Position cible du déplacement.
     */
    private void deplacementAvecTimer(Position positionApprenti, Position positionCliquee) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                graphicsContext2D.clearRect(positionApprenti.getAbscisse() * CARRE + 1, positionApprenti.getOrdonnee() * CARRE + 1, CARRE - 2, CARRE - 2);

                positionApprenti.deplacementUneCase(positionCliquee);

                graphicsContext2D.setFill(COULEUR_APPRENTI);
                graphicsContext2D.fillOval(positionApprenti.getAbscisse() * CARRE + CARRE / 8, positionApprenti.getOrdonnee() * CARRE + CARRE / 4, LARGEUR_OVALE, HAUTEUR_OVALE);

                Platform.runLater(() -> labelNombreDePas.setText("Nombre de pas : " + Position.getNombreDePas()));

                if (positionApprenti.equals(positionCliquee)) {
                    timer.cancel();
                    enMouvement = false;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 200);
    }


    public class ReadFromFileUsingScanner {
        public static void main(String[] args) {
            try {
                // Chemin vers le fichier
                File position = new File("C:\\Users\\22300445\\Downloads\\TP_Reservation_Java-main(2)\\TP_Reservation_Java-main\\demo\\src\\main\\java\\position.txt");

                // Création d'un scanner pour lire le fichier
                Scanner sc = new Scanner(position);

                // Lecture du fichier ligne par ligne
                while (sc.hasNextLine()) {
                    // Lecture de la ligne courante
                    String line = sc.nextLine();

                    // Séparation des valeurs en utilisant la virgule comme séparateur
                    String[] values = line.split(", ");

                    // Extraction des valeurs x et y
                    int x = Integer.parseInt(values[0]);
                    int y = Integer.parseInt(values[1]);

                    // Extraction des couleurs du temple et du cristal
                    Color couleurTemple = getColor(values[2]);
                    Color couleurCristal = getColor(values[3]);

                    // Affichage des valeurs extraites
                    System.out.println("x : " + x + ", y : " + y + ", Couleur Temple : " + couleurTemple + ", Couleur Cristal : " + couleurCristal);

                    // Remplissage des cases du canvas avec les couleurs
                    graphicsContext2D.setFill(couleurTemple);
                    graphicsContext2D.fillRect(x * CARRE, y * CARRE, CARRE, CARRE);
                    graphicsContext2D.setFill(couleurCristal);
                    graphicsContext2D.fillRect((x + 1) * CARRE, y * CARRE, CARRE, CARRE);
                    for (int i = 0; i < LARGEUR_CANVA; i += CARRE) {
                        if (i == x) {
                            graphicsContext2D.setFill(couleurTemple);
                            graphicsContext2D.fillRect(x * CARRE, y * CARRE, CARRE, CARRE);

                        }
                    }
                }

                // Fermeture du scanner
                sc.close();
            } catch (Exception e) {
                // Gestion des exceptions
                e.printStackTrace();
            }
        }

        // Méthode pour obtenir la couleur à partir du nom
        private static Color getColor(String couleurName) {
            switch (couleurName) {
                case "COULEUR_BLEU":
                    return ConstantesCanva.COULEUR_BLEU;
                case "COULEUR_ROUGE":
                    return ConstantesCanva.COULEUR_ROUGE;
                case "COULEUR_JAUNE":
                    return ConstantesCanva.COULEUR_JAUNE;
                case "COULEUR_PURPLE":
                    return ConstantesCanva.COULEUR_PURPLE;
                case "COULEUR_BLACK":
                    return ConstantesCanva.COULEUR_BLACK;
                default:
                    return Color.WHITE; // Valeur par défaut
            }
        }
    }
}
