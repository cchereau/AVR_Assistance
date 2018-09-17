/** ********************************************************************************************
 * FONCTION DE GESTION DE LA VOITURE AVEC ANALYSE DE L'ETAT A TOUT MOMENT
 * PERMETTANT DE VERIFIER LES POINTS SUIVANTS
 ********************************************************************************************** */
// ETAT DE LA JAUGE DE CARBURANT
// LA PRESSION EN ECHAPPEMENT
// LA TEMPERATURE DE SORTIE ECHAPPEMENT
// VITESSE EMBRAYAGE (POST & PRE)
// VITESSE DES ROUES (AVG, AVD, ARG, ARD)
// ET EN FONCTION DE CA RENVOIE LES ANALYSE SUIVANTE
// SURVIRAGE / SOUS VIRAGE / BLOCAGE ROUE / 
/** ******************************************************************************************** */
// Exemple : https://rom.developpez.com/java-listeners/
/** ******************************************************************************************** */
package com.avr.voiture;

import com.avr.voiture.composants.RoueDirectrice;
import com.avr.voiture.composants.Composants;
import com.avr.voiture.composants.Roue;
import com.avr.voiture.composants.Thermometre;
import com.avr.voiture.composants.Direction;
import com.avr.voiture.composants.Accelerateur;
import com.avr.Constante;
import com.avr.Constante.CTE_ETAT_VOITURE;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author 362294
 */
public class Voiture extends Thread implements VoitureRegister {

    // definition des Roues
    private final Roue avg;
    private final Roue avd;
    private final Roue arg;
    private final Roue ard;

    // definition de l'embrayage
    private final Roue postEmbrayage;
    private final Roue preEmbrayage;

    // definition des temperature
    private final Thermometre temperatureMoteur;
    private final Thermometre temperatureEchappement;

    // definition des données de la direction
    private final Composants direction;
    private final Composants accelerateur;

    //private final ArrayList<Constante.CTE_COMMANDE_VOITURE> actions;
    private final Map<Constante.CTE_ACTION_VOITURE, Integer> actions;

    private CTE_ETAT_VOITURE etatVoiture;
    private final List<VoitureObserver> observers;
    private final Object MUTEX = new Object();
    private boolean changed;

