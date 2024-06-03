package modele;

import controleur.Controleur;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vue.MenuGraphique;
import vue.VBoxRoot;

import java.util.HashMap;
import static vue.ConstantesCanva.*;

public class Algorithme {
    public HashMap<Position, Temple> mapTemple;
    private ApprentiOrdonnateur apprentiOrdonnateur;
    private Position position;

    public Algorithme(HashMap<Position, Temple> mapTemple) {
        this.mapTemple = mapTemple;
        apprentiOrdonnateur = VBoxRoot.getApprenti();
        this.position = (Position) apprentiOrdonnateur.getPositionApprenti();
    }

    public void Algorithme_Tri(HashMap<Position, Temple> mapTemple) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                // Observation de la liste de temple donnée
                for (Temple temple : mapTemple.values()) {
                    int couleur_temple = temple.getCouleurTemple();
                    int couleur_cristal = temple.getCouleurCristal();
                    while (!Temple.conditionVictoire(mapTemple))
                        // Si la couleur du premier temple n'est pas la couleur du cristal qu'il possède ;
                        if (couleur_temple != couleur_cristal) {
                            // On déplace l'ordonnateur vers le temple
                            while (!apprentiOrdonnateur.getPositionApprenti().equals(temple.getPosition())) {
                                apprentiOrdonnateur.getPositionApprenti().deplacementUneCase(temple.getPosition());
                                updateUI(apprentiOrdonnateur.getPositionApprenti());
                                try {
                                    Thread.sleep(20); // Adjust the sleep duration as needed
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Switch the crystal
                            apprentiOrdonnateur.switchCristal(temple, temple.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                            int couleur_cristal_apprenti = apprentiOrdonnateur.getMonCristal();

                            // Check if there is a temple matching the apprenti's crystal color
                            for (Temple temple_recursif : mapTemple.values()) {
                                if (couleur_cristal_apprenti == temple_recursif.getCouleurTemple()) {
                                    while (!apprentiOrdonnateur.getPositionApprenti().equals(temple_recursif.getPosition())) {
                                        apprentiOrdonnateur.getPositionApprenti().deplacementUneCase(temple_recursif.getPosition());
                                        updateUI(apprentiOrdonnateur.getPositionApprenti());
                                        try {
                                            Thread.sleep(20); // Adjust the sleep duration as needed
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    apprentiOrdonnateur.switchCristal(temple_recursif, temple_recursif.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                                }
                            }
                            for (Temple temple_verif : mapTemple.values()) {
                                if (temple_verif.getCouleurCristal() == 0) {
                                    apprentiOrdonnateur.getPositionApprenti().deplacementUneCase(temple_verif.getPosition());
                                    apprentiOrdonnateur.switchCristal(temple_verif, temple_verif.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                                    updateUI(apprentiOrdonnateur.getPositionApprenti());
                                }
                            }
                        }

                }
                return null;
            }
            private void updateUI(Position positionApprenti) {
                Platform.runLater(() -> {
                    // Clear the previous position
                    GraphicsContext graphicsContext2D = VBoxRoot.getMenuGraphique().getGraphicsContext2D();
                    graphicsContext2D.setFill(Color.WHITE);
                    graphicsContext2D.fillRect(positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE, CARRE, CARRE);

                    graphicsContext2D.setStroke(COULEUR_GRILLE);
                    graphicsContext2D.strokeRect(positionApprenti.getAbscisse() * CARRE, positionApprenti.getOrdonnee() * CARRE, CARRE, CARRE);

                    VBoxRoot.getControleur().redessinerTemples();

                    // Draw the ApprentiOrdonnateur at the new position
                    graphicsContext2D.drawImage(VBoxRoot.getMenuGraphique().getOrdonnateurImage().getImage(),
                            positionApprenti.getAbscisse() * CARRE,
                            positionApprenti.getOrdonnee() * CARRE);

                    // Update the label for the number of steps (optional, if needed)
                    VBoxRoot.getMenuGraphique().getLabelNombreDePas().setText("Nombre de pas : " + Position.getNombreDePas());

                    // Redraw temples (if needed)
                    VBoxRoot.getMenuGraphique().getControleur().redessinerTemples();
                });
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
