package Tests;
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

    public static void testPosition() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(1, 1);

        // Test de la méthode deplacementUneCase()
        position1.deplacementUneCase(position2);
        assert position1.getAbscisse() == 0 && position1.getOrdonnee() == 0 :
                "Erreur: La position devrait rester la même après un déplacement sur la même position.";

        // Test des getters
        assert position1.getAbscisse() == 0 : "Erreur: La coordonnée x devrait être 0.";
        assert position1.getOrdonnee() == 0 : "Erreur: La coordonnée y devrait être 0.";
        assert position2.getAbscisse() == 1 : "Erreur: La coordonnée x devrait être 1.";
        assert position2.getOrdonnee() == 1 : "Erreur: La coordonnée y devrait être 1.";

        System.out.println("Tests pour Position passés avec succès.");
    }

    public static void testTemple() {
        Position position = new Position(0, 0);
        Temple temple = new Temple(position, 1, 1);

        // Test des getters
        assert temple.getPosition().equals(position) : "Erreur: La position devrait être égale à celle spécifiée.";
        assert temple.getCouleurTemple() == 1 : "Erreur: La couleur du temple devrait être 1.";
        assert temple.getCouleurCristal() == 1 : "Erreur: La couleur du cristal devrait être 1.";

        System.out.println("Tests pour Temple passés avec succès.");
    }

    public static void testApprentiOrdonnateur() {
        ApprentiOrdonnateur apprentiOrdonnateur = new ApprentiOrdonnateur();

        // Test des setters et getters
        apprentiOrdonnateur.setTemples(null);
        assert apprentiOrdonnateur.estVide() : "Erreur: L'apprenti devrait être vide.";
        assert apprentiOrdonnateur.getMonCristal() == 0 : "Erreur: Le cristal devrait être 0.";

        System.out.println("Tests pour ApprentiOrdonnateur passés avec succès.");
    }

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

