package modele;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class ApprentiOrdonnateur {
    Collection <Temple> temples;
    List<Integer> mesCristaux = new ArrayList<>();

    public void setTemples(Collection<Temple> temples){
        this.temples = temples;
    }

    public void recupCristaux(int couleur_cristal){

        if (mesCristaux.size() > 0){
            return;
        }

        mesCristaux.add(couleur_cristal);

    }

    public void switchCristal(int cristalAEchanger){

        mesCristaux.remove(0);
        mesCristaux.add(cristalAEchanger);
    }

    public int getCristaux(){
        return mesCristaux.get(0);
    }

    public boolean estVide(){
        if (mesCristaux.size() > 0){
            return false;
        }
        return true;
    }
}


