/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aicharacters;

import com.jme3.math.FastMath;

/**
 *
 * @author Miguel Martrinez
 */
public enum LateralMovement {
    STATIONARY(0),LATERAL1(1),LATERAL2(2),LATERAL(3);
    static float transitionTime = 1f;
    int lanechanges;
    float duration;
    float changeTimes;
    int changeCounter = 0;
    float internalTimer = 0f;
    float lanechangeTimer = 0;
    boolean isTransitioning = false;
    private LateralMovement(int lanechanges){
        this.lanechanges = lanechanges;
    }
    
    public void setDuration(float duration){
        this.duration = duration;
        changeTimes = duration/lanechanges;
        reset();
    }
    
    public LateralMovement generateLateralMovement(){
        int index = FastMath.rand.nextInt(LateralMovement.values().length-1);
        return LateralMovement.values()[index];
    }
    
    public boolean move(float tpf){
        
      if(!isTransitioning){
          if(FastMath.rand.nextInt(5) == 2f){
              isTransitioning = true;
              lanechangeTimer = 0;
          }
      }
      else
          lanechangeTimer+=tpf;
       internalTimer+=tpf;
       
       if(lanechangeTimer >= changeTimes)
           isTransitioning = false;
       return isTransitioning;
    }
    
    public void reset(){
        internalTimer = 0;
        lanechangeTimer = 0;
        isTransitioning = false;
    }
}
