/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aicharacters;

import static aicharacters.AiManager.checkHit;
import static aicharacters.AiManager.checkObstacleCollision;
import audiosystem.AudioManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.terrain.geomipmap.picking.BresenhamYUpGridTracer;
import controllers.ControllerMath;
import guisystem.StatsManager;
import levels.PathConstructor;
import levels.RotationQuats;
import levels.TrackManager;
import maincharacter.PlayerListener;
import mygame.PlayState;
import obstaclesystem.ObstacleNode;
import pointsSystem.PointObjectSettings;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class SimpleControl extends AbstractControl{
Direction walkDirection;
Vector3f startingLocation = new Vector3f(0,0,0);
boolean isJumping = false;
float speed = 70f;
float floor;
float jumpHeight = 0;
boolean peaked = false;
float peak = 4f;
boolean walk = false;
float internalTimer = 0;
float timeLimit = 0;
boolean lateralFlag = false;
int laneGoal = 1;
int speedRange = 28;
float currentSpeed;
float speedGoal;
LateralMovement lateral = LateralMovement.LATERAL1;
SpeedController speedController = SpeedController.ONE;
public boolean caught = false;
    public SimpleControl() {
    }
    
    public void setWalkDirection(Direction direction){
        walkDirection = direction;
        spatial.setLocalRotation(RotationQuats.getRotationQuatTroughDirection(direction.vector));
    }
    
    public void setStartingLocation(Vector3f startingLocation){
        this.startingLocation = startingLocation;
        spatial.setLocalTranslation(startingLocation.x,2f,startingLocation.z);
    }
    
    public void setFloor(float floor){
        this.floor = floor;
    }
    
    public void jump(){
        isJumping = true;
    }
    
    public void play(float timeLimit){
        setEnabled(true);
        this.timeLimit = timeLimit;
        reset();
        speedController.play(timeLimit,this);
        PlayState.rootNode.attachChild(spatial);
        ChickenListener.animChannel.setAnim("walk");
        caught = false;
    }
    //this detaches chicken from player in case previous run was stopped with chicken caught
    public void uncatch(){
        caught = false;
        walk = false;
    }
    
    public void stop(){
        //setEnabled(false);
        caught = true;
        reset();
    }
    
    public void reset(){
        walk = true;
        
        internalTimer = 0;
        lateral.setDuration(timeLimit);
        lateralFlag = false;
        laneGoal = 1;
        currentSpeed = speed;
        speedGoal = speed;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(walk){
            if(caught){
                Vector3f loc = PlayerListener.character.getLocalTranslation();
                spatial.setLocalTranslation(loc.x, loc.y,loc.z );
                return;
            }
            calculateWalk(tpf);
            checkObstacleCollision(walkDirection);
            checkHit();
        }
        if(isJumping)
            calculateJump(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
    private void calculateJump(float tpf){
        float fallRate = 8f*tpf;
        if(!peaked && jumpHeight <= peak)
            jumpHeight+=fallRate;
        else{
            peaked = true;
            jumpHeight-=fallRate;
        }
        spatial.getLocalTranslation().y = floor + jumpHeight;
        if(jumpHeight <= 0){
            isJumping = false;
            peaked = false;
        }
    }
    
    private void calculateWalk(float tpf){
     
        Vector3f orig = spatial.getLocalTranslation();
        Vector3f location ;
        if(internalTimer > timeLimit)
            return;
        
        float distance = calculateSpeed();
        if(lateral.move(tpf) != lateralFlag){
            lateralFlag = !lateralFlag;
            if(lateralFlag)
                whichLaneMove();
        }
        if(walkDirection.equals(Direction.ZPOSITIVE)){
            location =  new Vector3f(orig.x,orig.y,orig.z+=distance);
        }
        else if(walkDirection.equals(Direction.ZNEGATIVE)){
            location = new Vector3f(orig.x,orig.y,orig.z-=distance);
        }
        else if(walkDirection.equals(Direction.XPOSITIVE)){
            location = new Vector3f(orig.x+=distance,orig.y,orig.z);
        }
        else{
            location = new Vector3f(orig.x-=distance,orig.y,orig.z);
        }
        if(lateral.isTransitioning)
            moveTowardsLateralGoal(location);
       spatial.setLocalTranslation(location);
        internalTimer+=PlayState.tpf;
        if(internalTimer >= timeLimit){
            PlayState.rootNode.detachChild(spatial);
            walk = false;
        }
    }
    
    public void whichLaneMove(){
        int lane = laneGoal;
        //if(walkDirection.equals(Direction.ZPOSITIVE) || walkDirection.equals(Direction.ZNEGATIVE))
         //  lane = TrackManager.getMyLane(spatial.getLocalTranslation().x);
        ///else
           /// lane = TrackManager.getMyLane(spatial.getLocalTranslation().z);

        if(lane == 1){
            int o = FastMath.rand.nextInt(2);
            laneGoal = o;
            if(laneGoal == 1)
                laneGoal = 2;
        }
        else 
            laneGoal = 1;
            
    }
    
    public void moveTowardsLateralGoal(Vector3f location){
        float lane = TrackManager.getLaneViaIndex(laneGoal);
        if(walkDirection.equals(Direction.ZPOSITIVE) || walkDirection.equals(Direction.ZNEGATIVE)){
           location.x = ControllerMath.lerp(location.x, lane, .9f);
        }
        else
            location.z = ControllerMath.lerp(location.z, lane, .9f);
       if(ControllerMath.withinRange(location.z, lane, .5f) || ControllerMath.withinRange(location.x, lane, .5f))
           lateral.isTransitioning = false;
    }
    
    public float calculateSpeed(){
        
        float lowSpeed = speed - 22f;
        float higSpeed = speed +5;
        if(speedController.update()){
          speedGoal = lowSpeed + FastMath.rand.nextInt(speedRange);
          speedController.setActive(false);
        }
        currentSpeed = ControllerMath.lerp(currentSpeed,speedGoal, .92f);
        if(ControllerMath.withinRange(currentSpeed, speedGoal, .5f))
            speedController.setActive(false);
        return PlayState.tpf * currentSpeed;
    }
    
        public  void checkObstacleCollision(Direction direction){
        Vector3f locationHolder = spatial.getLocalTranslation().clone();
        locationHolder.y+=1f;
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(locationHolder,direction.vector);
        for(ObstacleNode obstacle : PathConstructor.getCurrentObstacleRoad().getstaticObstacles()){
            //obstacle.getNode().collideWith(ray, results);
            for(BoundingVolume volume:obstacle.getBoundingVolumes())
                volume.collideWith(ray,results);
            for(int i = 0; i < results.size();i++){
                float distance = results.getCollision(i).getDistance();
                if(distance < 20f){
                    System.out.println("COlision!!!!!!!!     " + distance);
                    jump();
                }
            }
        }
    }
    
    public void checkHit(){
        CollisionResults results = new CollisionResults();
        ChickenListener.chicken.collideWith(PlayerListener.character.getWorldBound(), results);
        if(results.size() > 0){
            StatsManager.update(PointObjectSettings.ObjectType.CHICKEN);
            stop();
            AudioManager.chickenHitSound.playInstance();
            PlayerListener.animChannel.setAnim("catchChicken");
            ChickenListener.animChannel.setAnim("caught");
        }
    }
}
