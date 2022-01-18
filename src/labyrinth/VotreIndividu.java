/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

//import static labyrinthe.VotreIndividu.ALPHA;

/**
 *
 * @author GABBAY
 */
public class VotreIndividu implements Individu {

    private int[] genome;
    private double[][] scoreGenome;
    private int lim;
    private VotreLabyrinthe lab;
    private int nbl, nbc;
    private int il, ic;

    public static final double ALPHA = 0.5;

    // AFFICHER LE GENOME D'UN INDIVIDU
    public void afficher() {
        for (int i = 0; i < nbl * nbc; i++) {
            System.out.print(" ");
            this.afficherOrien(i);
        }
        System.out.println();
    }

    // AFFICHER L'ORIENTATION D'UN GÈNE DU GÉNOME
    public void afficherOrien(int k) {
        if (genome[k] == 1)
            System.out.print("Nord");
        if (genome[k] == 2)
            System.out.print("Est");
        if (genome[k] == 4)
            System.out.print("Sud");
        if (genome[k] == 8)
            System.out.print("Ouest");
    }

    // RETOURNE LES INDICES DE LA LIMITE
    public int[] retourIndice() {
        return new int[] { il, ic };
    }

    // CONSTRUCTEUR : CREATION D'UN INDIVIDU QUI CORRESPOND À UN GENOME
    public VotreIndividu(VotreLabyrinthe lab) {
        this.lab = lab;
        nbl = lab.getNbLignes();
        nbc = lab.getNbCols();
        // creation du genome : On choisit l'orientation
        int[] valPossible = { 1, 2, 4, 8 };
        genome = new int[nbl * nbc];
        // On créer un génome composer aléatoirement des entiers du tableau valPossible
        for (int i = 0; i < nbl * nbc; i++) {
            genome[i] = valPossible[(int) (Math.random() * valPossible.length)];
        }
    }

    // RETOURNE LA DISTANCE À LA CELLULE DE SORTIE DE LA DERNIERE CELLULE DE CHEMIN
    // VALIDE
    public double getDistance(int i, int j) {
        return (Math.sqrt((lab.getArrivee() - i) * (lab.getArrivee() - i) + (nbc - 1 - j) * (nbc - 1 - j)));
    }

    // DONNE LE SCORE DE LA CELLULE D'INDICE DE LIGNE I DE COLONNE J
    public double getScore(int i, int j) {
        double s = (1 - ALPHA) * this.getDistance(i, j) / Math.sqrt(nbl * nbl + nbc * nbc)
                + ALPHA * (nbl * nbc - this.getLimite()) / (nbl * nbc);
        return s;
    }

    // ON ACQUIERT TOUTES LES INFOS NECESSAIRE AU GENOME
    public int[] InfoGenome() {
        // System.out.println();
        /* On va supposer que le gene commence par l'entrée */
        // ----- double[][] scoreGenome = new double[nbl][nbc]; ----
        /* On veut les indices de départ (ces derniers vont évoluer): */
        int il = lab.getDepart(), ic = 0;
        // System.out.println("Les coordonnées de départ sont : "+ il+", "+ic);
        /*
         * On prend le genome et on regarde : - quels sont les endroits où il n'y a pas
         * de mur - si la direction prise par le génome est bonne - si oui : on passe à
         * la case suivante et on réitère le processus - si non : on calcul le score du
         * génome
         */
        /*
         * On regarde tous les genes : on regarde la cellule et on compare avec le gene
         */
        int i = 0;
        int k = 0;
        // boolean b=false;
        
        while (i < nbl * nbc) {

            k++;
            int m = lab.getCellule(il, ic);
            boolean[] walls = { false, false, false, false };

            if (m >= 8) {
                walls[3] = true;
                m -= 8;
            }
            if (m >= 4) {
                walls[2] = true;
                m -= 4;
            }
            if (m >= 2) {
                walls[1] = true;
                m -= 2;
            }
            if (m >= 1) {
                walls[0] = true;
            }
            /*
             * Après avoir regarde tous les murs disponibles on test si le genome ne saute
             * pas un mur : si non, on passe aux coordonnées suivantes
             */
            if (genome[i] == 1 && walls[0] && il >= 1) {
                il -= 1;
            } else if (genome[i] == 2 && walls[1] && ic <= 2) {
                ic += 1;
            } else if (genome[i] == 4 && walls[2] && il <= 2) {
                il += 1;
            } else if (genome[i] == 8 && walls[3] && ic >= 1) {
                ic -= 1;
            } else {
                i = nbl * nbc;
            }

            i++;
        }
        lim = k;
        this.il = il;
        this.ic = ic;
        
        return (new int[] { il, ic, k });
    }


    @Override
    public int[] getGenome() {
        return this.genome;
    }

    @Override
    public void setGenome(int[] genome) {
        this.genome = genome;
    }

    @Override
    public int getLimite() {
        return this.lim;
    }

    @Override
    public void setLimite(int lim) {
        this.lim = lim;
    }
}
