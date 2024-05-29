package modele;

import controleur.Controleur;
import vue.ConstantesCanva;
import vue.MenuGraphique;
import vue.VBoxRoot;

import java.util.Collection;
import java.util.HashMap;

public class ApprentiOrdonnateur {
    private Collection<Temple> temples;
    private int monCristal;
    private VBoxRoot vBoxRoot = new VBoxRoot();
    private Position positionApprenti = vBoxRoot.getApprenti().getPositionApprenti();
    private HashMap<Position, Temple> templeMap;
    private Controleur controleur = VBoxRoot.getControleur();

    // Constructor
    public ApprentiOrdonnateur() {
        monCristal = 0;
        positionApprenti = new Position(ConstantesCanva.LARGEUR_CANVAS / (2 * ConstantesCanva.CARRE),
                ConstantesCanva.LARGEUR_CANVAS / (2 * ConstantesCanva.CARRE));
        this.templeMap = new HashMap<>();
    }

    // Setter for temples
    public void setTemples(Collection<Temple> temples) {
        this.temples = temples;
    }

    // Method to switch crystals between apprenti and temple
    public void switchCristal(int cristalARecup, int cristalADonner) {
        // Check if the positionApprenti is valid
        if (templeMap == null) {
            templeMap = VBoxRoot.getMenuGraphique().getTempleMap();
        }

        if (templeMap.containsKey(positionApprenti)) {
            Temple currentTemple = templeMap.get(positionApprenti);
            currentTemple.setCouleurCristal(cristalADonner);
            this.monCristal = cristalARecup;
        } else {
            System.out.println("Position " + positionApprenti + " is not a key in templeMap.");
        }
    }

    // Getter for monCristal
    public int getMonCristal() {
        return this.monCristal;
    }

    // Method to check if apprenti is carrying a crystal
    public boolean estVide() {
        return monCristal == 0;
    }

    // Setter for templeMap
    public void setTempleMap(HashMap<Position, Temple> templeMap) {
        this.templeMap = templeMap;
    }

    // Getter for positionApprenti
    public Position getPositionApprenti() {
        return this.positionApprenti;
    }

    // Setter for positionApprenti
    public void setPositionApprenti(Position positionApprenti) {
        this.positionApprenti = positionApprenti;
    }
}
