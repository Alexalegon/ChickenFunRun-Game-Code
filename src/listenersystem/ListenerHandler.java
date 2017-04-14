/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listenersystem;

import listenersystem.MyTouchListener;
import maincharacter.PlayerListener;
import controllers.Controller;
import com.jme3.input.KeyInput;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import levels.PathConstructor;
import levels.TrackManager;
import mygame.PlayState;
import static maincharacter.PlayerListener.bikerControl;
import static mygame.PlayState.inputManager;

/**
 *
 * @author Miguel Martrinez
 */
public final class ListenerHandler {
    public static AnalogListener analogListener;
    public static ActionListener actionListener;
    public static MyTouchListener touchListener;
    public static boolean changeRoadFlag = false;
    
     public static void initListeners(){
        inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_G));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("Stop", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Duck", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("TrackLeft", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("TrackRight", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("",new TouchTrigger(TouchInput.ALL));
        analogListener = new AnalogListener(){

            @Override
            public void onAnalog(String name, float value, float tpf) {
                      if(name.equalsIgnoreCase("Walk")){
                        Controller.walk();
                }
                else if(name.equalsIgnoreCase("Walk")){
                    //Controller.stoppedWalking();
                }
                else if(name.equalsIgnoreCase("Jump")){
                    Controller.jump();
                  // bikerControl.setPhysicsLocation(new Vector3f(character.getLocalTranslation().x,  character.getLocalTranslation().y+=60f,character.getLocalTranslation().z));
                }
                else if(name.equalsIgnoreCase("Left") ){
                    Controller.turnLeft();
                }
                else if(name.equalsIgnoreCase("Right") ){
                    Controller.turnRight();
                }
                
            }
            
        };
        
        actionListener = new ActionListener(){

            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                
                        if(name.equalsIgnoreCase("Walk") && isPressed){
                                Controller.walk();
                }
                else if(name.equalsIgnoreCase("Walk") && !isPressed){
                    //Controller.stoppedWalking();
                }
                else if(name.equalsIgnoreCase("Jump") && isPressed){
                    Controller.jump();
                  // bikerControl.setPhysicsLocation(new Vector3f(character.getLocalTranslation().x,  character.getLocalTranslation().y+=60f,character.getLocalTranslation().z));
                }
                else if(name.equalsIgnoreCase("Left") && isPressed){
                    Controller.turnLeft();
                }
                else if(name.equalsIgnoreCase("Right") && isPressed){
                    Controller.turnRight();
                }
                else if(name.equalsIgnoreCase("Stop")){
                    Controller.stoppedWalking();
                }
                else if(name.equalsIgnoreCase("Duck")){
                    Controller.duck();
                }
                else if(name.equalsIgnoreCase("TrackLeft") && !isPressed){
                    bikerControl.setPhysicsLocation(TrackManager.changeTrackLeft(bikerControl.getPhysicsLocation()));
                    //bikerControl.setPhysicsLocation(new Vector3f(0,10f,0));
                }
                else if(name.equalsIgnoreCase("TrackRight") && !isPressed){
                    bikerControl.setPhysicsLocation(TrackManager.changeTrackRight(bikerControl.getPhysicsLocation()));
                }
            }
            
    };
      
        touchListener = new MyTouchListener();
        
    }
     
     public static void unregisterListeners(){
         inputManager.removeListener(analogListener);
         inputManager.removeListener(actionListener);
         inputManager.removeListener(touchListener);
     }
     
     public static void registerListeners(){
         inputManager.addListener(analogListener, "Jump");
        inputManager.addListener(actionListener, "Walk","Left", "Right","Stop","Duck","TrackLeft","TrackRight");
        inputManager.addListener(touchListener,"");
     }
     
     public static void pollTurns(String name, TouchEvent event, float tpf){
         if(event.getType().equals(TouchEvent.Type.FLING))
             if(changeRoadFlag){
                    if(event.getDeltaX() >= PlayState.screenWidth){
                        Controller.turnRight();
                        //PathConstructor.playerPassedCurentRoad();
                        //changeRoadFlag = false;
                        //bikerControl.changingRoad = true;
                    }
                    else if(event.getDeltaX() <= PlayState.screenWidth * -1f){
                        Controller.turnLeft();
                        //PathConstructor.playerPassedCurentRoad();
                        //changeRoadFlag = false;
                        //bikerControl.changingRoad = true;
                    }
             }

             if(event.getDeltaY() >= PlayState.screenHeight/5 && PlayerListener.bikerControl.onGround() && 
                     PlayerListener.animChannel.getAnimationName() != "duck")
                    Controller.duck();
             else if(event.getDeltaY() <= -PlayState.screenHeight/5 && PlayerListener.bikerControl.onGround())
                    Controller.jump();
     }
     public static void pollJump(String name, TouchEvent event, float tpf){
         //if(event.getType().equals(TouchEvent.Type.))
            //Controller.jump();
     }
}
