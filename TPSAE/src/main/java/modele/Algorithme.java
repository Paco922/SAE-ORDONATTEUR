package modele;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vue.VBoxRoot;

import java.util.HashMap;

import static vue.ConstantesCanva.CARRE;

/**
 * Classe Algorithme, détient les différents algorithmes et les méthodes utilisées par ceux-ci.
 */
public class Algorithme {
    /** Instanciation de la HashMap qui contient la carte avec les temples */
    public HashMap<Position, Temple> mapTemple;
    /** Instanciation de APpp*/
    private ApprentiOrdonnateur apprentiOrdonnateur;

    public Algorithme(HashMap<Position, Temple> mapTemple) {
        this.mapTemple = mapTemple;
        this.apprentiOrdonnateur = VBoxRoot.getApprenti();
    }

    /**
     * Méthode algorithmique algorithmeTriBase, prends en paramêtre la carte, et déplace l'ordonnateur
     * @param mapTemple la carte des temple
     * Pas optimisé.
     */
    public void algorithmeTriBase(HashMap<Position, Temple> mapTemple) {
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
                        }

                        for (Temple temple_recursif : mapTemple.values()) {
                            if (apprentiOrdonnateur.monCristal == temple_recursif.getCouleurTemple()) {
                                moveApprentiToTemple(temple_recursif);
                                apprentiOrdonnateur.switchCristal(temple_recursif, temple_recursif.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                            }
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

    /**
     * Méthode algorithmique triHeuristique, prends en paramêtre la carte et la position de l'ordonnateur.
     * @param mapTemple la carte des temple
     * @param positionOrdonnateur la position de l'ordonnateur
     * Utilise la méthode templePlusProche afin d'optimiser les déplacements.
     */
    public void triHeuristique(HashMap<Position, Temple> mapTemple, Position positionOrdonnateur) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                    Temple templePlusProche = templePlusProche(mapTemple, positionOrdonnateur);
                    int couleur_temple = templePlusProche.getCouleurTemple();
                    int couleur_cristal = templePlusProche.getCouleurCristal();


                    moveApprentiToTemple(templePlusProche);
                    apprentiOrdonnateur.switchCristal(templePlusProche, templePlusProche.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());
                    while (!Temple.conditionVictoire(mapTemple)) {
                        for (Temple temple_recursif : mapTemple.values()) {
                            if (apprentiOrdonnateur.monCristal == temple_recursif.getCouleurTemple()) {
                                moveApprentiToTemple(temple_recursif);
                                apprentiOrdonnateur.switchCristal(temple_recursif, temple_recursif.getCouleurCristal(), apprentiOrdonnateur.getMonCristal());

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


    /**
     * Méthode templePlusProche, récupère la position la plus proche de l'ordonnateur
     * @param mapTemple, prends la carte actuelle en paramêtre
     * @param positionOrdonnateur, prends la position actuelle de l'ordonnateur
     * @return le temple, templeVisee, le plus proche
     */
    private Temple templePlusProche(HashMap<Position, Temple> mapTemple, Position positionOrdonnateur){
        int distanceMin = Integer.MAX_VALUE;
        Temple templeVisee = null;
        for (Temple templePlusProche : mapTemple.values()) {
            int distanceX = Math.abs(templePlusProche.getPosition().getAbscisse() - positionOrdonnateur.getAbscisse());
            int distanceY = Math.abs(templePlusProche.getPosition().getOrdonnee() - positionOrdonnateur.getOrdonnee());
            int distance = distanceX + distanceY; // Calcul de la distance manhattan
            if (distance < distanceMin && templePlusProche.getCouleurCristal() > 0) {
                distanceMin = distance;
                templeVisee = templePlusProche;
            }
        }
        return templeVisee;
    }


    /**
     * Méthode moveApprentiToTemple, permet de bouger l'apprenti, seulement utilisé pour bouger dans l'algorithme
     * @param temple, prends le temple en paramêtre, permet de bouger l'apprenti dans l'algorithme (DIFFERENT DU MOUVEMENT DANS MENUGRAPHIQUE)
     */
    private void moveApprentiToTemple(Temple temple) {
        while (!apprentiOrdonnateur.getPositionApprenti().equals(temple.getPosition())) {
            apprentiOrdonnateur.getPositionApprenti().deplacementUneCase(temple.getPosition());
            majUI(apprentiOrdonnateur.getPositionApprenti());
            try {
                Thread.sleep(80); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Méthode majUI, met à jour l'interface graphique lors des déplacements de l'algorithme.
     * @param positionApprenti prends en paramêtre la position de l'apprenti, afin de le redessiner.
     */
    private void majUI(Position positionApprenti) {
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
