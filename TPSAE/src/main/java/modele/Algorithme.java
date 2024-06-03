package modele;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vue.VBoxRoot;

import java.util.HashMap;

import static vue.ConstantesCanva.CARRE;

public class Algorithme {
    public HashMap<Position, Temple> mapTemple;
    private ApprentiOrdonnateur apprentiOrdonnateur;

    public Algorithme(HashMap<Position, Temple> mapTemple) {
        this.mapTemple = mapTemple;
        this.apprentiOrdonnateur = VBoxRoot.getApprenti();
    }

    public void Algorithme_Tri(HashMap<Position, Temple> mapTemple) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                while (!Temple.conditionVictoire(mapTemple)) {
                    for (Temple temple : mapTemple.values()) {
                        int couleur_temple = temple.getCouleurTemple();
                        int couleur_cristal = temple.getCouleurCristal();

                        if (couleur_temple != couleur_cristal) {
                            moveApprentiToTemple(temple);
                            apprentiOrdonnateur.switchCristal(temple, temple.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());

                            int couleur_cristal_apprenti = apprentiOrdonnateur.getMonCristal();
                            for (Temple temple_recursif : mapTemple.values()) {
                                if (couleur_cristal_apprenti == temple_recursif.getCouleurTemple()) {
                                    moveApprentiToTemple(temple_recursif);
                                    apprentiOrdonnateur.switchCristal(temple_recursif, temple_recursif.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                                }
                            }
                        }
                    }
                    // Handle the crystal with value 0 last to avoid unnecessary trips
                    for (Temple templedef : mapTemple.values()) {
                        if (templedef.getCouleurCristal() == 0) {
                            moveApprentiToTemple(templedef);
                            apprentiOrdonnateur.switchCristal(templedef, templedef.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                            break; // Fetch the crystal with value 0 and break
                        }
                    }
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void triHeuristique(HashMap<Position, Temple> mapTemple, Position positionOrdonnateur) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                while (!Temple.conditionVictoire(mapTemple)) {
                    for (Temple templeHeur : mapTemple.values()) {
                        int couleur_temple = templeHeur.getCouleurTemple();
                        int couleur_cristal = templeHeur.getCouleurCristal();

                        Temple templePlusProche = templePlusProche(mapTemple, positionOrdonnateur);

                        if (templePlusProche != null) {
                            moveApprentiToTemple(templePlusProche);
                            apprentiOrdonnateur.switchCristal(templePlusProche, templePlusProche.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());

                            for (Temple temple_recursif : mapTemple.values()) {
                                if (apprentiOrdonnateur.getMonCristal() == temple_recursif.getCouleurTemple()) {
                                    moveApprentiToTemple(temple_recursif);
                                    apprentiOrdonnateur.switchCristal(temple_recursif, temple_recursif.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                                }
                            }
                        }
                    }

                    // Now handle the temples with color 0
                    for (Temple templedef : mapTemple.values()) {
                        if (templedef.getCouleurCristal() == 0) {
                            moveApprentiToTemple(templedef);
                            apprentiOrdonnateur.switchCristal(templedef, templedef.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                            break; // Fetch the crystal with value 0 and break
                        }
                    }
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }



    private Temple templePlusProche(HashMap<Position, Temple> mapTemple, Position positionOrdonnateur){
        int distanceMin = Integer.MAX_VALUE;
        Temple cristalVisee = null;
        for (Temple templePlusProche : mapTemple.values()) {
            int distanceX = Math.abs(templePlusProche.getPosition().getAbscisse() - positionOrdonnateur.getAbscisse());
            int distanceY = Math.abs(templePlusProche.getPosition().getOrdonnee() - positionOrdonnateur.getOrdonnee());
            int distance = distanceX + distanceY; // Calcul de la distance manhattan
            if (distance < distanceMin && templePlusProche.getCouleurCristal() > 0) {
                distanceMin = distance;
                cristalVisee = templePlusProche;
            }
        }
        return cristalVisee;
    }


    private void moveApprentiToTemple(Temple temple) {
        while (!apprentiOrdonnateur.getPositionApprenti().equals(temple.getPosition())) {
            apprentiOrdonnateur.getPositionApprenti().deplacementUneCase(temple.getPosition());
            updateUI(apprentiOrdonnateur.getPositionApprenti());
            try {
                Thread.sleep(80); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateUI(Position positionApprenti) {
        Platform.runLater(() -> {
            GraphicsContext graphicsContext2D = VBoxRoot.getMenuGraphique().getGraphicsContext2D();
            graphicsContext2D.setFill(Color.WHITE);
            graphicsContext2D.fillRect(0, 0, graphicsContext2D.getCanvas().getWidth(), graphicsContext2D.getCanvas().getHeight());

            VBoxRoot.getControleur().redessinerTemples();

            graphicsContext2D.drawImage(VBoxRoot.getMenuGraphique().getOrdonnateurImage().getImage(),
                    positionApprenti.getAbscisse() * CARRE,
                    positionApprenti.getOrdonnee() * CARRE);

            VBoxRoot.getMenuGraphique().getLabelNombreDePas().setText("Nombre de pas : " + Position.getNombreDePas());
        });
    }
}
