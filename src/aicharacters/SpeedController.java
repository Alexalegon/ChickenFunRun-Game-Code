/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aicharacters;

import com.jme3.math.FastMath;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public enum SpeedController {
    ONE();
    SimpleControl control;
    float internalTimer = 0;
    float timeLimit;
    boolean isActive = false;
    
    public void play(float timeLimit, SimpleControl control){
        this.timeLimit = timeLimit;
        isActive = true;
        this.control = control;
    }
    
    public void setActive(boolean active){
        isActive = active;
    }
    
    public boolean update(){
        if(!isActive){
            if(FastMath.rand.nextInt(3) == 2)
                isActive = true;
        }
        return isActive;
    }
}
