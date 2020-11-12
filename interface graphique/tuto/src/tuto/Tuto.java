/*
interface graphique 
 */
package tuto;

import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.GL_LINES;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author gademer
 */
public class Tuto {

    enum Etat {
        PLACEMENT_PION_J1, PLACEMENT_PION_J2, J1_MOUVEMENT_DEPART, J1_MOUVEMENT_ARRIVEE, J2_MOUVEMENT_DEPART, J2_MOUVEMENT_ARRIVEE, J1_CONSTRUCTION, J2_CONSTRUCTION;
    };

    static Etat etatcourant = Etat.PLACEMENT_PION_J1;

    // Nous allons créer une variable angleRotation
    static float angleRotation1 = 0;
    static float angleRotation2 = 0;
    // Nous la créons static pour qu'elle puisse être accessible depuis toutes les méthodes

    static float azimut = 10;
    static float elevation = 45; // en °
    static float radius = 10; // Eloignement de la caméra = rayon de la sphère
    static int ligneselecteur, colonneselecteur, ld, cd, lc, cc;
    static int nbpions = 0;
    static plateau plat = new plateau();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Santorini player");
        frame.setPreferredSize(new Dimension(900, 800)); //Taille fenetre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel label1 = new JLabel("You are playing... Good luck!"); //pour afficher texte non editable
        label1.setBorder(BorderFactory.createTitledBorder(null, "SANTORINI", TitledBorder.TOP, TitledBorder.TOP, new Font("Cosmic", Font.BOLD, 28), Color.blue));
        frame.add(label1, BorderLayout.NORTH); //positionnement du 1er label

