/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import aicharacters.ChickenListener;
import guisystem.HomeScreen;
import guisystem.PopupsManager;
import levels.LevelManager;
import listenersystem.ListenerHandler;
import static listenersystem.ListenerHandler.changeRoadFlag;
import maincharacter.PlayerListener;
import mygame.GameState;
import mygame.GameStateManager;
import mygame.Main;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class TutorialManager {
    public static boolean begin = false;
    static TutorialStep step = TutorialStep.TILT;
    static int turnRoadCounter = 0;
    static float finishCountdown = 0;
    static boolean isFinishing = false;
    
    public static void setBegin(boolean bool){
        begin = bool;
    }
   
    public static void run(){
        checkFinishCountdown();
        LevelManager.obstacleSystem.runTutorial();
        step.run();
        checkForTurnRoad();
        if(step.equals(TutorialStep.FREE))
            PlayerListener.setRunning(true);
        else
            PlayerListener.setRunning(false);
        
    }
    
    public static void setTutorialStep(TutorialStep st){
        if (step.equals(st))
                return;
        step = st;
        
        step.activate();
    }
    
    public static TutorialStep getTutorialStep(){
        return step;
    }
    
    public static void checkFinishCountdown(){
        if(!isFinishing)
            return;
        finishCountdown += PlayState.tpf;
        if(finishCountdown > 1f)
            finishFinal();
    }
    
    public static void checkForTurnRoad(){
        if(changeRoadFlag ){
           turnRoadCounter++;
        if(turnRoadCounter > 1f)
            return;
        setTutorialStep(TutorialStep.TURNROAD);
        }
    }
    
    public static void finish(){
       setTutorialStep(TutorialStep.FREE);
       isFinishing = true;
       Tutorial.hideInstruction();
    }
    
    public static void finishFinal(){
        ChickenListener.setEnabled(false);
        ListenerHandler.unregisterListeners();
        if(!Main.getIsAdsDisabled())
            Main.communication.showInterstitial();
       GameStateManager.setState(GameState.PAUSE);
       PopupsManager.showTutorialOverPopup();
    }
    
    public static void reset(){
        isFinishing = false;
        finishCountdown = 0;
        turnRoadCounter = 0;
        for(TutorialStep s: TutorialStep.values())
            s.reset();
        
        setTutorialStep(TutorialStep.TILT);
        step.activate();
    }
}
