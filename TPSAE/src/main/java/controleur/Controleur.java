package controleur;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import modele.Position;
import modele.Temple;
import vue.LectureScenario;
import vue.VBoxRoot;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

public class Controleur implements EventHandler {

   public void handle(Event event){
        Object userData = ((MenuItem)event.getSource()).getUserData();
        if(userData instanceof File){//l'user vient de choisir un scenario
            File fichierScenario = (File) userData;
            System.out.println(fichierScenario.getName());
            HashMap<Position, Temple> temples = LectureScenario.lecture(fichierScenario);
            System.out.println(temples.toString());
            System.out.println(temples);
            System.out.println(VBoxRoot.getApprenti());

            // a completer, mettre le dessin dans ce if de chaque scénario
            // effacer chaque carré
            // mettre ls temples & cristaux
            // good luck
    }
    }
}



