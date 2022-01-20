package labyrinth;


/**
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


    /**
     * Print sequence genome
     */
    public void printGenome(){
        for (int gene : genome)
            System.out.print(gene + " ");
    }

    /**
     * Print individual sequence orientation
     * <br>
     * <i>Change method to hashmap ?</i>
     * 
     * @param k gene index
     */
    public void Orintation(int k) {
        switch(genome[k]){
            case 1:
                System.out.print("North");
                break;
            case 2:
                System.out.print("Est");
                break;
            case 3:
                System.out.print("South");
                break;
            case 4:
                System.out.print("west");
                break;
        }
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
        // every possible orientation {North = 1, Est = 2, South = 4, West = 8}
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
     * For every gene of the genome, check if gene crosses a wall. If so, 
     * set the limite to the last valid square.
     * 
     * @return last valid square index in the genome
     * - limit's row index
     * - limit's column index
     */
    public int[] InfoGenome() {
 
        int il = lab.getDepart(), ic = 0;
        System.out.println("Les coordonnées de départ sont : "+ il+", "+ic);

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
        this.lim = k;
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
