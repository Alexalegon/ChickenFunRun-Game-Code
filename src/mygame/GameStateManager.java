/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import maincharacter.CamController;
import tutorial.TutorialManager;
import aicharacters.ChickenListener;
import cameras.CamAnimationsManager;
import com.jme3.animation.LoopMode;
import listenersystem.ListenerHandler;
import maincharacter.PlayerListener;
import controllers.Controller;
import com.jme3.math.Vector3f;
import de.lessvoid.nifty.effects.EffectEventId;
import guisystem.Gui;
import guisystem.HomeScreen;
import guisystem.Hud;
import guisystem.StatsManager;
import levels.PathConstructor;
import levels.PrefabPool;
import levels.Road;
import levels.RotationQuats;
import maincharacter.LifeManager;
import static mygame.PlayState.chaseCam;
import static mygame.PlayState.number;
import static mygame.PlayState.start;
import obstaclesystem.HitInfo;
import tutorial.Tutorial;

/**
 *
 * @author Miguel Martrinez
 */
public final class GameStateManager {
    public static GameState currentGameState =  GameState.HOME;
    public static HitInfo lastHit;
    public static boolean f = false;
    public static GameState getCurrentState(){
        return currentGameState;
    }
    
    public static void setState(GameState gameState){
        currentGameState = gameState;
        if(gameState.equals(GameState.PAUSE))
            pause();
        else if(gameState.equals(GameState.GAMEOVER))
            pause();
        else if(gameState.equals(GameState.RUN))
            run();
        else if(gameState.equals(GameState.INTRO))
            intro();
        else if(gameState.equals(GameState.HOME))
            home();
        else if(gameState.equals(GameState.RESUMEPLAY))
            resumePlay();
        else if(gameState.equals(GameState.TUTORIAL))
            playTutorial();
        else if(gameState.equals(GameState.RESUMETUTORIAL))
            resumeTutorial();
        else if(gameState.equals(GameState.TUTORIALINTRO))
            tutorialIntro();
    }
    
    public static void pause(){
        System.out.println("start set to false:   " + start + number);number++;
        PlayerListener.animControl.setEnabled(false);
        ChickenListener.setEnabled(false);
        PlayerListener.bikerControl.setEnabled(false);
            PlayState.setStart(false);
            PathConstructor.getCurrentPointsRoad().pauseRotations();
            
            //PlayState.bulletAppState.getPhysicsSpace().remove(PlayerListener.bikerControl);
            //PlayerListener.character.removeControl(PlayerListener.bikerControl);
            //PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,0,-60f));
           
            //if(PlayState.bulletAppState.getPhysicsSpace().getCharacterList().isEmpty())
            //throw new NullPointerException("Chartacter control is nul!");
            //PlayerListener.character.setLocalTranslation(0, 20, -60f);
    }
    
