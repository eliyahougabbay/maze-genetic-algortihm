/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;


/**
 *
 * @author GABBAY
 */
public class Main {


    public static void main(String[] args) {
    

        /*
         * TEST VotreLabyrinthe Ces instructions suivantes permettent d'afficher un
         * labyritnhe de taille 10x10
         */
        
        /**
         * Creer le Labyrinthe
         */
        Fenetre fen = new Fenetre();
        VotreLabyrinthe parcours = new VotreLabyrinthe(15, 15);
        fen.setLabyrinthe(parcours);

       

        // // TEST VotreIndividu
        // int nl = 12, nc = 12;
        // /*
        //  * Creer un labyrinthe de taille nl x nc puis créer une population de genome la
        //  * taille du labyrinthe
        //  */

        // VotreLabyrinthe lab = new VotreLabyrinthe(nl, nc);
        // VotrePopulation pop = new VotrePopulation(25, lab);
        // /* Affiche la populaton en code Nord, Est, Sud, Ouest */
        // // pop.Afficher();
        // /* Puis on affiche le score et la distance de chaque individu */
        // pop.AfficherScore();
        // /*
        //  * La fonction Mutation devrait s'appeler PorchaineGeneration car elle sert à
        //  * faire évoluer une population
        //  */
        // for (int k = 0; k < 10000; k++) {
        //     pop.Mutation();
        // }
        // System.out.println("Finished");
        // // pop.Afficher();
        // pop.AfficherScore();

        /*
         * //pop.tri(); //pop.Afficher();
         */

    }

}
