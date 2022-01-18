package labyrinth;

/**
 * Un labyrinthe est un tableau de cases limitées par des murs.
 * <p>
 * Il est muni d'une case de départ située sur le coté gauche, et d'une case
 * d'arrivée située sur le coté droit. Seul l'indice des lignes est nécessaire
 * pour repérer ces différentes cases.
 * </p>
 * <p>
 * Une case est définie par un entier contenant la somme des valeurs suivantes :
 * <ul>
 * <li>0 si pas de murs,</li>
 * <li>1 si mur au Nord,</li>
 * <li>2 si mur à l'Est,</li>
 * <li>4 si mur au Sud.</li>
 * <li>8 si mur à l'Ouest</li>
 * </ul>
 * </p>
 * <p>
 * Les labyrinthes générés doivent avoir obligatoirement au moins un chemin
 * menant de l'entrée à la sortie.
 * </p>
 * <p>
 * Les méthodes de cette interface sont nécessaires pour qu'un labyrinthe soit
 * utilisable avec l'interface graphique. Le labyrinthe doit donc appeler les
 * méthodes suivantes de Fenetre :
 * <ul>
 * <li>setLabyrinthe(labyrinthe lab) : affecte le labyrinthe utilisé.<br>
 * Doit être appelée en début de programme, avant toute autre action.</li>
 * <li>dessinerLabyrinthe() : dessine les contours du labyrinthe (cases avec des
 * murs) et colorie les cases de départ d'arrivée. <br>
 * Elle doit être appelée à la fin de la génération d'un labyrinthe</li>
 * <li>afficher(Individu indiv) : affiche la portion valide de trajet
 * représentée par les premiers gènes de cet individu. Cette portion s'arrête
 * sur le gêne dont l'indice est indiqué par la limite.<br>
 * Cette méthode doit être appelée à la fin de chaque nouvelle génération de
 * population, de préférence en transmettant comme individu celui qui a le
 * meilleur score (fitness).</li>
 * <li>afficher(Population po) : affiche l'intégralité de la population. En gris
 * les chemins de score moins probants, en jaune et par dessus les autres le
 * chemin de meilleur score.</li>
 * </ul>
 * </p>
 * <p>
 * Le mécanisme d'affichage peut utiliser 2 codages de gênes possibles. La
 * labyrinthe doit préciser le type de codage utilisé, à l'aide des constantes
 * statiques suivantes, dont la connaissance est nécessaire pour un affichage
 * correct :
 * <ul>
 * <li>ORIENTATION : chaque gêne code une orientation dans l'espace avec la
 * convention suivante : 1 Nord, 2 Est, 4 Sud, 8 Ouest.</li>
 * <li>DIRECTION : chaque gêne indique la direction à prendre par rapport à
 * celle d'où l'on vient : 1 à gauche, 2 tout droit, 3 à droite.</li>
 * </ul>
 * </p>
 * Voir aussi {@link Dessin}, {@link Fenetre} et {@link Individu}.
 *
 */
public interface Labyrinthe {

    /**
     * définition du codage des genes en fonction de l'orientation.
     */
    public static final int ORIENTATION = 1;
    /**
     * définition du codage des genes en fonction de la direction.
     */
    public static final int DIRECTION = 2;

    /**
     * Demande la génération d'un labyrinthe d'une taille donnée. Cette méthode
     * doit construire le labyrinthe avec au moins un chemin conduisant à la
     * sortie et définir les indices des lignes des cases de départ et
     * d'arrivée.
     *
     * @param nl nombre de lignes.
     * @param nc nombre de colonnes.
     */
    public void generer(int nl, int nc);

    /**
     * Affecte la fenêtre sur laquelle ce labyrinthe doit se dessiner. Cette
     * méthode est automatiquement appelée par Fenetre lorsque celle-ci connait
     * le labyrinthe utilisé.
     *
     * @param fen la fenêtre.
     */
    public void setFenetre(Fenetre fen);

    /**
     * Renvoie le nombre de lignes du labyrinthe en cours.
     *
     * @return le nombre de lignes.
     */
    public int getNbLignes();

    /**
     * Renvoie le nombre de colonnes du labyrinthe en cours.
     *
     * @return le nombre de colonnes.
     */
    public int getNbCols();

    /**
     * Renvoie la valeur de la case du labyrinthe indiquée par ses coordonnées.
     * Cette valeur peut-être le somme des valeurs suivantes : 1 (mur au Nord),
     * 2 (mur à l'Est), 4 mur au Sud, 8 mur à l'Ouest.
     *
     * @param nl numéro de ligne.
     * @param nc numéro de colonne.
     * @return la valeur de la case indiquée.
     */
    public int getCellule(int nl, int nc);

    /**
     * Renvoie le type de codage utilisé (ORIENTATION ou DIRECTION).
     *
     * @return le type de codage.
     */
    public int getType();

    /**
     * Renvoie l'indice en ligne de la case de départ.
     *
     * @return le numéro de ligne de cette case.
     */
    public int getDepart();

    /**
     * Renvoie l'indice en ligne de la case d'arrivée.
     *
     * @return le numéro de ligne de cette case.
     */
    public int getArrivee();

    /**
     * Print lab values
     */
    public void printLab();

    /**
     * Déclenche la recherche du chemin en utilisant le labyrinthe actuel.
     * <p>
     * Cette méthode suppose que la génération du labyrinthe a été faite avec
     * les dimensions indiquées, et que les valeurs de la taille de la
     * population et du nombre maximal de générations ont été correctement
     * indiquées.
     * </p>
     * <p>
     * Elle précise la taille souhaitée de la population, en nombre d'individus,
     * ainsi que le nombre maximal de générations souhaitées. Le logiciel doit
     * s'arrêter au bout de ce nombre de générations, même si le chamin n'a pas
     * été trouvé.
     *
     * @param npop le nombre d'individus dans la population.
     * @param ngen le nombre maximal de générations de la population.
     */
    public void evoluer(int npop, int ngen);
}
