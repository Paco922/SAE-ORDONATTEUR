package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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

/**
 * Classe VBoxRoot, permet d'accèder aux différents élèments du code, dont ApprentiOrdonanteur, le Controleur, et le MenuGraphique.
 * Permet également d'initialiser le MenuGraphique, et y ajoute le menu avec les scénarios, et le menu avec les algorithmes.
 */
public class VBoxRoot extends BorderPane implements ConstantesCanva {

    private static ApprentiOrdonnateur apprenti;
    private static Controleur controleur;
    private static MenuGraphique menuGraphique;




    /**
     * Constructeur VBoxRoot, permet d'initialiser les différentes classes à accèder.
     */
    public VBoxRoot() {
        /** Initialise apprenti pour acceder à la classe ApprentiOrdonnateur */
        apprenti = new ApprentiOrdonnateur();
        /** Initialise controleur pour acceder à la classe Controleur */
        controleur = new Controleur();
        /** Initialise menuGraphique pour acceder à la classe MenuGraphique */
        menuGraphique = new MenuGraphique();





        menuGraphique.setControleur(controleur);

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


        // Ajout de l'algorithme de Tri au menu Algorithme
        MenuItem algorithmeTriItem = new MenuItem("Algorithme Tri");
        algorithmeTriItem.setOnAction(event -> {
            HashMap<Position, Temple> templeMap = menuGraphique.getTempleMap();
            if (templeMap != null) {
                Algorithme algorithme = new Algorithme(templeMap);
                algorithme.algorithmeTriBase(templeMap);
            }
        });

         // Ajout de l'algorithme Heuristique au menu Algorithme
        MenuItem algorithmeHeuristique = new MenuItem("Algorithme Heuristique");
        algorithmeHeuristique.setOnAction(event -> {
            HashMap<Position, Temple> templeMap = menuGraphique.getTempleMap();
            if (templeMap != null) {
                Algorithme algorithme = new Algorithme(templeMap);
                algorithme.triHeuristique(templeMap, apprenti.getPositionApprenti());
            }

        });


        // Ajout des algorithmes aux menu
        menuAlgorithmes.getItems().add(algorithmeTriItem);
        menuAlgorithmes.getItems().add(algorithmeHeuristique);

        // Ajout de la barre des menus et du canvas du jeu
        this.setTop(menuBar);
        this.setCenter(menuGraphique);
    }

    /**
     * Méthode getteur getApprenti, renvoie apprenti, ce qui permet l'accès à la classe apprenti.
     * @return apprenti
     */
    public static ApprentiOrdonnateur getApprenti() {
        return apprenti;
    }

    /**
     * Méthode getteur getControleur, renvoie controleur, ce qui permet l'accès à la classe Controleur (important)
     * @return controleur
     */
    public static Controleur getControleur() { return controleur; }

    /**
     * Méthode getteur getMenuGraphique, renvoie menuGraphique, ce qui permet l'accès à la classe MenuGraphique
     * @return menuGraphique
     */
    public static MenuGraphique getMenuGraphique() { return menuGraphique; }

}
