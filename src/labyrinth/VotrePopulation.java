package labyrinth;

import javax.print.event.PrintJobListener;

/**
 * A class to generate & sort the individuals by their score inside the
 * population. This class also evolves the population by
 * 
 * @author GABBAY
 */
public class VotrePopulation implements Population {

    private int NbIndividu;
    // private VotreIndividu Ind;
    private VotreIndividu[] Population;
    // private int NbLignes,NbColonnes;
    private VotreLabyrinthe lab;
    // private double[] scor;

    /**
     * Initialise the population
     * 
     * @param NbIndividu total numebr of individuals
     * @param lab
     */
    public VotrePopulation(int NbIndividu, VotreLabyrinthe lab) {

        this.NbIndividu = NbIndividu;
        this.lab = lab;

        VotreIndividu Population[] = new VotreIndividu[NbIndividu];
        for (int i = 0; i < Population.length; i++) {
            Population[i] = new VotreIndividu(lab);
        }
        this.Population = Population;
    }

    @Override
    public int getSize() {
        return NbIndividu;
    }

    @Override
    public Individu get(int i) {
        System.out.print("coucou");
        return Population[i];
    }

    /**
     * Compute each individual score of the population
     * 
     * @return Population score
     */
    public double[] score() {

        double[] score = new double[NbIndividu];
        for (int i = 0; i < NbIndividu; i++) {
            int[] indexes = Population[i].InfoGenome();
            score[i] = Population[i].getScore(indexes[0], indexes[1]);
        }
        // scor = score;
        return score;
    }

    /**
     * Computes score of a specific individual
     * 
     * @param k index of the individual
     * @param i row index of the individual last valid square
     * @param j column index of the individual last valid square
     */
    public double score(int k, int i, int j) {
        return Population[k].getScore(i, j);
    }

    /**
     * <p>
     * Sort (by insertion) the population's individuals by their score in ascending
     * order
     * </p>
     * <p>
     * <i>Use hashmap instead</i>
     * </p>
     * 
     */
    public void sortByScore() {

        // int[] Position = new int[NbIndividu];
        double[] score = this.score();
        for (int i = 0; i < Population.length; i++) {
            for (int j = i + 1; j < Population.length; j++) {

                if (score[j] >= score[i]) {

                    double temp = score[j];
                    score[j] = score[i];
                    score[i] = temp;

                    VotreIndividu tmp = Population[j];
                    Population[j] = Population[i];
                    Population[i] = tmp;
                }
            }
        }
        // scor = score;

    }

    /**
     * Select the 50% best individuals of which 25% are consiedered the best and
     * the 75% remaining are taken randomly inside the population
     * 
     * @return population of the new individuals.
     */
    public void selection() {

        // VotreIndividu[] Populationbis = new VotreIndividu[NbIndividu / 2];

        this.sortByScore();

        // Sort the individuals
        // for (int i = 0; i < NbIndividu / 8; i++) {
        // // 25% des 50% d'individus doivent être choisis comme étant les meilleurs,
        // c'est-à-dire ceux ayant le meilleur score
        // Populationbis[i] = Population[i];
        // // on prend les individus ayant le meilleur score de tableau population
        // }

        // On selectionne ensuite 75% des individus restants en les prenant
        // aléatoirement dans la population initiale.
        for (int j = NbIndividu / 8; j < NbIndividu; j++) {
            int NombreAleatoire = (int) (NbIndividu / 8 + Math.random() * (NbIndividu - NbIndividu / 8));
            // (retourne un entier correspondant à la positions d'individus n'étant pas ceux
            // que nous avons sélectionnées comme étant les meilleurs dans l'étape juste
            // avant
            Population[j] = Population[NombreAleatoire];

        }
        // On retourne la nouvelle population Populationbis, après la sélection
        // effectuée sur l'ancienne population Population
        // return Populationbis;
    }

