package modele;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import vue.MenuGraphique;
import vue.VBoxRoot;

import java.util.HashMap;

/**
 * La classe Temple représente un temple avec une position, une couleur et un cristal.
 * Elle inclut des méthodes pour obtenir la couleur du temple, vérifier la condition de victoire et gérer les attributs du temple.
 */
public class Temple {

    private Position position;
    public int couleur;
    private int cristal;

    /**
     * Constructeur pour initialiser un temple avec une position, une couleur et un cristal.
     *
     * @param position La position du temple.
     * @param couleur La couleur du temple.
     * @param cristal La couleur du cristal.
     */
    public Temple(Position position, int couleur, int cristal) {
        this.position = position;
        this.couleur = couleur;
        this.cristal = cristal;
    }

    /**
     * Retourne la couleur en tant qu'objet Paint correspondant à l'entier de la couleur.
     *
     * @param couleur L'entier représentant la couleur.
     * @return L'objet Paint correspondant à la couleur.
     */
    public Paint getCouleurValue(int couleur) {
        switch (couleur) {
            case 1: return Color.RED;
            case 2: return Color.ORANGE;
            case 3: return Color.YELLOW;
            case 4: return Color.GREEN;
            case 5: return Color.BLUE;
            case 6: return Color.INDIGO;
            case 7: return Color.VIOLET;
            case 8: return Color.PINK;
            case 9: return Color.BROWN;
            case 10: return Color.GRAY;
            case 11: return Color.PURPLE;
        }
        return null;
    }

    /**
     * Getteur getPosition, pour récupèrer la position du temple.
     * @return La position du temple.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setteur setCouleurCristal, pour récupèrer la couleur du cristal du temple.
     * @param cristal La nouvelle couleur du cristal.
     */
    public void setCouleurCristal(int cristal) {
        this.cristal = cristal;
    }

    /** Méthode conditionVictoire,
     * Vérifie si tous les temples dans la carte ont des cristaux correspondant à leur couleur.
     * @param templeMap La carte des temples.
     * @return true si tous les cristaux correspondent à la couleur des temples, false sinon.
     */
    public static boolean conditionVictoire(HashMap<Position, Temple> templeMap) {
        for (Temple temple : templeMap.values()) {
            if (temple.getCouleurTemple() != temple.getCouleurCristal()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Getteur getCouleurTemple, pour récupèrer la couleur du temple.
     * @return La couleur du temple.
     */
    public int getCouleurTemple() {
        return couleur;
    }

    /**
     * Getteur getCouleurCristal, pour récupèrer la couleur du cristal du temple.
     * @return La couleur du cristal.
     */
    public int getCouleurCristal() {
        return cristal;
    }

    /**
     * Méthode toString pour récupèrer un string contenant l'information des temples.
     * @return Un string contenant les informations des temples.
     */
    @Override
    public String toString() {
        return "Temple{" +
                "position=" + position +
                ", couleur=" + couleur +
                ", cristal=" + cristal +
                '}';
    }
}