    public Voiture() {

        this.observers = new ArrayList<>();
        
        // definition de l'état de la voiture
        etatVoiture = CTE_ETAT_VOITURE.STOP;
        
        // definition des objets Roues - motrice et directrice
        avg = new RoueDirectrice(new Double(3));
        avd = new RoueDirectrice(new Double(3));
        arg = new Roue(new Double(3));
        ard = new Roue(new Double(3));

        // definition des objets embrayages
        postEmbrayage = new Roue(new Double(2));
        preEmbrayage = new Roue(new Double(4));
        
        // definition des objets de Temperatures
        temperatureMoteur = new Thermometre(40, 50, 55);
        temperatureEchappement = new Thermometre(30, 50, 60);
        
        // définition des objets de direction et d'accéleration
        direction = new Direction();
        accelerateur = new Accelerateur();

        // liste des actions & initialisation
        this.actions = new EnumMap<>(Constante.CTE_ACTION_VOITURE.class);
        this.actions.put(Constante.CTE_ACTION_VOITURE.VITESSE, 0);
        this.actions.put(Constante.CTE_ACTION_VOITURE.DIRECTION, 0);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Fonction permettant d'assigner une action à la voiure 
    /////////////////////////////////////////////////////////////////////////////////////////
    // Le principe est de stocker l'action dans une base d'action qui sera ensuite lue 
    // lors de la prochaine execution du Thread. Cette fonction  optimise les actions au fur 
    // et à mesure qu'elle sont reçues.
    // A améliorer
    /////////////////////////////////////////////////////////////////////////////////////////
    public void addActionVoiture(Constante.CTE_ACTION_VOITURE cte_action_voiture, int pValeur) {
        int valeur;
        
        switch (cte_action_voiture) 
        {
            case DIRECTION:
                valeur = actions.get(Constante.CTE_ACTION_VOITURE.DIRECTION);
                valeur +=pValeur;
                actions.replace(Constante.CTE_ACTION_VOITURE.DIRECTION, valeur);   // on envoi comme action de mettre à Zéro la valeur de la direction
                break;

            case DIRECTION_NEUTRE:
                valeur = direction.getValeur();
                actions.replace(Constante.CTE_ACTION_VOITURE.DIRECTION, -valeur);        // on envoi comme action de mettre à Zéro la valeur de la direction
                break;

            case VITESSE:
                valeur = actions.get(Constante.CTE_ACTION_VOITURE.VITESSE);
                valeur +=pValeur;
                actions.replace(Constante.CTE_ACTION_VOITURE.VITESSE, valeur);      // on envoi comme action de mettre à Zéro la valeur de l'accélérateur
                break;

            case VITESSE_NULL:
                valeur = accelerateur.getValeur();
                actions.replace(Constante.CTE_ACTION_VOITURE.VITESSE,-valeur);         // on envoi comme action de mettre à Zéro la valeur de l'accélérateur
                break;

            default:
                break;

        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Fonction permettant d'assigner une action à la voiure 
    /////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void run() {
        while (true) {
            try {

                System.out.println("av2r.voiture.Voiture.run() : ACTION");

                // optimisation des actions sur la file des actions en fonction de l'état du vehicule
                System.out.println("av2r.voiture.Voiture.run() : ------- OPTIMISATION ACTIONS");
                System.out.println("av2r.voiture.Voiture.run()------- OPTIMISATION ACTIONS - VITESSE  : " + actions.get(Constante.CTE_ACTION_VOITURE.VITESSE));
                System.out.println("av2r.voiture.Voiture.run()------- OPTIMISATION ACTIONS - DIRECTION : " + actions.get(Constante.CTE_ACTION_VOITURE.DIRECTION));

                // execution des actions optimisées
                System.out.println("av2r.voiture.Voiture.run() : ------- EXECUTIONS ACTIONS");
                direction.setAction(actions.get(Constante.CTE_ACTION_VOITURE.DIRECTION));
                accelerateur.setAction(actions.get(Constante.CTE_ACTION_VOITURE.VITESSE));
                
                // remise à niveau de toutes les commandes
                System.out.println("av2r.voiture.Voiture.run() : ------- SUPPRESSION ACTIONS");
                actions.replace(Constante.CTE_ACTION_VOITURE.DIRECTION, 0);
                actions.replace(Constante.CTE_ACTION_VOITURE.VITESSE, 0);

                // envoie du message sur l'état de la voiture
                this.postMessage(this.controleVoiture());

                // repos du thread pendant quelques secondes
                Thread.sleep(Constante.TEMPS_SLEEP_ANALYSE_VOITURE);
            } catch (InterruptedException e) {

                // gestion de l'erreur
            }
        }
    }

    //method to post message to the topic
    public void postMessage(Constante.CTE_ETAT_VOITURE pEtat) {
        this.etatVoiture = pEtat;
        this.changed = true;
        notifyObservers();
    }

    @Override
    public void register(VoitureObserver obj) {
        if (obj == null) {
            throw new NullPointerException("Null Observer");
        }
        synchronized (MUTEX) {
            if (!observers.contains(obj)) {
                observers.add(obj);
            }
        }
    }

    @Override
    public void unregister(VoitureObserver obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        //synchronization is used to make sure any observer registered after message is received is not notified
        List<VoitureObserver> observersLocal;
        synchronized (MUTEX) {
            if (!changed) {
                return;
            }
            observersLocal = new ArrayList<>(this.observers);
            this.changed = false;
        }
        observersLocal.forEach((obj) -> {
            obj.setEtatVoiture();
        });
    }

    @Override
    public Object getUpdate(VoitureObserver obj) {
        return this.etatVoiture;
    }

    //////////////////////////////////////////////////////////////////////
    // fonction d'analyse de l'état de la voiture
    //////////////////////////////////////////////////////////////////////
    // En Entrée : Rien
    // En sortie : La situation de la voiture
    private CTE_ETAT_VOITURE controleVoiture() {

        // test du survirage
        
        // test du sousvirage
        
        // test du patinage 
        
        // test de l'abs
        
        // test du dérapage
        
        

        //Constante.CTE_ETAT_ROUE etatRoue = getEtatRoues();
        //Constante.CTE_ETAT_ROUE etatEmbrayage = getEtatEmbrayage();
        //Constante.CTE_ETAT_DIRECTION etatDirection = direction.getEtatDirection();
        //Constante.CTE_ETAT_TEMPERATURE echappement = temperatureEchappement.getEtatTemperature();
        //Constante.CTE_ETAT_TEMPERATURE moteur = temperatureMoteur.getEtatTemperature();
        //Constante.CTE_ETAT_ACCELERATEUR etatAccelerateur = accelerateur.getEtatAccelration();
        return CTE_ETAT_VOITURE.ROULEMENT;
    }

    ///////////////////////////////////////////////////////////////////
    // Analyse des roue et définition des roues sont en rotation
    //////////////////////////////////////////////////////////////////
    
    
    
    private Constante.CTE_ETAT_ROUE getEtatRoues() {
        // les roues sont dans la même situation (en mouvement ou à l'arret)
        if (avg.isRoueEnMouvement() && arg.isRoueEnMouvement() && ard.isRoueEnMouvement() && avd.isRoueEnMouvement()) {
            if (avg.isRoueEnMouvement()) {
                return Constante.CTE_ETAT_ROUE.STOP;               // la voiture est à l'arret, le test estsur Avg mais pourrait être sur toutes les autres
            } else {
                return Constante.CTE_ETAT_ROUE.EN_ROTATION;
            }
        }
        return Constante.CTE_ETAT_ROUE.UNDEFINED;
    }

    ///////////////////////////////////////////////////////////////////
    // Analyse des embaraygaes et définition si rotation ou non
    //////////////////////////////////////////////////////////////////
    private Constante.CTE_ETAT_ROUE getEtatEmbrayage() {
        // les roues sont dans la même situation (en mouvement ou à l'arret)
        if (postEmbrayage.isRoueEnMouvement() && preEmbrayage.isRoueEnMouvement()) {
            if (postEmbrayage.isRoueEnMouvement()) {
                return Constante.CTE_ETAT_ROUE.STOP;               // la voiture est à l'arret, le test estsur Avg mais pourrait être sur toutes les autres
            } else {
                return Constante.CTE_ETAT_ROUE.EN_ROTATION;
            }
        }
        return Constante.CTE_ETAT_ROUE.UNDEFINED;
    }

}
