package modele;

public class Position {
    private static int nombreDePas = 0;
    private int abscisse;
    private int ordonnee;

    public Position (int abscisse, int ordonnee){
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    /**

     la méthode deplacementUneCase déplace la position this d'une case
     pour la rapporcher de celle du paramètre parPosition
     elle incrémente le champ static nombreDePas
     @param parPosition  : la position vers laquelle this se rapproche
     */

    public void deplacementUneCase (Position parPosition) {
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
     * La methode equal retourne vrai quand l'abscisse de this est egale a l'abscisse du parametre et que
     * l'ordonée de this est egal a l'ordonnée du parametre
     */
    public boolean equals(Position parPosition){
            if (this.abscisse == parPosition.abscisse)
                if (this.ordonnee == parPosition.ordonnee)
                    return true;
            return false;
    }

    
    public int getAbscisse(){
        return abscisse;

    }
    public int getOrdonnee(){
        return ordonnee;
    }
    // à compléter avec la méthode equals(),
    // les acceseurs nécessaires et la méthode toString()
    public String toString(){
        return "[ " + abscisse +"," + ordonnee + "]" ;
    }
    public static int getNombreDePas(){
        return nombreDePas;
    }
}
