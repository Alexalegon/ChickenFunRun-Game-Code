/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import aicharacters.ChickenListener;
import com.jme3.bullet.BulletAppState;
import static com.jme3.math.Spline.SplineType.CatmullRom;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import guisystem.HomeScreen;
import guisystem.Stats;
import guisystem.StatsManager;
import listenersystem.ListenerHandler;
import mygame.PlayState;
import maincharacter.PlayerListener;
import mygame.GameStateManager;
import obstaclesystem.ObstacleRoad;
import obstaclesystem.ObstacleSystem;
import pointsSystem.PointObjectSettings.Direction;
import pointsSystem.PointSystem;
import pointsSystem.PointsRoad;

/**
 *
 * @author Miguel Martrinez
 */
public class PathConstructor {
int roads;
public static int currentRoad;
public static int roadIndex;
public static PointSystem pointSystem;
public static ObstacleSystem obstacleSystem;
public static Road[] roadArray;
public static RunningAlg runningAlg;
public static RunningAlg currentRunningAlg;
public static NegativeXRunningAlg xNegAlg;
public static PositiveXRunningAlg xPosAlg;
public static PositiveZRunningAlg zPosAlg;
public static NegativeZRunningAlg zNegAlg;
public static LevelManager levelManager;
Stats stats;
Node worldNode;
Node characterNode;
public static Direction direction;
public static BulletAppState bulletAppState;
public static Vector3f initialLocationVector = new Vector3f(0,0.01f,-1f);
public static Vector3f pathLocation;

public PathConstructor(LevelManager levelManager) {
    this.levelManager = levelManager;
    characterNode = PlayerListener.character;
    worldNode = levelManager.playState.node;
    bulletAppState = levelManager.playState.bulletAppState;
    DirectionManager.setInitDirection(Direction.ZPOSITIVE);
    roadIndex = -1;
    currentRoad = 0;
    pathLocation = initialLocationVector.clone();
    direction = Direction.ZPOSITIVE;
    TrackManager.runningAlg = RunningAlgsFacility.zPosAlg;
    }
    
public void setPointSystem(PointSystem pointSystem,ObstacleSystem obstacleSystem){
    this.pointSystem = pointSystem;
    this.obstacleSystem = obstacleSystem;
    initRunningAlgs();
    initRoadArray();
}

public void initRoadArray(){
    roadArray = new Road[4];
    for(int i = 0; i < 4; i++)
        roadArray[i] = new Road(stats,pointSystem,characterNode,worldNode,bulletAppState,5f,i);
}

public void initRunningAlgs(){
    xNegAlg = new NegativeXRunningAlg(pointSystem,obstacleSystem,levelManager.pool);
    xPosAlg = new PositiveXRunningAlg(pointSystem,obstacleSystem,levelManager.pool);
    zPosAlg = new PositiveZRunningAlg(pointSystem,obstacleSystem,levelManager.pool);
    zNegAlg = new NegativeZRunningAlg(pointSystem,obstacleSystem,levelManager.pool);
    runningAlg = zPosAlg;
}

public static void setRunningAlg(){
    if(DirectionManager.direction.equals(Direction.ZPOSITIVE))
        runningAlg = zPosAlg;
    else if(DirectionManager.direction.equals(Direction.ZNEGATIVE))
        runningAlg = zNegAlg;
    else if(DirectionManager.direction.equals(Direction.XPOSITIVE))
        runningAlg = xPosAlg;
    else
        runningAlg = xNegAlg;
}

public static void showRoad(boolean initiate){
    roadIndex++;
    if(roadIndex >= roadArray.length)
        roadIndex = 0;
    setRunningAlg();
    runningAlg.showRoad(initiate,levelManager.nodez,bulletAppState,roadArray[roadIndex],pathLocation);
    DirectionManager.goToNextDirection();
    
}
int roadCount = 0;
public void run(){//System.out.println("fffffffffffff "+ currentRoad+"    " + roadIndex + "      " + roadCount);
    if(roadArray[currentRoad].run("Middle") && !roadArray[currentRoad].isDone()){
        //if(!roadArray[currentRoad].isDone()){
        /*if(currentRoad == 0){
            roadArray[roadArray.length-1].hidePrefab();  
        }
        else{
        roadArray[currentRoad-1].hidePrefab();
        }*/
        //roadArray[currentRoad].hidePrefab();
        //add the wall to end of road
        if(currentRoad == roadArray.length-1)
            TrackManager.runningAlg.setWall(levelManager.pool.releaseWall(),roadArray[currentRoad].fields.lastTileLoc, roadArray[0].getDirection());
        else{
            if(TrackManager.runningAlg == null)
                throw new AssertionError();
            TrackManager.runningAlg.setWall(levelManager.pool.releaseWall(),roadArray[currentRoad].fields.lastTileLoc, 
                    roadArray[currentRoad+1].getDirection());
            //for(int i = 0; i< 2000; i++)
                //System.out.println(roadArray[currentRoad].fields.lastTileLoc);
        }
        //set road done to true to ensure only one pass through this "middle" check
        roadArray[currentRoad].setDone(true);
        //pointSystem.clearPointsRoads(currentRoad);
        pointSystem.clearRoad(currentRoad);
        obstacleSystem.clearObstacleRoads(currentRoad);
       //System.out.println("passedMiddle         " + roadArray[currentRoad].fields.middleTileLoc);
       // }
    }
    
    if(roadArray[currentRoad].runThroughTiles()){
        if(currentRoad == roadArray.length-1)
            roadArray[0].attachFirstTile();
        else
            roadArray[currentRoad+1].attachFirstTile();
    }
    
    //allow to turn
    if(roadArray[currentRoad].canTurnToChangeRoad() == 1 && roadArray[currentRoad].turnFlag){
        ListenerHandler.changeRoadFlag = true;
        PlayerListener.bikerControl.changingRoad = true;
    }
    //else
        //ListenerHandler.changeRoadFlag = false;
    /*if(roadArray[currentRoad].runTravelingRoad()==1){
        //roadArray[currentRoad].setDone(false);
        PlayerListener.bikerControl.changingRoad = false;//resets flag so trackLimits are enforced
        roadArray[currentRoad].turnIndex = 0;//resets turnFlag for just passed Road
        currentRoad++;
      
        if(currentRoad >= roadArray.length)
            currentRoad = 0;
        showRoad();
        obstacleSystem.setCurrentRoad(currentRoad);
        pointSystem.setCurrentRoad(currentRoad);
        TrackManager.setDirection(roadArray[currentRoad].getDirection());
    }*/
}

public static void playerPassedCurentRoad(){
    //reset outgoing road "middle" check
     roadArray[currentRoad].setDone(false);
     TrackManager.runningAlg.clearAddition();
     PlayState.chaseCam.resetAutomaticDuck();
        PlayerListener.bikerControl.changingRoad = false;//resets flag so trackLimits are enforced
        roadArray[currentRoad].turnIndex = 0;//resets turnFlag for just passed Road
        currentRoad++;
       
        if(currentRoad >= roadArray.length)
            currentRoad = 0;
        showRoad(false);
        roadArray[currentRoad].attachSecondTile();
        obstacleSystem.setCurrentRoad(currentRoad);
        pointSystem.setCurrentRoad(currentRoad);
        TrackManager.setDirection(roadArray[currentRoad].getDirection());
        roadArray[currentRoad].runAi();
        HomeScreen.detach();
        GameStateManager.f = true;
}
public static Road getCurrentRoad(){
    return roadArray[currentRoad];
}

public static void resetConstructor(){
    StatsManager.initNewRun();
    DirectionManager.setInitDirection(Direction.ZPOSITIVE);
    TrackManager.setDirection(Direction.ZPOSITIVE);
    PlayerListener.bikerControl.changingRoad = false;
    ListenerHandler.changeRoadFlag = false;
    for(Road road: roadArray)
        road.reset();
    roadIndex = -1;
    currentRoad = 0;
    pathLocation = initialLocationVector.clone();
    pointSystem.reset();
    obstacleSystem.reset();
    PrefabPool.resetPool();
    HomeScreen.hideWall();
    showRoad(true);
    showRoad(false);
}

public static ObstacleRoad getCurrentObstacleRoad(){
    return obstacleSystem.getCurrentRoad();
}

public static PointsRoad getCurrentPointsRoad(){
    return pointSystem.getCurrentRoad();
}

public static String getTurnDirection(){
    int next = currentRoad+1;
    if(next >= roadArray.length)
        next = 0;
    return RunningAlgsFacility.getAlgByDirection(roadArray[currentRoad].getDirection()).
            getTurnDirection(roadArray[next].getDirection());
}

}
