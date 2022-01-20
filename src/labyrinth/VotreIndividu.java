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

    // AFFICHER LE GENOME D'UN 
    /**
     * Print invdual sequence 
     */
    public void afficher() {
        for (int i = 0; i < nbl * nbc; i++) {
            System.out.print(" ");
            this.afficherOrien(i);
        }
        System.out.println();
    }

    // AFFICHER L'ORIENTATION D'UN GÈNE DU GÉNOME
    /**
     * Print individual sequence orientation
     * 
     * @param k
     */
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
    /**
     * Limits indexes 
     * 
     * @return
     */
    public int[] retourIndice() {
        return new int[] { il, ic };
    }

    
    /**
     * Generates a random sequence of the individual
     * 
     * @param lab the labyrinth
     */
    public VotreIndividu(VotreLabyrinthe lab) {

        this.lab = lab;
        nbl = lab.getNbLignes();
        nbc = lab.getNbCols();
        // every possible orientation (North = 1, Est = 2, South = 4, West = 8)
        int[] possibleOrientations = { 1, 2, 4, 8 };
        this.genome = new int[nbl * nbc];
        for (int i = 0; i < nbl * nbc; i++) {
            this.genome[i] = possibleOrientations[(int) (Math.random() * possibleOrientations.length)];
        }
    }

    // RETOURNE LA DISTANCE À LA CELLULE DE SORTIE DE LA DERNIERE CELLULE DE CHEMIN
    // VALIDE
    /**
     * Get distance between last path square and labyrinth exit.
     * 
     * @param i row square number
     * @param j column square number
     * @return euclidean distance 
     */
    public double getDistance(int i, int j) {
        return (Math.sqrt(Math.pow((lab.getArrivee() - i), 2) + Math.pow((nbc - 1 - j), 2)));
    }

    /**
     * Get score of i,j coordinates square
     * 
     * @param i row number
     * @param j column number 
     * @return score
     */
    public double getScore(int i, int j) {
        double score = (1 - ALPHA) * this.getDistance(i, j) / Math.sqrt(nbl * nbl + nbc * nbc)
                + ALPHA * (nbl * nbc - this.getLimite()) / (nbl * nbc);
        return score;
    }

    
    // ON ACQUIERT TOUTES LES INFOS NECESSAIRE AU GENOME
    /**
     * 
     * @return 
     */
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
