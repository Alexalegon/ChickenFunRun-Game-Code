/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import aicharacters.AiManager;
import aicharacters.ChickenListener;
import com.jme3.bullet.BulletAppState;
import pointsSystem.PointsGroup;
import pointsSystem.PointObjectBag;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import guisystem.Stats;
import java.util.ArrayList;
import levels.PrefabRoad.RoadFields;
import mygame.PlayState;
import pointsSystem.PointObjectSettings.Direction;
import pointsSystem.PointSystem;

/**
 *
 * @author Miguel Martrinez
 */
public class Road {
    Node character;
    Node worldNode;
    PointObjectBag pointObjectBag;
    SceneObjectBag sceneObjectBag;
   int totalTiles=0;
   int pathTileCount=0;
   float tileDimensions;
   float totalLength = 0;
   Vector3f firstTileLocation;
   Vector3f middleTileLocation;
   Vector3f lastTileLocation;
   RoadLimits roadLimits;
   ArrayList <PointsGroup> pointsGroupList = new ArrayList<PointsGroup>();
   final static String positiveX = "+X";
 final static String positiveY = "+Y";
 final static String positiveZ = "+Z";
 final static String negativeX = "-X";
 final static String negativeY = "-Y";
 final static String negativeZ = "-Z";
   Direction direction;
   RunningAlg runningAlg;
   PositiveZRunningAlg zPosRunAlg;
   PositiveXRunningAlg xPosRunAlg;
   NegativeXRunningAlg xNegRunAlg;
   NegativeZRunningAlg zNegRunAlg;
   Vector3f[] groupLocations;
   Vector3f roadChangingFlag;
   Stats stats;
   Node motherNode;
   int index;
   PrefabRoad prefab;
   PrefabPool pool;
   public RoadFields fields;
   BulletAppState bulletAppState;
   ArrayList<Node> tileList;
   int currentTile;
   int lastShowingTile = 0;
   public boolean turnFlag = false;//ensures only 1 turn
   public int turnIndex = 0;
   PointSystem pointSystem;
   public boolean isAi;
   public Vector3f tracks = new Vector3f();
   
    public Road(Stats stats,PointSystem pointSystem, Node character, Node worldNode,BulletAppState bulletAppState,float tileDimensions,int index) {
        this.stats = stats;
        this.pointSystem = pointSystem;
        this.character = character;
        this.worldNode = worldNode;
        this.bulletAppState = bulletAppState;
        motherNode = new Node();
        worldNode.attachChild(motherNode);
        this.index = index;
        this.tileDimensions = tileDimensions;
        pointObjectBag = new PointObjectBag();
        sceneObjectBag = new SceneObjectBag();
        initRunningAlgs();
    }
    
    public void initRunningAlgs(){
        zPosRunAlg = new PositiveZRunningAlg();
        xPosRunAlg = new PositiveXRunningAlg();
        xNegRunAlg = new NegativeXRunningAlg();
        zNegRunAlg = new NegativeZRunningAlg();
        runningAlg = zPosRunAlg;
    }
    
    public void setDirection(Direction direction){
        this.direction = direction;
        if(direction.equals(Direction.ZPOSITIVE))
            runningAlg = zPosRunAlg;
        else if(direction.equals(Direction.ZNEGATIVE))
            runningAlg = zNegRunAlg;
        else if(direction.equals(Direction.XPOSITIVE))
            runningAlg = xPosRunAlg;
        else
            runningAlg = xNegRunAlg;
        
    }
    
    public void setTotalTiles(int total){
        totalTiles = total;
        setTotalLength();
    }
    
    public void setPathTileCount(int pathTileCount){
        this.pathTileCount = pathTileCount;
    }
    
    public void setfirstTileLocation(Vector3f vector){
        firstTileLocation = vector;
        distributeGroupLocs(roadLimits.size.pointGroups);
    }
    
    public void setMiddleTileLocation(Vector3f vector){
        middleTileLocation = vector;
    }
    
    public void setLastTileLocation(Vector3f vector){
        lastTileLocation = vector;
    }
    
