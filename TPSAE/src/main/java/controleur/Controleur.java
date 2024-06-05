package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import modele.Position;
import modele.Temple;
import vue.LectureScenario;
import vue.MenuGraphique;
import vue.VBoxRoot;

/**
 * La classe Controleur implémente l'interface EventHandler pour gérer les événements liés à un menu graphique dans une application JavaFX.
 * Elle gère la sélection d'un fichier de scénario et met à jour la vue graphique avec les positions des temples correspondants.
 */
public class Controleur implements EventHandler {

    /**
     * Cette méthode est déclenchée lorsqu'un événement se produit. Elle vérifie si les données utilisateur de la source de l'événement
     * sont un fichier, lit le fichier de scénario et met à jour le menu graphique avec les données des temples.
     *
     * @param event L'événement déclenché par l'utilisateur.
     */
    public void handle(Event event) {
        MenuGraphique menuGraphique = VBoxRoot.getMenuGraphique();
        Object userData = ((MenuItem) event.getSource()).getUserData();
        if (userData instanceof File) {
            File fichierScenario = (File) userData;
            System.out.println(fichierScenario.getName());
            HashMap<Position, Temple> temples = LectureScenario.lecture(fichierScenario);
            menuGraphique.setTempleMap(temples);
            menuGraphique.dessinSurCarte(temples);
        }

        if (userData instanceof Method){
            System.out.println("algorithme_tri cliqué!");
            System.out.println();
        }
    }

    /**
     * Cette méthode redessine les temples sur la carte en fonction des données actuelles stockées dans le MenuGraphique.
     */
    public void redessinerTemples() {
        MenuGraphique menuGraphique = VBoxRoot.getMenuGraphique();
        HashMap<Position, Temple> temples = menuGraphique.getTempleMap();
        if (temples != null) {
            menuGraphique.dessinSurCarte(temples);
        }
    }


}
