/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import com.jme3.input.event.TouchEvent;
import controllers.Controller;
import de.lessvoid.nifty.render.NiftyImage;
import levels.PathConstructor;
import maincharacter.PlayerListener;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public enum TutorialStep {
    INTRO(""),TILT("TILT"),SLIDE("SWIPE DOWN"),JUMP("SWIPE UP"),CATCH("CATCH"),TURNROAD("SWIPE LEFT"),FREE("FREE");
    NiftyImage image,image2;
    String instructions;
    String instructions2 = "Swipe Right";
    float gravityAccumulated = 0;
    boolean isComplete = false;
    private TutorialStep(String instructions) {
        this.instructions =instructions;
    }
    public void setImage(NiftyImage image){
        this.image = image;
    }
    
    public void setExtraImage(NiftyImage image2){
        this.image2 = image2;
    }
    
   
    
    public void activate(){
        if(this.equals(TURNROAD)){
            if(PathConstructor.getTurnDirection().equalsIgnoreCase("left"))
            Tutorial.showInstruction(this,image,instructions);
            else 
            Tutorial.showInstruction(this,image2,instructions2);
        }
        else
            Tutorial.showInstruction(this, image, instructions);
    }
   
    public void run(){
        if(this.equals(TutorialStep.TILT)){
            if(Controller.gravityChange[0] < 0)
                gravityAccumulated +=(Controller.gravityChange[0] * -1);
            else
                gravityAccumulated +=Controller.gravityChange[0];
            if(gravityAccumulated > 36f){
                isComplete = true;
                TutorialManager.setTutorialStep(TutorialStep.FREE);
            }
        }
    }

    public void poll(String name, TouchEvent event, float tpf) {
        if(this.equals(TutorialStep.SLIDE)){
            if(event.getType().equals(TouchEvent.Type.FLING)){
                if(event.getDeltaY() >= PlayState.screenHeight/5 && PlayerListener.bikerControl.onGround() && 
                     PlayerListener.animChannel.getAnimationName() != "duck"){
                    Controller.duck();
                    TutorialManager.setTutorialStep(TutorialStep.FREE);
                }
            }
        }
        else if(this.equals(JUMP)){
             if(event.getDeltaY() <= -PlayState.screenHeight/5){
                    Controller.jump();
                    TutorialManager.setTutorialStep(FREE);
             }
        }
        else if(this.equals(TURNROAD)){
            if(event.getDeltaX() >= PlayState.screenWidth && PathConstructor.getTurnDirection().equalsIgnoreCase("right")){
                        Controller.turnRight();
                        TutorialManager.finish();
                    }
            else if(event.getDeltaX() <= PlayState.screenWidth * -1f && PathConstructor.getTurnDirection().equalsIgnoreCase("left")){
                        Controller.turnLeft();
                        TutorialManager.finish();
                    }
        }
    }
    
    public void reset(){
        gravityAccumulated = 0;
        isComplete = false;
    }
}
