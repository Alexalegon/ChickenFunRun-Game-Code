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
import levels.RoadLimits.RoadSize;
import maincharacter.CamController;
import controllers.ControllerMath;
import mygame.CustomNode;
import mygame.Main;
import mygame.PlayState;
import maincharacter.PlayerListener;
import obstaclesystem.ObstacleAndPointSpawner;
import obstaclesystem.ObstacleNode;
import obstaclesystem.ObstacleSystem;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.Direction;

import pointsSystem.PointSystem;
import pointsSystem.PointsGroup;

/**
 *
 * @author Miguel Martrinez
 */
public class PositiveZRunningAlg implements RunningAlg{
    public static final PointObjectSettings.Direction direction = PointObjectSettings.Direction.ZPOSITIVE;
    PointSystem pointSystem;
    ObstacleSystem obstacleSystem;
    PrefabPool pool;
    PrefabRoad prefab;
    float length;

    public PositiveZRunningAlg() {
    }


    public PositiveZRunningAlg(PointSystem pointSystem, ObstacleSystem obstacleSystem, PrefabPool pool) {
        this.pointSystem = pointSystem;
        this.obstacleSystem = obstacleSystem;
        this.pool = pool;
    }

    @Override
    public boolean passedCheckpoint(Vector3f characterLoc, Vector3f checkPoint) {
        if(checkPoint == null)
            throw new AssertionError("characterLoc is null");
        if(characterLoc.z >= checkPoint.z)
            return true;
        else 
            return false;
    }

    @Override
    public int runTravelingRoad(Vector3f characterLoc, Vector3f checkPoint) {
        if(characterLoc.z >= checkPoint.z && (characterLoc.x >= checkPoint.x
                || characterLoc.x <= checkPoint.y)){
            
            return 1;
        }
        else return 0;
    }
    
    public int canTurnToChangeRoad(Vector3f characterLoc, Vector3f checkPoint) {
        if(characterLoc.z >= checkPoint.z && (characterLoc.x <= checkPoint.x
                || characterLoc.x >= checkPoint.y))
            return 1;
        else return 0;
    }

    @Override
    public Vector3f[] getGroupLocations(float length, int divisor) {
    this.length = length;
        Vector3f[] array = new Vector3f[divisor];
        if(divisor == 1){
            Vector3f vec = new Vector3f(0,0,length/2);
            array[0] = vec;
             }
        else{
        float unit = length/divisor;
       
        for(int i = 0; i < divisor; i++){
           Vector3f vector = new Vector3f(0,0,i*unit);
           array[i] = vector;//System.out.println(length);System.out.println(vector);
       }
     } 
        return array;
    }

    @Override
    public Vector3f calculateRoadChangingFlag(float offset, float dimensions, Vector3f firstTile) {
       Vector3f vec = new Vector3f(offset,firstTile.x - offset,length - offset - dimensions);
       return vec;
    }

    @Override
    public float getLength() {
       return length;
    }

     @Override
    public float calculateDistanceTraveled(Vector3f characterLoc, Vector3f startingLoc, float offset) {
       if(characterLoc.z <= startingLoc.z)
           return 0;
       float endSpot = characterLoc.z - startingLoc.z;
       endSpot += offset;
       return endSpot;
    }

