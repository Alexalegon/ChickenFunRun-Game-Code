/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import controllers.JumpInfo;
import de.lessvoid.nifty.effects.EffectEventId;
import guisystem.Gui;
import levels.PathConstructor;
import levels.RunningAlgsFacility;
import levels.TrackManager;
import maincharacter.PlayerListener;
import obstaclesystem.ObstacleDescription;
import obstaclesystem.ObstacleNode;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class JumpingMission extends Mission{
ObstacleNode obstacle;
ObstacleDescription description;
    public JumpingMission(String name, MissionType type, int goal) {
        super(name, type, goal);
    }

    @Override
    public void run() {
        super.run();
        listenforJump();
    }

    @Override
    public void setTargetObject(String target) {
        super.setTargetObject(target);
       description = ObstacleDescription.throughName(target);
    }
    
    public void listenforJump(){
        if(isCompleted)
            return;
       for(ObstacleNode node: PathConstructor.getCurrentObstacleRoad().getstaticObstacles()){
           if(node.getDescription().equals(description) && !node.isPassed()){
               if(RunningAlgsFacility.getAlgByDirection(TrackManager.direction).
                       withinRange(JumpInfo.getJumpLocation(), node.getNode().getLocalTranslation(), 56f)
                       && RunningAlgsFacility.getAlgByDirection(TrackManager.direction)
                               .passedCheckpoint(PlayerListener.bikerControl.getPhysicsLocation(), getCheckPoint(node.getNode()))){
                  node.setisPassed(true);
                  count++;
                  if(count >= goal){
                      isCompleted = true;
                      Gui.nifty.getScreen("hud").findElementById("missionAccomplished").startEffect(EffectEventId.onShow);
                  }
               }
            }
        }
    }
    
    public Vector3f getCheckPoint(Node node){
        if(TrackManager.direction.equals(Direction.ZPOSITIVE) || TrackManager.direction.equals(Direction.XPOSITIVE))
            return node.getLocalTranslation().add(5f,5f,5f);
        else 
            return node.getLocalTranslation().add(-5f,-5f,-5f);
    }
}
