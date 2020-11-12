/*TP santorini
 *GUENOUN Ava 
 *GUICHARD Ingrid
 */
package santorini.console;

/**
 *
 * @author ingrid 
 */


public class plateau {
    
    int[][] pion = new int[5][5];
    int[][] terrain = new int[5][5];
    
    
    
 plateau (){
    pion [0][0]=1; 
    pion [1][1]=1;
    pion [2][1]=2;
    pion [0][2]=2;
    pion [0][3]=3;
    pion [3][0]=3;
}
 

    public boolean presencePion (int ld , int cd, int joueurC ) { 
        boolean presencePion;       
        presencePion= false; 
        if (pion [ld][cd]==joueurC){
            presencePion=true; 
        return true;   
            }
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
        else {
        legalite= false;
        return false;
        }
         }

    public boolean possibiliteDeplacement (int la, int ca, int ld, int cd/*, enum Pouvoir, int joueurC, String pouvoirJoueurs[]*/){
        boolean deplacementPossible; 
        deplacementPossible=false; 
        if (legaliteDeplacement (ld, cd, la, ca)==true){
            if (caseLibre1(la,ca/* ,pouvoir, joueurC, pouvoirJoueurs*/)==true){
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
        if (terrain[la][ca]!=4){ //avec 4 qui correpsond à la présence d'un dome 
            dome= false; 
            return false; 
        }
        else {
            dome=true; 
            return true;
        }
    }
    
    public boolean caseLibre1 (int la, int ca/*, enum Pouvoir, int joueurC, String pouvoirJoueurs[]*/){
        boolean caseLibre; 
        caseLibre=false; 
         
        if (pion[la][ca]==0){ /*|| pouvoirJoueurs[joueurC-1].equals(Pouvoir.APPOLON))){*/
            caseLibre=true; //donc pas de pion car egal a zero ou toutes les cases sont disponibles si appolon
            return true; 
        }
        else { 
        caseLibre=false; 
        return false;
        }
    }
    
    
    public boolean deplacement (int la, int ca, int ld, int cd /*, enum Pouvoir, int joueurC, String pouvoirJoueurs[]*/){
        
        /*if (pouvoirJoueurs[joueurC-1].equals(Pouvoir.APPOLON)){
            int x = pion [la][ca]
            pion [la][ca] = pion [ld][cd]
            pion [ld][cd] = x;
            
            else { */
                pion [la][ca] = pion [ld][cd];
                pion [ld][cd]=0;
            //}
                return true;
    }
        
    
    public boolean caseLibre2 (int lc, int cc){
         boolean caseLibre;
        caseLibre= false; 
        if ( pion [lc][cc]!=0){
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
    
    
    
    public boolean caseConstructible (int lc, int cc/*, enum Pouvoir, int joueurC, String pouvoirJoueurs[]*/){
        boolean caseConstructible; 
        caseConstructible=false; 
        /*if (caseLibre2(lc,cc)==true && presenceDome2(lc,cc)==false && if (pouvoirJoueurs[joueurC-1].equals(Pouvoir.HEPHAISTOS)){ 
                   if (terrain [lc][cc] <= 2 && pouvheph = 2){
                   caseConstructible=true;
                   return true;
                }
                else {
                    caseConstructible = false;
                    return false;
                }
            }            
            */            
        /*else*/if (caseLibre2(lc,cc)==true && presenceDome2(lc,cc)==false) {
        
            caseConstructible=true;
            return true;
        }
        else{ 
            caseConstructible=false;
            return false;
        }
    }
    
    public boolean construction (int lc,int cc/*, enum Pouvoir, int joueurC, String pouvoirJoeurs[], int pouvheph */){
        
        /*if (pouvoirJoueurs[joueurC-1].equals(Pouvoir.HEPHAISTOS)){
            if (pouvheph = 2)
                terrain [lc][cc] = terrain [lc][cc] +2;
            else 
                terrain {lc][cc] = terrain [lc][cc] +1;
        }
        */
        
        /*else*/terrain[lc][cc]= terrain[lc][cc]+1;
        return true;
    }
    
    public boolean Victoire (int la, int ca/*, enum Pouvoir, int joueurC, String pouvoirJoeurs[] */){
        boolean winner; 
        winner= false; 
        
        /*if (pouvoirJoueurs[joueurC-1].equals(Pouvoir.PAN)){
            if (terrain [la][ca] == terrain [ld][cd]-2){
                winner = true; 
                return true;
        }
            */
        
        /*else*/if (terrain[la][ca]==3){
            winner= true; 
            return true;
        }
        
            
        else {
            winner = false; 
            return false;
        }
        
    }
}
                          