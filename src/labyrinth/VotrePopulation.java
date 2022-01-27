package labyrinth;

import java.net.CacheRequest;

import javax.print.event.PrintJobListener;
import javax.xml.catalog.CatalogException;

/**
 * A class to generate & sorts the individuals by their score inside the
 * population. This class also evolves the population by
 * 
 * @author GABBAY
 */
public class VotrePopulation implements Population {

    public int instances = 0;

    private int nbIndividu;
    // private VotreIndividu Ind;
    private VotreIndividu[] population;
    // private int NbLignes,NbColonnes;
    private VotreLabyrinthe lab;
    // private double[] scor;

    /**
     * Initialise the population
     * 
     * @param nbIndividu total numebr of individuals
     * @param lab        the labyritnh
     */
    public VotrePopulation(int nbIndividu, VotreLabyrinthe lab) {

        instances++;

        this.nbIndividu = nbIndividu;
        this.lab = lab;

        VotreIndividu population[] = new VotreIndividu[nbIndividu];
        for (int i = 0; i < population.length; i++) {
            population[i] = new VotreIndividu(lab);
        }
        this.population = population;
    }

    @Override
    public int getSize() {
        return nbIndividu;
    }

    @Override
    public Individu get(int i) {
        return population[i];
    }

    /**
     * Computes each individual score of the population
     * 
     * @return population score
     */
    public double[] score() {

        double[] score = new double[nbIndividu];
        for (int i = 0; i < nbIndividu; i++) {
            int[] indexes = population[i].InfoGenome();
            score[i] = population[i].getScore(indexes[0], indexes[1]);
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
        return population[k].getScore(i, j);
    }

    /**
     * <p>
     * Sorts (by insertion) the population's individuals by their score in ascending
     * order
     * </p>
     * <p>
     * <i>Mayber use hashmap instead</i>
     * </p>
     * 
     */
    public void sortByScore() {

        // int[] Position = new int[nbIndividu];
        double[] score = this.score();
        for (int i = 0; i < population.length; i++) {
            for (int j = i + 1; j < population.length; j++) {

                if (score[j] >= score[i]) {

                    double temp = score[j];
                    score[j] = score[i];
                    score[i] = temp;

                    VotreIndividu tmp = population[j];
                    population[j] = population[i];
                    population[i] = tmp;
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

        // VotreIndividu[] populationbis = new VotreIndividu[nbIndividu / 2];
        try {

            this.sortByScore();

            

            // Sort the individuals
            // for (int i = 0; i < nbIndividu / 8; i++) {
            // // 25% des 50% d'individus doivent être choisis comme étant les meilleurs,
            // c'est-à-dire ceux ayant le meilleur score
            // populationbis[i] = population[i];
            // // on prend les individus ayant le meilleur score de tableau population
            // }
    
            // On selectionne ensuite 75% des individus restants en les prenant
            // aléatoirement dans la population initiale.
            for (int j = nbIndividu / 8; j < nbIndividu; j++) {
                int NombreAleatoire = (int) (nbIndividu / 8 + Math.random() * (nbIndividu - nbIndividu / 8));
                // (retourne un entier correspondant à la positions d'individus n'étant pas ceux
                // que nous avons sélectionnées comme étant les meilleurs dans l'étape juste
                // avant
                population[j] = population[NombreAleatoire];
    
            } 
        } catch (Exception e) {
            System.err.println(this.instances);
        }
        // On retourne la nouvelle population populationbis, après la sélection
        // effectuée sur l'ancienne population population
        // return populationbis;
    }

    // METHODE CROISEMENT (2EME ETAPE)
    /**
     * <p>
     * Crossing the second half of the genome with two other genome.
     * </p>
     * 
     * <p>
     * Select a mother and a father among the population
     * </p>
     * 
     * @return
     */
    public void crossing() {
        // Méthode permettant de réaliser des croisement sur la populationbis, pour
        // obtenir une nouvelle génération d'individus contenant populationbis et leur
        // ddance provenant de croisements
        // VotreIndividu[] populationterce = new VotreIndividu[nbIndividu];
        // VotreIndividu[] populationbis = this.Selection();
        // ---------------
        // this.selection();
        // ---------------
        // On commence par ajouter tous les individus issus de la sélection dans la
        // liste d'individus populationterce

        // for (int i = 0; i < populationbis.length; i++) {
        // populationterce[i] = populationbis[i];
        // }
        // Ensuite, on va réaliser des croisements entre deux individus au hasard parmi
        // les individus de populationbis

        for (int j = nbIndividu / 2; j < nbIndividu; j++) {
            // On va compléter populationterce en y ajoutant des individus issus de
            // croisements
            int ChoixPere = (int) (Math.random() * nbIndividu),
                    ChoixMere = (int) (Math.random() * nbIndividu);
            // On choisit un indice correspondant à la postition du Père dans populationbis

            // On choisit un indice correspondant à la position de la Mère dans
            // populationbis. On s'assure que ChoixMere et ChoixPere ne prennent pas la même
            // valeur, sinon on prend comme mère l'individu situé juste avant ou juste après
            // dans populationbis
            if (ChoixPere == ChoixMere) {
                if (ChoixPere < nbIndividu - 1) {
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
         
            for (int k = 0; k < nbIndividu; k++) {
                // On choisit si le gène numéro k provient du père ou de la mère
                double ChoixPereouMere = Math.random();
                if (ChoixPereouMere < 0.5) {
                    Enfant.getGenome()[k] = population[ChoixPere].getGenome()[k];
                    // Si ChoixPereouMere est dans [0,0.5[, le gène k proviendra du père
                } else {
                    Enfant.getGenome()[k] = population[ChoixMere].getGenome()[k];// Sinon, le gène k proviendra de la
                                                                                 // mère
                }

            }
            population[j].setGenome(Enfant.getGenome());
            // populationterce[j] = Enfant;
            // On place l'enfant issue du Pere et de la Mere dans la liste des individus
            // populationterce

        }
        // On retourne la liste des individus constituants la nouvelle population (suite
        // à la sélection et au croisement)
        // population = populationterce;
        // return populationterce;
    }

    /*
     * MÉTHODE AVEC LE RETURN public Individu [] Mutation()
     */

    // METHODE MUTATION (DERNIÈRE ETAPE)

    /**
     * Select a small pourcentage of the population
     */
    public void mutation() {
        // Méthode permettant de réaliser des mutations sur 1% des invidividus provenant
        // de la nouvelle population
        // ----------
        // VotreIndividu[] populationquat = this.Croisement();
        // ----------
        // On commence par récuperer la population issues de la sélection et du
        // croisement, pour pouvoir ensuite faire muter 1% de ses individus
        // Nous allons parcourir les gènes de chacun des individus de la population
        // populationquat. Pour chaque gène on tire au sort un nombre entre 0 et 1. Si
        // sa valeur est inférieure au seuil de probabilité de mutation (1%), on change
        // la valeur de ce gène.

        for (int i = 0; i < nbIndividu; i++) {

            // int[] genome = population[i].getGenome();
            // On obtient le génome de l'individu i
            for (int j = 0; j < nbIndividu; j++) {
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
                    population[i].getGenome()[j] = (int) Math.pow(2, rnd);

                }
            }

        }
    }

    /**
     * Print every individuals score of the population
     * 
     */
    public void AfficherScore() {
        double max = 0;
        for (int i = 0; i < population.length; i++) {
            double p = population[i].getDistance(population[i].retourIndice()[0], population[i].retourIndice()[1]);
            if (p > max)
                max = p;
        }
        System.out.println("Best score: " + max);
    }
}