    @Override
    public void fillRoadTiles(BulletAppState bulletAppState,CollisionShape shape,Node node, Road road, Vector3f firstTile, int numberOfTiles, float dimensions) {
        Node bindingNode = new Node();
        for(int i = 0;i < numberOfTiles;i++){
            Node nodes = (Node) node.clone();//nodes.setLocalRotation(RotationQuats.ROTATONQUAT180);
            nodes.setLocalTranslation(firstTile.x*dimensions, firstTile.y*dimensions,(++firstTile.z) * dimensions);
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
           roadScenePointObjects(nodes,road,i,numberOfTiles, dimensions);
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
        int i = 0;
        float pointRoadDimensions = prefab.roadFields.tileDimensionsZ;
        for(Node nodes: prefab.tileList){
            float dimensions = prefab.zDimensions.get(i);
            nodes.setLocalTranslation(location.x*dimensions, location.y*dimensions,(++location.z) * dimensions);
            nodes.getControl(RigidBodyControl.class).setPhysicsLocation(nodes.getLocalTranslation());
            nodes.getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT360);
            
            i++;
            
            bulletAppState.getPhysicsSpace().add(nodes.getControl(RigidBodyControl.class));
        }
       
        
        
        Vector3f[] locArray = road.getGroupLocations();
        //.setRoad(road.getIndex(),prefab.roadFields.limits.size.pointGroups);
       //prefab.roadFields.limits.size.pointGroups
        
        /*ObstacleNode obstacle = obstacleSystem.getPool().releaseStatic("nopal");
        obstacle.setLocalTranslation(locArray[0]);
        ObstacleNode rock = obstacleSystem.getPool().releaseStatic("rock");
        rock.setLocalTranslation(locArray[1]);
        ObstacleNode duckRock = obstacleSystem.getPool().releaseStatic("duckRock");
        Vector3f vector = locArray[2];
        vector.y=2f;
        duckRock.setLocalTranslation(vector);
        obstacleSystem.setRoad(direction,road.getIndex(), new ObstacleNode[]{obstacle,rock,duckRock});
        */
        Vector3f[] vecArray = new Vector3f[prefab.tileList.size()];
        int f = 0;
        for(Node node2: prefab.tileList){
            vecArray[f] = node2.getLocalTranslation();
            f++;
        }
        ObstacleAndPointSpawner.setTracks(vecArray, this);
        ObstacleAndPointSpawner.spawn(obstacleSystem, pointSystem,road.index);
        
         prefab.fillFinalRoadFields();
        road.setRoadFields(prefab.getRoadFields());
        //prefab.getMothernode().setLocalTranslation(prefab.getRoadFields().firstTileLoc);
         road.setTiles(prefab.tileList,initiate);
    }

    @Override
    public boolean withinProximityOfObstacle(Vector3f characterLoc, Vector3f obsLoc) {
       if(characterLoc.z >= obsLoc.z -10f && characterLoc.z < obsLoc.z + 10f)
           return true;
       return false;
    }

    @Override
    public Vector3f changeTrackRight(Vector3f playerLoc) {
        playerLoc.x = ObstacleSystem.getCurrentRoad().getMiddleLane().x ;//- TrackManager.TRACKGAP;
       
        return playerLoc;
    }

    @Override
    public Vector3f changeTrackLeft(Vector3f playerLoc) {
       playerLoc.x = ObstacleSystem.getCurrentRoad().getMiddleLane().x;// + TrackManager.TRACKGAP;
       
       return playerLoc;
    }

    @Override
    public PointObjectSettings.Direction getDirection() {
        return direction;
    }

    @Override
    public Vector3f setLeftTrack(Vector3f middleLane) {
        middleLane.x += TrackManager.OBSTACLEGAP;
        return middleLane;
     }

    @Override
    public Vector3f setRightTrack(Vector3f middleLane) {
        middleLane.x -= TrackManager.OBSTACLEGAP;
        return middleLane;
    }

    @Override
    public Vector3f changeTrack(Vector3f playerLoc, int index) {
        if(index == 0)
            playerLoc.x = ObstacleSystem.getCurrentRoad().getMiddleLane().x + TrackManager.TRACKGAP;
        else if(index == 1)
            playerLoc.x = ObstacleSystem.getCurrentRoad().getMiddleLane().x;
        else
            playerLoc.x = ObstacleSystem.getCurrentRoad().getMiddleLane().x - TrackManager.TRACKGAP;
        return playerLoc;
    }

