package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import modele.ApprentiOrdonnateur;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * Classe représentant la racine de l'interface graphique.
 * Extends BorderPane et implémente ConstantesCanva.
 */
public class VBoxRoot extends BorderPane implements ConstantesCanva {

    private static ApprentiOrdonnateur apprenti;
    private static Controleur controleur;
    private static MenuGraphique menuGraphique;

    /**
     * Constructeur de la classe VBoxRoot.
     * Initialise l'apprenti, le contrôleur et le menu graphique.
     * Crée la barre des menus et charge les scénarios disponibles.
     */
    public VBoxRoot() {
        apprenti = new ApprentiOrdonnateur();
        controleur = new Controleur();
        menuGraphique = new MenuGraphique();

        // La barre des menus
        MenuBar menuBar = new MenuBar();
        VBox.setMargin(menuBar, new Insets(9));

        // Le menu des scénarios
        Menu menuScenarios = new Menu(INTITULE_MENU_SCENARIOS);
        menuBar.getMenus().add(menuScenarios);

        // Les items du menu scénario
        File[] scenario = new File("Scenario").listFiles();
        for (int i = 0; i < scenario.length; i++) {
            MenuItem menuItem = new MenuItem(scenario[i].getName());
            menuItem.setUserData(scenario[i]);
            menuItem.setOnAction(controleur);
            menuScenarios.getItems().add(menuItem);
        }

        // Ajout de la barre des menus et du canvas du jeu
        this.setTop(menuBar);
        this.setCenter(menuGraphique);
    }

    /**
     * Retourne l'instance de l'apprenti ordonnateur.
     *
     * @return l'apprenti ordonnateur.
     */
    public static ApprentiOrdonnateur getApprenti() {
        return apprenti;
    }

    public static Controleur getControleur() { return controleur;}

    public static MenuGraphique getMenuGraphique(){ return menuGraphique;}
}
