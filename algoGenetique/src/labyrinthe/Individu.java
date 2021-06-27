package labyrinthe;

/**
 * Interface que doit implémenter tout objet représentant un individu de la
 * population.
 * <p>
 * Un individu dispose d'un génome composé d'un tableau d'entiers, dont chaque
 * élément représente la direction à prendre pour atteindre la case suivante.
 * </p>
 * <p>
 * Le génome est initialement défini aléatoirement. Par la suite il évolue en
 * fonction de règles génétiques (sélection, croisement, mutation) et
 * d'éventuelles optimisations. D'une manière generale, seul le début du génome
 * représente une portion de chemin valide sur le labyrinthe. La limite de cette
 * portion indique l'indice du premier gène ne pouvant correspondre à une
 * case valide du labyrinthe (franchissement d'un mur).
 * </p>
 * Voir aussi {@link Population}, {@link Labyrinthe} et {@link Fenetre}.
 * <br>
 */
public interface Individu {

    /**
     * Renvoie le génome de l'individu.
     *
     * @return son génome.
     */
    public int[] getGenome();

    /**
     * Affecte un nouveau genome à l'individu.
     *
     * @param genome le nouveau genome.
     */
    public void setGenome(int[] genome);

    /**
     * Renvoie la limite du génome actuel.
     *
     * @return l'indice du premier gene correspondant à une case non valide.
     */
    public int getLimite();
    /**
     * Affecte la valeur limite.
     * @param lim l'indice dans le génome du premier gene non valide.
     */
    public void setLimite(int lim);
}
