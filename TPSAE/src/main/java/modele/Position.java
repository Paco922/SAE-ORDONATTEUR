package modele;

import java.util.Objects;

/**
 * La classe Position représente une position avec des coordonnées x (abscisse) et y (ordonnée).
 * Elle inclut des méthodes pour déplacer la position d'une case, comparer des positions, et obtenir les coordonnées.
 */
public class Position {
    private static int nombreDePas = 0;
    private int abscisse;
    private int ordonnee;

    /**
     * Constructeur pour initialiser une position avec des coordonnées spécifiées.
     *
     * @param abscisse La coordonnée x.
     * @param ordonnee La coordonnée y.
     */
    public Position(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    /**
     * La méthode deplacementUneCase déplace la position this d'une case
     * pour la rapprocher de celle du paramètre parPosition.
     * Elle incrémente le champ statique nombreDePas.
     *
     * @param parPosition La position vers laquelle this se rapproche.
     */
    public void deplacementUneCase(Position parPosition) {
        nombreDePas++;
        if (this.abscisse > parPosition.abscisse) {
            this.abscisse -= 1;
            return;
        }
        if (this.abscisse < parPosition.abscisse) {
            this.abscisse += 1;
            return;
        }
        if (this.ordonnee > parPosition.ordonnee) {
            this.ordonnee -= 1;
            return;
        }
        if (this.ordonnee < parPosition.ordonnee) {
            this.ordonnee += 1;
            return;
        }
    }

    /**
     * La méthode equals retourne vrai quand l'abscisse de this est égale à l'abscisse du paramètre
     * et que l'ordonnée de this est égale à l'ordonnée du paramètre.
     *
     * @param o L'objet à comparer avec this.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return abscisse == position.abscisse && ordonnee == position.ordonnee;
    }

    /**
     * Retourne le hash code de la position.
     *
     * @return Le hash code de la position.
     */
    @Override
    public int hashCode() {
        return Objects.hash(abscisse, ordonnee);
    }

    /**
     * Retourne la coordonnée x (abscisse) de la position.
     *
     * @return La coordonnée x.
     */
    public int getAbscisse() {
        return abscisse;
    }

    /**
     * Retourne la coordonnée y (ordonnee) de la position.
     *
     * @return La coordonnée y.
     */
    public int getOrdonnee() {
        return ordonnee;
    }

    /**
     * Retourne une représentation en chaîne de la position.
     *
     * @return Une chaîne représentant la position.
     */
    @Override
    public String toString() {
        return "[ " + abscisse + "," + ordonnee + " ]";
    }

    /**
     * Retourne le nombre total de déplacements effectués.
     *
     * @return Le nombre de déplacements.
     */
    public static int getNombreDePas() {
        return nombreDePas;
    }
}
