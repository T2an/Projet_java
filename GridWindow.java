import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GridWindow extends JFrame {
    private JPanel gridPanel;
    private Carte carte1Cliquee;
    private Carte carte2Cliquee;
    private Timer timer;
    private Map<Carte, JLabel> carteLabelMap;
    private JLabel joueurActifLabel;
    private Joueur joueurActif;
    private JButton rejouerButton;
    private JButton terminerButton;

    public GridWindow(String title, int col, int row, List<Carte> cartes, Image verso, Joueur joueur1, Joueur joueur2, String theme) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200*row, 200*col); // Taille initiale de la fenêtre
        setMinimumSize(new Dimension(600, 600)); // Taille minimale de la fenêtre
        initializeComponents(col, row, cartes, verso, joueur1, joueur2, theme);
    }

    private void initializeComponents(int col, int row, List<Carte> cartes, Image verso, Joueur joueur1, Joueur joueur2, String theme) {

        // Création du bandeau gris en haut de l'interface
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));

        // Création du panel pour le label du joueur 1
        JPanel joueur1Panel = new JPanel();
        joueur1Panel.setLayout(new BorderLayout());

        // Création du panel pour le label du joueur 2
        JPanel joueur2Panel = new JPanel();
        joueur2Panel.setLayout(new BorderLayout());

        Random random = new Random();
        //Choix aléatoire du premier joueur
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0) {
            joueurActif = joueur1;
            joueur1Panel.setBackground(Color.BLUE);
            joueur2Panel.setBackground(Color.RED);
        } else {
            joueurActif = joueur2;
            joueur2Panel.setBackground(Color.RED);
            joueur1Panel.setBackground(Color.BLUE);
        }

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

        // Ajout des labels aux panels des joueurs
        joueur1Panel.add(joueur1Label, BorderLayout.CENTER);
        joueur2Panel.add(joueur2Label, BorderLayout.CENTER);

        // Ajout des panels des joueurs au bandeau supérieur
        headerPanel.add(joueur1Panel);
        headerPanel.add(joueur2Panel);

        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        headerPanel.setBackground(Color.BLACK);

        // Ajout du bandeau supérieur en haut de la fenêtre
        add(headerPanel, BorderLayout.NORTH);


        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(col, row, 10, 10));
        gridPanel.setBackground(Color.BLUE);
        carteLabelMap = new HashMap<>();

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
                                            joueur1Panel.setBackground(Color.BLUE);
                                            joueur2Panel.setBackground(Color.RED);

                                        } else {
                                            joueurActif = joueur1;
                                            joueur2Panel.setBackground(Color.BLUE);
                                            joueur1Panel.setBackground(Color.RED);
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



        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.BLUE);
        footerPanel.setBorder(new LineBorder(Color.BLACK, 5)); // Définition de la couleur et de l'épaisseur de la bordure
        
        // Création du label pour le message du joueur actif
        joueurActifLabel = new JLabel("Au tour de " + joueurActif.getPseudo() + " !");
        joueurActifLabel.setFont(font);
        joueurActifLabel.setForeground(Color.WHITE);
        joueurActifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Utilisation d'un BoxLayout pour aligner les composants horizontalement
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.X_AXIS));
        
        // Création des boutons
        rejouerButton = new JButton("Rejouer");
        rejouerButton.setFont(font);
        
        terminerButton = new JButton("Terminer");
        terminerButton.setFont(font);
        
        // Ajout des boutons au footerPanel

        footerPanel.add(rejouerButton);
        footerPanel.add(Box.createHorizontalGlue()); // Ajout d'un espace flexible pour séparer les boutons
        footerPanel.add(joueurActifLabel);
        footerPanel.add(Box.createHorizontalGlue()); // Ajout d'un espace flexible pour séparer les boutons
        footerPanel.add(terminerButton);
        
        rejouerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joueur1.reinitialiserNbPairs();
                joueur2.reinitialiserNbPairs();
                GestionnaireCartes gestionnaireCartes = new GestionnaireCartes();
                gestionnaireCartes.chargerCartes(200, 200, col*row/2, theme);
                gestionnaireCartes.melangerCartes();
                GridWindow gridWindow = new GridWindow("Memory", col, row, gestionnaireCartes.getCartes(), gestionnaireCartes.getVerso(), joueur1, joueur2, theme);
                gridWindow.display();
                dispose();
            }
        });

        terminerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow mainWindow = new MainWindow("Nouvelle partie", joueur1.getPseudo(), joueur2.getPseudo());
                mainWindow.display();
                dispose();
            }
        });

        // Ajout du footerPanel en bas de la fenêtre
        add(footerPanel, BorderLayout.SOUTH);
        add(gridPanel);
    }

    public void display() {
        setVisible(true);
    }
}
