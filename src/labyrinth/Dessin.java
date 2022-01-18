package labyrinth;

import java.awt.*;
import javax.swing.JPanel;

/**
 * Espace permettant l'affichage graphique du labyrinthe. C'est un element de la
 * fenetre d'affichage.
 * <p>
 * Un labyrinthe est un tableau a 2 dimensions de cellules. Les séparations
 * entre les cellules peuvent etre soit un mur soit un passage.
 * </p>
 * <p>
 * La cellule de depart est affichée en jaune, celle d'arrivée en vert. Les
 * cellules du meilleur chemin en cours sont affichées en jaune.
 * </p>
 * <ul>
 * <li>Un labyrinthe est un objet qui implemente l'interface Labyrinthe,</li>
 * <li>Un chemin est un objet qui implemente l'interface Individu.</li>
 * </ul>
 * Voir egalement {@link Fenetre}, {@link Labyrinthe} et {@link Individu}.
 */
public class Dessin extends JPanel {

    /**
     * Fenetre
     */
    Fenetre fen;
    /**
     * largeur de la zone en pixels
     */
    private int larg;
    /**
     * hauteur de la zone en pixels
     */
    private int haut;
    /**
     * Dimension minimale d'une cellule
     */
    private int dMin = 6;
    /**
     * Dimension maximale d'une cellule
     */
    private int dMax = 25;
    /**
     * Dimension actuelle d'une cellule
     */
    private int dim = 6;
    /**
     * Labyrinthe utilise.
     */
    Labyrinthe labyrinthe;
    /**
     * individu à afficher
     */
    Individu indiv;
    /**
     * Population à afficher
     */
    Population pop;
    /**
     * dernière opération effectuée : 1 indiv, 2 population
     */
    int lastOp = 0;

    /**
     * Definition de la zone de dessin, appelee par la fenetre.
     *
     * @param larg largeur du dessin en pixels.
     * @param haut hauteur du dessin en pixels.
     */
    protected Dessin(int larg, int haut, Fenetre fen) {
        this.fen = fen;
        this.larg = larg;
        this.haut = haut;
        setPreferredSize(new Dimension(larg, haut));
    }

    /**
     * Affecte le labyrinthe utilise.
     *
     * @param labyrinthe l'objet representant le labyrinthe.
     */
    protected void setLabyrinthe(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
    }

    /**
     * Renvoie le labyrinthe en cours.
     *
     * @return le labyrinthe declaré.
     */
    protected Labyrinthe getLabyrinthe() {
        return labyrinthe;
    }

    /**
     * Verifie la dimension du labyrinthe afin de garantir un affichage correct.
     *
     * @param nl nombre de lignes.
     * @param nc nombre de colonnes.
     * @return true si correct, false sinon.
     */
    protected boolean setDim(int nl, int nc) {
        if (nl > 30) {
            nl = 30;
        }
        if (nc > 50) {
            nl = 50;
        }
        larg = this.getWidth() - 15;
        haut = this.getHeight() - 20;

        // estimation de la dimension des cases
        int largCase = larg / nc;
        int hautCase = haut / nl;
        if (largCase < dMin || hautCase < dMin) {
            return false;
        }
        // correction de la dimension des cases
        dim = largCase;
        if (largCase > hautCase) {
            dim = hautCase;
        }
        if (dim > dMax) {
            dim = dMax;
        }
        indiv = null;
        pop = null;
        lastOp = 0;
        return true;
    }

    /**
     * Dessine le labyrinthe.
     */
    public void dessinerLabyrinthe() {
        lastOp = -1;
        paint(getGraphics());
    }

    /**
     * Affiche un seul individu, supposé être le meilleur (en jaune).
     *
     * @param indiv le chemin specifié.
     */
    public void afficher(Individu indiv) {
        if (labyrinthe != null && labyrinthe.getNbLignes() > 0 && labyrinthe.getNbCols() > 0) {
            lastOp = 1;
            this.indiv = indiv;
            Graphics2D g = (Graphics2D) getGraphics();
            dessinerIndiv(g);
        }
    }

    /**
     * affiche les emplacements balayés par la population. Tous les individus
     * autres que le premier sont affichés en gris, le premier en jaune.
     *
     * @param pop
     */
    public void afficher(Population pop) {
        if (labyrinthe != null && labyrinthe.getNbLignes() > 0 && labyrinthe.getNbCols() > 0) {
            lastOp = 2;
            this.pop = pop;
            Graphics2D g = (Graphics2D) getGraphics();
            dessinerPop(g);
        }
    }

