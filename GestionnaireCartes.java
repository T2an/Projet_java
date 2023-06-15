import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

public class GestionnaireCartes {
    private List<Carte> cartes;
    private Image carteVerso;

    public GestionnaireCartes() {
        cartes = new ArrayList<>();
        carteVerso = null; // La carte verso n'est pas chargée par défaut
    }

    public List<Carte> getCartes() {
        return cartes;
    }

    public Image getVerso() {
        return carteVerso;
    }

    public void setCarteVerso(File ficherVerso, int largeurCase, int hauteurCase) {
        try {
            BufferedImage image = ImageIO.read(ficherVerso);
            this.carteVerso = redimensionnerImage(image, largeurCase, hauteurCase);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de l'image : " + ficherVerso.getName());
        }
    }

    public void chargerCartes(int largeurCase, int hauteurCase, int nb_cartes, String theme) {
        File fichierVerso = new File("verso.jpg");
        setCarteVerso(fichierVerso, largeurCase, hauteurCase);
        File dossierCartes = new File(theme);
        if (dossierCartes.isDirectory()) {
            File[] fichiersImages = dossierCartes.listFiles();
            if (fichiersImages != null) {
                int indice = 0;
                for (File fichierImage : fichiersImages) {
                    if (nb_cartes > 0) {
                        try {
                            BufferedImage image = ImageIO.read(fichierImage);
                            Image imageRedimensionnee = redimensionnerImage(image, largeurCase, hauteurCase);
                            Carte carte = new Carte(indice, imageRedimensionnee);
                            Carte carte2 = new Carte(indice, imageRedimensionnee);
                            cartes.add(carte2);
                            cartes.add(carte);
                            indice++;
                            nb_cartes--;
                        } catch (IOException e) {
                            System.out.println("Erreur lors du chargement de l'image : " + fichierImage.getName());
                        }
                    }
                }
            }
        }
    }

    private Image redimensionnerImage(BufferedImage image, int largeur, int hauteur) {
        Image imageRedimensionnee = image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        BufferedImage imageBuffered = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
    
        Graphics2D g2d = imageBuffered.createGraphics();
        g2d.drawImage(imageRedimensionnee, 0, 0, null);
        g2d.dispose();
    
        return imageBuffered;
    }
    public void melangerCartes() {
        Collections.shuffle(cartes);
    }
    
}
