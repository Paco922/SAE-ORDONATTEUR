package modele;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;

public class Temple {

    public Position position;
    public Integer couleur;
    public Integer cristal;

    public Temple(Position position, int couleur, int cristal) {

        this.position = position;
        this.couleur = couleur;
        this.cristal = cristal;

    }

    public Paint getCouleurValue(int couleur){
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

    public Position getPosition() {
        return position;
    }

    public void setCouleurCristal(int cristal){

        this.cristal = cristal;

    }

    public static boolean conditionVictoire(HashMap<Position, Temple> templeMap){
        for (Position key : templeMap.keySet()) {
            if (templeMap.get(key).getCouleurTemple() != templeMap.get(key).getCouleurCristal()){
                return false;
            }
        }
        return true;
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
