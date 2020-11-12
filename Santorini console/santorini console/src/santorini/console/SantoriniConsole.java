/*PROJET SANTORINI
 *GUENOUN Ava 
 *GUICHARD Ingrid
 */
package santorini.console;

import java.util.Scanner;

/**
 *
 * @author ingrid
 */
public class SantoriniConsole {
    
    enum Pouvoir {APPOLON, HEPHAISTOS, PAN};
    
    public SantoriniConsole (){
        
        
        
        System.out.println("Bienvenue sur le jeu Santorini");
        Scanner sc = new Scanner (System.in);
        System.out.println ("entrer le nombre de joueur (entre 2 et 4)");
        int nbJ = sc.nextInt();
        plateau Plateau = new plateau ();
        
                
        Pouvoir[] pouvoirJoueurs= new Pouvoir[nbJ];
        
        for(int j=0;j < nbJ; j++) {
        System.out.println (" entrer le pouvoir associer au joueur " + j+1);
        
        for(int i = 0; i < Pouvoir.values().length; i++) {
            System.out.println( i + " - " + Pouvoir.values()[i]);
        }
        
        int pouvoir = sc.nextInt();
        pouvoirJoueurs[j] = Pouvoir.values()[pouvoir];
       }
        
        int joueurC=1;
        int ld, cd; //coordonnées départ
        int la, ca; //coordonnées arrivée
        int lc, cc; //coordonnées constructions
        /*int pouvheph; //pouvoir de hephaistos construire 1 ou deuc étages */
        System.out.println ("début de la partie"); 
        do{  
        do{

            System.out.println("Votre pouvoir est " + pouvoirJoueurs[joueurC-1]);
            /*if(pouvoirJoueurs[joueurC-1].equals(Pouvoir.APPOLON)) {
                System.out.println("Vous avez appolon :)");
            }*/
            
        System.out.println ("coordonnées du pion du joueur " + joueurC + " à déplacer (entre 0 et 4)");
         ld = sc.nextInt();
         cd = sc.nextInt();
         if (Plateau.presencePion(ld, cd, joueurC)==false){
             System.out.println ("coordonnées non valides");
        } 
        }
        while (Plateau.presencePion(ld, cd, joueurC)==false);
        
        do{

        System.out.println ("coordonnées de destination du pion (entre 0 et 4) ");        
        la = sc.nextInt();
        ca = sc.nextInt();
        if (Plateau.possibiliteDeplacement(la, ca, ld, cd/*, Pouvoir, joueurC, pouvoirJoueurs[]*/)== false){
                System.out.println ("coordonnées non valides");
        }
        
        }
        while (Plateau.possibiliteDeplacement(la, ca, ld, cd/*, Pouvoir, joueurC, pouvoirJoueurs[]*/)== false);
        
        //deplacement du pion 
        Plateau.deplacement(la, ca, ld, cd/*, Pouvoir, joueurC, pouvoirJoueurs[]*/);
        
        
        do{

            System.out.println("coordonnées de la construction (entre 0 et 4)");          
            lc= sc.nextInt();
            cc= sc.nextInt();
            /* if (pouvoirJoueurs[joueurC-1].equals(Pouvoir.HEPHAISTOS)){
                System.out.println("voulez vous construire 1 ou 2 étages?");
                pouvheph = sc.nextInt();
            }
            */
            if (Plateau.caseConstructible(lc, cc/*, Pouvoir, joueurC, pouvoirJoueurs[]*/)== false){
                System.out.println("coordonnées non valides");
            }
        }
        while (Plateau.caseConstructible(lc, cc/*, Pouvoir, joueurC, pouvoirJoueurs[]*/)== false);
        
        //construction d'un étage
        
        Plateau.construction(lc, cc/*, Pouvoir, joueurC, pouvoirJoueurs[]*/);
        
        if (Plateau.Victoire(la, ca)==true){
            System.out.println("le joueur" + joueurC + "est le winner");
        }
        
                    
        joueurC ++;
        if (joueurC > (nbJ)) {       
            joueurC=1;
        }
        }
        while (Plateau.Victoire(la,ca/*, Pouvoir, joueurC, pouvoirJoueurs[]*/) == false);// personne n'a gagné 
        }
        
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SantoriniConsole console = new SantoriniConsole();
    }
    
}
