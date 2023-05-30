import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GridWindow extends JFrame {
    private JPanel gridPanel;
    private Carte carte1Cliquee;
    private Carte carte2Cliquee;
    private Timer timer;
    private Map<Carte, JLabel> carteLabelMap;
    private JLabel joueurActifLabel;
    private Joueur joueurActif;

    public GridWindow(String title, int col, int row, List<Carte> cartes, Image verso, Joueur joueur1, Joueur joueur2) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200*row, 200*col); // Taille initiale de la fenêtre
        setMinimumSize(new Dimension(600, 600)); // Taille minimale de la fenêtre
        initializeComponents(col, row, cartes, verso, joueur1, joueur2);
    }

    private void initializeComponents(int col, int row, List<Carte> cartes, Image verso, Joueur joueur1, Joueur joueur2) {
        // Création du bandeau gris en haut de l'interface
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.GRAY);
        headerPanel.setLayout(new BorderLayout());

        // Création des panneaux pour les informations des joueurs
        JPanel joueur1Panel = new JPanel();
        joueur1Panel.setBackground(Color.BLUE);
        JPanel joueur2Panel = new JPanel();
        joueur2Panel.setBackground(Color.RED);

        // Création des labels pour les informations des joueurs
        JLabel joueur1Label = new JLabel(joueur1.getPseudo() + " (" + joueur1.getNbPairs() + " pairs)");
        JLabel joueur2Label = new JLabel(joueur2.getPseudo() + " (" + joueur2.getNbPairs() + " pairs)");

        // Stylisation des labels
        Font font = joueur1Label.getFont().deriveFont(Font.BOLD, 20f);
        joueur1Label.setFont(font);
        joueur2Label.setFont(font);
        joueur1Label.setForeground(Color.WHITE);
        joueur2Label.setForeground(Color.WHITE);
        joueur1Label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        joueur2Label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Ajout des labels aux panneaux des joueurs
        joueur1Panel.add(joueur1Label);
        joueur2Panel.add(joueur2Label);

        // Ajout des panneaux des joueurs au bandeau
        headerPanel.add(joueur1Panel, BorderLayout.WEST);
        headerPanel.add(joueur2Panel, BorderLayout.EAST);

        // Création du label pour le message du joueur actif
        joueurActifLabel = new JLabel("Au tour de " + joueur1.getPseudo() + " !");
        joueurActifLabel.setFont(font);
        joueurActifLabel.setForeground(Color.WHITE);
        joueurActifLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        joueurActifLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Ajout du label du joueur actif au centre du bandeau
        headerPanel.add(joueurActifLabel, BorderLayout.CENTER);

        // Ajout du bandeau en haut de la fenêtre
        add(headerPanel, BorderLayout.NORTH);
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(col, row, 10, 10));
        gridPanel.setBackground(Color.WHITE);
        carteLabelMap = new HashMap<>();
        joueurActif = joueur1;
        // Ajout des composants représentant les cartes
        for (Carte carte : cartes) {
            JLabel carteLabel;
            if (carte.isDecouvert()) {
                carteLabel = new JLabel(new ImageIcon(carte.getImage()));
            } else {
                carteLabel = new JLabel(new ImageIcon(verso));
            }

            // Ajout de l'actionListener pour le clic sur la carte
            carteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (carte2Cliquee == null & !carte.isDecouvert()) {
                        if (carte1Cliquee == null) {
                            carte1Cliquee = carte;
                            carte1Cliquee.retournerCarte();
                            carteLabel.setIcon(new ImageIcon(carte1Cliquee.getImage()));
                        } else {
                            carte2Cliquee = carte;
                            carte2Cliquee.retournerCarte();
                            carteLabel.setIcon(new ImageIcon(carte2Cliquee.getImage()));
                            if (carte1Cliquee.getIndice() != carte2Cliquee.getIndice()) {
                                timer = new Timer(500, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        carte1Cliquee.retournerCarte();
                                        carteLabelMap.get(carte1Cliquee).setIcon(new ImageIcon(verso));
                                        carte2Cliquee.retournerCarte();
                                        carteLabel.setIcon(new ImageIcon(verso));
                                        carte1Cliquee = null;
                                        carte2Cliquee = null;
                                        if (joueurActif == joueur1) {
                                            joueurActif = joueur2;
                                        } else {
                                            joueurActif = joueur1;
                                        }
                                        joueurActifLabel.setText(("Au tour de " + joueurActif.getPseudo() + " !"));
                                        timer.stop();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                            } else {
                                carte1Cliquee = null;
                                carte2Cliquee = null;
                                joueurActif.incrementNbPairs(); // Incrémenter le nombre de pairs du joueur actif
                                if (joueurActif == joueur1) {
                                    joueur1Label.setText(joueur1.getPseudo() + " (" + joueur1.getNbPairs() + " pairs)"); // Mettre à jour l'affichage du nombre de pairs du joueur 1
                                } else {
                                    joueur2Label.setText(joueur2.getPseudo() + " (" + joueur2.getNbPairs() + " pairs)"); // Mettre à jour l'affichage du nombre de pairs du joueur 1
                                }
                            }
                        }
                        if (joueur1.getNbPairs() + joueur2.getNbPairs() == (col * row / 2)) {
                            ScoreWindow scoreWindow = new ScoreWindow(joueur1, joueur2);
                            scoreWindow.display();
                            dispose();
                        }
                    }
                }

            });

            carteLabelMap.put(carte, carteLabel);

            gridPanel.add(carteLabel);
        }

        add(gridPanel);
    }

    public void display() {
        setVisible(true);
    }
}