    @Override
    public Vector3f changeObstacleTrack(Vector3f obstacleLoc, int trackIndex, int roadIndex) {
        if(trackIndex == 0)
            obstacleLoc.x = ObstacleSystem.obstacleRoads.get(roadIndex).getLeftLane().x;
        else if(trackIndex == 1)
            obstacleLoc.x = ObstacleSystem.obstacleRoads.get(roadIndex).getMiddleLane().x;
        else
            obstacleLoc.x = ObstacleSystem.obstacleRoads.get(roadIndex).getRightLane().x;
        return obstacleLoc;
    }
    
    public void checkTrackBounds(Vector3f characterLoc){
        Vector3f leftVec = ObstacleSystem.obstacleRoads.get(ObstacleSystem.currentRoad).getLeftLane();
        Vector3f rightVec = ObstacleSystem.obstacleRoads.get(ObstacleSystem.currentRoad).getRightLane();
        //System.out.println("LeftLaneValue:  " + leftVec.x);
        //System.out.println("RightLaneValue:  " + rightVec.x);
        if(characterLoc.x >= leftVec.x)
            characterLoc.x = leftVec.x;
        else if(characterLoc.x <= rightVec.x)
            characterLoc.x = rightVec.x;
        PlayerListener.bikerControl.setPhysicsLocation(characterLoc);
    }

    @Override
    public void rotate(Node node) {
        node.setLocalRotation(RotationQuats.ROTATIONQUAT360);
    }

    @Override
    public void rotate(CustomNode node) {
        node.setLocalRotation(RotationQuats.ROTATIONQUAT360);
    }

    @Override
    public void setAccViewDirection(Vector3f viewDirection, float[] linearAcc) {
        viewDirection.x+=linearAcc[0];
    }
float lastXAddition = 0;
float lastXCameraAddition = 0;
float previousAddition = 0;
float goal;
    @Override
    public void setPlayerLocTroughGravity(Vector3f playerLoc, float[] gravity, float[] gravityChange, float gravityBounds) {
       
        
        /*float x = PathConstructor.getCurrentRoad().fields.firstTileLoc.x;
        float posLimit = x + TrackManager.TRACKGAP;
        float negLimit = x - TrackManager.TRACKGAP;
        float gravityChangeFlag = .2f;
        if(gravity[0] > 0)
            playerLoc.x+=PlayState.tpf*7f;
        else if(gravity[0] < 0)
                playerLoc.x-=PlayState.tpf*7f;
            if(playerLoc.x > posLimit)
                playerLoc.x = posLimit;
            else if(playerLoc.x < negLimit)
                playerLoc.x = negLimit;
        //}
           playerLoc.x =0;
            TrackManager.CurrentGravity = playerLoc.x;
            //playerLoc.x = 0;*/
        
     
           
        //float alpha=ControllerMath.camLerp(ControllerMath.absDifference(FastMath.abs(lastXAddition), addition), 1.2f);
        //float alpha = ControllerMath.playerLerp(ControllerMath.absDifference(FastMath.abs(lastXAddition), addition), 1.2f);
        //lastXAddition = lastXAddition*alpha + addition *(1-alpha);
        //goal = playerLoc.x + addition;
        //float alpha = ControllerMath.camLerp(ControllerMath.absDifference(goal, playerLoc.x), 10f);
        //playerLoc.x = playerLoc.x * alpha + goal * (1-alpha);
        //playerLoc.x += lastXAddition;
        
        /*if(playerLoc.x > posLimit)
            playerLoc.x = posLimit;
        else if(playerLoc.x < negLimit)
            playerLoc.x = negLimit;*/
        PlayerListener.ensureCorrectRotation(direction);
         float x = PathConstructor.getCurrentRoad().fields.firstTileLoc.x;
        //float z = PathConstructor.getCurrentRoad().fields.firstTileLoc.z-PathConstructor.getCurrentRoad().fields.tileDimensionsZ/2;
         float z = Controller.positionAtRoadChanged.z;
        
        float posLimit = x + TrackManager.TRACKGAP;
        float negLimit = x - TrackManager.TRACKGAP; 
        
        Vector3f b = CamController.lerp(0,gravity[0],this,x);
        playerLoc.z = z + b.z;
        playerLoc.x = x + b.x;//x + b.x;
        
        //System.out.println("Center of tile      " + x);
        //System.out.println(this+"    "+playerLoc.x);
        if(playerLoc.x > posLimit)
            playerLoc.x = posLimit;
        else if(playerLoc.x < negLimit)
            playerLoc.x = negLimit;
        
         PlayerListener.bikerControl.setPhysicsLocation(playerLoc);
            TrackManager.CurrentGravity = playerLoc.x;
        }
    
