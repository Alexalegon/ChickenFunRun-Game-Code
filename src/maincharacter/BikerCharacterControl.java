/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincharacter;

import maincharacter.PlayerListener;
import controllers.ControllerMath;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import java.util.ArrayList;
import levels.TrackManager;
import listenersystem.ListenerHandler;
import mygame.Main;
import mygame.PlayState;
import obstaclesystem.ObstacleDescription;
import obstaclesystem.ObstacleNode;
import obstaclesystem.ObstacleSettings;
import obstaclesystem.ObstacleSettings.ObstacleTypes;
import pointsSystem.PointObjectsFactory;

/**
 *
 * @author Miguel Martrinez
 */
public class BikerCharacterControl extends CharacterControl{
    Main main;
    PlayState playState;
    Geometry geom;
    BoundingBox box;
    BoundingBox scaledBox;
    Vector3f boxBounds;
    float duckingX;
    Vector3f height;
    Vector3f physicsLocation;
    Vector3f lastWalkD;
    boolean ducking = false;
    boolean isResetting = false;
    public boolean changingRoad = false;
    public boolean graduallyRotate = false;
    public Vector3f rotationGoal;
   // public boolean activated = true;
    
    public BikerCharacterControl(CollisionShape shape, float stepHeight){
        super(shape,stepHeight);
        geom = (Geometry) PlayerListener.character.getChild("model1");
        //geom = (Geometry) PlayerListener.character.getChild("Cube11");
        box = (BoundingBox)geom.getWorldBound();System.out.println("Box"+box);
        scaledBox = (BoundingBox) box.clone(scaledBox);
        boxBounds = box.getExtent(boxBounds).clone();
        duckingX = boxBounds.x /3f;
        System.out.println("Boxbounds"+boxBounds);
        height = new Vector3f();
        physicsLocation = this.getPhysicsLocation();
        
    }
    

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
        if(getPhysicsLocation().y < -2f){
            PlayState.main.stop();
            
        }
        if(!ListenerHandler.changeRoadFlag && !changingRoad);
            //TrackManager.runningAlg.checkTrackBounds(getPhysicsLocation());
       checkGradualRotation();
    }
    
    public void scaleBounds(float x, float y, float z){
        //box.setXExtent(boxBounds.x*x);
        //box.setXExtent(boxBounds.y*y);
        //box.setXExtent(boxBounds.z*z);
        //geom.getMesh().setBound(box);
        //geom.getMesh().updateBound();
        
        //geom.updateModelBound();
        boxBounds.y = box.getYExtent() * y; 
    }
    
    public void resetBox(){
        //box.setXExtent(boxBounds.x);
        //box.setYExtent(boxBounds.y);
        //box.setZExtent(boxBounds.z);
        //geom.getMesh().setBound(box);
        //geom.getMesh().updateBound();
        
        //geom.updateModelBound();
       
    }
    
    public void setReset(boolean reset){
        isResetting = reset;
    }
    
    public void enableDucking(float y){
        ducking = true;
        //height.y = y;
        boxBounds.y = y;
        //boxBounds.x = 0;
        //boxBounds.z = 0;
        //resetBox();
    }
    
    public void disableDucking(){
        ducking = false;
    }