    // METHODE CROISEMENT (2EME ETAPE)
    /**
     * <p>Crossing the second half of the genome with two other genome.</p>
     * 
     * <p>Select a mother and a father among the population</p> 
     * 
     * @return
     */
    public void crossing() {
        // Méthode permettant de réaliser des croisement sur la Populationbis, pour
        // obtenir une nouvelle génération d'individus contenant Populationbis et leur
        // ddance provenant de croisements
        // VotreIndividu[] Populationterce = new VotreIndividu[NbIndividu];
        // VotreIndividu[] Populationbis = this.Selection();
        // ---------------
        // this.selection();
        // ---------------
        // On commence par ajouter tous les individus issus de la sélection dans la
        // liste d'individus Populationterce

        // for (int i = 0; i < Populationbis.length; i++) {
        //     Populationterce[i] = Populationbis[i];
        // }
        // Ensuite, on va réaliser des croisements entre deux individus au hasard parmi
        // les individus de Populationbis
        for (int j = NbIndividu / 2; j < NbIndividu; j++) {
            // On va compléter Populationterce en y ajoutant des individus issus de
            // croisements
            int ChoixPere = (int) (Math.random() * NbIndividu), 
                ChoixMere = (int) (Math.random() * NbIndividu);
            // On choisit un indice correspondant à la postition du Père dans Populationbis

            // On choisit un indice correspondant à la position de la Mère dans
            // Populationbis. On s'assure que ChoixMere et ChoixPere ne prennent pas la même
            // valeur, sinon on prend comme mère l'individu situé juste avant ou juste après
            // dans Populationbis
            if (ChoixPere == ChoixMere) {
                if (ChoixPere < NbIndividu - 1) {
                    ChoixMere += 1;
                } else {
                    ChoixMere -= 1;
                }
            }
            // Ensuite, comme on fait l'hypothèse qu'un couple de parent ne forme qu'un seul
            // enfant.
            // De ce fait, on va choisir pour chaque gène de l'enfant s'il provient de la
            // mère ou du père
            // Pour cela, on va creer un individu pour lequel on va affecter son génome avec
            // la méthode setGenome() et modifier chacun de ses gènes en le remplaçant soit
            // par celui de son père, soit par celui de sa mère
            VotreIndividu Enfant = new VotreIndividu(lab);
            // On créer un enfant grâce au constructeur par défaut. Son génome sera donc
            // quelconque et on va le modifier par la suite
            // -----------------
            // int[] nouveaugenome = Enfant.getGenome();
            // -----------------
            // On créer une liste nouveaugenome qui correspond au genome créer par le
            // constructeur. On va ensuite le modifier pour obtenir un génome issue de celui
            // du père et de la mère de l'enfant
            // Création du génome de l'enfant
            for (int k = 0; k < NbIndividu; k++) {
                // On choisit si le gène numéro k provient du père ou de la mère
                double ChoixPereouMere = Math.random();
                if (ChoixPereouMere < 0.5) {
                    Enfant.getGenome()[k] = Population[ChoixPere].getGenome()[k];
                    // Si ChoixPereouMere est dans [0,0.5[, le gène k proviendra du père
                } else {
                    Enfant.getGenome()[k] = Population[ChoixMere].getGenome()[k];// Sinon, le gène k proviendra de la mère
                }

            }
            Population[j].setGenome(Enfant.getGenome());
            // Populationterce[j] = Enfant;
            // On place l'enfant issue du Pere et de la Mere dans la liste des individus
            // Populationterce

        }
        // On retourne la liste des individus constituants la nouvelle population (suite
        // à la sélection et au croisement)
        // Population = Populationterce;
        // return Populationterce;
    }

    /*
     * MÉTHODE AVEC LE RETURN public Individu [] Mutation()
     */

    // METHODE MUTATION (DERNIÈRE ETAPE)

    /**
     * Select a small pourcentage of the population 
     */
    public void Mutation() {
        // Méthode permettant de réaliser des mutations sur 1% des invidividus provenant
        // de la nouvelle population
        // ----------
        // VotreIndividu[] Populationquat = this.Croisement();
        // ----------
        // On commence par récuperer la population issues de la sélection et du
        // croisement, pour pouvoir ensuite faire muter 1% de ses individus
        // Nous allons parcourir les gènes de chacun des individus de la population
        // Populationquat. Pour chaque gène on tire au sort un nombre entre 0 et 1. Si
        // sa valeur est inférieure au seuil de probabilité de mutation (1%), on change
        // la valeur de ce gène.

        for (int i = 0; i < NbIndividu; i++) {

            // int[] genome = Population[i].getGenome();
            // On obtient le génome de l'individu i
            for (int j = 0; j < NbIndividu; j++) {
                // On tire au sort un nombre dans [0;1[
                if (Math.random() < 0.01) {
                    // Si ce nombre est plus petit ou égal à 1%, on modifie le gène j de l'individu
                    // i
                    // Pour cela, on va tirer un nombre au hasard valant 0,1,2 ou 3.
                    // Si on tombe sur 0, le gène j prendra la valeur 1 qui correspond au Nord
                    // Si on tombe sur 1, le gène j prendra la valeur 2 qui correspond à l'Est
                    // Si on tombe sur 2, le gène j prendra la valeur 4 qui correspond au Sud
                    // Si on tombe sur 3, le gène j prendra la valeur 8 qui correspond à l'Ouest
                    // Random number in {0, 1, 2, 3} to select the orientation
                    int rnd = (int) (Math.random() * 4);
                    Population[i].getGenome()[j] = (int) Math.pow(2, rnd);
                    
                }
            }

        }
    }

    /**
     * Print every individuals score of the population
     
     */
    public void AfficherScore() {
        double max = 0;
        double[] score = this.score();
        for (int i = 0; i < Population.length; i++) {
            double p = Population[i].getDistance(Population[i].retourIndice()[0], Population[i].retourIndice()[1]);
            if (p > max)
                max = p;
        }
        System.out.println("Best score: " + max);
    }
}