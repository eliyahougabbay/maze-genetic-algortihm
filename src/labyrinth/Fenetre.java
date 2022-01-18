package labyrinth;

import javax.swing.*;
// import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Représente la fenêtre principale de l'interface graphique.
 * <p>
 * L'interface graphique utilise un espace vertical à gauche pour placer les
 * elements de saisie ou d'affichage et les boutons. L'espace de droite est
 * réservé à la zone de dessin.
 * </p>
 * Voir {@link Dessin}.
 */
public class Fenetre extends JFrame implements ActionListener {

    /**
     * texte pour saisie du nombre de lignes
     */
    private JTextField tLignes;
    /**
     * texte pour saisie du nombre de colonnes
     */
    private JTextField tCols;
    /**
     * texte pour saisie du nombre d'individus dans une population.
     */
    private JTextField tPop;
    /**
     * texte pour saisie du nombre de generations
     */
    private JTextField tGen;
    /**
     * bouton activant la génération du labyrinthe
     */
    private JButton bNouveau;
    /**
     * bouton lancant la recherche du chemin
     */
    private JButton bChercher;
    /**
     * Composant de dessin
     */
    private Dessin canvas;
    /**
     * Le labyrinthe utilise
     */
    private Labyrinthe lab;

    public Fenetre() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Labyrinth");
        
        // Recupere l'environnement graphique en cours
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        // Recupere le peripherique d'affichage
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        
        // Recupere les parametres de ce peripherique
        GraphicsConfiguration gc = gs.getDefaultConfiguration();
        
        // Le parametres interessant est la taille du rectangle disponible
        // pour l'affichage.
        java.awt.Rectangle limites = gc.getBounds();
        int larg = (int) (limites.getWidth() * 0.8);
        int haut = (int) (limites.getHeight() * .8);
        setSize(larg, haut);
        
        // positionnement de la fenetre a l'ecran
        int px = ((int) (limites.getWidth() - larg)) / 2;
        int py = 20;
        setLocation(px, py);

        // Declaration du contenu de la fenetre
        Container contenu = getContentPane();
        contenu.setLayout(new BorderLayout());

        /**
         * Left Panel
         */
        JPanel gauche = new JPanel();
        contenu.add(gauche, BorderLayout.LINE_START);
        JPanel vertic = new JPanel();
        gauche.add(vertic);
        vertic.setLayout(new BoxLayout(vertic, BoxLayout.Y_AXIS));

        // saisie des lignes
        JLabel lLignes = new JLabel();
        lLignes.setText("lignes");
        // lLignes.setAlignmentX(Component.LEFT_ALIGNMENT);
        vertic.add(lLignes);
        tLignes = new JTextField();
        tLignes.setText("10");
        vertic.add(tLignes);

        // saisie des colonnes
        JLabel lCols = new JLabel();
        lCols.setText("colonnes");;
        lCols.setAlignmentX(0.0f);
        vertic.add(lCols);
        // lCols.setAlignmentX(0.5f);
        tCols = new JTextField();
        tCols.setText("10");
        vertic.add(tCols);
        
        // bouton de génération du labyrinthe
        bNouveau = new JButton();
        bNouveau.setText("New");
        bNouveau.setActionCommand("Nouveau");
        bNouveau.addActionListener(this);
        bNouveau.setBackground(Color.WHITE);
        vertic.add(bNouveau);
        
        vertic.add(Box.createVerticalStrut(20));
        
        // Polutation field
        JLabel lPop = new JLabel();
        lPop.setText("population");
        lPop.setAlignmentX(0.5f);
        vertic.add(lPop);
        lPop.setAlignmentX(0.5f);
        tPop = new JTextField();
        tPop.setText("25");
        vertic.add(tPop);

        // Generation field
        JLabel lGen = new JLabel();
        lGen.setText("générations");
        lGen.setAlignmentX(0.5f);
        vertic.add(lGen);
        lGen.setAlignmentX(0.5f);
        tGen = new JTextField();
        tGen.setText("1000");
        vertic.add(tGen);
        
        // Find solution field
        bChercher = new JButton();
        bChercher.setText("Chercher");
        bChercher.setActionCommand("Chercher");
        bChercher.addActionListener(this);
        bChercher.setBackground(Color.WHITE);
        vertic.add(bChercher);

        vertic.add(Box.createVerticalGlue());

        /**
         * Drawing area
         */
        Dimension dimV = vertic.getMinimumSize();
        int ll = larg - dimV.width - 5;
        int hh = haut - 25;
        canvas = new Dessin(ll, hh, this);
        contenu.add(canvas, BorderLayout.CENTER);
     
        /**
         * Logs area
         */
        JPanel logsPanel = new JPanel();
        logsPanel.setBackground(Color.LIGHT_GRAY);
        contenu.add(logsPanel, BorderLayout.PAGE_END);

        JTextArea logs = new JTextArea();
        logs.setEditable(false);
        logs.setBackground(Color.LIGHT_GRAY);
        String logString = "> Labyrinth initialized";
        logs.setText(logString); 
        logsPanel.add(logs);

        pack();
        setVisible(true);
    }


    /**
     * Affectation du labyrinthe.
     *
     * @param labyrinthe le labyrinthe choisi.
     */
    public void setLabyrinthe(Labyrinthe labyrinthe) {
        this.lab = labyrinthe;
        canvas.setLabyrinthe(labyrinthe);
        labyrinthe.setFenetre(this);
        actionPerformed(new ActionEvent(this, 0, "Nouveau"));
    }


    /**
     * Demande l'affichage du labyrinthe vide.
     */
    public void dessinerLabyrinthe() {
        canvas.dessinerLabyrinthe();
    }


    /**
     * Demande l'affichage d'un individu (en jaune).
     *
     * @param ind l'individu à afficher.
     */
    public void afficher(Individu ind) {
        canvas.afficher(ind);
    }


    /**
     * Affiche les emplacements balayés par la population. Tous les individus
     * autres que le premier sont affichés en gris, le premier en jaune
     *
     * @param pop
     */
    public void afficher(Population pop) {
        canvas.afficher(pop);
    }

    
    /**
     * Reception et traitement des évènements de la fenetre. Les informations
     * correspondantes sont transmises au labyrinthe.
     * <ul>
     * <li>Le bouton <b>Nouveau</b> demande de générer un labyrinthe avec les
     * nombres de lignes et de colonnes indiqués.</li>
     * <li>Le bouton <b>Chercher</b>
     *
     * @param e l'evenement active.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Nouveau":
                int nl = Integer.parseInt(tLignes.getText());
                int nc = Integer.parseInt(tCols.getText());
                if (canvas.setDim(nl, nc)) {
                    canvas.getLabyrinthe().generer(nl, nc);
                    System.out.println(canvas.getLabyrinthe());
                    repaint();
                } 
                break;
            case "Chercher":
                int npop = Integer.parseInt(tPop.getText());
                int ngen = Integer.parseInt(tGen.getText());
                canvas.getLabyrinthe().evoluer(npop, ngen);
                break;
        }
    }
}
