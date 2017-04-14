/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import audiosystem.AudioManager;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import levels.PathConstructor;
import levels.RotationQuats;
import levels.RunningAlgsFacility;
import levels.TrackManager;
import listenersystem.ListenerHandler;
import mygame.PlayState;
import maincharacter.PlayerListener;
import mygame.SpeedSettings;
import static listenersystem.ListenerHandler.changeRoadFlag;
import static maincharacter.PlayerListener.bikerControl;
import static mygame.PlayState.cam;
import static mygame.PlayState.chaseCam;
import obstaclesystem.ObstacleSystem;

import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class Controller {
    public static int rotationIndex = 0;
    public static float speed = SpeedSettings.ONE;
    public static Vector3f viewDirection = Vector3f.UNIT_Z;
    public static Vector3f walkDirection = Vector3f.UNIT_Z;
    public static Direction camDirection = Direction.ZPOSITIVE;
    public static boolean accelerometerReset = true,trueBool = false;
    public static float[] gravity = new float[]{0,0,0};
    public static float[] gravityChange = new float[]{0,0,0};
    public static final float GRAVITY_MAX = 2f; 
    public static final float GRAVITY_MIN = -2f;
    public static final float GRAVITY_RANGE = 4f;
    public static boolean roadChanged = false;
    public static Vector3f positionAtRoadChanged; 
    
    public static void handleEvent(String name, TouchEvent event, float tpf){
        if(event.getType().equals(TouchEvent.Type.FLING))
                    if(event.getDeltaX() < -10f)
                        turnRight();
                    else if(event.getDeltaX() > 10f)
                        turnLeft();
    }
    
    public static void turnLeft(){
      
      Vector3f localSpeed;
      cam.setRotation(checkRotation(0));  
      if(checkRoadChange()){
          bikerControl.setGradualRotation(viewDirection);
          PlayState.chaseCam.setGradualRotation(viewDirection);
          bikerControl.setWalkDirection(Vector3f.ZERO);
          roadChanged = true;
          positionAtRoadChanged = bikerControl.getPhysicsLocation().clone();
      }
      else{
      bikerControl.setViewDirection(viewDirection);
      }
      localSpeed = viewDirection.mult(speed);
     
      //bikerControl.setWalkDirection(walkDirection);
      
  }
  public static void turnRight(){
      
       Vector3f localSpeed;
        cam.setRotation(checkRotation(1));
      
      
      if(checkRoadChange()){
        bikerControl.setGradualRotation(viewDirection);
        PlayState.chaseCam.setGradualRotation(viewDirection);
        bikerControl.setWalkDirection(Vector3f.ZERO);
        roadChanged = true;
        positionAtRoadChanged = bikerControl.getPhysicsLocation().clone();
      }
      else{
       bikerControl.setViewDirection(viewDirection);
      }
       //bikerControl.setWalkDirection(walkDirection);
  }
  
  public static boolean checkRoadChange(){
      if(ListenerHandler.changeRoadFlag){
          PathConstructor.playerPassedCurentRoad();
          ListenerHandler.changeRoadFlag = false;
          return true;
      }
         return false;
  }
  
  public static Quaternion checkRotation(int direction){
      Quaternion quat = new Quaternion();
      if(direction == 0)
      rotationIndex++;
      else 
          rotationIndex--;
      if(rotationIndex >= 4)
          rotationIndex=0;
      else if(rotationIndex <= -1)
          rotationIndex=3;
  
      if(rotationIndex == 0){
          
          quat = posZSettings();
      }
      else if(rotationIndex == 1){
          quat = posXSettings();
          
      }
      else if(rotationIndex == 2){
          quat = negZSettings();
          
      }
      else if(rotationIndex==3){
          quat = negXSettings();
          
      }
      return quat;
  }
  
  public static Quaternion posZSettings(){
      
          viewDirection = Direction.ZPOSITIVE.vector;
          walkDirection = Vector3f.UNIT_Z;
          //ControllerMath.calculateWalkDirection(walkDirection, direction, viewDirection);
          camDirection = Direction.ZPOSITIVE;
          chaseCam.setDefaultHorizontalRotation(FastMath.DEG_TO_RAD*-90f);
          chaseCam.setDirection(Direction.ZPOSITIVE);
          return RotationQuats.ROTATIONQUAT360;
  }
  public static Quaternion negZSettings(){
      
          viewDirection = Direction.ZNEGATIVE.vector;
          walkDirection = Vector3f.UNIT_Z.negate();
          //ControllerMath.calculateWalkDirection(walkDirection, direction, viewDirection);
          camDirection = Direction.ZNEGATIVE;
          chaseCam.setDefaultHorizontalRotation(FastMath.DEG_TO_RAD*-270f);
          chaseCam.setDirection(Direction.ZNEGATIVE);
          return RotationQuats.ROTATONQUAT180;
  }
  public static Quaternion posXSettings(){
      
          viewDirection = Direction.XPOSITIVE.vector;
          walkDirection = Vector3f.UNIT_X;
          //ControllerMath.calculateWalkDirection(walkDirection, direction, viewDirection);
          camDirection = Direction.XPOSITIVE;
          chaseCam.setDefaultHorizontalRotation(FastMath.DEG_TO_RAD*-180f);
          chaseCam.setDirection(Direction.XPOSITIVE);
          return RotationQuats.ROTATIONQUAT90;
  }
  public static Quaternion negXSettings(){
      
          viewDirection = Direction.XNEGATIVE.vector;
          walkDirection = Vector3f.UNIT_X.negate();
          //ControllerMath.calculateWalkDirection(walkDirection, direction, viewDirection);
          camDirection = Direction.XNEGATIVE;
          chaseCam.setDefaultHorizontalRotation(FastMath.DEG_TO_RAD*-0f);
          chaseCam.setDirection(PointObjectSettings.Direction.XNEGATIVE);
          return RotationQuats.ROTATIONQUAT270;
  }
  
    public static void walk(){
      //bikerControl.setWalkDirection(viewDirection.mult(.6f));

  }
  
  public static void stoppedWalking(){
      bikerControl.setWalkDirection(Vector3f.ZERO);
  }
  
  public static void jump(){
      JumpInfo.registerJump(bikerControl.getPhysicsLocation());
      bikerControl.jump();
      bikerControl.setGravity(50f);
      PlayerListener.animChannel.setAnim("jump");
      AudioManager.jumpSound.playInstance();
  }
  
  public static void duck(){
      DuckInfo.registerDuck(bikerControl.getPhysicsLocation());
      PlayerListener.animChannel.setAnim("duck");
      PlayerListener.animChannel.setSpeed(1f);
      PlayState.chaseCam.setCameraHeight(-11.5f);
      bikerControl.scaleBounds(1f,.05f,1f);
  }
  
  public static void restartPlay(){
      //if(PathConstructor.getCurrentRoad().getRoadFields().getTileLocations().get(0)!= null)
      
      PathConstructor.resetConstructor();
      
  }
  
  public static void handleAccelerometer(float[] values){
      
      //System.out.println("X VALUE =   " + values[0]);
       // System.out.println("Y VALUE =   " + values[1]);
        //System.out.println("Z VALUE =   " + values[2]);
        /*if(values[0] > -.5f && values[0] < .5f)
            accelerometerReset = true;
        if(values[0] <= -5f && accelerometerReset){
            bikerControl.setPhysicsLocation(TrackManager.changeTrackRight(bikerControl.getPhysicsLocation()));
            accelerometerReset = false;
        }
        else if(values[0] >= 5f && accelerometerReset){
            bikerControl.setPhysicsLocation(TrackManager.changeTrackLeft(bikerControl.getPhysicsLocation()));
            accelerometerReset = false;
        }*/
        if(PlayState.start||trueBool){
            Vector3f location = bikerControl.getPhysicsLocation();
            //float[] linearAcc = filter(values);
            //check if accelerometer is out of bounds
            gravityChange = filterForGravity(values);
            if(gravity[0] < -2f)
                gravity[0] = -2f;
            if(gravity[0] > 2f)
                gravity[0] = 2f;
            //linearAcc[0]*= 2f;//.2f;
            gravityChange[0] *= 2f;
            //RunningAlgsFacility.getAlgByDirection(PathConstructor.getCurrentRoad().getDirection())
                    //.setAccViewDirection(location,gravityChange);//viewDirection.x += linearAcc[0];
            
            //RunningAlgsFacility.getAlgByDirection(TrackManager.direction)
                    //.setPlayerLocTroughGravity(location, gravity, gravityChange, 2f);uncomment !!!!!!!!!!!
            
            //walkDirection.normalizeLocal();
            //bikerControl.setWalkDirection(walkDirection);
              
             
            //bikerControl.setPhysicsLocation(location);//Uncomentthisto movecharacter!!!!!!!!!!!!!!
        }
  }
  
  public static float[] filter(float values[]){
      //low pass filter to isolate gravity
     final float alpha = 0.5f;
      gravity[0] = alpha * gravity[0] + (1-alpha) * values[0];
      gravity[1] = alpha * gravity[1] + (1-alpha) * values[1];
      gravity[2] = alpha * gravity[2] + (1-alpha) * values[2];
      
      //high pass filter to isolate acceleration
      float[] linearAcc = new float[3];
      linearAcc[0] = values[0] - gravity[0];
      linearAcc[1] = values[1] - gravity[1];
      linearAcc[2] = values[2] - gravity[2];
      return linearAcc;
  }
  public static float[] filterForGravity(float[] values){
      float[] oldGravity = gravity.clone();
      
        //low pass filter to isolate gravity
     final float alpha = 0.90f;
      gravity[0] = alpha * gravity[0] + (1-alpha) * values[0];
      gravity[1] = alpha * gravity[1] + (1-alpha) * values[1];
      gravity[2] = alpha * gravity[2] + (1-alpha) * values[2];
      
      float[] gravityChange = new float[3];
      gravityChange[0] = gravity[0] - oldGravity[0];
      gravityChange[1] = gravity[1] - oldGravity[1];
      gravityChange[2] = gravity[2] - oldGravity[2];
      
      
      return gravityChange;
  }
  
  public static float[] attenuateLinearAcc(float[] linearAcc){
      for(float i: linearAcc)
          i*=.01;
      return linearAcc;
  }
}
