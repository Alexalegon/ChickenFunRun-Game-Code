/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import guisystem.Gui;
import tutorial.Tutorial;
import java.util.ArrayList;
import levels.RunningAlg;
import levels.RunningAlgsFacility;
import levels.TrackManager;
import maincharacter.BikerCharacterControl;
import maincharacter.PlayerListener;
import missions.MissionManager;
import mygame.GameState;
import mygame.GameStateManager;
import mygame.PlayState;
import obstaclesystem.ObstacleSettings.ObstaclePlacingConst;
import obstaclesystem.ObstacleSettings.ObstacleTypes;
import pointsSystem.PointObjectSettings.Direction;
import tutorial.TutorialManager;
import tutorial.TutorialStep;

/**
 *
 * @author Miguel Martrinez
 */
public class ObstacleRoad {
    Node character;
    Node worldNode;
    int index;
    RunningAlg runningAlg;
    Vector3f[] tracks;  
    Direction direction;
    ObstaclePool pool;
    ObstaclePlacingConst placingConst;
    int tutorialStepSlide = 0;
    int tutorialStepJump = 0;
    ArrayList<ObstacleNode> staticObs = new ArrayList<ObstacleNode>();
    ArrayList<ObstacleNode> aiObs = new ArrayList<ObstacleNode>();
    
    public ObstacleRoad() {
    }
    
    public ObstacleRoad(Node character,Node worldNode,ObstaclePool pool,int index){
        this.character = character;
        this.worldNode = worldNode;
        this.index = index;
        this.pool = pool;
        
    }
    
    public void setObstaclePlacingConst(ObstaclePlacingConst placingConst){
        this.placingConst = placingConst;
    }
    
    public void setTracks(Vector3f[] tracks){
        this.tracks = tracks;
    }
    
    public Vector3f getLeftLane(){
        return tracks[0];
    }
    public Vector3f getMiddleLane(){
        return tracks[1];
    }
    public Vector3f getRightLane(){
        return tracks[2];
    }
    
    public void setDirection(Direction direction){
     this.direction = direction;
     if(direction.equals(Direction.ZPOSITIVE))
         runningAlg = RunningAlgsFacility.zPosAlg;
     else if(direction.equals(Direction.XPOSITIVE))
         runningAlg = RunningAlgsFacility.xPosAlg;
     else if(direction.equals(Direction.ZNEGATIVE))
         runningAlg = RunningAlgsFacility.zNegAlg;
     else
         runningAlg = RunningAlgsFacility.xNegAlg;
    }
    
    public RunningAlg getRunningAlf(){
        return runningAlg;
    }
    
    public void addStaticObstacle(ObstacleNode obstacle){
        staticObs.add(obstacle);
        worldNode.attachChild(obstacle.getNode());
        obstacle.setisPassed(false);
    }
    
    public void addAiObstacle(ObstacleNode obstacle){
        aiObs.add(obstacle);
    }
    
    public boolean run(){
        //Vector3f vector = character.getControl(BikerCharacterControl.class).getHeight();
        
        //Ray ray = new Ray(vector,character.getControl(BikerCharacterControl.class).getViewDirection());
       // CollisionResults results = new CollisionResults();
       
        for(ObstacleNode obstacle: staticObs){
            
            Node node = obstacle.getNode();
            if(character.getControl(BikerCharacterControl.class).checkIntersection(obstacle))
           //if(character.getControl(BikerCharacterControl.class).getGeometry().collideWith(node.getWorldBound(), new CollisionResults()) != 0)
            //if(withinProximity(node.getLocalTranslation()))
            //if(runningAlg.withinProximityOfObstacle(vector, node.getLocalTranslation()))
            //if(node.collideWith(ray, results) != 0)
               return true; 
        }
        
        return false;
    }
    
    public void runTutorial(){
       
        for(ObstacleNode obstacle: staticObs){
            Node node = obstacle.getNode();
            if(TrackManager.runningAlg.withinProximityOfObstacle(PlayerListener.character
                    .getLocalTranslation(),node.getLocalTranslation(),25f)){
                TutorialStep step = getStepFromObstacle(obstacle.getType());
                if(isFlagBroken(step) > 1)
                    return;
                TutorialManager.setTutorialStep(step);
            }
        }
    }
    
    public TutorialStep getStepFromObstacle(ObstacleTypes type){
        if(type.equals(ObstacleTypes.Ducking)){
            tutorialStepSlide++;
            return TutorialStep.SLIDE;
        }
        else{
            tutorialStepJump++;
            return TutorialStep.JUMP;
        }
    }
    public int isFlagBroken(TutorialStep st){
        if(st.equals(TutorialStep.SLIDE))
            return tutorialStepSlide;
        else
            return tutorialStepJump;
    }
    
    public boolean withinProximityOfObstacle(Vector3f characterLoc, Vector3f obsLoc){
        return true;
    }
    
    public void clearRoad(){
        for(ObstacleNode obstacle: aiObs){
            pool.putBackStatic(obstacle);
            worldNode.detachChild(obstacle.getNode());
            obstacle.setisPassed(false);
        }
         for(ObstacleNode obstacle: staticObs){
            pool.putBackStatic(obstacle);
            worldNode.detachChild(obstacle.getNode());
            obstacle.setisPassed(false);
        }
        staticObs.clear();
        aiObs.clear();
        tutorialStepSlide = 0;
        tutorialStepJump = 0;
    }
    
    public ArrayList<ObstacleNode> getstaticObstacles(){
        return staticObs;
    }
}