    public static void gameOver(){
        PlayState.setStart(true);
        //PlayState.bulletAppState.getPhysicsSpace().add(PlayerListener.bikerControl);
            //PlayerListener.bikerControl.setWalkDirection(Vector3f.UNIT_Z.mult(1.3f));
            PlayerListener.bikerControl.setViewDirection(Vector3f.UNIT_Z);
            PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,PlayerListener.bikerControl.getPhysicsLocation().y,0));
            PlayerListener.animControl.setEnabled(true);
            
            CamController.resetRunningAlg();
            Controller.posZSettings();
            PlayState.chaseCam.setGradualRotation(Vector3f.UNIT_Z);
            ListenerHandler.changeRoadFlag = false;
            PlayerListener.bikerControl.changingRoad = false;
            chaseCam.resetAutomaticDuck();
            
            PathConstructor.getCurrentPointsRoad().pauseRotations();
            for(Road road: PathConstructor.roadArray)
                road.turnIndex=0;
            Controller.rotationIndex=0;
        Hud.clearPoints();
    }
    
    public static void run(){
        
        Gui.nifty.gotoScreen("hud");
        PlayState.setStart(true);
        ListenerHandler.registerListeners();
        //PlayState.bulletAppState.getPhysicsSpace().add(PlayerListener.bikerControl);
            //PlayerListener.bikerControl.setWalkDirection(Vector3f.UNIT_Z.mult(1.3f));
            PlayerListener.customAnimControl.setEnabled(false);
            PlayerListener.bikerControl.setViewDirection(Vector3f.UNIT_Z);
            PlayerListener.character.setLocalRotation(RotationQuats.ROTATIONQUAT360);
            PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,PlayerListener.bikerControl.getPhysicsLocation().y,000));
            CamController.resetRunningAlg();
            PlayerListener.setRunning(true);
            
            Controller.posZSettings();
            PlayState.chaseCam.setGradualRotation(Vector3f.UNIT_Z);
            ListenerHandler.changeRoadFlag = false;
            PlayerListener.bikerControl.changingRoad = false;
            PlayerListener.bikerControl.setEnabled(true);
            PlayerListener.animControl.setEnabled(true);
            PlayerListener.animChannel.setAnim("runBottom");
            PlayerListener.animChannel.setSpeed(3f);
            ListenerHandler.touchListener.setEnabled(true);
            ChickenListener.setEnabled(true);
            ChickenListener.simpleControl.uncatch();
            chaseCam.setEnabled(true);
            chaseCam.reset();
            
            PathConstructor.getCurrentPointsRoad().resumeRotations();
            for(Road road: PathConstructor.roadArray)
                road.turnIndex=0;
            Controller.rotationIndex=0;
            
            
                
                
    }
    
    public static void intro(){
        
        PlayerListener.character.setLocalRotation(RotationQuats.ROTATIONQUAT360);
        CamAnimationsManager.initAnimations("home");
        
       
        CamAnimationsManager.initAnimations("playerTakeOff");
        CamAnimationsManager.setCallBackState(GameState.RUN);
    }
    
    public static void home(){
        CamAnimationsManager.resetAnimations();
        HomeScreen.showHome("");
        Gui.nifty.gotoScreen("start");
    }
    
    public static void resumePlay(){
        PathConstructor.getCurrentPointsRoad().resumeRotations();
        ListenerHandler.registerListeners();
        PlayerListener.animControl.setEnabled(true);
        PlayerListener.bikerControl.setEnabled(true);
        ChickenListener.setEnabled(true);
        PlayState.setStart(true);
        
        
    }
    
    public static void resumeTutorial(){
        ListenerHandler.registerListeners();
        PlayerListener.animControl.setEnabled(true);
        PlayerListener.bikerControl.setEnabled(true);
        ChickenListener.setEnabled(true);
        PlayState.setStart(true);
    }
    
    public static void playTutorial(){
        Gui.nifty.gotoScreen("tutorialScreen");
        PlayerListener.bikerControl.graduallyRotate = false;
        ListenerHandler.registerListeners();
        PlayState.setStart(true);
        //PlayState.bulletAppState.getPhysicsSpace().add(PlayerListener.bikerControl);
            //PlayerListener.bikerControl.setWalkDirection(Vector3f.UNIT_Z.mult(1.3f));
            PlayerListener.customAnimControl.setEnabled(false);
            PlayerListener.bikerControl.setViewDirection(Vector3f.UNIT_Z);
            PlayerListener.bikerControl.setPhysicsLocation(new Vector3f(0,PlayerListener.bikerControl.getPhysicsLocation().y,0));
            PlayState.bulletAppState.getPhysicsSpace().add(PlayerListener.bikerControl);
            CamController.resetRunningAlg();
            PlayerListener.setRunning(true);
            TutorialManager.reset();
            
            Controller.posZSettings();
            PlayState.chaseCam.setGradualRotation(Vector3f.UNIT_Z);
            ListenerHandler.changeRoadFlag = false;
            PlayerListener.bikerControl.changingRoad = false;
            PlayerListener.bikerControl.setEnabled(true);
            PlayerListener.animControl.setEnabled(true);
            PlayerListener.animChannel.setAnim("runBottom");
            PlayerListener.animChannel.setSpeed(3f);
            ListenerHandler.touchListener.setEnabled(true);
            ChickenListener.setEnabled(true);
            ChickenListener.simpleControl.uncatch();
            chaseCam.setEnabled(true);
            chaseCam.reset();
            for(Road road: PathConstructor.roadArray)
                road.turnIndex=0;
            Controller.rotationIndex=0;
    }
    
    public static void tutorialIntro(){
        
       Tutorial.hideInstruction();
        PlayerListener.character.setLocalRotation(RotationQuats.ROTATIONQUAT360);
        CamAnimationsManager.initAnimations("home");
       
        CamAnimationsManager.initAnimations("playerTakeOff");
        CamAnimationsManager.setCallBackState(GameState.TUTORIAL);
        
    }
    
    public static void initTutorial(){
        //PlayState.setStart(false);
        setState(GameState.TUTORIALINTRO);
        Controller.restartPlay();
    }
}
