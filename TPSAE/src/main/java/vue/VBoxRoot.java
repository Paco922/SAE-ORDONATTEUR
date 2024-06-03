package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import modele.ApprentiOrdonnateur;
import modele.Algorithme;
import modele.Position;
import modele.Temple;

import java.io.File;
import java.util.HashMap;

public class VBoxRoot extends BorderPane implements ConstantesCanva {

    private static ApprentiOrdonnateur apprenti;
    private static Controleur controleur;
    private static MenuGraphique menuGraphique;
    private static Algorithme algorithme;

    public VBoxRoot() {
        apprenti = new ApprentiOrdonnateur();
        controleur = new Controleur();
        menuGraphique = new MenuGraphique();

        menuGraphique.setControleur(controleur); // Set the controller here

        // La barre des menus
        MenuBar menuBar = new MenuBar();
        VBox.setMargin(menuBar, new Insets(9));

        // Le menu des scénarios
        Menu menuScenarios = new Menu(INTITULE_MENU_SCENARIOS);
        menuBar.getMenus().add(menuScenarios);

        // Le menu des algorithmes
        Menu menuAlgorithmes = new Menu("Algorithmes");
        menuBar.getMenus().add(menuAlgorithmes);

        // Les items du menu scénario
        File[] scenario = new File("Scenario").listFiles();
        for (int i = 0; i < scenario.length; i++) {
            MenuItem menuItem = new MenuItem(scenario[i].getName());
            menuItem.setUserData(scenario[i]);
            menuItem.setOnAction(controleur);
            menuScenarios.getItems().add(menuItem);
        }

        // Inside VBoxRoot constructor or appropriate method
        MenuItem algorithmeTriItem = new MenuItem("Algorithme Tri");
        algorithmeTriItem.setOnAction(event -> {
            HashMap<Position, Temple> templeMap = menuGraphique.getTempleMap();
            if (templeMap != null) {
                Algorithme algorithme = new Algorithme(templeMap);
                algorithme.Algorithme_Tri(templeMap);
            }
        });

        MenuItem algorithmeHeuristique = new MenuItem("Algorithme Heuristique");
        algorithmeHeuristique.setOnAction(event -> {
            HashMap<Position, Temple> templeMap = menuGraphique.getTempleMap();
            if (templeMap != null) {
                Algorithme algorithme = new Algorithme(templeMap);
                algorithme.triHeuristique(templeMap, apprenti.getPositionApprenti());
            }

        });


        menuAlgorithmes.getItems().add(algorithmeTriItem);
        menuAlgorithmes.getItems().add(algorithmeHeuristique);

        // Ajout de la barre des menus et du canvas du jeu
        this.setTop(menuBar);
        this.setCenter(menuGraphique);
    }

    public static ApprentiOrdonnateur getApprenti() {
        return apprenti;
    }

    public static Controleur getControleur() { return controleur; }

    public static MenuGraphique getMenuGraphique() { return menuGraphique; }

    public static Algorithme getAlgorithme() { return algorithme; }
}