        // on ajoute une fenetre au centre dans la fenetre principale:
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createTitledBorder(null, "Plateau de jeu"));
        frame.add(centerPanel, BorderLayout.CENTER);

        // on ajoute une interface 3D (ne pas oublier d'ajouter la library jar)
        GLJPanel panel = new GLJPanel();
        centerPanel.add(panel);
        panel.setPreferredSize(new Dimension(700, 600));

        GLEventListener openGLListener;
        openGLListener = new GLEventListener() {

            GLU glu;

            // mouvement caméra:            
            private void resetView(GL2 gl) {
                // positionner notre caméra (notre point de vue)
                gl.glMatrixMode(GL2ES1.GL_PROJECTION); // On passe en mode "projection"
                gl.glLoadIdentity(); // On charge la matrice identité (par défaut)
                glu.gluPerspective(45.0, 800.0 / 600, 0.1, 100);

                gl.glMatrixMode(GL2ES1.GL_MODELVIEW); // On repasse en mode "modèle"
                gl.glLoadIdentity();

                float posX = (float) (radius * Math.sin(Math.toRadians(elevation)) * Math.cos(Math.toRadians(azimut)));
                float posY = (float) (radius * Math.sin(Math.toRadians(elevation)) * Math.sin(Math.toRadians(azimut)));
                float posZ = (float) (radius * Math.cos(Math.toRadians(elevation)));

                glu.gluLookAt(posX, posY, posZ, 0, 0, 0, 0, 0, 1); // On positionne la caméra
            }

            private void drawSphere(GL2 gl, float[] position, float[] rotation, float radius, float[] color) {

                //repositionner la caméra "comme au début".
                resetView(gl);

                gl.glTranslatef(position[0], position[1], position[2]);// Translation
                gl.glRotatef(rotation[0], 1.0f, 0.0f, 0.0f); // Rotation selon l'axe x
                gl.glRotatef(rotation[1], 0.0f, 1.0f, 0.0f); // Rotation selon l'axe y
                gl.glRotatef(rotation[2], 0.0f, 1.0f, 0.0f); // Rotation selon l'axe z

                gl.glColor3fv(color, 0); // Couleur choisie

                GLUquadric quad = glu.gluNewQuadric();
                glu.gluSphere(quad, radius, 10, 15);
                glu.gluDeleteQuadric(quad);

            }

            private void drawCube(GL2 gl, float[] position, float[] rotation, float[] size, float[] color) {

                //repositionner la caméra "comme au début".
                resetView(gl);

                gl.glTranslatef(position[0], position[1], position[2]);// Translation
                gl.glRotatef(rotation[0], 1.0f, 0.0f, 0.0f); // Rotation selon l'axe x
                gl.glRotatef(rotation[1], 0.0f, 1.0f, 0.0f); // Rotation selon l'axe y
                gl.glRotatef(rotation[2], 0.0f, 1.0f, 0.0f); // Rotation selon l'axe z

                gl.glBegin(GL_QUADS); //afficher des carrés

                float[] bottomLeftRear = {-size[0] / 2, -size[1] / 2, -size[2] / 2};
                float[] bottomRightRear = {size[0] / 2, -size[1] / 2, -size[2] / 2};
                float[] topRightRear = {size[0] / 2, size[1] / 2, -size[2] / 2};
                float[] topLeftRear = {-size[0] / 2, size[1] / 2, -size[2] / 2};
                float[] bottomLeftFront = {-size[0] / 2, -size[1] / 2, size[2] / 2};
                float[] bottomRightFront = {size[0] / 2, -size[1] / 2, size[2] / 2};
                float[] topRightFront = {size[0] / 2, size[1] / 2, size[2] / 2};
                float[] topLeftFront = {-size[0] / 2, size[1] / 2, size[2] / 2};

                if (color != null) {
                    gl.glColor3fv(color, 0); // Couleur choisie
                }

                // Face du dessus
                if (color == null) {
                    gl.glColor3f(0.0f, 1.0f, 0.0f); // Vert
                }
                gl.glVertex3fv(topRightRear, 0);
                gl.glVertex3fv(topLeftRear, 0);
                gl.glVertex3fv(topLeftFront, 0);
                gl.glVertex3fv(topRightFront, 0);

                // Face du dessous
                if (color == null) {
                    gl.glColor3f(1.0f, 0.5f, 0.0f); // orange
                }
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomRightFront, 0);
                gl.glVertex3fv(bottomLeftFront, 0);

                // Face de devant
                if (color == null) {
                    gl.glColor3f(1.0f, 0.0f, 0.0f); // rouge
                }
                gl.glVertex3fv(topRightFront, 0);
                gl.glVertex3fv(topLeftFront, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(bottomRightFront, 0);

                // Face de derrière
                if (color == null) {
                    gl.glColor3f(1.0f, 1.0f, 0.0f); // jaune
                }
                gl.glVertex3fv(topLeftRear, 0);
                gl.glVertex3fv(topRightRear, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomLeftRear, 0);

                // Face de gauche
                if (color == null) {
                    gl.glColor3f(0.0f, 0.0f, 1.0f); // bleu
                }
                gl.glVertex3fv(topLeftRear, 0);
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(topLeftFront, 0);

                // Face de droite
                if (color == null) {
                    gl.glColor3f(1.0f, 0.0f, 1.0f); // magenta
                }
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(topRightRear, 0);
                gl.glVertex3fv(topRightFront, 0);
                gl.glVertex3fv(bottomRightFront, 0);

                gl.glEnd(); // Fin du cube

                // On désire aussi afficher les arrêtes pour plus de visibilité
                gl.glColor3f(0.0f, 0.0f, 0.0f); // black
                gl.glBegin(GL_LINES); // Cette fois nous allons passer des lignes

                // Carré de devant
                gl.glVertex3fv(topLeftFront, 0);
                gl.glVertex3fv(topRightFront, 0);
                gl.glVertex3fv(topRightFront, 0);
                gl.glVertex3fv(bottomRightFront, 0);
                gl.glVertex3fv(bottomRightFront, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(topLeftFront, 0);
                // Carré de l'arrière
                gl.glVertex3fv(topLeftRear, 0);
                gl.glVertex3fv(topRightRear, 0);
                gl.glVertex3fv(topRightRear, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(topLeftRear, 0);
                // Lignes des côtés (puisque les carrés devant et derrière existent déjà)
                gl.glVertex3fv(topLeftFront, 0);
                gl.glVertex3fv(topLeftRear, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(topRightFront, 0);
                gl.glVertex3fv(topRightRear, 0);
                gl.glVertex3fv(bottomRightFront, 0);
                gl.glVertex3fv(bottomRightRear, 0);

                gl.glEnd(); // Fin des arrêtes
            }

            private void drawPyramid(GL2 gl, float[] position, float[] rotation, float[] size, float[] color) {

                //repositionner la caméra "comme au début".
                resetView(gl);

                gl.glTranslatef(position[0], position[1], position[2]);// Translation
                gl.glRotatef(rotation[0], 1.0f, 0.0f, 0.0f); // Rotation selon l'axe x
                gl.glRotatef(rotation[1], 0.0f, 1.0f, 0.0f); // Rotation selon l'axe y
                gl.glRotatef(rotation[2], 0.0f, 1.0f, 0.0f); // Rotation selon l'axe z

                float[] bottomLeftRear = {-size[0] / 2, -size[1] / 2, 0};
                float[] bottomRightRear = {size[0] / 2, -size[1] / 2, 0};
                float[] bottomLeftFront = {-size[0] / 2, size[1] / 2, 0};
                float[] bottomRightFront = {size[0] / 2, size[1] / 2, 0};
                float[] top = {0, 0, size[2]};

                if (color != null) {
                    gl.glColor3fv(color, 0); // Couleur choisie
                }

                gl.glBegin(GL_QUADS); //afficher des carrés

                // Face du dessous
                if (color == null) {
                    gl.glColor3f(1.0f, 0.5f, 0.0f); // orange
                }

                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomRightFront, 0);
                gl.glVertex3fv(bottomLeftFront, 0);

                gl.glEnd();

                gl.glBegin(GL.GL_TRIANGLES); //afficher des carrés

                // Face de devant
                if (color == null) {
                    gl.glColor3f(1.0f, 0.0f, 0.0f); // rouge
                }
                gl.glVertex3fv(top, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(bottomRightFront, 0);

                // Face de derrière
                if (color == null) {
                    gl.glColor3f(1.0f, 1.0f, 0.0f); // jaune
                }
                gl.glVertex3fv(top, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomLeftRear, 0);

                // Face de gauche
                if (color == null) {
                    gl.glColor3f(0.0f, 0.0f, 1.0f); // bleu
                }
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(top, 0);

                // Face de droite
                if (color == null) {
                    gl.glColor3f(1.0f, 0.0f, 1.0f); // magenta
                }
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(top, 0);
                gl.glVertex3fv(bottomRightFront, 0);

                gl.glEnd(); // Fin du cube

                // On désire aussi afficher les arrêtes pour plus de visibilité
                gl.glColor3f(0.0f, 0.0f, 0.0f); // black
                gl.glBegin(GL_LINES); // Cette fois nous allons passer des lignes

                // Carré du bas
                gl.glVertex3fv(bottomRightFront, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(bottomRightFront, 0);
                // Lignes des côtés (puisque les carrés devant et derrière existent déjà)
                gl.glVertex3fv(bottomRightFront, 0);
                gl.glVertex3fv(top, 0);
                gl.glVertex3fv(bottomLeftFront, 0);
                gl.glVertex3fv(top, 0);
                gl.glVertex3fv(bottomLeftRear, 0);
                gl.glVertex3fv(top, 0);
                gl.glVertex3fv(bottomRightRear, 0);
                gl.glVertex3fv(top, 0);

                gl.glEnd(); // Fin des arrêtes

            }

            @Override
            public void init(GLAutoDrawable glad) {
                glu = new GLU();

                GL2 gl = glad.getGL().getGL2();

                gl.glEnable(GL.GL_DEPTH_TEST); // Tester si c'est devant ou derrière
                gl.glDepthFunc(GL.GL_LEQUAL); // Afficher si c'est devant ou égal

            }

            @Override
            public void dispose(GLAutoDrawable glad) {

            }

            @Override
            public void display(GLAutoDrawable glad) {

                GL2 gl = glad.getGL().getGL2();
                // instruction de nettoyage
                gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

                float[] position = {-1, 1, 0};
                float[] rotation = {0, 0, 0};
                float[] size = {0.95f, 0.95f, 0.45f};
                float[] color = {1, 1, 1}; // Blanc

                // afficher un damier de 5x5
                for (int ligne = 0; ligne < 5; ligne++) {
                    for (int colonne = 0; colonne < 5; colonne++) {
                        position[0] = colonne - 2f;
                        position[1] = ligne - 2f;

                        if (ligne == ligneselecteur && colonne == colonneselecteur) { //mettre la case sélctionnée en violet!
                            color[0] = 0.8f;
                            color[1] = 0;
                            color[2] = 0.7f;
                        } else {
                            color[0] = 0.09f;
                            color[1] = 0.72f;
                            color[2] = 0.3f; //sol gazon
                        }
                        drawCube(gl, position, rotation, size, color);

                    }
                }

                //afficher le bati
                float[] colorbat = {1, 1, 1};
                float[] colordome = {1, 1, 1};

                for (int i = 0; i < plat.terrain.length; i++) {
                    for (int j = 0; j < plat.terrain.length; j++) {
                        if (plat.terrain[i][j] != 0 && plat.terrain[i][j] != 4) {
                            colorbat[0] = colorbat[1] = colorbat[2] = 1; //couleur bati                
                            position[0] = j - 2;
                            position[1] = i - 2;
                            position[2] = 0.2f * plat.terrain[i][j];
                            size[0] = 0.8f;
                            size[1] = 0.8f;
                            size[2] = 0.45f;
                            drawCube(gl, position, rotation, size, colorbat); //cube bati
                        }

                        if (plat.terrain[i][j] == 4) {
                            position[0] = j - 2;
                            position[1] = i - 2;
                            position[2] = 0.2f * (plat.terrain[i][j] - 1);
                            size[0] = 0.8f;
                            size[1] = 0.8f;
                            size[2] = 0.45f;
                            drawCube(gl, position, rotation, size, colorbat); //cube bati
                            colordome[0] = 0;
                            colordome[1] = 0;
                            colordome[2] = 1; //couleur dome               
                            position[0] = j - 2;
                            position[1] = i - 2;
                            position[2] = 0.2f * plat.terrain[i][j];
                            size[2] = 0.45f;
                            drawPyramid(gl, position, rotation, size, colordome);

                        }

                    }
                }

                //afficher pions
                float[] colorpion1 = {1, 1, 0};
                float[] colorpion2 = {0.5f, 0, 1};

                for (int i = 0; i < plat.pion.length; i++) {
                    for (int j = 0; j < plat.pion.length; j++) {
                        if (plat.pion[i][j] == 1) {

                            position[0] = j - 2;
                            position[1] = i - 2;
                            position[2] = 0.5f;
                            drawSphere(gl, position, rotation, 0.25f, colorpion1);
                        }

                        if (plat.pion[i][j] == 2) {
                            position[0] = j - 2;
                            position[1] = i - 2;
                            position[2] = 0.5f;
                            drawSphere(gl, position, rotation, 0.25f, colorpion2);
                        }
                    }
                }

                // azimut = azimut + 1;
            }

            @Override
            public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {

            }
        };
        panel.addGLEventListener(openGLListener); // on ajoute le listener au tableau

        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e);
                System.out.println(etatcourant);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        azimut = azimut + 45;
                        break;

                    case KeyEvent.VK_NUMPAD6:
                        if (ligneselecteur < 4) {
                            ligneselecteur++;
                        } else {
                        }
                        break;

                    case KeyEvent.VK_NUMPAD4:
                        if (ligneselecteur > 0) {
                            ligneselecteur--;
                        } else {
                        }
                        break;

                    case KeyEvent.VK_NUMPAD8:
                        if (colonneselecteur > 0) {
                            colonneselecteur--;
                        } else {
                        }
                        break;

                    case KeyEvent.VK_NUMPAD2:
                        if (colonneselecteur < 4) {
                            colonneselecteur++;
                        } else {
                        }
                        break;

                    case KeyEvent.VK_NUMPAD9:
                        colonneselecteur--;
                        ligneselecteur++;
                        break;

                    case KeyEvent.VK_NUMPAD7:
                        colonneselecteur--;
                        ligneselecteur--;
                        break;

                    case KeyEvent.VK_NUMPAD1:
                        colonneselecteur++;
                        ligneselecteur--;
                        break;

                    case KeyEvent.VK_NUMPAD3:
                        colonneselecteur++;
                        ligneselecteur++;
                        break;

                    case KeyEvent.VK_ENTER:

                        //Construction joueur 1:
                        if (etatcourant == Etat.J1_CONSTRUCTION) {
                            if (plat.caseConstructible(ligneselecteur, colonneselecteur)) {
                                System.out.println("coords construction J1 " + ligneselecteur + "," + colonneselecteur);

                                System.out.println("construction autorisé");
                                plat.construction(ligneselecteur, colonneselecteur);
                                etatcourant = Etat.J2_MOUVEMENT_DEPART;

                            }
                        } // Constrcution joueur 2:
                        else if (etatcourant == Etat.J2_CONSTRUCTION) {
                            System.out.println("coords construction J2 " + ligneselecteur + "," + colonneselecteur);

                            if (plat.caseConstructible(ligneselecteur, colonneselecteur)) {
                                System.out.println("construction autorisée");
                                plat.construction(ligneselecteur, colonneselecteur);
                                etatcourant = Etat.J1_MOUVEMENT_DEPART;

                            }

                        } else {
                            System.out.println("construction interdite");
                            etatcourant = Etat.J2_CONSTRUCTION;
                        }

                        break;

                    case KeyEvent.VK_P:

                        // placement pions joueur 1
                        if (etatcourant == Etat.PLACEMENT_PION_J1) {
                            if (plat.terrain[ligneselecteur][colonneselecteur] != 4 && plat.pion[ligneselecteur][colonneselecteur] == 0 && nbpions < 2) {
                                plat.pion[ligneselecteur][colonneselecteur] = 1;
                                nbpions++;
                                if (nbpions >= 2) {
                                    etatcourant = Etat.PLACEMENT_PION_J2;
                                    nbpions = 0;
                                }
                            } else {
                            }
                        } //placement pions joueur 2
                        else if (etatcourant == Etat.PLACEMENT_PION_J2) {
                            if (plat.terrain[ligneselecteur][colonneselecteur] != 4 && plat.pion[ligneselecteur][colonneselecteur] == 0 && nbpions < 2) {
                                plat.pion[ligneselecteur][colonneselecteur] = 2;
                                nbpions++;
                                if (nbpions >= 2) {
                                    etatcourant = Etat.J1_MOUVEMENT_DEPART;
                                    nbpions = 0;
                                }
                            } else {
                            }
                        }

                        break;

                    case KeyEvent.VK_O:

                        // déplacement du joueur 1
                        if (etatcourant == Etat.J1_MOUVEMENT_DEPART) {
                            ld = ligneselecteur;
                            cd = colonneselecteur;
                            System.out.println("coords de depart " + ld + "," + cd);

                            if (plat.presencePion(ligneselecteur, colonneselecteur, 1)) {
                                etatcourant = Etat.J1_MOUVEMENT_ARRIVEE;
                            }
                        } else if (etatcourant == Etat.J1_MOUVEMENT_ARRIVEE) {
                            System.out.println("coords darrivee " + ligneselecteur + "," + colonneselecteur);

                            if (plat.possibiliteDeplacement(ligneselecteur, colonneselecteur, ld, cd)) {
                                System.out.println("deplacement autorisé");
                                plat.deplacement(ligneselecteur, colonneselecteur, ld, cd);

                                etatcourant = Etat.J1_CONSTRUCTION;
                            } else {
                                System.out.println("deplacement interdit");
                                etatcourant = Etat.J1_MOUVEMENT_DEPART;
                            }

                        } else if (etatcourant == Etat.J2_MOUVEMENT_DEPART) {

                            // déplacement du joueur 2
                            ld = ligneselecteur;
                            cd = colonneselecteur;
                            System.out.println("coords de depart " + ld + "," + cd);

                            if (plat.presencePion(ligneselecteur, colonneselecteur, 2) == true) {
                                etatcourant = Etat.J2_MOUVEMENT_ARRIVEE;
                            }

                        } else if (etatcourant == Etat.J2_MOUVEMENT_ARRIVEE) {
                            System.out.println("coords darrivee " + ligneselecteur + "," + colonneselecteur);

                            if (plat.possibiliteDeplacement(ligneselecteur, colonneselecteur, ld, cd)) {
                                System.out.println("deplacement autorisé");
                                plat.deplacement(ligneselecteur, colonneselecteur, ld, cd);
                            }
                            etatcourant = Etat.J2_CONSTRUCTION;
                        } else {
                            System.out.println("deplacement interdit");
                            etatcourant = Etat.J2_MOUVEMENT_DEPART;
                        }

                }
            }

            @Override

            public void keyReleased(KeyEvent e) {
            }
        }
        );
        
        
        
       
        
        // on ajoute une fenetre au sud
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // on ajoute les composants de la fenetre Sud:
        JLabel label2 = new JLabel();
        label2.setBorder(BorderFactory.createTitledBorder(null, "Joueur 1", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Cosmic", Font.BOLD, 18), Color.ORANGE));
        bottomPanel.add(label2);

        JLabel label3 = new JLabel();
        label3.setBorder(BorderFactory.createTitledBorder(null, "Joueur 2", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Cosmic", Font.BOLD, 18), Color.MAGENTA));
        bottomPanel.add(label3);
        
        //fenêtre à droite
        JPanel rightPanel = new JPanel(new GridLayout(1, 2));
        frame.add(rightPanel, BorderLayout.EAST);
        rightPanel.setPreferredSize(new Dimension(250, 50));
        
        
        JTextArea labelArea = new JTextArea("\n\n\n\n\n\n\n\n\n\nPressez P pour placer vos pions\n\nPressez O pour choisir le pion à déplacer\n\nPressez O pour déplacer le pion sur la case\n\nPressez ENTER pour construire\n");
        labelArea.setEditable(false);
        labelArea.setOpaque(false);
                
        rightPanel.add(labelArea);
        

        FPSAnimator animator = new FPSAnimator(panel, 60);
        frame.pack();
        frame.setVisible(true);

        animator.start();

    }

}