    public void setRoadLimits(RoadLimits roadLimits){
        this.roadLimits = roadLimits;
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public int getTotalTiles(){
        return totalTiles;
    }
    
    public Vector3f getFirstTile(){
        return firstTileLocation;
    }
    
    public Vector3f getMiddleTile(){
        return middleTileLocation;
    }
    
    public Vector3f getLastTile(){
        return lastTileLocation;
    }
    
    public int getPathTileCount(){
        return pathTileCount;
    }
    
    public RoadLimits getRoadLimits(){
        return roadLimits;
    }
    
    public int runTravelingRoad(){
        int result = runningAlg.runTravelingRoad(character.getLocalTranslation(), roadChangingFlag); 
        if(result == 1){
            //System.out.println("jj  " + index);
            setDone(false);
            stats.addDistanceTraveled(runningAlg.getLength());
        }
        return result;
      
    }
    
    public int canTurnToChangeRoad(){
        int result = runningAlg.canTurnToChangeRoad(character.getLocalTranslation(), roadChangingFlag);
        if(result == 1){
            turnIndex++;
            turnFlag = turnIndex == 1;//ensures only 1 call to set ChangeRoadFlag to true
            
        }
        return result;
    }
    
    boolean running = false;
    int sentinel=0;
    public boolean run(String specificTile){
        Vector3f checkPoint = Vector3f.ZERO;
        if(specificTile.equalsIgnoreCase("Middle"))
            checkPoint = fields.tileLocList.get(5);
        else if(specificTile.equalsIgnoreCase("Last"))
            checkPoint = fields.lastTileLoc;
        else 
            checkPoint = fields.firstTileLoc;
        return runningAlg.passedCheckpoint(character.getLocalTranslation(), checkPoint);
/*        if(direction.equals(Direction.XPOSITIVE) || direction.equals(Direction.XNEGATIVE)){
            running = runX(checkPoint);
            return running;
        }
            
        else {
            running = runZ(checkPoint);
            return running;
        }*/
            
    }
    
    boolean done = false;
    public void setDone(boolean done){
        this.done = done;
    }
    public boolean isDone(){
        return done;
    }
    
    public void reset(){
        totalTiles = 0;
        firstTileLocation = Vector3f.ZERO;
        middleTileLocation = Vector3f.ZERO;
        lastTileLocation = Vector3f.ZERO;
        totalLength = 0;
        if(tileList != null){
            prefab.disactivate();
            for(Node node: tileList){
                worldNode.detachChild(node);
                bulletAppState.getPhysicsSpace().remove(node);
            }
        }
        //tileList = null;
        currentTile = 0;
        lastShowingTile = 0;
        done = false;
        turnIndex = 0;
        turnFlag = false;
        isAi = false;
    }
    
   public void setPointsGroups(PointsGroup group){
       pointsGroupList.add(group);
   }
   
   public void clearPointsGroups(){
       pointsGroupList.clear();
   }
    
    
   public void setTotalLength(){
       totalLength = fields.numOfTiles * fields.tileDimensionsZ;
   }
   
   public void distributeGroupLocs(int numberOfGroups){
       groupLocations = runningAlg.getGroupLocations(totalLength, numberOfGroups);
       for(Vector3f vec: groupLocations){
           vec.addLocal(fields.firstTileLoc);
       }
       roadChangingFlag = fields.firstTileLoc.add(runningAlg.calculateRoadChangingFlag(fields.tileDimensionsZ/2,fields.tileDimensionsZ,
               fields.firstTileLoc)); 
   }
   
   public Vector3f[] getGroupLocations(){
       return groupLocations;
   }
   
   public void addLastDistance(){
       stats.addDistanceTraveled(runningAlg.calculateDistanceTraveled(character.getLocalTranslation(),firstTileLocation, 
               tileDimensions/2));
   }
   
   public void attachToMotherNode(Node node){
       //motherNode.attachChild(node);
       //for(int i = 0; i < node.getQuantity(); i++)
        //worldNode.attachChild(node);
   }
   
   public void setTiles(ArrayList<Node> list,boolean initiate){
       this.tileList = list;
       RunningAlgsFacility.getAlgByDirection(direction).getTracks(tracks,list.get(0).getLocalTranslation());
       if(initiate)
           attachToMotherNode();
   }
   
   public void attachToMotherNode(){
       
       for(int i = 0; i <tileList.size()/3 ; i++){
       worldNode.attachChild(tileList.get(i));
       pointSystem.attachGroups(i);
       }
       lastShowingTile = 2;
   }
   
   public void attachFirstTile(){
       worldNode.attachChild(tileList.get(0));
       pointSystem.attachGroupsNextRoad(0);
       lastShowingTile = 0;
   }
   
   public void attachSecondTile(){
       worldNode.attachChild(tileList.get(1));
       pointSystem.attachGroupsNextRoad(1);
       lastShowingTile = 1;
   }
   
   public boolean runThroughTiles(){
       boolean nextRoad = false;
      
       Vector3f checkPoint = tileList.get(currentTile).getLocalTranslation();
       if(runningAlg.passedCheckpoint(character.getLocalTranslation(), checkPoint)){
           PlayState.chaseCam.checkAutomaticDuck(currentTile);
           detachTile();
           nextRoad = attachTile();
           if(currentTile == lastShowingTile)
               return nextRoad;
           currentTile++;
           
       }
       return nextRoad;
   }
   
   
   public boolean attachTile(){
       if(lastShowingTile < tileList.size()-1){
            worldNode.attachChild(tileList.get(lastShowingTile+1));
            pointSystem.attachGroups(lastShowingTile+1);
            lastShowingTile++;
            return false;
       }
       else
           return true;
       
       
   }
   
   public void detachTile(){
       if(currentTile > 0){
            worldNode.detachChild(tileList.get(currentTile-1));
            bulletAppState.getPhysicsSpace().remove(tileList.get(currentTile-1));
            pointSystem.detachGroups(currentTile-1);
       }
       else if(index == 0){
           PathConstructor.roadArray[PathConstructor.roadArray.length-1].reset();
           pointSystem.detachLastRoad();
       }
       else{
           PathConstructor.roadArray[index-1].reset();
           pointSystem.detachRoad();
       }
   }
   
  public int getIndex(){
      return index;
  }
   
   public Node getMotherNode(){
       return motherNode;
   }
   
   public RoadFields getRoadFields(){
       return fields;
   }
   
   public void setRoadFields(RoadFields fields){
       this.fields = fields;
       setTotalLength();
       distributeGroupLocs(fields.limits.size.pointGroups);
   }
   
   public void setPrefabPool(PrefabPool pool){
       this.pool = pool;
   }
   
   public void setPrefab(PrefabRoad prefab){
       this.prefab = prefab;
   }
   public void hidePrefab(){
       if(prefab == null)
           return;
           prefab.disactivate();
           worldNode.detachChild(prefab.motherNode);
           for(Spatial node: prefab.motherNode.getChildren()){
              bulletAppState.getPhysicsSpace().remove(node); 
           }
           
           
   }
   
   public void setAi(boolean ai){
       isAi = ai;
   }
   
   public void runAi(){
       if(isAi){
           ChickenListener.simpleControl.setStartingLocation(tileList.get(0).getLocalTranslation());
           ChickenListener.simpleControl.setWalkDirection(direction);
           ChickenListener.simpleControl.play(18f);
       }
   }
   
   public int getCurrentTile(){
       return currentTile;
   }
}