BoundingBox boxx;
    public boolean checkIntersection(ObstacleNode obstacle){
        if(isResetting)
            return false;
        if(obstacle.getType().equals(ObstacleSettings.ObstacleTypes.Ducking) ||
                obstacle.getDescription().equals(ObstacleDescription.FIREPLACE) || obstacle.getDescription().equals(ObstacleDescription.QUICKSAND))
                        {
           
          for(BoundingVolume volume: obstacle.getBoundingVolumes())
            if(checkIntersection(volume,"obstacle")){
                System.out.println("Obstacle description:   " + obstacle.getDescription() + "  " );obstacle.printBoundingVolumesTranslations();
                    return true;
            }
        }
        else 
            return checkIntersection(obstacle.getNode().getWorldBound(),"obstacle");
       // obstacle.getNode().getWorldBound().intersects(PlayerListener.character.getWorldBound());
            //return checkIntersection(obstacle.getNode().getWorldBound());
        //else{
            //for(BoundingBox boxes: obstacle.getBoundingVolumes())
                
        //}
        return false;
    }
    
    public boolean checkIntersection(BoundingVolume bv,String type){
       /* BoundingBox bb = (BoundingBox) bv;bb.intersects(bv)
                
            
        float xExtent = bb.getXExtent();
        float yExtent = bb.getYExtent();
        float zExtent = bb.getZExtent();
        Vector3f center = bb.getCenter();
         if (physicsLocation.x + boxBounds.x < center.x - xExtent
                || physicsLocation.x - boxBounds.x > center.x + xExtent) {
            return false;
        } else if (physicsLocation.y + boxBounds.y < center.y - yExtent
                || physicsLocation.y - boxBounds.y > center.y + yExtent) {
            return false;
        } else return !(physicsLocation.z + boxBounds.z < center.z - zExtent
                || physicsLocation.z - boxBounds.z > center.z + zExtent);*/
        BoundingBox bb = (BoundingBox) bv;
        Vector3f center = bb.getCenter();
        float xValue; 
        if(type.equalsIgnoreCase("obstacle")){
            xValue = duckingX; //System.out.println("Center  " + bb.getCenter()+ "Extents    " + bb.getExtent(null));
        }
        else if(type.equals("points")){
            
            xValue = boxBounds.x;//System.out.println("orr center is  " + center);System.out.println("orr extens are   " + bb.getExtent(null));
        }
        else{System.out.println("Not a ducking obstacle!");
            xValue = boxBounds.x;
        }
       
        float newX = PlayerListener.character.getLocalTranslation().x;
        float newY = PlayerListener.character.getLocalTranslation().y;
        float newZ = PlayerListener.character.getLocalTranslation().z;
         if (newX + xValue < center.x - bb.getXExtent()
                || newX- xValue > center.x + bb.getXExtent()) {
            return false;
        } else if (newY + boxBounds.y < center.y - bb.getYExtent()
                || newY - boxBounds.y > center.y + bb.getYExtent()) {
            return false;
        } else if (newZ + boxBounds.z < center.z - bb.getZExtent()
                || newZ - boxBounds.z > center.z + bb.getZExtent()) {
            return false;
        } else {
            if(type.equals("obstacle"))
            System.out.println("Obstacle Hit");
            return true;
        }
    }
    
      
    
    public Vector3f getHeight(){
        if(ducking){
        height.x = getPhysicsLocation().x;
        height.z = getPhysicsLocation().z;
        height.y = -120f;
        }
        else{
            height = getPhysicsLocation();
        }
        return height;
    }
    
    public Geometry getGeometry(){
        return geom;
    }
    
    public void stopWalking(){
        if(isWalking())
            return;
        lastWalkD = this.getWalkDirection().clone();
        System.out.println("At time of stoppage" + lastWalkD);
        this.setWalkDirection(Vector3f.ZERO);
    }
    
    public void restartWalk(){
        if(lastWalkD == Vector3f.ZERO)
            throw new AssertionError("ZZZZZZZZero");
        this.setWalkDirection(lastWalkD);
        System.out.println("WalkD" + lastWalkD);
    }
    
    public boolean isWalking(){
        return this.getWalkDirection().equals(Vector3f.ZERO);
    }
    
    public void setGradualRotation(Vector3f rotationGoal){
        graduallyRotate = true;
        this.rotationGoal = rotationGoal;
        newRotation = this.getViewDirection().clone();
    }
    Vector3f newRotation = new Vector3f(0,0,0);
    public void checkGradualRotation(){
        
        if(graduallyRotate){
            newRotation.x = ControllerMath.lerp(newRotation.x,rotationGoal.x,.8f);
            newRotation.y = ControllerMath.lerp(newRotation.y,rotationGoal.y,.8f);
            newRotation.z = ControllerMath.lerp(newRotation.z,rotationGoal.z,.8f);
            
            this.setViewDirection(newRotation);
        }
    }
    
    
}
