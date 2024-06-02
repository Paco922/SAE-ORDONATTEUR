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
import vue.VBoxRoot;

public class Controleur implements EventHandler {


    public void handle(Event event){

        MenuGraphique menuGraphique = VBoxRoot.getMenuGraphique();
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
        MenuGraphique menuGraphique = VBoxRoot.getMenuGraphique();
        HashMap<Position, Temple> temples = menuGraphique.getTempleMap();
        System.out.println("Voici les temples à redessiner : " + temples);
        if (temples != null) {
            menuGraphique.dessinSurCarte(temples);
        }
    }
}