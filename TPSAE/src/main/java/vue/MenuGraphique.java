package vue;

import controleur.Controleur;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
 * Classe MenuGraphique, gère tout ce qui à un rapport au GUI, dont le Label de pas, la carte, et le contexteGraphique (pour déssiner)
 */
public class MenuGraphique extends VBox {
    /** Label labelNombreDePas, permet de démontrer le nombre de pas effectué. */
    private Label labelNombreDePas;
    /** canvasCarte, permet d'utiliser la carte (en canva de JavaFX)*/
    static Canvas canvasCarte;
    /** graphicsContext2D, permet de dessiner sur la carte*/
    static GraphicsContext graphicsContext2D;

    /** apprentiOrdonnateur, instancie un objet de l'apprentiOrdonnateur */
    private ApprentiOrdonnateur apprentiOrdonnateur;
    /** positionApprenti, instancie un objet de la positionApprenti */
    private Position positionApprenti;
    /** boolean enMouvement, permet d'interdire de cliquer plusieurs fois d'affiler avant que l'adonnateur n'ait fini son voyage */
    private boolean enMouvement = false;
    /** controleur, instancie le controleur */
    private Controleur controleur;

    /** image, instancie l'image de l'ordonnateur*/
    Image image = new Image("file:///F:\\JavaFX\\SAE-ORDONATTEUREREEEE\\SpriteJeu\\ordonnateur.png");
    /** instancie la vision de l'image de l'ordonnateur (merciJavaFX).. */
    ImageView ordonnateurImage = new ImageView(image);

    /** instancie le HashMap qui contient les informations sur les temples */
    private HashMap<Position, Temple> templeMap;

