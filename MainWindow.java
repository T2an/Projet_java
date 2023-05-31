import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JLabel label1;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label2;
    private JComboBox<String> comboBoxTheme;
    private JComboBox<String> comboBoxTaille;
    private JButton jouerButton;

    public MainWindow(String title, String pseudo1, String pseudo2) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        getContentPane().setBackground(new Color(210, 210, 210));
        initializeComponents(pseudo1,pseudo2);
        setupListeners();
    }

    private void initializeComponents(String pseudo1, String pseudo2) {
        Font labelFont = new Font("Arial", Font.BOLD, 24);
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        label1 = new JLabel("Pseudo 1:");
        label1.setFont(labelFont);
        label1.setBounds(200, 200, 200, 40);

        textField1 = new JTextField();
        textField1.setFont(labelFont);
        textField1.setBounds(400, 200, 300, 40);
        textField1.setText(pseudo1); // Ajout de la valeur par défaut

        JLabel label3 = new JLabel("Pseudo 2:");
        label3.setFont(labelFont);
        label3.setBounds(200, 300, 200, 40);

        textField2 = new JTextField();
        textField2.setFont(labelFont);
        textField2.setBounds(400, 300, 300, 40);
        textField2.setText(pseudo2); // Ajout de la valeur par défaut

        label2 = new JLabel("Thème :");
        label2.setFont(labelFont);
        label2.setBounds(200, 400, 200, 40);

        comboBoxTheme = new JComboBox<>(new String[]{"Cartes", "Animaux", "Drapeaux"});
        comboBoxTheme.setFont(labelFont);
        comboBoxTheme.setBounds(400, 400, 300, 40);

        JLabel label4 = new JLabel("Taille :");
        label4.setFont(labelFont);
        label4.setBounds(200, 500, 200, 40);

        comboBoxTaille = new JComboBox<>(new String[]{"2x2", "3x6","4x5", "2x4", "4x4", "3x4"});
        comboBoxTaille.setFont(labelFont);
        comboBoxTaille.setBounds(400, 500, 300, 40);

        jouerButton = new JButton("Jouer");
        jouerButton.setFont(buttonFont);
        jouerButton.setBounds(400, 600, 200, 50);

        // Ajout des composants à la fenêtre
        setLayout(null);
        add(label1);
        add(textField1);
        add(label3);
        add(textField2);
        add(label2);
        add(comboBoxTheme);
        add(label4);
        add(comboBoxTaille);
        add(jouerButton);
    }

    private void setupListeners() {
        jouerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pseudo1 = textField1.getText();
                String pseudo2 = textField2.getText();
                String theme = (String) comboBoxTheme.getSelectedItem();
                String taille = (String) comboBoxTaille.getSelectedItem();

                System.out.println("Pseudo 1: " + pseudo1);
                System.out.println("Pseudo 2: " + pseudo2);
                System.out.println("Thème: " + theme);
                System.out.println("Taille: " + taille);

                // Vérification que les noms des pseudos sont remplis
                if (!pseudo1.isEmpty() && !pseudo2.isEmpty()) {
                    // Lancer la partie en utilisant les noms des pseudos et le thème sélectionné
                    String[] parts = taille.split("x");
                    Joueur joueur1 = new Joueur(pseudo1);
                    Joueur joueur2 = new Joueur(pseudo2);
                    if (parts.length == 2) {
                        try {
                            int col = Integer.parseInt(parts[0]);
                            int row = Integer.parseInt(parts[1]);
                            GestionnaireCartes gestionnaireCartes = new GestionnaireCartes();
                            gestionnaireCartes.chargerCartes(200, 200, col*row/2, theme);
                            gestionnaireCartes.melangerCartes();
                            GridWindow gridWindow = new GridWindow("Memory", col, row, gestionnaireCartes.getCartes(), gestionnaireCartes.getVerso(), joueur1, joueur2, theme);
                            gridWindow.display();
                            dispose(); 
                        } catch (NumberFormatException j) {
                            System.out.println("Le message n'est pas dans le bon format.");
                        }
                    } else {
                        System.out.println("Le message n'est pas dans le bon format.");
                    }
                } else {
                    // Afficher un message d'erreur si les noms des pseudos ne sont pas remplis
                    JOptionPane.showMessageDialog(MainWindow.this, "Veuillez saisir les pseudos des deux joueurs",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void display() {
        setVisible(true);
    }
}
