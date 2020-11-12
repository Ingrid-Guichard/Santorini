/*PROJET SANTORINI
 * GUICHARD Ingrid
 */
package tuto;

import java.util.Scanner;

/**
 *
 * @author ingrid
 */
public class SantoriniConsole {
    
    public SantoriniConsole (){
        System.out.println("Bienvenue sur le jeu Santorini");
        Scanner sc = new Scanner (System.in);
        System.out.println ("entrer le nombre de joueur (entre 2 et 4)");
        int nbJ = sc.nextInt();
        plateau Plateau = new plateau ();
        int joueurC=1;
        int ld, cd; //coordonnées départ
        int la, ca; //coordonnées arrivée
        int lc, cc; //coordonnées constructions
        System.out.println ("début de la partie");
        do{  
        do{
        
        Scanner sa = new Scanner (System.in);
        Scanner sb = new Scanner (System.in);
        System.out.println ("coordonnées du pion du joueur "+joueurC+" à déplacer (entre 0 et 4)");
         ld = sa.nextInt();
         cd = sb.nextInt();
         if (Plateau.presencePion(ld, cd, joueurC)==false){
             System.out.println ("coordonnées non valides");
        } 
        }
        while (Plateau.presencePion(ld, cd, joueurC)==false);
        
        do{
        Scanner sd = new Scanner (System.in);        
        Scanner se = new Scanner (System.in);
        System.out.println ("coordonnées de destination du pion (entre 0 et 4) ");        
        la = sd.nextInt();
        ca = se.nextInt();
        if (Plateau.possibiliteDeplacement(la, ca, ld, cd)== false){
                System.out.println ("coordonnées non valides");
        }
        
        }
        while (Plateau.possibiliteDeplacement(la, ca, ld, cd)== false);
        
        //deplacement du pion 
        Plateau.deplacement(la, ca, ld, cd);
        
        do{
            Scanner sf = new Scanner (System.in);
            Scanner sg = new Scanner (System.in);
            System.out.println("coordonnées de la construction (entre 0 et 4)");
            lc= sf.nextInt();
            cc= sg.nextInt();
            if (Plateau.caseConstructible(lc, cc)== false){
                System.out.println("coordonnées non valides");
            }
        }
        while (Plateau.caseConstructible(lc, cc)== false);
        
        //construction d'un étage
        Plateau.construction(lc, cc);
        
        if (Plateau.Victoire(la, ca)==true){
            System.out.println("le joueur" + joueurC + "à gagné la partie");
        }
        
                    
        joueurC ++;
        if (joueurC > (nbJ)) {       
            joueurC=1;
        }
        }
        while (true);// personne n'a gagné 
        }
        
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SantoriniConsole console = new SantoriniConsole();
    }
    
}
