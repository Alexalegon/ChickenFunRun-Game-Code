/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import aicharacters.ChickenListener;
import guisystem.Stats;
import pointsSystem.PointObjectsFactory;
import pointsSystem.PointObjectSettings;
import com.jme3.material.Material;
import com.jme3.math.Spline;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Node;
import com.jme3.util.TempVars;
import guisystem.Gui;
import java.util.HashMap;

import levels.RoadLimits.RoadSize;
import controllers.Controller;
import static controllers.Controller.gravity;
import guisystem.StatsManager;
import maincharacter.LifeManager;
import mygame.GameState;
import mygame.GameStateManager;
import mygame.Main;
import mygame.PlayState;
import maincharacter.PlayerListener;
import missions.MissionManager;
import obstaclesystem.HitInfo;
import tutorial.TutorialManager;
import obstaclesystem.ObstacleNode;
import obstaclesystem.ObstaclePool;
import obstaclesystem.ObstacleSettings;
import obstaclesystem.ObstacleSystem;
import pointsSystem.PointObjectSettings.Direction;
import pointsSystem.PointSpawningAlg;
import pointsSystem.PointSpawningAlg.PointSettings;
import pointsSystem.PointSystem;
import pointsSystem.PointsNode;

/**
 *
 * @author Miguel Martrinez
 */
public class LevelManager {
   public Main main;
    public PlayState playState;
    
    int level;
    Gui gui;
    PointSystem pointSystem;
    public static ObstacleSystem obstacleSystem;
    PointObjectsFactory pointObjectsFactory;
    PointObjectSettings pointObjectSettings;
    
    PrefabPool pool;
    PathConstructor pathC;
    Node nodez;
    public LevelManager(Main main, PlayState playState){TempVars var = TempVars.get();
        this.main = main;
        this.playState = playState;
       
        
        level = 1;
       pointSystem = new PointSystem();
      
       pointObjectSettings = new PointObjectSettings(4);
    }
    
    
    
