/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import aicharacters.ChickenListener;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.picking.BresenhamYUpGridTracer;
import controllers.Controller;
import java.util.HashMap;
import static levels.PositiveZRunningAlg.direction;
import maincharacter.CamController;
import mygame.CustomNode;
import mygame.PlayState;
import maincharacter.PlayerListener;
import obstaclesystem.ObstacleAndPointSpawner;
import obstaclesystem.ObstacleSystem;
import pointsSystem.PointObjectSettings.Direction;
import pointsSystem.PointSystem;
import pointsSystem.PointsGroup;

/**
 *
 * @author Miguel Martrinez
 */
public class NegativeXRunningAlg implements RunningAlg{
    public static final Direction direction = Direction.XNEGATIVE;
    PointSystem pointSystem;
    ObstacleSystem obstacleSystem;
    PrefabPool pool;
    PrefabRoad prefab;
float length;

    public NegativeXRunningAlg() {
    }


public NegativeXRunningAlg(PointSystem pointSystem,ObstacleSystem obstacleSystem,PrefabPool pool) {
        this.pointSystem = pointSystem;
        this.obstacleSystem = obstacleSystem;
        this.pool = pool;
    }
    @Override
    public boolean passedCheckpoint(Vector3f characterLoc, Vector3f checkPoint) {
        if(characterLoc.x <= checkPoint.x)
            return true;
        else 
            return false;
    }

    @Override
    public int runTravelingRoad(Vector3f characterLoc, Vector3f checkPoint) {
         if(characterLoc.x <= checkPoint.x &&(characterLoc.z >= checkPoint.z
                 || characterLoc.z <= checkPoint.y)){
             
            return 1;
         }
        else return 0;
    }
    
    public int canTurnToChangeRoad(Vector3f characterLoc, Vector3f checkPoint) {
         if(characterLoc.x <= checkPoint.x &&(characterLoc.z <= checkPoint.z
                 || characterLoc.z >= checkPoint.y))
            return 1;
        else return 0;
    }

    @Override
    public Vector3f[] getGroupLocations(float length, int divisor) {
        this.length = length;
        Vector3f[] array = new Vector3f[divisor];
        if(divisor == 1){
            Vector3f vec = new Vector3f(-length/2,0,0);
            array[0] = vec;
             }
        else{
        float unit = length/divisor;
       
        for(int i = 0; i < divisor; i++){
           Vector3f vector = new Vector3f(i*-unit,0,0);
           array[i] = vector;
       }
     } 
        return array;
    }

    @Override
    public Vector3f calculateRoadChangingFlag(float offset,float dimensions,Vector3f firstTile) {
        Vector3f vec = new Vector3f(-length+dimensions+offset,firstTile.z - offset,offset);
        return vec;
    }

    @Override
    public float getLength() {
       return length;
    }

    @Override
    public float calculateDistanceTraveled(Vector3f characterLoc, Vector3f startingLoc, float offset) {
        if(characterLoc.x >= startingLoc.x)
            return 0;
        float endSpot = (characterLoc.x - startingLoc.x) * -1;
        endSpot += offset;
        return endSpot;
    }

    @Override
    public void fillRoadTiles(BulletAppState bulletAppState,CollisionShape shape,Node node, Road road, Vector3f firstTile, int numberOfTiles, float dimensions) {
        Node bindingNode = new Node();
        for(int i = 0;i < numberOfTiles;i++){
                  Node nodes = (Node) node.clone();//nodes.setLocalRotation(RotationQuats.ROTATIONQUAT270);
                nodes.setLocalTranslation((--firstTile.x)*dimensions, firstTile.y*dimensions,firstTile.z*dimensions);
                bindingNode.attachChild(nodes);
                RigidBodyControl body = new RigidBodyControl(shape,0);
            nodes.addControl(body);
            bulletAppState.getPhysicsSpace().add(nodes);
                setRoadFields(nodes,road,i,numberOfTiles,dimensions);
        }
        road.attachToMotherNode(bindingNode);
    }

    @Override
    public void setRoadFields(Node nodes, Road road, int i, int numberOfTiles,float dimensions) {
       int middle = numberOfTiles/2;
      
        if(i==0){
            road.setfirstTileLocation(nodes.getLocalTranslation());
           roadScenePointObjects(nodes,road,i,numberOfTiles,dimensions);
        }
        else if(i== middle){
            road.setMiddleTileLocation(nodes.getLocalTranslation());
        }
        else if(i==numberOfTiles-1){
            road.setLastTileLocation(nodes.getLocalTranslation());
            
        }
    }