    @Override
     public void clearAddition(){
         previousAddition = 0;
     }

    @Override
    public void setWall(Node node, Vector3f location, PointObjectSettings.Direction nextDirection) {
        PlayState.bulletAppState.getPhysicsSpace().remove(node);
        PlayState.bulletAppState.getPhysicsSpace().remove(node.getChild("hay"));
        if(nextDirection.equals(Direction.XPOSITIVE)){
            //node.setLocalTranslation(location.x ,location.y,location.z);//- TrackManager.TRACKGAP
            //node.setLocalRotation(RotationQuats.ROTATIONQUAT360);
            node.getControl(RigidBodyControl.class).setPhysicsLocation(location);
            node.getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT360);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getControl(RigidBodyControl.class));
            
            Vector3f vector = new Vector3f(location.x,location.y,location.z + 12f);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsLocation(vector);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATIONQUAT360);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getChild("hay").getControl(RigidBodyControl.class));
        }
        else{
            //node.setLocalTranslation(location.x ,location.y,location.z);//+ TrackManager.TRACKGAP
            //node.setLocalRotation(RotationQuats.ROTATONQUAT180);
            node.getControl(RigidBodyControl.class).setPhysicsLocation(location);
            node.getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATONQUAT180);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getControl(RigidBodyControl.class));
            
            Vector3f vector = new Vector3f(location.x,location.y,location.z + 12f);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsLocation(vector);
            node.getChild("hay").getControl(RigidBodyControl.class).setPhysicsRotation(RotationQuats.ROTATONQUAT180);
            PlayState.bulletAppState.getPhysicsSpace().add(node.getChild("hay").getControl(RigidBodyControl.class));
        }
       
        PlayState.rootNode.attachChild(node);
        
    }

    @Override
    public void runAi(Road road, float speed) {
       Vector3f location = ChickenListener.chicken.getLocalTranslation().clone();
        float z = road.getRoadFields().firstTileLoc.z + ChickenListener.walk(speed);
        ChickenListener.chicken.setLocalTranslation(road.getRoadFields().firstTileLoc.x, location.y, z);
    }

    @Override
    public boolean withinRange(Vector3f start, Vector3f target, float range) {
        if(start.z >= target.z)
            return false;
        return (target.z - start.z <= range);
    }

    @Override
    public Vector3f getTracks(Vector3f tracks, Vector3f tile) {
        tracks.y = tile.x;
        tracks.x = tile.x + TrackManager.TRACKGAP;
        tracks.z = tile.x - TrackManager.TRACKGAP;
        return tracks;
    }

    @Override
    public String getTurnDirection(Direction nextDirection) {
        if(nextDirection.equals(Direction.XPOSITIVE))
            return "left";
        return "right";
    }
    //This method checks if two objects are proximity units apart but goal has to be in front of startingLoc
    @Override
    public boolean withinProximityOfObstacle(Vector3f goal, Vector3f startingLoc, float proximity) {
        if(startingLoc.z < goal.z)
            return false;
        return startingLoc.z - goal.z <= proximity;
    }

    @Override
    public void spawnAhead(float addition) {
        Vector3f location = PlayerListener.character.getLocalTranslation();
        location.z += addition;
        PlayerListener.bikerControl.setPhysicsLocation(location);
    }
    
}
