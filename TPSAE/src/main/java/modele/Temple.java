package modele;

public class Temple {

    public Position position;
    public Integer couleur;
    public Integer cristal;

    public Temple(Position position, int couleur, int cristal) {

        this.position = position;
        this.couleur = couleur;
        this.cristal = cristal;

    }

    public Position getPosition() {
        return position;
    }

    public int getCouleurTemple(){
        return couleur;
    }

    public int getCouleurCristal(){
        return cristal;
    }
    public String toString() {
        return "Temple{" +
                "position=" + position +
                ", couleur=" + couleur +
                ", cristal=" + cristal +
                '}';
    }
}
