/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*********************************************************************************/
/*********************************************************************************/
// LIBREAIRIE EXTERNES
//--------------------MAEVEN
//                          - EVENBUS : https://github.com/greenrobot/EventBus
//--------------------AUTRES
/*********************************************************************************/
/*********************************************************************************/
package com.mycompany.av2r_assistance;

/**
 *
 * @author 362294
 */
public class Constante {

    public enum CTE_AV2R_VOITURE{
        ASSISTANCE_SURVIRAGE, ASSISTANCE_ABS, ASSISTANCE_SOUSVIRAGE, ASSISTANCE_PATINAGE
    }
    
    
    public static enum CTE_COMMANDE_VOITURE {
        ACCELERATION, DECELERATION,STOP, TOUT_DROIT, TURN_RIGHT, TURN_LEFT, NO_ACTION
    };

    public static enum CTE_ACTION_VOITURE {
        VITESSE,DIRECTION,NO_ACTION, DIRECTION_NEUTRE, VITESSE_NULL
    };
    
    public static enum CTE_ETAT_VOITURE {
        ACCELERATION, DECELERATION,ROULEMENT, STOP,VIRAGE,TOUT_DROIT 
    };
    
    
    
    public static enum CTE_ETAT_ROUE {
        EN_ROTATION, STOP, UNDEFINED
    };

    public static enum CTE_ETAT_DIRECTION {
         TURN_RIGHT, NEUTRE,TURN_LEFT,UNDEFINED 
    };

    public static enum CTE_ETAT_ACCELERATEUR {
        ACCELERATION, NEUTRE, DECELERATION, UNDEFINED
    };
    
    public static enum CTE_ETAT_TEMPERATURE {
        FROID, NORMAL,CHAUD, SURCHAUFFE, UNDEFINED
    };
    
    public static long TEMPS_SLEEP_ANALYSE_VOITURE = 500;
    
}
