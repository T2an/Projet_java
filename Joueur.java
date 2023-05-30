public class Joueur {
    private String pseudo;
    private int nb_pairs;

    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.nb_pairs = 0;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getNbPairs() {
        return nb_pairs;
    }

    public void incrementNbPairs() {
        nb_pairs++;
    }
    public void reinitialiserNbPairs() {
        nb_pairs = 0;
    }
}
