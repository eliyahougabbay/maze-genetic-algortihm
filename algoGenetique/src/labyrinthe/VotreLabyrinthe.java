package labyrinthe;

import java.util.ArrayList;
import static labyrinthe.Labyrinthe.ORIENTATION;

/**
 * A Class to randomly generate a {@link Labyrinthe}.
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

        for (int i = 0; i < nl; i++) {
            for (int j = 0; j < nc; j++) {
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
    public void afficherLab() {
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
     * Check on which square coordinates the path may continue
     * 
     * @param ligne   the row square coordinates where the chemin stopped
     * @param colonne the row square coordinates where the chemin stopped
     * @return array of available square coordinates
     */
    public int[][] ChercheLesVoisines(int ligne, int colonne) {

        // Maze squares coordinates where the chemin may continue
        int[][] CoordonnesPossibles = new int[5][2];
        int[][] Coordonnees = { { ligne - 1, colonne },
                { ligne, colonne + 1 },
                { ligne + 1, colonne },
                { ligne, colonne - 1 } };

        // Check for each square coordinates if it's inside the labyrinth.
        // If not, each square coordinates (x, y as in 2D) is replace by -1
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


    // METHODE PRENDRE LA VOISINE ET CASSER LES MURS
    /**
     * Each
     * 
     * @param Tab ?
     * @return
     */
    public int[] PrendreLaVoisine(int[][] Tab) {
        /*
         * Dans Tab se situe respectivement le Nord, l'Est, le Sud, et l'Ouest des
         * coordonnées de la case précédente
         */

        // On cherche les voisines non visitées
        ArrayList<Integer> TabPuce = new ArrayList();

        int x = (int) (Math.random() * (Tab.length - 2));

        // Futur possibles coordonnées où nous nous placerons
        int[] Coord = { Tab[x][0], Tab[x][1] };

        // indice respectif de la case Nord, Est, Sud, Ouest
        int k = Tab.length - 2; // 2 ? current index and ?

        // on selectionne les cases qui sont nouvelles que l'on peut visiter
        while (k > -1) {
            if (Tab[k][0] != -1 && chemin[Tab[k][0]][Tab[k][1]] == 0) {
                // On ajoute les voisines libres
                TabPuce.add(k);
            }
            k -= 1;
        }

        int l = (int) (Math.random() * TabPuce.size());

        // On choisit au hasard l'indice des coordonnées dans la liste TabPuce qui
        // correspond à la voisine à visiter
        if (TabPuce.size() == 0) {
            Coord[0] = Tab[4][0];
            Coord[1] = Tab[4][1];
            return (Coord);
        }
        

        if (TabPuce.get(l) == 0) { // Si la case Nord fut choisie
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 1;
            // On casse notre mur
            labyrinthe[Tab[4][0] - 1][Tab[4][1]] -= 4;
            // On casse le mur de la voisine
            Coord[0] = Tab[4][0] - 1;
            Coord[1] = Tab[4][1];

            return (Coord);
        }

        if (TabPuce.get(l) == 1) { // si la case Est fut choisie
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 2;
            // On casse notre mur
            labyrinthe[Tab[4][0]][Tab[4][1] + 1] -= 8;
            // On casse le mur de la voisine
            Coord[0] = Tab[4][0];
            Coord[1] = Tab[4][1] + 1;
            return (Coord);
        }

        if (TabPuce.get(l) == 2) { // Si la case Sud fut choisie
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 4;
            // On casse notre mur
            labyrinthe[Tab[4][0] + 1][Tab[4][1]] -= 1;
            // On casse le mur de la voisine
            Coord[0] = Tab[4][0] + 1;
            Coord[1] = Tab[4][1];
            // System.out.println("\n"+"Les coordonnées sont : "+Coord[0]+" "+Coord[1]);
            return (Coord);
        }

        if (TabPuce.get(l) == 3) {// Si la case Ouest fut choisie
            labyrinthe[Tab[4][0]][Tab[4][1]] -= 8;
            // On casse notre mur
            labyrinthe[Tab[4][0]][Tab[4][1] - 1] -= 2;
            // On casse le mur de la voisine
            Coord[0] = Tab[4][0];
            Coord[1] = Tab[4][1] - 1;
            // System.out.println("\n"+"Les coordonnées sont : "+Coord[0]+" "+Coord[1]);
            return (Coord);
        }

        return Coord;
    }
    // CHERCHE LIGNE CASE DE DEBUT ET CASE DE FIN

    /**
     * Generate start and end row coordinates 
     * 
     * @return vector of start and end coordinates 
     */
    public int[] ChercheES() {
        int[] coordinates = { (int) (Math.random() * nbLigne), (int) (Math.random() * nbLigne) };
        return coordinates;
    }

    // CHOIX AlÉTOIRE DE LA CASE VOISINE QUE L'ON CHOISIRA SI AUCUNE CASE N'EST
    // DISPONIBLE AU ALENTOURE
    public int[] ChoixVoisine(int[][] Tab) {
        ArrayList<Integer> Indice = new ArrayList(); // Futur possible coordonnées où nous nous placeron
        int k = Tab.length - 2;
        while (k > -1) {
            if (Tab[k][0] != -1) {
                Indice.add(k); // On ajoute les indices des possibles voisines
            }
            k -= 1;
        }
        // System.out.println();
        int x = (int) (Math.random() * Indice.size());// On effectue le choix de l'indice aléatoire
        // System.out.println("Le nombre aléatoire est : "+x);
        int[] Coord = new int[2];
        Coord[0] = Tab[Indice.get(x)][0];
        Coord[1] = Tab[Indice.get(x)][1];
        // System.out.print("Les nouveaux coordonnées sont : "+Coord[0]+", "+Coord[1]);
        return Coord;
    }

    // CREATION DU LABYRINTHE

    public void CreerLabyrinte() {
        // Choix aleatoire de la premiere cellule
        // System.out.println();
        int l = (int) (Math.random() * nbLigne);
        int c = (int) (Math.random() * nbColonne);
        // On met la premiere case choisie comme visitee
        chemin[l][c] = 1;
        int curseur = 1;
        int k = 1;
        while (curseur != nbLigne * nbColonne) {// on arrete une fois avoir visité toutes les cases
            int[][] lesVoisines = ChercheLesVoisines(l, c);
            int[] PredreLaVoisine = PrendreLaVoisine(lesVoisines);
            l = PredreLaVoisine[0];
            c = PredreLaVoisine[1];
            // System.out.println("La valeur du chemin est : "+chemin[l][c]);
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
        // cassage des murs entrée et sortie
        int[] LigneMursES = ChercheES();
        ligneDep = LigneMursES[0];
        ligneArr = LigneMursES[1];
        labyrinthe[LigneMursES[0]][0] = labyrinthe[LigneMursES[0]][0] - 8;// on casse le mur a l'ouest
        labyrinthe[LigneMursES[1]][nbColonne - 1] = labyrinthe[LigneMursES[0]][0] - 2;// on casse le mur a l'est
    }
    // IMPLEMENTATION DES METHODES DE L'INTERFACE LABYRINTHE

    @Override
    public void generer(int nl, int nc) {
        // nbLigne=nl;
        // nbColonne=nc;
        // Choix aleatoire de la premiere cellule
        // System.out.println();
        int l = (int) (Math.random() * nbLigne);
        int c = (int) (Math.random() * nbColonne);
        // On met la premiere case choisie comme visitee
        chemin[l][c] = 1;
        int curseur = 1;
        int k = 1;
        while (curseur != nbLigne * nbColonne) {// on arrete une fois avoir visité toutes les cases
            int[][] lesVoisines = ChercheLesVoisines(l, c);
            int[] PredreLaVoisine = PrendreLaVoisine(lesVoisines);
            l = PredreLaVoisine[0];
            c = PredreLaVoisine[1];
            // System.out.println("La valeur du chemin est : "+chemin[l][c]);
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
        // cassage des murs entrée et sortie
        int[] LigneMursES = ChercheES();
        ligneDep = LigneMursES[0];
        ligneArr = LigneMursES[1];
        labyrinthe[LigneMursES[0]][0] -= 8;// on casse le mur a l'ouest
        labyrinthe[LigneMursES[1]][nbColonne - 1] -= 2;// on casse le mur a l'est

        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFenetre(Fenetre fen) {
        this.fen = fen;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNbLignes() {
        return nbLigne;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNbCols() {
        return nbColonne;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getCellule(int nl, int nc) {
        return labyrinthe[nl][nc];
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getType() {
        return ORIENTATION;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getDepart() {
        return ligneDep;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getArrivee() {
        return ligneArr;
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }

    @Override
    public void evoluer(int npop, int ngen) {
        population = new VotrePopulation(npop, this);
        for (int k = 0; k < ngen; k++) {
            population.Mutation();
        }
        // population = new VotrePopulation();
        // throw new UnsupportedOperationException("Not supported yet."); //To change
        // body of generated methods, choose Tools | Templates.
    }
}