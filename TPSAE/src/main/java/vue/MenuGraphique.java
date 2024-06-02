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

public class MenuGraphique extends VBox {
    private Label labelNombreDePas;
    static Canvas canvasCarte;
    static GraphicsContext graphicsContext2D;

    private ApprentiOrdonnateur apprentiOrdonnateur;
    private Position positionApprenti;
    private boolean enMouvement = false;
    private Controleur controleur;

    Image image = new Image("file:///F:\\JavaFX\\SAE-ORDONATTEURLETUSDO\\SpriteJeu\\Ordonateur.png");
    ImageView ordonnateurImage = new ImageView(image);
    private HashMap<Position, Temple> templeMap;

    public MenuGraphique() {
        apprentiOrdonnateur = new ApprentiOrdonnateur();

        labelNombreDePas = new Label("Nombre de pas : 0");
        canvasCarte = new Canvas(LARGEUR_CANVAS, HAUTEUR_CANVAS);
        graphicsContext2D = canvasCarte.getGraphicsContext2D();

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

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void setTempleMap(HashMap<Position, Temple> templeMap) {
        this.templeMap = templeMap;
    }

    public HashMap<Position, Temple> getTempleMap() {
        return templeMap;
    }

    private void dessinerGrille() {
        graphicsContext2D.setStroke(COULEUR_GRILLE);
        for (int i = 0; i <= 30; i++) {
            for (int j = 0; j <= 30; j++) {
                graphicsContext2D.strokeRect(i * CARRE, j * CARRE, CARRE, CARRE);
            }
        }
    }

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

    public void touchTemple(Position positionTemple) {
        System.out.println(" dans touchTemple " + templeMap);
        System.out.println(" dans touchTemple " + templeMap.keySet());
        System.out.println(" dans touchTemple " + positionTemple);
        System.out.println("La position du joueur " + positionApprenti);

        Temple temple = templeMap.get(positionTemple);
        if (apprentiOrdonnateur != null && temple != null) {
            apprentiOrdonnateur.switchCristal(temple, temple.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
        }
    }

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

    private void dessinerCristal(Position position, Temple temple) {
        double centerX = (position.getAbscisse() * CARRE) + (CARRE / 2);
        double centerY = (position.getOrdonnee() * CARRE) + (CARRE / 2);
        double radius = CARRE / 4;

        graphicsContext2D.setFill(temple.getCouleurValue(temple.getCouleurCristal()));
        graphicsContext2D.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

}
