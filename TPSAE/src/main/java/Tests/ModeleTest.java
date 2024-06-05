package Tests;
import javafx.scene.paint.Color;
import modele.Algorithme;
import modele.ApprentiOrdonnateur;
import modele.Position;
import modele.Temple;

import java.util.HashMap;

public class ModeleTest {

    public static void main(String[] args) {
        testPosition();
        testTemple();
        testApprentiOrdonnateur();
        testAlgorithme();
    }

    /**
     * Méthode testPosition
     * Teste la classe Position, et la méthode deplacementUneCase
     * Tout devrait retourner sans erreur
     */
    public static void testPosition() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(1, 1);
        Position position3 = new Position(0, 0);

        // Test de la méthode deplacementUneCase()
        position1.deplacementUneCase(position2);
        assert position1.getAbscisse() == 0 && position1.getOrdonnee() == 0 :
                "Erreur: La position devrait rester la même après un déplacement sur la même position.";

        // Test des getters
        assert position1.getAbscisse() == 0 : "Erreur: La coordonnée x devrait être 0.";
        assert position1.getOrdonnee() == 0 : "Erreur: La coordonnée y devrait être 0.";
        assert position2.getAbscisse() == 1 : "Erreur: La coordonnée x devrait être 1.";
        assert position2.getOrdonnee() == 1 : "Erreur: La coordonnée y devrait être 1.";

        // Test de la méthode equals
        assert position1.equals(position3) : "Erreur: position1 devrait être égale à position3.";
        assert !position1.equals(position2) : "Erreur: position1 ne devrait pas être égale à position2.";
        assert !position1.equals(null) : "Erreur: position1 ne devrait pas être égale à null.";
        assert !position1.equals(new Object()) : "Erreur: position1 ne devrait pas être égale à un objet d'un autre type.";


        System.out.println("Tests pour Position passés avec succès.");
    }

    /**
     * Méthode testTemple, teste la méthode Temple
     * Tout devrait retourner sans erreur
     */
    public static void testTemple() {
        Position position = new Position(0, 0);
        Temple temple = new Temple(position, 1, 1);

        // Test des getters
        assert temple.getPosition().equals(position) : "Erreur: La position devrait être égale à celle spécifiée.";
        assert temple.getCouleurTemple() == 1 : "Erreur: La couleur du temple devrait être 1.";
        assert temple.getCouleurCristal() == 1 : "Erreur: La couleur du cristal devrait être 1.";

        // Test de la méthode setCouleurCristal
        temple.setCouleurCristal(2);
        assert temple.getCouleurCristal() == 2 : "Erreur: La couleur du cristal devrait être 2 après modification.";

        // Test de la méthode getCouleurValue
        assert temple.getCouleurValue(1) == Color.RED : "Erreur: La couleur 1 devrait être RED.";
        assert temple.getCouleurValue(2) == Color.ORANGE : "Erreur: La couleur 2 devrait être ORANGE.";
        assert temple.getCouleurValue(11) == Color.PURPLE : "Erreur: La couleur 11 devrait être PURPLE.";
        assert temple.getCouleurValue(0) == null : "Erreur: Une couleur invalide devrait retourner null.";

        // Test de la méthode conditionVictoire
        HashMap<Position, Temple> templeMap = new HashMap<>();
        templeMap.put(new Position(0, 0), new Temple(new Position(0, 0), 1, 1));
        templeMap.put(new Position(1, 1), new Temple(new Position(1, 1), 2, 2));
        assert Temple.conditionVictoire(templeMap) : "Erreur: Toutes les couleurs des cristaux correspondent aux couleurs des temples.";

        templeMap.put(new Position(2, 2), new Temple(new Position(2, 2), 3, 4));
        assert !Temple.conditionVictoire(templeMap) : "Erreur: Les couleurs des cristaux ne correspondent pas aux couleurs des temples.";

        System.out.println("Tests pour Temple passés avec succès.");
    }

    /**
     * Méthode testApprentiOrdonnateur, teste la méthode ApprentiOrdonnateur
     * Tout devrait retourner sans erreur
     */
    public static void testApprentiOrdonnateur() {
        ApprentiOrdonnateur apprentiOrdonnateur = new ApprentiOrdonnateur();

        // Test des setters et getters
        apprentiOrdonnateur.setTemples(null);
        assert apprentiOrdonnateur.estVide() : "Erreur: L'apprenti devrait être vide.";
        assert apprentiOrdonnateur.getMonCristal() == 0 : "Erreur: Le cristal devrait être 0.";

        System.out.println("Tests pour ApprentiOrdonnateur passés avec succès.");
    }

    /**
     * Méthode testAlgorithme, teste la méthode Algorithme
     * Tout devrait retourner sans erreur
     */
    public static void testAlgorithme() {
        HashMap<Position, Temple> mapTemple = new HashMap<>();
        // Ajoute des temples à la carte pour le test
        Temple temple = new Temple(new Position(0, 0), 1, 1);
        mapTemple.put(temple.getPosition(), temple);

        Algorithme algorithme = new Algorithme(mapTemple);

        // Test des méthodes sans lancement effectif de l'algorithme (fonctionnalité à vérifier manuellement)
        algorithme.algorithmeTriBase(mapTemple);
        algorithme.triHeuristique(mapTemple, new Position(0, 0));

        System.out.println("Tests pour Algorithme passés avec succès.");
    }
}