    /**
     * Méthode générale de dessin imposée par les concepts de l'affichage
     * graphique. Appelée initialement pour le labyrinthe, et automatiquement au
     * redimensionnement ou au réaffichage de la fenêtre.
     *
     * @param gr le graphic utilisé.
     */
    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        larg = getWidth();
        haut = getHeight();
        g.setColor(Color.white);
        g.fillRect(0, 0, larg, haut);
        if (labyrinthe != null && labyrinthe.getNbLignes() > 0 && labyrinthe.getNbCols() > 0) {
            int nl = labyrinthe.getNbLignes();
            int nc = labyrinthe.getNbCols();
            // decalage necessaire pour centrer le labyrinthe
            int dx = (larg - (nc * (dim - 1) + 1)) / 2;
            int dy = (haut - (nl * (dim - 1) + 1)) / 2;
            // reference des lignes des cellules de depart et d'arrivee
            int ld = labyrinthe.getDepart();
            int la = labyrinthe.getArrivee();
            // dessin des cellules et des murs
            for (int l = 0; l < nl; l++) {
                for (int c = 0; c < nc; c++) {
                    g.setColor(Color.BLACK);
                    // position du coin superieur gauche de la case
                    int y0 = dy + l * dim;
                    int x0 = dx + c * dim;
                    // recupere la description de la cellule
                    int v = labyrinthe.getCellule(l, c);
                    // dessin du mur Nord
                    if ((v & 0x01) > 0) {
                        g.drawLine(x0, y0, x0 + dim, y0);
                    }
                    // dessin du mur Est (sauf pour cellule d'arrivée)
                    if ((v & 0x02) > 0 && !(c == nc - 1 && l == la)) {
                        g.drawLine(x0 + dim, y0, x0 + dim, y0 + dim);
                    }
                    // dessin du mur Sud
                    if ((v & 0x04) > 0) {
                        g.drawLine(x0, y0 + dim, x0 + dim, y0 + dim);
                    }
                    // dessin du mur Ouest (sauf pour cellule de départ)
                    if ((v & 0x08) > 0 && !(c == 0 && l == ld)) {
                        g.drawLine(x0, y0, x0, y0 + dim);
                    }
                    // cellule de départ
                    if (c == 0 && l == ld) {
                        g.setColor(Color.yellow);
                        g.fillRect(x0 + 1, y0 + 1, dim - 2, dim - 2);
                    }
                    // cellule d'arrivée
                    if (c == nc - 1 && l == la) {
                        g.setColor(Color.GREEN);
                        g.fillRect(x0 + 1, y0 + 1, dim - 2, dim - 2);
                    }
                }
            }
        }
        if (lastOp == 1) {
            dessinerIndiv(g);
        }
        if (lastOp == 2) {
            dessinerPop(g);
        }
    }

    /**
     * Dessine un individu.
     *
     * @param g
     */
    private void dessinerIndiv(Graphics2D g) {
        effacer(g);
        colorier(g, Color.YELLOW, indiv);

    }

    /**
     * Dessine une population.
     *
     * @param g
     */
    private void dessinerPop(Graphics2D g) {
        effacer(g);
        Color gris = Color.GRAY;
        for (int i = pop.getSize() - 1; i > 0; i--) {
            colorier(g, gris, pop.get(i));
        }
        colorier(g, Color.yellow, pop.get(0));

    }

    /**
     * Efface les chemins antérieurs.
     *
     * @param gr le Graphic utilise.
     * @param indiv le nouveau chemin.
     */
    private void effacer(Graphics2D g) {
        int nl = labyrinthe.getNbLignes();
        int nc = labyrinthe.getNbCols();
        int dx = (larg - (nc * (dim - 1) + 1)) / 2;
        int dy = (haut - (nl * (dim - 1) + 1)) / 2;
        int ld = labyrinthe.getDepart();
        int la = labyrinthe.getArrivee();
        g.setColor(Color.white);

        // effacement des chemins precedents
        for (int l = 0; l < nl; l++) {
            for (int c = 0; c < nc; c++) {
                if (!(c == 0 && l == ld) && !(c == nc - 1 && l == la)) {
                    int yc = dy + l * dim;
                    int xc = dx + c * dim;
                    g.fillRect(xc + 1, yc + 1, dim - 2, dim - 2);
                }
            }
        }
    }

    /**
     * Colorie un parcours avec la couleur indiquee.
     *
     * @param g le graphics
     * @param col couleur de dessin.
     * @param indiv le chemin à dessiner.
     */
    private void colorier(Graphics2D g, Color col, Individu indiv) {
        int choix = labyrinthe.getType();
        // déclage du bord supérieur gauche du labyrinthe
        int nl = labyrinthe.getNbLignes();
        int nc = labyrinthe.getNbCols();
        int dx = (larg - (nc * (dim - 1) + 1)) / 2;
        int dy = (haut - (nl * (dim - 1) + 1)) / 2;
        int ld = labyrinthe.getDepart();
        // int la = labyrinthe.getArrivee();
        int cc = 0;
        int lc = ld;
        // tous les parcours s'effectuent en orientation
        int orient = 2;
        g.setColor(col);
        int[] gen = indiv.getGenome();

        // balayage des genes déclarés valides
        for (int i = 0; i < indiv.getLimite(); i++) {
            // si orientation OK
            if (choix == Labyrinthe.ORIENTATION) {
                orient = gen[i];
            } else {
                // si DIRECTION conversion en orientation
                // si gauche (1) : 1->8, 2->1, 4->2, 8->4 : /2
                // si en face (2) : inchangé
                // si droite (3) : 1->2, 2->4; 4->8; 8 -> 1 : *2
                int dir = gen[i];
                if (dir == 1) {
                    orient = orient / 2;
                }
                if (dir == 3) {
                    orient = orient * 2;
                }
                if (orient == 0) {
                    orient = 8;
                }
                if (orient == 16) {
                    orient = 1;
                }
            }
            // coin supérieur gauche de la cellule
            int yc = dy + lc * dim;
            int xc = dx + cc * dim;
            g.fillRect(xc + 1, yc + 1, dim - 2, dim - 2);
            // détermination de la prochaine cellule
            if (orient == 1) {
                lc = lc - 1;
            }
            if (orient == 2) {
                cc = cc + 1;
            }
            if (orient == 4) {
                lc = lc + 1;
            }
            if (orient == 8) {
                cc = cc - 1;
            }
        }
    }
}
