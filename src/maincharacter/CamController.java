/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincharacter;

import maincharacter.PlayerListener;
import controllers.ControllerMath;
import controllers.Controller;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import guisystem.StatsManager;
import levels.NegativeZRunningAlg;
import levels.PositiveZRunningAlg;
import levels.RunningAlg;
import levels.TrackManager;
import mygame.GameState;
import mygame.GameStateManager;
import mygame.PlayState;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class CamController {
    static float position=0;
    static float max = 6f;
    static float min = -6f;
    static float acc = 0;
    static float accGoal=.5f;
    static float rateLock = 1/60f;
    static boolean positive = true,isReset = false;
    static float base = 6f;
    static float zPosition = 0;
    static RunningAlg lastRunningAlg = new PositiveZRunningAlg();
    static float begin = 0;
    static float speed = 70f;
    
    public static Vector3f lerp(float value, float change,RunningAlg currentRunningAlg,float gravityCenter){
   
        //base+=.2f;
        /*float speed = (base*PlayState.tpf);
        if(change > 0.5f){
            if(positive){
                acc =0;
                positive = false;
            }
            acc = ControllerMath.lerp(acc/2, accGoal/2, (.92f + .92f*Main.tpf));
            float acc2 = ControllerMath.camLerp(change-.05f, Controller.GRAVITY_MAX-0.5f);
            acc2+=1f;
            acc*=acc2;
            
            position+=speed;//ControllerMath.lerp(position, position+speed, (.2f + .2f*Main.tpf));
        }
        else if(change<-0.5f){
            if(!positive){
                acc =0;
                positive = true;
            }
            acc = ControllerMath.lerp(acc/2, accGoal/2, (.92f+.92f*Main.tpf));
            float acc2 = ControllerMath.camLerp(change+0.5f, Controller.GRAVITY_MIN+0.5f);
            acc2+=1f;
            acc*=acc2;
            
            position-=speed;//ControllerMath.lerp(position, position-speed, (.2f + .2f*Main.tpf));
        }*/
        
        /*if(change > 0f){
            if(positive){
                acc=0;
                positive = false;
            }
            if(change < 0.5f)
                acc=.0f;
            else  if(acc < .3f)
            acc+=.01f;
        
            PlayerListener.bikerControl.setWalkDirection(new Vector3f(acc,0f,1f));
            position = PlayerListener.bikerControl.getPhysicsLocation().x;
       }
        else if(change < -0f){
            if(!positive){
                acc=0;
                positive = true;
            }
            if(change > -0.5f)
                acc=.0f;
            else if(acc < .3f)
                acc+=.01f;
            PlayerListener.bikerControl.setWalkDirection(new Vector3f(-acc,0,1f));
            position = PlayerListener.bikerControl.getPhysicsLocation().x;
        }*/
        //if(positive)
            //PlayerListener.bikerControl.setWalkDirection( Vector3f.ZERO);
        //else
            //PlayerListener.bikerControl.setWalkDirection(Vector3f.ZERO);
        position = PlayerListener.bikerControl.getPhysicsLocation().x;
       
        if(!lastRunningAlg.equals(currentRunningAlg) || isReset){
            zPosition = 0;//reset  for walking direction
            lastRunningAlg = currentRunningAlg;
            isReset = false;
        }
        //position = change;
        float z =speed * PlayState.tpf;
        zPosition+=z;
        float gravityPer = 12f/4f;
        float x;
        if(FastMath.abs(Controller.gravityChange[0])>.1f)
             x= change*gravityPer;
        else 
            x = change*gravityPer;//PlayerListener.bikerControl.getPhysicsLocation().x;
        
        //float begin;
        //if(TrackManager.direction.equals(Direction.ZPOSITIVE) || TrackManager.direction.equals(Direction.ZNEGATIVE))
           // begin = PlayerListener.bikerControl.getPhysicsLocation().x-gravityCenter;
        //else 
            //begin = PlayerListener.bikerControl.getPhysicsLocation().z-gravityCenter;
        //System.out.println("Begin in camController is    "+begin);System.out.println("gravityCenter in camController is    "+gravityCenter);
        //System.out.println("X in camController is    "+x);
        //System.out.println("End in camController is    "+x);
        
        //float g = ControllerMath.easeIn(.2f+(FastMath.abs(Controller.gravityChange[0])*.2f),begin , x, 1f);
         float g = ControllerMath.easeIn(.45f,0, x-begin, 1f);

//if(g> max){
           // g = max;
           // positive = false;
        //}
        //else if(g < min){
            //g = min;
            //positive = true;
        //}
         float f = g+begin;
         Vector3f vec = new Vector3f(f,PlayerListener.bikerControl.getPhysicsLocation().y,zPosition);
         begin = f;
         //keep track of distance traveled
         if(GameStateManager.getCurrentState().equals(GameState.RUN)
                 || GameStateManager.getCurrentState().equals(GameState.RESUMEPLAY))
             StatsManager.getCurrentRunStats().addDistanceTraveled(z);
        return vec;
    }
    
    public static void movePlayer(float offset){
        zPosition+=offset;
    }
    
    public static void resetRunningAlg(){
         isReset = true;
         begin = 0;
    }
}
