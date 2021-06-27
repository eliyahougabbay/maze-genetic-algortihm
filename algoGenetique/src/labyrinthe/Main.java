/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthe;

import java.util.Scanner;


/**
 *
 * @author GABBAY
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*Scanner sc = new Scanner(System.in);
        System.out.print("Le nombre choisi est : "+sc);*/
        
        /*TEST VotreLabyrinthe
        Ces instructions suivantes permettent d'afficher un labyritnhe de taille 10x10 */
        Fenetre fen = new Fenetre();
        VotreLabyrinthe parcours = new VotreLabyrinthe(10,10);
        fen.setLabyrinthe(parcours);
        
       
        
        /*TEST VotrePopulation
        int nl=5,nc=5;
        VotreLabyrinthe lab= new VotreLabyrinthe(nl,nc);
        lab.CreerLabyrinte();
        VotreIndividu ind = new VotreIndividu(lab);
        ind.afficher();
        int[] liste = ind.InfoGenome();
        System.out.println("Score : "+ind.getScore(liste[0], liste[1]));
        /*VotrePopulation pop=new VotrePopulation(1,nl,nc,lab);
        pop.Afficher();
        System.out.print("\nAprès selection :");
        pop.Selection();
        pop.Afficher();
        System.out.print("\n");*/
        
       
        //TEST VotreIndividu
        int nl=5,nc=5;
        /*Creer un labyrinthe de taille nl x nc puis créer une population de genome la taille du labyrinthe */
        VotreLabyrinthe lab = new VotreLabyrinthe(nl,nc);
        VotrePopulation pop = new VotrePopulation(25,  lab);
        /* Affiche la populaton en code Nord, Est, Sud, Ouest */
        pop.Afficher();
        /* Puis on affiche le score et la distance de chaque individu */
        pop.AfficherScore();
        /*La fonction Mutation devrait s'appeler PorchaineGeneration car elle sert à faire
        évoluer une population */
        for (int k = 0; k<1000;k++){
        pop.Mutation();
        }
        pop.Afficher();
        pop.AfficherScore();
        
        /*
        //pop.tri();
        //pop.Afficher();
        */
        
    }
    
}
