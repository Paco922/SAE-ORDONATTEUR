package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import java.io.File;
import java.util.HashMap;
import modele.Position;
import modele.Temple;
import vue.LectureScenario;
import vue.MenuGraphique;

public class Controleur implements EventHandler {
    MenuGraphique menuGraphique = new MenuGraphique();

    public void handle(Event event){


        Object userData = ((MenuItem)event.getSource()).getUserData();
        if(userData instanceof File){//l'user vient de choisir un scenario
            File fichierScenario = (File) userData;
            System.out.println(fichierScenario.getName());
            HashMap<Position, Temple> temples = LectureScenario.lecture(fichierScenario);
            System.out.println(temples.toString());
            menuGraphique.setTempleMap(temples);

            menuGraphique.dessinSurCarte(temples);


        }

    }

    public void redessinerTemples() {
        HashMap<Position, Temple> temples = menuGraphique.getTempleMap();
        if (temples != null) {
            menuGraphique.dessinSurCarte(temples);
        }
    }
}