/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import aicharacters.ChickenListener;
import com.jme3.math.Vector3f;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public final class ControllerMath {
    //reflects walkDirection vector
    public static void calculateWalkDirection(Vector3f walkDirection, int direction,Vector3f viewDirection){
       if(viewDirection == Vector3f.UNIT_Z)
           switchingToPositiveZ(walkDirection,direction,viewDirection);
        
        else if(viewDirection == Vector3f.UNIT_X){
            switchingToPositiveX(walkDirection,direction,viewDirection);
        }
        
        else if(viewDirection == Vector3f.UNIT_Z.mult(-1)){
            switchingToNegativeZ(walkDirection,direction,viewDirection);
        }
        
        else{
            switchingToNegativeX(walkDirection,direction,viewDirection);
        }
        walkDirection.normalizeLocal();
        for(int i = 0; i < 2000; i++)
                    System.out.println("walkDirection =     " + walkDirection);//PlayState.main.stop();
    }
    
    public static void switchingToPositiveZ(Vector3f walkDirection, int direction,Vector3f viewDirection){
         Vector3f projection;
            //use the reflection formula V - 2Vp   //Vp is the projected vector
            if(direction == 0){
                //last Direction was -X
                // check if current walkDirection is to the right
                if(walkDirection.z <= 0){
                    projection = walkDirection.project(Vector3f.UNIT_Z.mult(-1f));
                    walkDirection.subtractLocal(projection.multLocal(2f));
                    
                }
                else{//walk Dierection is to the left
                    projection = walkDirection.project(Vector3f.UNIT_Z);
                    walkDirection.subtractLocal(projection.multLocal(2f)).negateLocal();
                }
                
            }
            else{// last Direction was +X 
                if(walkDirection.z <= 0){//check if walkDirection was to the left
                    projection = walkDirection.project(Vector3f.UNIT_Z.mult(-1f));
                    walkDirection.subtractLocal(projection.multLocal(2f));
                }
                else{//walk Direction was to thhe right
                    projection = walkDirection.project(Vector3f.UNIT_Z);
                    walkDirection.subtractLocal(projection.multLocal(2f).negateLocal());
                }
            }
    }
    
    public static void switchingToPositiveX(Vector3f walkDirection, int direction,Vector3f viewDirection){
        Vector3f projection;
                //use the reflection formula V - 2Vp   //Vp is the projected vector
        if(direction == 0){
            //last Direction was +Z
            //check if current walk Direction is to right
            if(walkDirection.x <= 0){
                projection = walkDirection.project(Vector3f.UNIT_X);
                for(int i = 0; i < 2000; i++){
                    System.out.println("projection =        " + projection);
                    System.out.println("preWalkDirection =      " + walkDirection);
                }
                walkDirection.subtractLocal(projection.multLocal(2f));
                for(int i = 0; i < 2000; i++)
                    System.out.println("walkDirection =     " + walkDirection);
            }
            else{// walk direction is to left
                projection = walkDirection.project(Vector3f.UNIT_X.negate());
                walkDirection.subtractLocal(projection.multLocal(2f));
            }
        }
        else{// last direction was -Z
            //check if current walkDirectio is to left
            if(walkDirection.x <= 0){
                projection = walkDirection.project(Vector3f.UNIT_Z.mult(-1f));
                walkDirection.subtractLocal(projection.multLocal(2f)).negateLocal();
            }
            else{// walk direction is to right
                projection = walkDirection.project(Vector3f.UNIT_Z.mult(-1f));
                walkDirection.subtractLocal(projection.multLocal(2f));
            }
        }
    }
    
    public static void switchingToNegativeZ(Vector3f walkDirection, int direction,Vector3f viewDirection){
        Vector3f projection;
        //use the reflection formula V - 2Vp   //Vp is the projected vector
        if(direction == 0){
            // last direction was +X
            //check if current walkDirection is to left
            if(walkDirection.z <= 0){
                projection = walkDirection.project(Vector3f.UNIT_Z.mult(-1f));
                walkDirection.subtractLocal(projection.multLocal(2f)).negateLocal();
            }
            else{//current walk Direction is to right
                projection = walkDirection.project(Vector3f.UNIT_Z);
                walkDirection.subtractLocal(projection.multLocal(2f));
            }
        }
        else{
            //last direction was -X
            // check if current walk Direction is to the right
            if(walkDirection.z <= 0){
                projection = walkDirection.project(Vector3f.UNIT_Z.mult(-1f));
                walkDirection.subtractLocal(projection.multLocal(2f)).negateLocal();
            }
            else{// current walkDirection is to the left
                projection = walkDirection.project(Vector3f.UNIT_Z);
                walkDirection.subtractLocal(projection.multLocal(2f));
            }
        }
    }
    
    public static void switchingToNegativeX(Vector3f walkDirection, int direction,Vector3f viewDirection){
        Vector3f projection;
        //use the reflection formula V - 2Vp   //Vp is the projected vector
        if(direction == 0){
            //last direction was -Z
            //check if current walk direction is to right
            if(walkDirection.x <= 0){
                projection = walkDirection.project(Vector3f.UNIT_Z.mult(-1f));
                walkDirection.subtractLocal(projection.multLocal(2f)).negateLocal();
            }
            else{// current walk Direction is to left
                projection = walkDirection.subtractLocal(Vector3f.UNIT_Z.mult(-1));
                walkDirection.subtractLocal(projection.multLocal(2f));
            }
        }
        else{
            // last direction was +Z
            //check if current walkDirection is to right
            if(walkDirection.x <= 0){
                projection = walkDirection.project(Vector3f.UNIT_Z);
                walkDirection.subtractLocal(projection.multLocal(2f));
            }
            else{//current walk direction is to left
                projection = walkDirection.project(Vector3f.UNIT_Z);
                walkDirection.subtractLocal(projection.multLocal(2f)).negateLocal();
            }
        }
    }
    
    public static float lerp(float newValue, float goalValue,float alpha){
        
        return newValue = newValue * alpha + goalValue * (1-alpha);
    }
    
    public static boolean withinRange(float value, float goal, float range){
        return (value >= goal-range && value <= goal+range);
    }
    
    public static float absDifference(float a, float b){
        if(a > b)
            return a-b;
        else
            return b-a;
    }
    
    public static float camLerp(float value,float max){
        if(value/max >.99)
            return .99f;
        //else if(value/max > .2f)
            //return 0f;
        return value/max;
    }
    public static float playerLerp(float value,float max){
        float alpha = value/max;
        if(alpha>.8f)
            return .2f;
        return 1-(alpha);
    }
    
    
    public static float easeIn(float time, float begin, float end, float duration){
        return end*(time/duration) * time + begin;
    }
    
    
}