     public void roadScenePointObjects(Node nodes, Road road, int i, int numberOfTiles, float dimensions){
        //if(roadArray[roadIndex-1].getRoadLimits().getRoadSize().equals(RoadSize.SHORT)){
        RoadLimits.RoadSize roadSize = road.getRoadLimits().size;
        //System.out.println("TOTWL TIels " + road.totalLength);
        Vector3f[] locArray = road.getGroupLocations();
        pointSystem.setRoad(road.getIndex(),road.getRoadLimits().size.pointGroups);
        
        for(int j = 0; j < road.getRoadLimits().size.pointGroups; j++){
          PointsGroup group = pointSystem.getPointSpawningAlg().getPointSettings().generatePointsGroup();
          group.setVectorLocation(direction, locArray[j], dimensions/4);
          group.attachToRootNode();
          pointSystem.addGroup(group);
        }
    }
    
      @Override
    public void showRoad(boolean initiate,Node node, BulletAppState bulletAppState, Road road, Vector3f location) {
        road.setPrefabPool(pool);
        prefab = pool.releasePrefab();
       //if(FastMath.nextRandomInt(0, 3) == 1)
            road.setAi(true);
       
        
        road.setPrefab(prefab);
        road.setDirection(direction);
        float pointRoadDimensions = prefab.getRoadFields().tileDimensionsZ;
        int i = 0;
        for(Node nodes: prefab.tileList){
            float dimensions = prefab.zDimensions.get(i);
            nodes.setLocalTranslation((--location.x)*dimensions, location.y*dimensions,location.z*dimensions);
            nodes.getControl(RigidBodyControl.class).setPhysicsLocation(nodes.getLocalTranslation());
            nodes.getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT270);
            
            i++;
            bulletAppState.getPhysicsSpace().add(nodes);
        }
        
           
        Vector3f[] locArray = road.getGroupLocations();
        //pointSystem.setRoad(road.getIndex(),prefab.roadFields.limits.size.pointGroups);
        
        
        Vector3f[] vecArray = new Vector3f[prefab.tileList.size()];
        int f = 0;
        for(Node node2: prefab.tileList){
            vecArray[f] = node2.getLocalTranslation();
            f++;
        }
        ObstacleAndPointSpawner.setTracks(vecArray, this);
        ObstacleAndPointSpawner.spawn(obstacleSystem, pointSystem, road.index);
        
