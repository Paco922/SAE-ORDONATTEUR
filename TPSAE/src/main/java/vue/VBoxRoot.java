package vue;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modele.Position;

import java.util.Timer;
import java.util.TimerTask;

import static vue.ConstantesCanva.*;

public class VBoxRoot extends VBox {
    private Label labelNombreDePas;
    private Canvas canvasCarte;
    private GraphicsContext graphicsContext2D;
    private Position positionApprenti;

    public VBoxRoot() {
        // position apprenti
        positionApprenti = new Position (LARGEUR_CANVA/(CARRE*2), HAUTEUR_CANVA/(CARRE*2));
        //l'etiquette qui affiche le nombre de pas
        labelNombreDePas = new Label("Nombre de pas : 0");

        // le canva est son contexte graphique
        canvasCarte = new Canvas();
        canvasCarte.setWidth(LARGEUR_CANVA);
        canvasCarte.setHeight(HAUTEUR_CANVA);
        graphicsContext2D = canvasCarte.getGraphicsContext2D();

        //Les carrés
        graphicsContext2D.setStroke(COULEUR_GRILLE);
        for(int i = 0; i < LARGEUR_CANVA; i += CARRE) {
            for (int j = 0 ; j < HAUTEUR_CANVA;j+=CARRE){
                graphicsContext2D.strokeRect(i,j,CARRE,CARRE);
            }
        }

        //ajout des composant graphique a la racine
        this.getChildren().add(labelNombreDePas);
        VBox.setMargin(labelNombreDePas,new Insets(30));
        this.getChildren().add(canvasCarte);
        VBox.setMargin(canvasCarte,new Insets(30));

        //Les numeros de colonne
        int numCol = 1;
        graphicsContext2D.setFill(COULEUR_GRILLE);
        for (int i = CARRE;i<LARGEUR_CANVA;i +=CARRE){
            graphicsContext2D.fillText(Integer.toString(numCol), i+CARRE/3,CARRE/2);
            numCol++;
        }

        //Les numeros de colonne
        int numLigne = 1;
        graphicsContext2D.setFill(COULEUR_GRILLE);
        for (int i = CARRE;i<HAUTEUR_CANVA;i +=CARRE){
            graphicsContext2D.fillText(Integer.toString(numLigne), CARRE/3,i+CARRE/2);
            numLigne++;
        }

        graphicsContext2D.setFill(COULEUR_APPRENTI);
        graphicsContext2D.fillOval(positionApprenti.getAbscisse()*CARRE,
                positionApprenti.getOrdonnee()*CARRE,LARGEUR_OVALE,HAUTEUR_OVALE);

        canvasCarte.setOnMouseClicked(event ->{
            int abscisse=(int) event.getX() / CARRE;
            int ordonnee=(int) event.getY() / CARRE;
            Position positionCliquee = new Position(abscisse,ordonnee);
            System.out.println(positionCliquee);
            graphicsContext2D.setFill(COULEUR_POSITION);
            graphicsContext2D.fillRect(abscisse*CARRE,ordonnee*CARRE,CARRE,CARRE);
            deplacementAvecTimer(positionApprenti,positionCliquee);
        });




    }
    /**
     * la methode deplacement() prend en parametre la position de l'apprenti et la position cible
     * tant que les deux position ne sont pas les meme l'apprenti bouge soit horizontalement soit verticalement
     * et a chaque depalcement il faut supprimer l'apprenti de la case precedente
     */
    private void deplacementAvecTimer(Position positionApprenti, Position positionCliquee) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // effacer l'oval
                graphicsContext2D.clearRect(positionApprenti.getAbscisse()*CARRE+1,positionApprenti.getOrdonnee()*CARRE+1,CARRE-2,CARRE-2);


                // deplacement de l'apprenti vers la cible
                positionApprenti.deplacementUneCase(positionCliquee);


                //dessiner l'apprenti
                graphicsContext2D.setFill(COULEUR_APPRENTI);
                graphicsContext2D.fillOval(positionApprenti.getAbscisse()*CARRE+ CARRE/8,
                        positionApprenti.getOrdonnee()*CARRE+CARRE/4,LARGEUR_OVALE,HAUTEUR_OVALE);

                Platform.runLater(()->{
                    labelNombreDePas.setText("Nombre de pas : "+ Position.getNombreDePas());
                });

                //SI l'apprent arrive a la pos le timer prend fin
                if(positionApprenti.equals(positionCliquee)){
                    timer.cancel();
                }
            }

        };
        timer.scheduleAtFixedRate(timerTask,1000,200);
    }
}
