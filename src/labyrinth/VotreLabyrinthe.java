package labyrinth;

import static labyrinth.Labyrinthe.ORIENTATION;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class to generate a {@link Labyrinthe}.
 * 
 * @author GABBAY
 */
public class VotreLabyrinthe implements Labyrinthe {

    private int nbLigne, nbColonne;
    private int ligneDep, ligneArr;

    private int[][] labyrinthe;
    private int[][] chemin;

    private Fenetre fen;
    private VotrePopulation population;

    /**
     * Constructor of the labyrinth.
     * Each square case has its 4 walls: North = 1, Est = 2, South = 4 & West = 8
     * whose sum is 15 = 1 + 2 + 4 + 8
     * 
     * 
     * @param nl row number
     * @param nc column number
     */
    public VotreLabyrinthe(int nl, int nc) {

        this.nbLigne = nl;
        this.nbColonne = nc;
        this.labyrinthe = new int[nl][nc];
        this.chemin = new int[nl][nc];

    }

    /**
     * Initialize both labyrinth and path values 
     */
    public void initLabyrinthValues() {
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                // each case sum is 15 = (North) 1 + (Est) 2 + (South) 4 + (West) 8
                labyrinthe[i][j] = 15;
                //
                chemin[i][j] = 0;
            }
        }
    }

    /**
     * Print each labyrinthe square value & path value
     */
    public void printLab() {
        for (int i = 0; i < labyrinthe.length; i++) {
            for (int j = 0; j < labyrinthe[0].length; j++) {
                System.out.print(labyrinthe[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < chemin.length; i++) {
            for (int j = 0; j < chemin[0].length; j++) {
                System.out.print(chemin[i][j] + " ");
            }
            System.out.println();
        }
    }


    /**
     * Find all neighbors where the path may continue
     * 
     * @param ligne   the row square coordinates where the path stopped
     * @param colonne the column square coordinates where the path stopped
     * @return array of all available neighbor square coordinates
     */
    public int[][] ChercheLesVoisines(int ligne, int colonne) {

        int[][] CoordonnesPossibles = new int[5][2];
        int[][] Coordonnees = {
                { ligne - 1, colonne },
                { ligne, colonne + 1 },
                { ligne + 1, colonne },
                { ligne, colonne - 1 }
        };

        // Check for each square coordinates if it's inside the labyrinth.
        // If not, both square coordinates (x & y as in 2D) are replaced by -1
        for (int i = 0; i != 4; i++) {

            if (Coordonnees[i][0] >= 0 && Coordonnees[i][0] < nbLigne && Coordonnees[i][1] >= 0
                    && Coordonnees[i][1] < nbColonne) {

                CoordonnesPossibles[i][0] = Coordonnees[i][0];
                CoordonnesPossibles[i][1] = Coordonnees[i][1];
            } else {
                CoordonnesPossibles[i][0] = -1;
                CoordonnesPossibles[i][1] = -1;
            }
        }
        // Add the current coordinates to the returned array
        CoordonnesPossibles[4][0] = ligne;
        CoordonnesPossibles[4][1] = colonne;

        return CoordonnesPossibles;
    }

    
    /**
     * Select neighbors squares path which are unvisited and inside the labyrinthe,
     * randomly select one and generate a path from the current path square
     * to its neighbor by breaking crossing walls
     * 
     * @param Tab neighbor coordinates of the current square path
     * @return the neighbor coordinates of the next path square
     */
    public int[] PrendreLaVoisine(int[][] Tab) {

        ArrayList<Integer> TabPuce = new ArrayList();

        int x = (int) (Math.random() * (Tab.length - 2));

        int[] Coord = { Tab[x][0], Tab[x][1] };

        // indice respectif de la case Nord, Est, Sud, Ouest
        int k = Tab.length - 2; // 2 ? current index and ?

        // select all existing and unvisited neighbor square
        while (k > -1) {

            // Check if the neihbor square is inside the labyrint and is not visited
            if (Tab[k][0] != -1 && chemin[Tab[k][0]][Tab[k][1]] == 0) {
                // If so, add the neighbor to the potential next path square
                TabPuce.add(k);
            }
            k -= 1;
        }

        // Randomly select the avalaible neighbor
        int l = (int) (Math.random() * TabPuce.size());

        if (TabPuce.size() == 0) {
            Coord[0] = Tab[4][0];
            Coord[1] = Tab[4][1];
            return (Coord);
        }

        // If North square neighbor is selected, a crossing is created
        // by breaking the North wall of current square and the South Wall of neighbor square
        if (TabPuce.get(l) == 0) {
            // Break North wall of current path square
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 1;
            // Break South wall of neighbor path square
            labyrinthe[Tab[4][0] - 1][Tab[4][1]] -= 4;

            Coord[0] = Tab[4][0] - 1;
            Coord[1] = Tab[4][1];

            return (Coord);
        }

        // Est neighbor
        if (TabPuce.get(l) == 1) {
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 2;
            // Break Est wall of current path square
            labyrinthe[Tab[4][0]][Tab[4][1] + 1] -= 8;
            // Break West wall of neighbor path square
            Coord[0] = Tab[4][0];
            Coord[1] = Tab[4][1] + 1;
            return (Coord);
        }

        // South neighbor
        if (TabPuce.get(l) == 2) {
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 4;
            // Break South wall of neighbor path square
            labyrinthe[Tab[4][0] + 1][Tab[4][1]] -= 1;
            // Break North wall of neighbor path square
            Coord[0] = Tab[4][0] + 1;
            Coord[1] = Tab[4][1];
            return (Coord);
        }

        // West neighbor
        if (TabPuce.get(l) == 3) {
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 8;
            // Break West wall of neighbor path square
            labyrinthe[Tab[4][0]][Tab[4][1] - 1] -= 2;
            // Break Est wall of neighbor path square
            Coord[0] = Tab[4][0];
            Coord[1] = Tab[4][1] - 1;
            return (Coord);
        }

        return Coord;
    }


    /**
     * Randomly generate start and end row coordinates
     * 
     * @return coordinates vector
     */
    public int[] ChercheES() {
        int[] ESRowCoordinates = { (int) (Math.random() * nbLigne), (int) (Math.random() * nbLigne) };
        return ESRowCoordinates;
    }


    /**
     * Randomly select a neighbor if all neighbors are unavailable.
     * 
     * @param Tab coordinates available
     * @return the neighbor coordinates
     */
    public int[] ChoixVoisine(int[][] Tab) {

        ArrayList<Integer> potentialNeighborIndexes = new ArrayList();
        int k = Tab.length - 2;

        // Check all unvisited neighbors
        while (k > -1) {
            if (Tab[k][0] != -1) {
                potentialNeighborIndexes.add(k);
            }
            k -= 1;
        }

        // Randomly select the neighbor
        int randNeighborIndex = (int) (Math.random() * potentialNeighborIndexes.size());
        int[] neighborCoordinates = {
                Tab[potentialNeighborIndexes.get(randNeighborIndex)][0],
                Tab[potentialNeighborIndexes.get(randNeighborIndex)][1]
        };

        return neighborCoordinates;
    }


    @Override
    public void generer(int nl, int nc) {

        initLabyrinthValues();

        // Randomly select the square coordinates to start generating the labyritnh
        int l = (int) (Math.random() * nbLigne);
        int c = (int) (Math.random() * nbColonne);

        // First square is marked as visited
        chemin[l][c] = 1;

        int curseur = 1;
        int k = 1;
        // Stop once all square are visited
        while (curseur != nbLigne * nbColonne) {

            int[][] lesVoisines = ChercheLesVoisines(l, c);

            int[] PredreLaVoisine = PrendreLaVoisine(lesVoisines);

            l = PredreLaVoisine[0];
            c = PredreLaVoisine[1];

            if (chemin[l][c] == 0) {
                chemin[l][c] = k + 1;
                k = chemin[l][c];
                curseur++;
            } else {
                int[] Voisine = ChoixVoisine(lesVoisines);
                l = Voisine[0];
                c = Voisine[1];
            }

        }

        int[] LigneMursES = ChercheES();
        ligneDep = LigneMursES[0];
        ligneArr = LigneMursES[1];
        // Break start West wall
        labyrinthe[LigneMursES[0]][0] -= 8;
        // Break end Est wall
        labyrinthe[LigneMursES[1]][nbColonne - 1] -= 2;
    }

    @Override
    public void setFenetre(Fenetre fen) {
        this.fen = fen;
    }

    @Override
    public int getNbLignes() {
        return nbLigne;
    }

    @Override
    public int getNbCols() {
        return nbColonne;
    }

    @Override
    public int getCellule(int nl, int nc) {
        return labyrinthe[nl][nc];
    }

    @Override
    public int getType() {
        return ORIENTATION;
    }

    @Override
    public int getDepart() {
        return ligneDep;
    }

    @Override
    public int getArrivee() {
        return ligneArr;
    }

    @Override
    public void evoluer(int npop, int ngen) {
        population = new VotrePopulation(npop, this);
        for (int k = 0; k < ngen; k++) {
            population.Mutation();
        }
        // population = new VotrePopulation();
    }
}