        prefab.fillFinalRoadFields();
        road.setRoadFields(prefab.getRoadFields());
         road.setTiles(prefab.tileList,initiate);
         
    }

    @Override
    public boolean withinProximityOfObstacle(Vector3f characterLoc, Vector3f obsLoc) {
        if(characterLoc.x < obsLoc.x + 10f && characterLoc.x > obsLoc.x - 10f )
            return true;
        return false;
    }

    @Override
    public Vector3f changeTrackRight(Vector3f playerLoc) {
        playerLoc.z = playerLoc.z - TrackManager.TRACKGAP;
        return playerLoc;
    }

    @Override
    public Vector3f changeTrackLeft(Vector3f playerLoc) {
        playerLoc.z = playerLoc.z + TrackManager.TRACKGAP;
        return playerLoc;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Vector3f setLeftTrack(Vector3f middleLane) {
        middleLane.z += TrackManager.OBSTACLEGAP;
        return middleLane;
    }

    @Override
    public Vector3f setRightTrack(Vector3f middleLane) {
        middleLane.z -= TrackManager.OBSTACLEGAP;
        return middleLane;
    }

    @Override
    public Vector3f changeTrack(Vector3f playerLoc, int index) {
        if(index == 0)
            playerLoc.z = ObstacleSystem.getCurrentRoad().getMiddleLane().z + TrackManager.TRACKGAP;
        else if(index == 1)
            playerLoc.z = ObstacleSystem.getCurrentRoad().getMiddleLane().z;
        else
            playerLoc.z = ObstacleSystem.getCurrentRoad().getMiddleLane().z - TrackManager.TRACKGAP;
        return playerLoc;
    }

    @Override
    public Vector3f changeObstacleTrack(Vector3f obstacleLoc, int trackIndex, int roadIndex) {
        if(trackIndex == 0)
            obstacleLoc.z = ObstacleSystem.obstacleRoads.get(roadIndex).getLeftLane().z;
        else if(trackIndex == 1)
            obstacleLoc.z = ObstacleSystem.obstacleRoads.get(roadIndex).getMiddleLane().z;
        else
            obstacleLoc.z = ObstacleSystem.obstacleRoads.get(roadIndex).getRightLane().z;
        return obstacleLoc;
    }
    
    public void checkTrackBounds(Vector3f characterLoc){
        Vector3f leftVec = ObstacleSystem.obstacleRoads.get(ObstacleSystem.currentRoad).getLeftLane();
        Vector3f rightVec = ObstacleSystem.obstacleRoads.get(ObstacleSystem.currentRoad).getRightLane();
        if(characterLoc.z >= leftVec.z)
            characterLoc.z = leftVec.z;
        else if(characterLoc.z <= rightVec.z)
            characterLoc.z = rightVec.z;
        PlayerListener.bikerControl.setPhysicsLocation(characterLoc);
    }

    @Override
    public void rotate(Node node) {
        node.setLocalRotation(RotationQuats.ROTATIONQUAT270);
    }

    @Override
    public void rotate(CustomNode node) {
        node.setLocalRotation(RotationQuats.ROTATIONQUAT270);
    }

    @Override
    public void setAccViewDirection(Vector3f viewDirection, float[] linearAcc) {
        viewDirection.z+=linearAcc[0];
    }
float lastZAddition = 0;
float lastZCameraAddition = 0f;
float previousAddition = 0;
    @Override
    public void setPlayerLocTroughGravity(Vector3f playerLoc, float[] gravity, float[] gravityChange, float gravityBounds) {
        /*float z = PathConstructor.getCurrentRoad().fields.firstTileLoc.z;//center of gravity
        float unitPerGravity = TrackManager.TRACKGAP/gravityBounds;
        float alpha = 0.95f;
        float zAddition = gravity[0] * unitPerGravity; 
        //if(gravityChange[0] > .1f || gravityChange[0] < -.1f){
            lastZAddition =  (lastZAddition * alpha )+  zAddition * (1f - alpha);
            playerLoc.z = z + lastZAddition;
        //}
                */
         float z = PathConstructor.getCurrentRoad().fields.firstTileLoc.z;
         //float x = PathConstructor.getCurrentRoad().fields.firstTileLoc.x+PathConstructor.getCurrentRoad().fields.tileDimensionsZ;
         float x = Controller.positionAtRoadChanged.x;
        Vector3f b = CamController.lerp(0,gravity[0],this,z);
        playerLoc.z = z + b.x;
        playerLoc.x = x - b.z;
        //System.out.println(this+"    "+playerLoc.z);
        //System.out.println("Center of tile      " + z);
        float posLimit = z + TrackManager.TRACKGAP;
        float negLimit = z - TrackManager.TRACKGAP; 
        
        if(playerLoc.z > posLimit)
            playerLoc.z = posLimit;
        else if(playerLoc.z < negLimit)
            playerLoc.z = negLimit;
          
          PlayerListener.bikerControl.setPhysicsLocation(playerLoc);
       TrackManager.CurrentGravity = playerLoc.z;
    }
    
    @Override
     public void clearAddition(){
         previousAddition = 0;
     }

    @Override
    public void setWall(Node node, Vector3f location, Direction nextDirection) {
        PlayState.bulletAppState.getPhysicsSpace().remove(node);
        PlayState.bulletAppState.getPhysicsSpace().remove(node.getChild("hay"));
        if(nextDirection.equals(Direction.ZPOSITIVE)){
            //node.setLocalTranslation(location.x ,location.y,location.z);//- TrackManager.TRACKGAP
            //node.setLocalRotation(RotationQuats.ROTATIONQUAT270);
            node.getControl(RigidBodyControl.class).setPhysicsLocation(location);
            node.getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT270);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getControl(RigidBodyControl.class));
            
            Vector3f vector = new Vector3f(location.x-12f,location.y,location.z);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsLocation(vector);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT270);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getChild("hay").getControl(RigidBodyControl.class));
            
        }
        else{
            //node.setLocalTranslation(location.x ,location.y,location.z);//+ TrackManager.TRACKGAP
            //node.setLocalRotation(RotationQuats.ROTATIONQUAT90);
             node.getControl(RigidBodyControl.class).setPhysicsLocation(location);
            node.getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT90);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getControl(RigidBodyControl.class));
            
            Vector3f vector = new Vector3f(location.x-12f,location.y,location.z);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsLocation(vector);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT90);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getChild("hay").getControl(RigidBodyControl.class));
        }
        PlayState.rootNode.attachChild(node);
    }
    
    @Override
    public void runAi(Road road, float speed) {
       Vector3f location = ChickenListener.chicken.getLocalTranslation().clone();
        float x = road.getRoadFields().firstTileLoc.x - ChickenListener.walk(speed);
        ChickenListener.chicken.setLocalTranslation(x, location.y, road.getRoadFields().firstTileLoc.z);
    }

    @Override
    public boolean withinRange(Vector3f start, Vector3f target, float range) {
        if(start.x <= target.x)
            return false;
        return (target.x - start.x >= -range);
    }

    @Override
    public Vector3f getTracks(Vector3f tracks, Vector3f tile) {
        tracks.y = tile.z;
        tracks.x = tile.z + TrackManager.TRACKGAP;
        tracks.z = tile.z - TrackManager.TRACKGAP;
        return tracks;
    }

    @Override
    public String getTurnDirection(Direction nextDirection) {
        if(nextDirection.equals(Direction.ZPOSITIVE))
            return "left";
        return "right";
    }

    @Override
    public boolean withinProximityOfObstacle(Vector3f goal, Vector3f startingLoc, float proximity) {
        if(startingLoc.x - goal.x > 0)
            return false;
        return startingLoc.x - goal.x >= -proximity;
    }
    
        @Override
    public void spawnAhead(float addition) {
        Vector3f location = PlayerListener.bikerControl.getPhysicsLocation();
        location.x -= addition;
        PlayerListener.bikerControl.setPhysicsLocation(location);
    }
}