    public void initPrefabs(){
        playState.bulletAppState.setDebugEnabled(false);
        pointObjectsFactory = new PointObjectsFactory(playState.assetManager);
        BatchNode bath;
/*Spline example
        Vector3f[] array = {new Vector3f(0,3f,3f),new Vector3f(0f,6f,12f),new Vector3f(0,9f,21f),new Vector3f(0,6f,30f),
    new Vector3f(0,3f,15f)};
        Spline spline = new Spline(SplineType.CatmullRom,array,1f,true);
       
        for (int i = 0; i < array.length-1; i++){
            float inter = .33f;
            for(int j = 0; j < 1; j++){
                Node star = pointObjectsFactory.addPine(Vector3f.ZERO, "+Z");
                star.scale(.05f);
                star.setLocalTranslation(spline.interpolate(0, i, null));
                playState.node.attachChild(star);
                inter+=inter;
            }
        }*/
        //setup obstacles
        ObstacleNode obs =  pointObjectsFactory.addNopalObstacle(5f);
         
        
         ObstacleSettings obstacleSettings = new ObstacleSettings();
         
         obstacleSettings.addStaticObstacle(pointObjectsFactory.addRockObstacle(5f));
         obstacleSettings.addStaticObstacle(obs);
         obstacleSettings.addStaticObstacle(pointObjectsFactory.addDuckRock(8f));
         obstacleSettings.addStaticObstacle(pointObjectsFactory.addWagon());
         obstacleSettings.addStaticObstacle(PointObjectsFactory.addQuicksand());
         obstacleSettings.addStaticObstacle(PointObjectsFactory.addFirePlace());
         ObstaclePool obstaclePool = new ObstaclePool();
         obstaclePool.addObstacleSettings(obstacleSettings);
         obstaclePool.initStaticPool(10);
         obstacleSystem = new ObstacleSystem(PlayerListener.character,playState.node,obstaclePool,4);
         
         //setup prefabs
        HashMap<String, Node> map = new HashMap<String,Node>();
        map.put("node",pointObjectsFactory.getSaloonTile() );
        map.put("tile2", pointObjectsFactory.getOasisTile());
        map.put("transition", pointObjectsFactory.getTransitionNode());
        map.put("tile3",pointObjectsFactory.getRocksTile());
        map.put("tile5",pointObjectsFactory.getCactusTile());
        map.put("tile6", pointObjectsFactory.getArcsTile());
        map.put("treesTile", pointObjectsFactory.getTreesTile());
       pool = new PrefabPool(2);
        RoadGenerator roadGenerator = new RoadGenerator();
        roadGenerator.setTiles(map);
        for(int i =0; i < 4; i++){
            pool.addPrefab(roadGenerator.generatePrefabRoad(new RoadLimits(RoadSize.LONG,10,10)));
        }
        //add wall object
        pool.addWall(pointObjectsFactory.getTransitionWall());
        
        
        //pool.addPrefab(roadGenerator.generatePrefabRoad(new RoadLimits(RoadSize.MIDSIZE,10,10)));
       // pool.addPrefab(roadGenerator.generatePrefabRoad(new RoadLimits(RoadSize.LONG,10,10)));
        //pool.addPrefab(roadGenerator.generatePrefabRoad(new RoadLimits(RoadSize.LONG,10,10)));
    //setip Point System     
  
    
    PointsNode pinata = pointObjectsFactory.addPinata();ChickenListener.init();//chicken added
    
    pointObjectSettings.setGui(main.gui);
    pointObjectSettings.addLowPointsObject(pinata);
    pointObjectSettings.addMidPointsObject(pinata);
    pointObjectSettings.addHighPointsObject(pinata);
    //pointObjectSettings.addHarmfulObject(new PointsNode(New));
   // pointObjectSettings.addMidPointsObject(star);
    pointObjectSettings.setRootNode8Character(playState.node, PlayerListener.character);
    pointObjectSettings.initLOWPointsSettingGroups();
    pointObjectSettings.initMIDPointsSettingGroups();
    pointObjectSettings.initHIGHPointsSettingGroups();
    pointSystem.setPointObjectSettings(pointObjectSettings);
    
    PointSpawningAlg spawningAlg = new PointSpawningAlg(PointSettings.LOW);
    pointSystem.setPointSpawningAlg(spawningAlg);
    RunningAlgsFacility.initAlgs(pointSystem, obstacleSystem, pool);
    pathC = new PathConstructor(this);
        pathC.setPointSystem(pointSystem,obstacleSystem);
        //PathConstructor.resetConstructor();
        //pathC.showRoad(true);
        //pathC.showRoad(false);
        //playState.setStart(true);
        //PlayState.node.attachChild(pointObjectsFactory.addWaterPlane());
        //PlayState.node.attachChild(pointObjectsFactory.getParticleEmitter());
        //PlayState.node.attachChild(pointObjectsFactory.getTestQuad());
       // ObstacleNode obss = pointObjectsFactory.addWagon();
        //obss.setLocalTranslation(0,1f,60f);
        //PlayState.node.attachChild(obss.getNode());
        //PlayState.node.attachChild(pointObjectsFactory.addQuicksand());
        //PlayState.node.attachChild(pointObjectsFactory.getFirePlace().getNode());
    }
    
    public void run(){
        PlayerListener.run();
       
        if(GameStateManager.getCurrentState().equals(GameState.TUTORIAL) || 
                GameStateManager.getCurrentState().equals(GameState.RESUMETUTORIAL)
                || GameStateManager.getCurrentState().equals(GameState.TUTORIALINTRO)){
            TutorialManager.run();
            pathC.run();
            return;
        }
        MissionManager.run();
        StatsManager.update();
        
        if(obstacleSystem.run()){
            HitInfo hitInfo = new HitInfo();
            hitInfo.setHitType(HitInfo.HitType.OBSTACLE);
            StatsManager.getCurrentRunStats().setHitInfo(hitInfo);
            PlayState.main.gui.gameOver();
        }
        else{
            pathC.run();
            if(!GameStateManager.currentGameState.equals(GameState.TUTORIAL) &&
                    !GameStateManager.currentGameState.equals(GameState.RESUMETUTORIAL)
                    && !GameStateManager.currentGameState.equals(GameState.TUTORIALINTRO))
            pointSystem.run();
        }
            //PlayerListener.bikerControl.stopWalking();
        checkWallHit();
        
    }
    
    public static void checkWallHit(){
        if(PlayState.rootNode.getChild("hay") != null)
            if(PlayState.rootNode.getChild("hay").getWorldBound().intersects(PlayerListener.character.getWorldBound())){
                HitInfo hitInfo = new HitInfo();
                hitInfo.setHitType(HitInfo.HitType.HAY);
                StatsManager.getCurrentRunStats().setHitInfo(hitInfo);
                   PlayState.main.gui.gameOver();
            }
        
    }
    
    public void setCameraDirection(Direction direction){
        playState.chaseCam.setDirection(direction);
    }
    
  
    public void calculateStats(){
        
    }
}
