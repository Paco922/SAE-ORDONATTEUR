package modele;

import javafx.geometry.Pos;
import vue.ConstantesCanva;

import java.util.Collection;

public class ApprentiOrdonnateur {
    Collection <Temple> temples;
    int monCristal;

    Position positionApprenti;

    // Accesseur pour la classe Temple
    private Temple templeTest;
    // Setteur pour le temple  .

    public ApprentiOrdonnateur(){
        monCristal = 0;
        positionApprenti = new Position(ConstantesCanva.LARGEUR_CANVAS/(2 * ConstantesCanva.CARRE),
                ConstantesCanva.LARGEUR_CANVAS/(2 * ConstantesCanva.CARRE));

    }
    public void setTemples(Collection<Temple> temples){
        this.temples = temples;
    }

    /**
     * Méthode switchCristal, met le cristal dans l'inventaire de l'ordonnateur,
     * et met le cristal qui était dans l'inventaire de l'ordonnateur, dans la couleur du temple
     * @param cristalARecup, le cristal qui est à récupérer
     * @param cristalADonner, le cristal qui est à donner (au temple)
     */
    public void switchCristal(Temple temple, int cristalARecup, int cristalADonner) {
        if (temple != null) {
            temple.setCouleurCristal(cristalADonner);
            monCristal = cristalARecup;
        }
    }
    /**
     * Accesseur sur le champ monCristal
     * @return le cristal de l'ordonnateur
     */
    public int getMonCristal(){
        return monCristal;
    }

    /**
     * Méthode booléene estVide, vérifie si l'ordonnateur porte un cristal
     * @return booléen, true si vide, faux sinon.
     */
    public boolean estVide(){

        return monCristal == 0;
    }
}


