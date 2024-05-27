package vue;

import controleur.Controleur;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import modele.ApprentiOrdonnateur;
import modele.Position;
import modele.Temple;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static vue.ConstantesCanva.*;

/**
 * Classe représentant le menu graphique de l'application.
 * Cette classe est responsable de l'affichage et de la gestion des interactions avec l'utilisateur.
 */
public class MenuGraphique extends VBox {
    private Label labelNombreDePas;
    static Canvas canvasCarte;
    static GraphicsContext graphicsContext2D;

    private ApprentiOrdonnateur apprentiOrdonnateur;
    private Position positionApprenti;
    private boolean enMouvement = false;
    private Controleur controleur;

    // Chargement de l'image de l'apprenti
    Image image = new Image("file:///C:\\Users\\ousse\\OneDrive\\Bureau\\BUTINFOCOURS\\GRAPHE\\SAE-ORDONATTEUR\\SpriteJeu\\Ordonateur.png");
    ImageView ordonnateurImage = new ImageView(image);
    private HashMap<Position, Temple> templeMap;

    /**
     * Constructeur de la classe MenuGraphique.
     * Initialise l'interface graphique et les gestionnaires d'événements.
     */
    public MenuGraphique() {
        // Création de l'étiquette affichant le nombre de pas
        labelNombreDePas = new Label("Nombre de pas : 0");

        // Initialisation du canvas et de son contexte graphique
        canvasCarte = new Canvas(LARGEUR_CANVAS, HAUTEUR_CANVAS);
        graphicsContext2D = canvasCarte.getGraphicsContext2D();

        // Dessin des carrés de la grille
        dessinerGrille();

        // Ajout de l'étiquette et du canvas à la racine
        this.getChildren().add(labelNombreDePas);
        VBox.setMargin(labelNombreDePas, new Insets(30));
        this.getChildren().add(canvasCarte);
        VBox.setMargin(canvasCarte, new Insets(30));

        // Dessin des numéros de colonnes et de lignes
        dessinerNumeros();

        // Dessin du ordonnateur
        positionApprenti = new Position(15, 15);
        graphicsContext2D.drawImage(ordonnateurImage.getImage(), positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE);

        // Gestion des clics sur le canvas
        canvasCarte.setOnMouseClicked(event -> {
            templeMap = VBoxRoot.getMenuGraphique().getTempleMap();
            System.out.println("Templemap de MenuGraphique" + templeMap);
            if (!enMouvement) {
                enMouvement = true;

                int abscisse = (int) event.getX() / CARRE;
                int ordonnee = (int) event.getY() / CARRE;
                Position positionCliquee = new Position(abscisse, ordonnee);
                System.out.println(positionCliquee);

                if (abscisse == -15 || ordonnee == -15) {
                    positionCliquee = positionApprenti;
                }
                deplacementAvecTimer(positionApprenti, positionCliquee, templeMap);
            }
        });
    }

    /**
     * Définit le contrôleur de l'application.
     *
     * @param controleur le contrôleur à associer.
     */
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    /**
     * Définit la carte des temples.
     *
     * @param templeMap la carte des temples.
     */
    public void setTempleMap(HashMap<Position, Temple> templeMap) {
        this.templeMap = templeMap;
    }

    /**
     * Retourne la carte des temples.
     *
     * @return la carte des temples.
     */
    public HashMap<Position, Temple> getTempleMap() {
        return templeMap;
    }

    /**
     * Dessine la grille sur le canvas.
     */
    private void dessinerGrille() {
        graphicsContext2D.setStroke(COULEUR_GRILLE);
        for (int i = 0; i <= 30; i++) {
            for (int j = 0; j <= 30; j++) {
                graphicsContext2D.strokeRect(i * CARRE, j * CARRE, CARRE, CARRE);
            }
        }
    }

    /**
     * Dessine les numéros des colonnes et des lignes sur le canvas.
     */
    private void dessinerNumeros() {
        int numCol = 0;
        graphicsContext2D.setFill(COULEUR_GRILLE);
        for (int i = 0; i <= 30; i++) {
            graphicsContext2D.fillText(Integer.toString(numCol), i * CARRE + CARRE / 3, CARRE / 2);
            numCol++;
        }

        int numLigne = 0;
        for (int i = 0; i <= 30; i++) {
            graphicsContext2D.fillText(Integer.toString(numLigne), CARRE / 3, i * CARRE + CARRE / 2);
            numLigne++;
        }
    }

    /**
     * Gère le déplacement de l'apprenti avec un timer pour les animations.
     *
     * @param positionApprenti la position actuelle de l'apprenti.
     * @param positionCliquee  la position cliquée par l'utilisateur.
     * @param templeMap        la carte des temples.
     */
    private void deplacementAvecTimer(Position positionApprenti, Position positionCliquee, HashMap<Position, Temple> templeMap) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    graphicsContext2D.setFill(Color.WHITE);
                    graphicsContext2D.fillRect(positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE, CARRE, CARRE);

                    graphicsContext2D.setStroke(COULEUR_GRILLE);
                    graphicsContext2D.strokeRect(positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE, CARRE, CARRE);

                    positionApprenti.deplacementUneCase(positionCliquee);



                    if (controleur != null) {
                        controleur.redessinerTemples();
                    }

                    graphicsContext2D.drawImage(ordonnateurImage.getImage(), positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE);

                    labelNombreDePas.setText("Nombre de pas : " + Position.getNombreDePas());

                    if (positionApprenti.equals(positionCliquee)) {
                        timer.cancel();
                        enMouvement = false;
                        touchTemple(positionCliquee);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 200);
    }

    /** La méthode échange le cristal de l'ordonnateur avec celui du temple,
     * Qui a pour position le paramêtre donné
     *
     * @param positionTemple : le temple ou est arrivé l'ordonnateur
     */
    public void touchTemple(Position positionTemple){

        System.out.println(" dans touchTemple " + templeMap);
        System.out.println(" dans touchTemple " + templeMap.keySet());
        System.out.println(" dans touchTemple " + templeMap
        System.out.println(" dans touchTemple " + templeMap.get(positionTemple));
        System.out.println(" dans touchTemple " + positionTemple);
        apprentiOrdonnateur.switchCristal(templeMap.get(positionTemple).getCouleurCristal(), apprentiOrdonnateur.getMonCristal());



    }

    /**
     * Dessine les temples sur la carte.
     *
     * @param templeMap la carte des temples.
     */
    public void dessinSurCarte(HashMap<Position, Temple> templeMap) {
        dessinerGrille();
        dessinerNumeros();

        for (Map.Entry<Position, Temple> entry : templeMap.entrySet()) {
            Position position = entry.getKey();
            Temple temple = entry.getValue();

            double pixelX = (position.getAbscisse()) * CARRE;
            double pixelY = (position.getOrdonnee()) * CARRE;

            graphicsContext2D.setFill(temple.getCouleurValue(temple.couleur));
            graphicsContext2D.fillRect(pixelX, pixelY, CARRE, CARRE);
        }
    }
}
