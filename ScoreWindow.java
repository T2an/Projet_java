import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreWindow extends JFrame {
    private JLabel labelGagnant;
    private JLabel labelScoreGagnant;
    private JLabel labelScorePerdant;
    Joueur gagnant;
    Joueur perdant;

    public ScoreWindow(Joueur joueur1, Joueur joueur2) {
        super("Résultats du jeu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        initializeComponents(joueur1, joueur2);
    }

    private void initializeComponents(Joueur joueur1, Joueur joueur2) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        if (joueur1.getNbPairs() > joueur2.getNbPairs()) {
            gagnant = joueur1;
            perdant = joueur2;
        } else if (joueur1.getNbPairs() < joueur2.getNbPairs()){
            gagnant = joueur2;
            perdant = joueur1;
        }else{
            gagnant=null;
            perdant=null;
        }

        if (gagnant==null){
            labelGagnant = new JLabel("Egalité entre les deux joueurs !");
            labelScoreGagnant = new JLabel("Score de " + joueur1.getPseudo() + ": " + joueur1.getNbPairs());
            labelScorePerdant = new JLabel("Score de " + joueur2.getPseudo() + ": " + joueur2.getNbPairs());
        }else{
            labelGagnant = new JLabel("Bravo " + gagnant.getPseudo() + " !");
            labelScoreGagnant = new JLabel("Score de " + gagnant.getPseudo() + ": " + gagnant.getNbPairs());
            labelScorePerdant = new JLabel("Score de " + perdant.getPseudo() + ": " + perdant.getNbPairs());
        }
        labelGagnant.setFont(new Font("Arial", Font.BOLD, 40));
        labelGagnant.setForeground(Color.BLUE);

        labelScoreGagnant.setFont(new Font("Arial", Font.PLAIN, 30));
        labelScoreGagnant.setForeground(Color.GREEN);

        labelScorePerdant.setFont(new Font("Arial", Font.PLAIN, 30));
        labelScorePerdant.setForeground(Color.RED);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(labelGagnant, constraints);

        constraints.gridy = 1;
        panel.add(labelScoreGagnant, constraints);

        constraints.gridy = 2;
        panel.add(labelScorePerdant, constraints);

        JButton nouvellePartieButton = new JButton("Nouvelle partie");
        nouvellePartieButton.setFont(new Font("Arial", Font.BOLD, 25));
        nouvellePartieButton.setForeground(Color.WHITE);
        nouvellePartieButton.setBackground(Color.BLUE);
        nouvellePartieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fermer la fenêtre des résultats
                MainWindow mainWindow = new MainWindow("Nouvelle partie");
                mainWindow.display(); // Lancer une nouvelle instance de MainWindow
            }
        });

        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(nouvellePartieButton, constraints);

        getContentPane().setBackground(new Color(240, 240, 240));
        add(panel);
    }

    public void display() {
        setVisible(true);
    }
}
