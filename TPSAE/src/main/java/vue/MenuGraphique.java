package vue;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import modele.Position;

import java.util.*;

import static vue.ConstantesCanva.*;

public class MenuGraphique extends VBox {
    private Label labelNombreDePas;
    static Canvas canvasCarte;
    static GraphicsContext graphicsContext2D;
    private Position positionApprenti;
    private boolean enMouvement = false;

    // Chargement de l'image de l'apprenti (merci bing AI pour le sprite)

    Image image = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\Ordonateur.png");
    ImageView ordonnateurImage = new ImageView(image);


    public MenuGraphique() {

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

        // Dessin du ordonnateur
        // Position de départ de l'apprenti
        positionApprenti = new Position(LARGEUR_CANVA / (CARRE * 2), HAUTEUR_CANVA / (CARRE * 2));
        graphicsContext2D.drawImage(ordonnateurImage.getImage(), positionApprenti.getAbscisse() * CARRE + 2, positionApprenti.getOrdonnee() * CARRE + 2); // dimensiosn
        //new ReadFromFileUsingScanner();

        // Gestion des clics sur le canvas
        canvasCarte.setOnMouseClicked(event -> {
            if (!enMouvement) {
                enMouvement = true;

                int abscisse = (int) event.getX() / CARRE;
                int ordonnee = (int) event.getY() / CARRE;
                Position positionCliquee = new Position(abscisse, ordonnee);
                System.out.println(positionCliquee);
                // Si l'ordonnateur clique sur la case des ordonnées/abscisses ;
                if (abscisse == 0){
                    // On ne bouge pas
                    positionCliquee = (positionApprenti);
                }
                if (ordonnee == 0){
                    positionCliquee = (positionApprenti);
                }
                deplacementAvecTimer(positionApprenti, positionCliquee);
            }
        });
    }

    /**
     * Méthode de déplacement avec un Timer.
     * Elle déplace l'apprenti vers la position cible.
     * !! ENLEVER LA COULEUR QU'IL MET A CHAQUE DEPLACEMENT !!
     *
     * @param positionApprenti Position actuelle de l'apprenti.
     * @param positionCliquee  Position cible du déplacement.
     */
    private void deplacementAvecTimer(Position positionApprenti, Position positionCliquee) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                // Début du déplacement de l'apprenti
                ordonnateurImage.setImage(null);
                graphicsContext2D.clearRect(positionApprenti.getAbscisse() * CARRE + 2, positionApprenti.getOrdonnee() * CARRE + 2, CARRE - 4, CARRE - 4);
                Image ordonnateurImage = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\Ordonateur.png");
                positionApprenti.deplacementUneCase(positionCliquee);
                ImageView en_mouv = new ImageView(ordonnateurImage);
                graphicsContext2D.drawImage(en_mouv.getImage(), positionApprenti.getAbscisse() * CARRE + 2, positionApprenti.getOrdonnee() * CARRE + 2); // dimensiosn


                Platform.runLater(() -> labelNombreDePas.setText("Nombre de pas : " + Position.getNombreDePas()));

                if (positionApprenti.equals(positionCliquee)) {
                    timer.cancel();
                    enMouvement = false;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 200);
    }

    /*
    Read file from scanner
    Lis tout simplement le document, parfait, juste a régler le probleme des deux cases l'une a cote de l'autre
     */

    /*
    public static class ReadFromFileUsingScanner {
        public ReadFromFileUsingScanner() {
            try {
                // Chemin vers le fichier
                File position = new File("C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\senario0.txt");

                // Création d'un scanner pour lire le fichier
                Scanner sc = new Scanner(position);

                // Obtention du GraphicsContext à partir du Canvas
                GraphicsContext graphicsContext2D = canvasCarte.getGraphicsContext2D();

                // Lecture du fichier ligne par ligne
                while (sc.hasNextLine()) {
                    // Lecture de la ligne courante
                    String line = sc.nextLine();

                    // Séparation des valeurs en utilisant la virgule comme séparateur
                    String[] values = line.split(", ");

                    // Extraction des valeurs x et y
                    int x = Integer.parseInt(values[0]);
                    int y = Integer.parseInt(values[1]);

                    String couleurTemple = values[2];
                    String couleurCristal = values[3];
                    System.out.println(couleurTemple);
                    System.out.println(couleurCristal);

                    // Import des temples

                    // Chargement des temples
                    Image imp_templeBleu = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\TempleBleu.jpg");
                    ImageView templeBleu = new ImageView(imp_templeBleu);

                    Image imp_templeGris = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\TempleGris.jpg");
                    ImageView templeGris = new ImageView(imp_templeGris);

                    Image imp_templeOrange = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\TempleOrange.jpg");
                    ImageView templeOrange = new ImageView(imp_templeOrange);

                    Image imp_templeRouge = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\TempleRouge.png");
                    ImageView templeRouge = new ImageView(imp_templeRouge);

                    // Chargement des Cristaux
                    Image imp_cristalBleu = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\CristaleBleu.png");
                    ImageView cristalBleu = new ImageView(imp_cristalBleu);

                    Image imp_cristalGris = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\CristaleGris.png");
                    ImageView cristalGris = new ImageView(imp_cristalGris);

                    Image imp_cristalVert = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\CristaleVert.png");
                    ImageView cristaleVert = new ImageView(imp_cristalVert);

                    Image imp_cristalRouge = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\CristaleRouge.png");
                    ImageView cristalRouge = new ImageView(imp_cristalRouge);


                    System.out.println(values[2].toString());
                    if (values[2].equals(1)){
                        graphicsContext2D.drawImage(templeBleu.getImage(),x * CARRE, y * CARRE);
                    }
                    if (values[2].equals(2)){
                        graphicsContext2D.drawImage(templeOrange.getImage(), x * CARRE, y * CARRE);
                    }
                    if (values[2].equals(3)){
                        graphicsContext2D.drawImage(templeRouge.getImage(), x * CARRE, y * CARRE);
                    }
                    if (values[2].equals(4)){
                        graphicsContext2D.drawImage(templeGris.getImage(), x * CARRE, y * CARRE);
                    }

                    if (values[3].equals(1)){
                        graphicsContext2D.drawImage(cristalBleu.getImage(),x * CARRE, y * CARRE);
                    }
                    if (values[3].equals(2)){
                        graphicsContext2D.drawImage(cristaleVert.getImage(), x * CARRE, y * CARRE);
                    }
                    if (values[3].equals(3)){
                        graphicsContext2D.drawImage(cristalRouge.getImage(), x * CARRE, y * CARRE);
                    }
                    if (values[3].equals(4)){
                        graphicsContext2D.drawImage(cristalGris.getImage(), x * CARRE, y * CARRE);
                    }


                }



                // Fermeture du scannerscr
                sc.close();
            } catch (Exception e) {
                // Gestion des exceptions
                e.printStackTrace();
            }

        }


    }
*/

}