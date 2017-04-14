/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.util.HashMap;
import static levels.PositiveZRunningAlg.direction;
import levels.RunningAlg;
import levels.TrackManager;
import mygame.GameState;
import mygame.GameStateManager;
import obstaclesystem.ObstacleSettings.ObstacleAlg;
import obstaclesystem.ObstacleSettings.ObstaclePlacingConst;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.PointGroupShape;
import pointsSystem.PointSystem;
import pointsSystem.PointsGroup;
import pointsSystem.PointsNode;

/**
 *
 * @author Miguel Martrinez
 */
public final class ObstacleAndPointSpawner {
    public static Vector3f trackOne;
    public static Vector3f trackTwo;
    public static Vector3f trackThree;
    public static Vector3f[] tiles;
    public static Vector3f[] tracks;
    public static RunningAlg runningAlg;
    
    
    public static void setTracks(Vector3f[] tiles, RunningAlg runningAlg){
        ObstacleAndPointSpawner.tiles = tiles;
        trackTwo = tiles[0];
        trackOne = runningAlg.setLeftTrack(trackTwo.clone());
        trackThree = runningAlg.setRightTrack(trackTwo.clone());
        tracks = new Vector3f[]{trackOne,trackTwo,trackThree};
        ObstacleAndPointSpawner.runningAlg = runningAlg;
    }
    
    public static void spawn(ObstacleSystem obstacleSystem,PointSystem pointSystem,int roadIndex){
        ObstaclePlacingConst placingConst;
        if(GameStateManager.currentGameState.equals(GameState.TUTORIAL)
                || GameStateManager.currentGameState.equals(GameState.TUTORIALINTRO))//tutorial mode is on 
            placingConst = ObstacleAlg.HighDifficulty.getTutorialLocations();
        else
            placingConst = ObstacleAlg.HighDifficulty.getLocations();
        ObstacleNode[] obstacleArray = new ObstacleNode[placingConst.array.length];
        for(int i = 0; i < placingConst.array.length; i ++){
            ObstacleNode obstacle;
            if(GameStateManager.getCurrentState().equals(GameState.TUTORIAL)
                    || GameStateManager.getCurrentState().equals(GameState.TUTORIALINTRO))
                obstacle = getTutorialObstacle(i,obstacleSystem);
            else
                obstacle = obstacleSystem.getPool().releaseStatic(FastMath.rand.nextInt(obstacleSystem.getPool().getSaticTemplateSize()));
            Vector3f location = tiles[placingConst.array[i]].clone();
            obstacle.setLocalTranslation(location.x , location.y, location.z);
            runningAlg.rotate(obstacle);
           
            obstacleArray[i] = obstacle;
        }
        obstacleSystem.setRoad(runningAlg.getDirection(), roadIndex, obstacleArray,tracks);
        for(int i = 0; i < placingConst.array.length; i++){
            setObstacleOnTrack(obstacleArray[i].getNode().getLocalTranslation(),roadIndex,obstacleArray[i].getDescription());
        }
        
        if(GameStateManager.getCurrentState().equals(GameState.TUTORIAL)
                || GameStateManager.getCurrentState().equals(GameState.TUTORIALINTRO))
            return;
        
        HashMap<Integer,PointsGroup> pointsMap = new HashMap<Integer,PointsGroup>();
        for(int j = 0; j < 9; j++){
          PointsGroup group = pointSystem.getPointSpawningAlg().getPointSettings().generatePointsGroup();
          group.setShape(PointGroupShape.NORMAL);
          
          pointsMap.put(j, group);
          Vector3f groupLocation = checkObstacleMatch(j,placingConst,obstacleArray,group);
          
          if(groupLocation != null)
              group.setVectorLocation(runningAlg.getDirection(), groupLocation, 3f);
          else{
            //group.setVectorLocation(runningAlg.getDirection(), tiles[j], 3f);
            Vector3f loc = setPointsOnTrack(tiles[j].clone(),roadIndex,"points");
            group.setVectorLocation(runningAlg.getDirection(), loc, 3f);
          }
          for(PointsNode pointNode: group.getPointObjectBag().getPointObjectsList())
              runningAlg.rotate(pointNode);
         // group.attachToRootNode();
          pointSystem.addGroup(group);
        }
         pointSystem.setRoad(roadIndex,pointsMap);
    }
    
    public static void setObstacleOnTrack(Vector3f location,int roadIndex,ObstacleDescription description){
        int trackLoc = FastMath.rand.nextInt(3);
        if(!description.equals(ObstacleDescription.NOPAL))
            trackLoc = 1;
       runningAlg.changeObstacleTrack(location, trackLoc,roadIndex);
    }
    
    public static Vector3f setPointsOnTrack(Vector3f location, int roadIndex,String description){
        int trackLoc = FastMath.rand.nextInt(3);
       return runningAlg.changeObstacleTrack(location, trackLoc,roadIndex);
    }
    
    public static Vector3f checkObstacleMatch(int index,ObstaclePlacingConst placingConst,ObstacleNode[] obstacleArray,PointsGroup group){
        int i = 0;
        for(int j: placingConst.array){
            if(j == index){
                if(obstacleArray[i].getType().equals(ObstacleSettings.ObstacleTypes.Jumping))
                    group.setShape(PointGroupShape.SPLINE);
                return obstacleArray[i].getNode().getLocalTranslation();
            }
            
            i++;
         }
        return null;
    }
    
    public static ObstacleNode getTutorialObstacle(int index, ObstacleSystem obstacleSystem){
        ObstacleSettings.ObstacleTypes type;
        if(index == 0)
            type = ObstacleSettings.ObstacleTypes.Ducking;
        else 
            type = ObstacleSettings.ObstacleTypes.Jumping;
        return obstacleSystem.getPool().releaseStatic(type);
    }
        
}
