/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cameras;

import com.jme3.math.Vector3f;
import controllers.ControllerMath;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import guisystem.Gui;
import maincharacter.PlayerListener;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class CamAnimation {

public boolean continous = false;
public boolean endReached = false;
Vector3f startingLoc;
Vector3f endingLoc = new Vector3f();
Vector3f currentLoc = new Vector3f(0,0,0);
Vector3f distance;
float speed;
float duration;
float time = 0;
String name;
String type;
boolean enabled = true;
    
    public CamAnimation(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public void setStartingLocation(Vector3f startingLoc){
        this.startingLoc = startingLoc.clone();
        currentLoc.x = this.startingLoc.x;
        currentLoc.y = this.startingLoc.y;
        currentLoc.z = this.startingLoc.z;
    }
    
    public void setEndingLocation(Vector3f endingLoc){
        this.endingLoc = endingLoc;
        
    }
    
    public void setDistance(Vector3f distance){
        this.distance = distance;
        setEndingLocation(startingLoc.add(distance));
    }
    
    public void setSpeed(float speed){
        this.speed = speed;
    }
    
    public void setDuration(float duration){
        this.duration = duration;
    }
    
    public void setContinous(boolean continous){
        this.continous = continous;
    }
    
    public void resetStartingLoc(){
        currentLoc.x = startingLoc.x;System.out.println("startinglo.x=   " +startingLoc.x);
        currentLoc.y = startingLoc.y;
        currentLoc.z = startingLoc.z;
        endReached = false;
    }
    
    public void play(){
        if(!enabled)
            return;
       
        if(continous && endReached)
            resetStartingLoc();
        else if(!continous && endReached){
            CamAnimationsManager.animationEnded(name);
            return;
        }
        float alpha = calculateAlpha();
        currentLoc.x = ControllerMath.lerp(startingLoc.x, endingLoc.x, 1-alpha);
        
        currentLoc.y = ControllerMath.lerp(startingLoc.y, endingLoc.y, 1-alpha);
        currentLoc.z = ControllerMath.lerp(startingLoc.z, endingLoc.z, 1-alpha);
        if(type.equalsIgnoreCase("cam")){
            if(time!=0)
           PlayState.cam.setLocation(currentLoc);//System.out.println("EndingLocation   "+endingLoc);
        }
        else{
            currentLoc.y = 2.5f;
            checkIntro();
            if(time!=0)
                PlayerListener.character.setLocalTranslation(currentLoc);
        }
        checkEndLinear();
        
    }
    
  public float calculateAlpha(){
      return time/duration;
  }
    
    public void checkEndFilter(){
        //if(startingLoc.x == endingLoc.x && startingLoc.y == endingLoc.y 
                //&&startingLoc.z == endingLoc.z)
        if(ControllerMath.withinRange(currentLoc.x, endingLoc.x, 1f))
            endReached = true;
    }
    
    public void checkEndLinear(){
        if (time >= duration){
            endReached = true;
            time = 0;
        }
        else
            time+=PlayState.tpf;
    }
    
    public void checkIntro(){
        int second = (int)time/1;
        if(second<3 && !endReached)
            Gui.introElement.getRenderer(ImageRenderer.class).setImage(Gui.introImages[second]);
    }
    
    public void reset(){
        endReached = false;
        currentLoc.set(Vector3f.ZERO);
        time = 0;
    }
    
    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
