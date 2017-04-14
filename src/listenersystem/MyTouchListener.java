/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenersystem;

import maincharacter.PlayerListener;
import controllers.Controller;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.event.TouchEvent;
import levels.PathConstructor;
import mygame.PlayState;
import static listenersystem.ListenerHandler.changeRoadFlag;
import static listenersystem.ListenerHandler.pollJump;
import static listenersystem.ListenerHandler.pollTurns;
import mygame.GameState;
import mygame.GameStateManager;
import tutorial.TutorialManager;

/**
 *
 * @author Miguel Martrinez
 */
public class MyTouchListener implements TouchListener{
public static boolean isEnabled = true;

    public void setEnabled(boolean enabled){
        isEnabled = enabled;
}

    @Override
    public void onTouch(String name, TouchEvent event, float tpf) {
        if(isEnabled){
            if(GameStateManager.currentGameState.equals(GameState.TUTORIAL)
                || GameStateManager.currentGameState.equals(GameState.RESUMETUTORIAL)){
                tutorialPoll(name, event, tpf);
                return;
            }
                pollTurns(name, event, tpf);
                pollJump(name, event, tpf);
        }
                
    }
    
    public static void pollTurns(String name, TouchEvent event, float tpf){
        
            
         if(event.getType().equals(TouchEvent.Type.FLING))
             if(changeRoadFlag){
                    if(event.getDeltaX() >= PlayState.screenWidth && PathConstructor.getTurnDirection().equalsIgnoreCase("right")){
                        Controller.turnRight();
                        //PathConstructor.playerPassedCurentRoad();
                        //changeRoadFlag = false;
                        //bikerControl.changingRoad = true;
                    }
                    else if(event.getDeltaX() <= PlayState.screenWidth * -1f && PathConstructor.getTurnDirection().equalsIgnoreCase("left")){
                        Controller.turnLeft();
                        //PathConstructor.playerPassedCurentRoad();
                        //changeRoadFlag = false;
                        //bikerControl.changingRoad = true;
                    }
             }

             if(event.getDeltaY() >= PlayState.screenHeight/5 && PlayerListener.bikerControl.onGround() && 
                     PlayerListener.animChannel.getAnimationName() != "duck")
                    Controller.duck();
             else if(event.getDeltaY() <= -PlayState.screenHeight/5)
                    Controller.jump();
     }
     public static void pollJump(String name, TouchEvent event, float tpf){
         //if(event.getType().equals(TouchEvent.Type.))
            //Controller.jump();
     }
    
     public static void tutorialPoll(String name, TouchEvent event, float tpf){
         TutorialManager.getTutorialStep().poll(name,event,tpf);
     }
}
