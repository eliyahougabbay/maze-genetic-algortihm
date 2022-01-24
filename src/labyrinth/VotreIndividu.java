package labyrinth;

/**
 * @author GABBAY
 */
public class VotreIndividu implements Individu {

    public static final double ALPHA = 0.5;

    private int[] genome;
    private double[][] scoreGenome;
    private int lim;
    private VotreLabyrinthe lab;
    private int nbl, nbc;
    private int il, ic;

    

    /**
     * Generates a random sequence of the individual and associates a random
     * sequence
     * 
     * @param lab the labyrinth
     */
    public VotreIndividu(VotreLabyrinthe lab) {

        this.lab = lab;
        nbl = lab.getNbLignes();
        nbc = lab.getNbCols();

        int[] possibleOrientations = { 1, 2, 4, 8 };
        this.genome = new int[nbl * nbc];
        for (int i = 0; i < nbl * nbc; i++) {
            this.genome[i] = possibleOrientations[(int) (Math.random() * possibleOrientations.length)];
        }
    }


    /**
     * Get score of x & y coordinates square
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


    /**
     * Get distance between last path square and labyrinth exit.
     * 
     * @param i row square number
     * @param j column square number
     * @return euclidean distance
     */
    public double getDistance(int i, int j) {
        return (Math.sqrt(Math.pow(lab.getArrivee() - i, 2) + Math.pow(nbc - 1 - j, 2)));
    }


    /**
     * For every gene of the genome, check if the gene crosses a wall. If so, set the limit to the last valid square.
     * 
     * @return vector of { limit's row index, limit's column index, last valid square index in the genome }
     */
    public int[] InfoGenome() {

        int il = lab.getDepart(), ic = 0;

        int i = 0, k = 0;
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
            // Checks existing walls and if path doesn't get through one
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

        this.il = il;
        this.ic = ic;
        this.lim = k;

        return (new int[] { il, ic, k });
    }


   /**
     * Limits indexes
     * 
     * @return indexes
     */
    public int[] retourIndice() {
        return new int[] { il, ic };
    }

    
    /**
     * Print sequence genome
     */
    public void printGenome() {
        for (int gene : genome) {
            switch (gene) {
                case 1:
                    System.out.print("North ");
                    break;
                case 2:
                    System.out.print("Est ");
                    break;
                case 3:
                    System.out.print("South ");
                    break;
                case 4:
                    System.out.print("west ");
                    break;
            }
        }
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