    /**
     * Constructeur menugraphique, permet d'initialiser la carte et le GUI, et gère également les clics sur le GUI par l'utilisateur pour la résolution manuelle
     * grâce à la méthode deplacementAvecTimer.
     */
    public MenuGraphique() {
        apprentiOrdonnateur = new ApprentiOrdonnateur();

        labelNombreDePas = new Label("Nombre de pas : 0");
        canvasCarte = new Canvas(LARGEUR_CANVAS, HAUTEUR_CANVAS);
        graphicsContext2D = canvasCarte.getGraphicsContext2D();

        Button cleanMapButton = new Button("Nettoyer la carte!");
        this.getChildren().add(cleanMapButton);

        dessinerGrille();
        this.getChildren().add(labelNombreDePas);
        VBox.setMargin(labelNombreDePas, new Insets(30));
        this.getChildren().add(canvasCarte);
        VBox.setMargin(canvasCarte, new Insets(30));
        dessinerNumeros();

        positionApprenti = new Position(15, 15);
        graphicsContext2D.drawImage(ordonnateurImage.getImage(), positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE);

        canvasCarte.setOnMouseClicked(event -> {
            templeMap = VBoxRoot.getMenuGraphique().getTempleMap();
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
        cleanMapButton.setOnAction(event -> reinitialisationCarte());
    }




    /** Getteur getGraphicsContext pour récupèrer le graphicsContext */
    public GraphicsContext getGraphicsContext2D() {
        return graphicsContext2D;
    }

    /** Getteur getOrdonnateurImage pour récupèrer l'ordonnateurImage */
    public ImageView getOrdonnateurImage() {
        return ordonnateurImage;
    }

    /** Getteur getLabelNombreDePas pour récupèrer le nombreDePas */
    public Label getLabelNombreDePas() {
        return labelNombreDePas;
    }
    /** Setteur setLabelNombreDePas pour set le nombreDePas */
    public void setLabelNombreDePas(Label labelNombreDePas){
        this.labelNombreDePas = labelNombreDePas;
    }

    /** Setteur setControleur pour set le controleur */
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    /** Setteur setTempleMap pour set la carte */
    public void setTempleMap(HashMap<Position, Temple> templeMap) {
        this.templeMap = templeMap;
    }

    /** Getteur getTempleMap pour récupèrer le templeMap contenant les temples */
    public HashMap<Position, Temple> getTempleMap() {
        return templeMap;
    }

    /**
     * Méthode dessinerGrille, permet de dessiner la grille sur le GUI.
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
     * Méthode dessinerNumeros, permet de mettre les numéros dans les cases sur le GUI.
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
     * Méthode déplacementAvecTimer, permet de gérer les déplacements venant du clic des utilisateurs.
     * Redessine la carte entière à chaque déplacement..
     * @param positionApprenti récupère la position de l'apprenti afin de le redessiner
     * @param positionCliquee récupère la position cliquée
     * @param templeMap récupère le templeMap afin de redessiner le templeMap.
     */
    private void deplacementAvecTimer(Position positionApprenti, Position positionCliquee, HashMap<Position, Temple> templeMap) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    redessinCanva(positionApprenti);
                    positionApprenti.deplacementUneCase(positionCliquee);
                    VBoxRoot.getControleur().redessinerTemples();


                    graphicsContext2D.drawImage(ordonnateurImage.getImage(), positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE);

                    labelNombreDePas.setText("Nombre de pas : " + Position.getNombreDePas());

                    if (positionApprenti.equals(positionCliquee)) {
                        timer.cancel();
                        enMouvement = false;
                        System.out.println(templeMap);
                        System.out.println(positionApprenti);
                        if (templeMap.containsKey(positionApprenti)) {
                            touchTemple(positionApprenti);
                            System.out.println("Voici mon cristal! : " + apprentiOrdonnateur.getMonCristal());
                        }
                        if (Temple.conditionVictoire(templeMap)){
                            System.out.println("Jeu Gagné!");
                        }

                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 200);
    }

    /**
     * Méthode redessinCanva, permet de redessiner le Canva.
     * @param positionApprenti afin de redessiner l'apprenti (oui, c'est un thème récurrent.)
     */
    public void redessinCanva(Position positionApprenti){
        graphicsContext2D.setFill(Color.WHITE);
        graphicsContext2D.fillRect(positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE, CARRE, CARRE);

        graphicsContext2D.setStroke(COULEUR_GRILLE);
        graphicsContext2D.strokeRect(positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE, CARRE, CARRE);

        graphicsContext2D.clearRect(positionApprenti.getAbscisse(), positionApprenti.getOrdonnee(), CARRE, CARRE);
    }

    /**
     * Méthode touchTemple, permet de gérer l'échange de cristaux.
     * @param positionTemple récupère la position du temple avec lequel échanger.
     */
    public void touchTemple(Position positionTemple) {

        Temple temple = templeMap.get(positionTemple);
        if (apprentiOrdonnateur != null && temple != null) {
            apprentiOrdonnateur.switchCristal(temple, temple.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
        }
    }

    /**
     * Méthode dessinSurCarte, permet de redessiner toute la carte.
     * @param templeMap récupère les temples.
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

            dessinerCristal(position, temple);
        }
    }

    /**
     * Méthode dessinerCristal, permet de dessiner les cristaux sur la carte.
     * @param position récupère la position du cristal
     * @param temple récupère le temple.
     */
    private void dessinerCristal(Position position, Temple temple) {
        double centerX = (position.getAbscisse() * CARRE) + (CARRE / 2);
        double centerY = (position.getOrdonnee() * CARRE) + (CARRE / 2);
        double radius = CARRE / 4;

        graphicsContext2D.setFill(temple.getCouleurValue(temple.getCouleurCristal()));
        graphicsContext2D.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    /**
     * Méthode reinitialisationCarte, permet de reinitialiser la carte, en la redessinant
     */
    public void reinitialisationCarte() {
        // Clear the canvas
        graphicsContext2D.setFill(Color.WHITE);
        graphicsContext2D.fillRect(0, 0, LARGEUR_CANVAS, HAUTEUR_CANVAS);

        // Draw the grid and numbers again
        dessinerGrille();
        dessinerNumeros();
        Position.resetNombreDePas();

        // Reset the position of the apprentice
        positionApprenti = new Position(15, 15);
        graphicsContext2D.drawImage(ordonnateurImage.getImage(), positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE);

        labelNombreDePas.setText("Nombre de pas : 0");

        // Clear the temples map
        templeMap.clear();
    }
}


