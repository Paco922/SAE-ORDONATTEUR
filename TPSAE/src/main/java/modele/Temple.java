package modele;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
            default: return Color.BLACK;
        }
    }

    /**
     * Retourne la position du temple.
     *
     * @return La position du temple.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Définit la couleur du cristal du temple.
     *
     * @param cristal La nouvelle couleur du cristal.
     */
    public void setCouleurCristal(int cristal) {
        this.cristal = cristal;
    }

    /**
     * Vérifie si tous les temples dans la carte ont des cristaux correspondant à leur couleur.
     *
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
     * Retourne la couleur du temple.
     *
     * @return La couleur du temple.
     */
    public int getCouleurTemple() {
        return couleur;
    }

    /**
     * Retourne la couleur du cristal du temple.
     *
     * @return La couleur du cristal.
     */
    public int getCouleurCristal() {
        return cristal;
    }

    /**
     * Retourne une représentation en chaîne du temple.
     *
     * @return Une chaîne représentant le temple.
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
