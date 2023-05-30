import java.awt.Image;

public class Carte {
    private int indice;
    private boolean decouvert;
    private Image image;

    public Carte(int indice, Image image) {
        this.decouvert = false;
        this.image = image;
        this.indice = indice;
    }

    public boolean isDecouvert() {
        return decouvert;
    }

    public void setDecouvert(boolean decouvert) {
        this.decouvert = decouvert;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getIndice() {
        return indice;
    }

    public void retournerCarte() {
        decouvert = !decouvert;
    }
}
