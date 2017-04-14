/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.math.Vector3f;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public final class TrackManager {
 public static final int TOTALTRACKS = 3;
 public static final int firstTrack = 0;
 public static final float TRACKGAP = 6.5f;
 public static final float OBSTACLEGAP = 5f;
 public static int currentTrack = 1;
 public static Direction direction = Direction.ZPOSITIVE;
 public static RunningAlg runningAlg;
 public static float CurrentGravity = 0f;
 public static float gravityPlayerSpeed = 0.2f;
 public static float gravityCameraSpeed = 0.2f;
 public static float lastGravityLocationAddition = 0f;
 public static Direction lastGravityDirection;
 public static boolean gravityChange = false;
 public static float lastLocationValue=0;
 public static float rightTrack;
 public static float leftTrack;
 public static float middleTrack;
 static{
     runningAlg = RunningAlgsFacility.zPosAlg;
 }
 public static Vector3f changeTrackRight(Vector3f playerLoc){
     currentTrack++;
     if(currentTrack >= TOTALTRACKS){
         currentTrack = 2;
         return playerLoc;
     }
     else
         return runningAlg.changeTrack(playerLoc,currentTrack);
         
 }
 
 public static Vector3f changeTrackLeft(Vector3f playerLoc){
     currentTrack--;
     if(currentTrack < 0){
         currentTrack = 0;
         return playerLoc;
     }
     else
         return runningAlg.changeTrack(playerLoc,currentTrack);
 }
 
 public static void setDirection(Direction direction){
     TrackManager.direction = direction;
     runningAlg = RunningAlgsFacility.getAlgByDirection(direction);
 }
 

 public static void setLastGravityAddition(Direction direction,float addition){
     lastGravityDirection = direction;
     lastGravityLocationAddition = addition;
 }
 
 public static void transferGravityAddition(Direction direction){
     
     gravityChange = false;
 }
 
 public static void setGravityChange(boolean change){
     gravityChange = change;
 }
 
 public static RunningAlg getCurrentRunningAlg(){
     return runningAlg;
 }
 
 public static int getMyLane(float lane){
     if(lane == getLeftTrack())
         return 0;
     else if(lane == getRightTrack())
         return 2;
     else return 1;
 }
 
 public static float getMiddleTrack(){
    return PathConstructor.getCurrentRoad().tracks.y;
 }
 
 public static float getLeftTrack(){
     return PathConstructor.getCurrentRoad().tracks.x;
 }
  public static float getRightTrack(){
     return PathConstructor.getCurrentRoad().tracks.z;
 }
  
  public static float getLaneViaIndex(int index){
      if(index == 0)
          return getLeftTrack();
      else if(index == 1)
          return getMiddleTrack();
      else
          return getRightTrack();
  }
}
