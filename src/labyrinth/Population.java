package labyrinth;

/**
 * Votre Population doit implémenter cette interface. Elle est composée
 * d'Individus (ou plus exactement de classes implémentant l'interface
 * Individu). Une population doit ranger les individus en fonction de leur
 * score, du meilleur score au plus faible score.
 */
public interface Population {

    /**
     * Renvoie le nombre d'individus constituant la population.
     *
     * @return le nombre d'individus.
     */
    public int getSize();

    /**
     * Renvoie l'Individu de rang i.
     *
     * @param i le rang de l'individu cherché.
     * @return l'individu correspondant.
     */
    public Individu get(int i);
}
