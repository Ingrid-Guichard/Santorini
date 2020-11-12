/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuto;

/**
 *
 * @author ingrid
 */


public class plateau {
    
    int[][] pion = new int[5][5];
    int[][] terrain = new int[5][5];
    
    
    
 plateau (){
    
    // initialiser terraina zero terrain [][]= {0,0,0,0,0},
}
 

    public boolean presencePion (int ld , int cd, int joueurC ) {
        boolean presencePion;
        presencePion= false; 
        //if (pion [xd][yd]!=0){
            if (pion [ld][cd]==joueurC){
            presencePion=true; 
        return true;   
            }
        //}
        else 
            presencePion=false;
        return false;
            } 
    
    
               
    public boolean legaliteDeplacement (int ld, int cd, int la, int ca){
        boolean legalite; 
        legalite = false; 
        int voisin = (ca-cd)*(ca-cd)+(la-ld)*(la-ld);
        if (voisin<=2){
            legalite=true;                
            return true;
    }
        else 
        legalite= false;
        return false;
         }

    public boolean possibiliteDeplacement (int la, int ca, int ld, int cd){
        boolean deplacementPossible; 
        deplacementPossible=false; 
        if (legaliteDeplacement (ld, cd, la, ca)==true){
            if (caseLibre1(la,ca)==true){
                if (presenceDome1(la,ca)==false){
                    if (etageFranchit(ld, cd, la, ca)==true){
                    deplacementPossible=true; 
                    return true; 
            }
        }
        }
        }
        else {
            deplacementPossible=false;
            return false;
        }
        return false; 
    }

    public boolean etageFranchit (int la, int ca, int ld, int cd){
        //soit on monte 0 ou 1 etage soit on tombe de tous les étages
        boolean franchissement;
        franchissement=false; 
        if (terrain[ld][cd]==terrain[la][ca]-1 ||terrain[ld][cd]==terrain[la][ca]||terrain[ld][cd]>terrain[la][ca]){
            franchissement=true; 
            return true; 
        }
        else {
            franchissement=false; 
            return false; 
        }        
    }
    

    public boolean presenceDome1 (int la, int ca){
        boolean dome; 
        dome = false; 
        if (terrain[la][ca]!=4){
            dome= false; 
            return false; 
        }
        else {
            dome=true; 
            return true;
    }
    }
    
    public boolean caseLibre1 (int la, int ca){
        boolean caseLibre; 
        caseLibre=false; 
        if (pion[la][ca]==0){
            caseLibre=true; //donc pas de pion car egal a zero 
        return true; 
        }
        else{ 
        caseLibre=false; 
        return false;
        }
    }
    
    public boolean deplacement (int la, int ca, int ld, int cd){
        pion [la][ca]=pion [ld][cd];
        pion [ld][cd]=0;
        return true;
    }
    
    public boolean caseLibre2 (int lc, int cc){
         boolean caseLibre;
        caseLibre= false; 
        if (pion [lc][cc]!=0){
            caseLibre=false; 
            return false;   
            }
        else 
            caseLibre=true;
        return true;
            } 
    
    public boolean presenceDome2 (int lc, int cc){
        boolean dome; 
        dome = false; 
        if (terrain[lc][cc]!=4){
            dome= false; 
            return false; 
        }
        else {
            dome=true; 
            return true;
    }
    }
    
    
    
    public boolean caseConstructible (int lc, int cc){
        boolean caseConstructible; 
        caseConstructible=false; 
        if (caseLibre2(lc,cc)==true && presenceDome2(lc,cc)==false){
            caseConstructible=true;
            return true;
        }
        else 
            caseConstructible=false;
        return false;
    }
    
    public boolean construction (int lc,int cc){
        terrain[lc][cc]= terrain[lc][cc]+1;
        return true;
    }
    
    public boolean Victoire (int la, int ca){
        boolean winner; 
        winner= false; 
        if (terrain[la][ca]==3){
            winner= true; 
            return true;
        }
        else {
            winner = false; 
            return false;
        }
    }
                        
        }

