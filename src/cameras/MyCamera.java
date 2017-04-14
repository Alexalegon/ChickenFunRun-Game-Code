/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cameras;

import controllers.ControllerMath;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import levels.RunningAlgsFacility;
import levels.SplineFactory;
import levels.TrackManager;
import mygame.Main;
import mygame.PlayState;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class MyCamera extends ChaseCamera{
public Vector3f copyVec;
public Direction direction;
public float lookAtAddition;
public float cameraHeight;
public float targetHeight;
public float vertical;
public float maxVertical = -3f;
public float minVertical = -6f;
public float camLowPassFilter;//this variable smooths cam movement sideways
public boolean initialVrotation = false;
public boolean automaticDuck = false;
public boolean transitioning = false;
public boolean graduallyRotating = false;
Vector3f rotationGoal = new Vector3f(0,0,15f);
Vector3f rotationLerp = new Vector3f(0,0,0);
CamAnimation camAnimation;
boolean isAnimated = false;
    public MyCamera(Camera cam, Spatial target) {
        super(cam, target);
        this.canRotate = false;
        direction = Direction.ZPOSITIVE;
        targetHeight = 0;
        
    }

    @Override
    public void setSpatial(Spatial spatial) {
       target = spatial;
        if (spatial == null) {
            return;
        }
        copyVec = new Vector3f(0,0,0);
        cameraHeight = 0;
        computePosition();
        prevPos = new Vector3f(target.getWorldTranslation());
        cam.setLocation(pos);
    }

    @Override
    protected void computePosition() {
        //fi
       
        //float xh = distance*FastMath.cos(vRotation);
          float hDistance = (distance) * FastMath.sin((FastMath.PI / 2) - vRotation);
        pos.set(hDistance * FastMath.cos(rotation), (distance) * FastMath.sin(vRotation), hDistance * FastMath.sin(rotation));
        //System.out.println("pos before addition     " +  pos);
        //smooth horizontal movement of camera with filter
        
        float alpha = .9f; 
        //if(TrackManager.direction.equals(Direction.ZPOSITIVE)){
            //alpha = checkDifference(copyVec.x,target.getWorldTranslation().x);
            //if(ControllerMath.absDifference(pos.x, target.getWorldTranslation().x)>.05f)
            //copyVec.x = copyVec.x * alpha + target.getWorldTranslation().x * (1-alpha);
            if(withinRange(copyVec.x,target.getWorldTranslation().x,1))
                transitioning = false;
            copyVec.z = target.getWorldTranslation().z;
           //if(ControllerMath.absDifference(pos.x, target.getWorldTranslation().x)>.05f)
            /*float posLimit = TrackManager.TRACKGAP;
            float negLimit = -TrackManager.TRACKGAP;
            if(Controller.gravity[0]>.5f)
               copyVec.x += .05f;
            else if(Controller.gravity[0] <-.5f)
                copyVec.x -=.05f;
            if(copyVec.x > posLimit)
                copyVec.x = posLimit;
            else if(copyVec.x < negLimit)
                copyVec.x = negLimit;*/
            float delta = .2f+ (.2f*Main.tpf);
            copyVec.x = target.getWorldTranslation().x;//copyVec.x * delta + target.getWorldTranslation().x * (1-delta);
            //copyVec.x = target.getWorldTranslation().x;
//target.getWorldTranslation().x;
           //else
               //copyVec.x = copyVec.x * alpha + target.getWorldTranslation().x * (1-alpha);
        //}
        /*else if(TrackManager.direction.equals(Direction.ZNEGATIVE)){
            alpha = checkDifference(copyVec.x,target.getWorldTranslation().x);
            copyVec.x = TrackManager.CurrentGravity;//copyVec.x * alpha + target.getWorldTranslation().x * (1-alpha);
            if(withinRange(copyVec.x,target.getWorldTranslation().x,1))
                transitioning = false;
            copyVec.z = target.getWorldTranslation().z;
        }
        else if(TrackManager.direction.equals(Direction.XPOSITIVE)){
            alpha = checkDifference(copyVec.z,target.getWorldTranslation().z);
            copyVec.z = TrackManager.CurrentGravity;//copyVec.z * alpha + target.getWorldTranslation().z * (1-alpha);
            if(withinRange(copyVec.z,target.getWorldTranslation().z,1))
                transitioning = false;
            copyVec.x = target.getWorldTranslation().x;
        }
        else{
            alpha = checkDifference(copyVec.z,target.getWorldTranslation().z);
            copyVec.z = TrackManager.CurrentGravity;//copyVec.z * alpha + target.getWorldTranslation().z * (1-alpha);
            if(withinRange(copyVec.z,target.getWorldTranslation().z,1))
                transitioning = false;
            copyVec.x = target.getWorldTranslation().x;
        }*/
        
        float beta = .9f;
        copyVec.y= copyVec.y * beta + cameraHeight * (1-beta);
       
        if(smoothMotion)
            pos.addLocal(target.getWorldTranslation());
        else
            pos.addLocal(copyVec);
        //pos.addLocal(target.getWorldTranslation());
        //System.out.println("pos is     " + pos);
        //System.out.println("target is     " + target.getWorldTranslation());
        //System.out.println("copyvec is     " + copyVec);
    }
    public float checkDifference(float copyVec,float translation){
        float diff = ControllerMath.absDifference(copyVec, translation);
        
        return ControllerMath.camLerp(diff,2.0f);
    }
    public boolean withinRange(float current, float target, float interval){
        return (current > target - interval && current < target + interval);
    }

    @Override
    protected void updateCamera(float tpf) {
        if (enabled) {
            targetLocation.set(target.getWorldTranslation()).addLocal(lookAtOffset);
             
                //easy no smooth motion
                vRotation = targetVRotation;
                rotation = targetRotation;
                distance = targetDistance;
                computePosition();
                if(isAnimated){
                   camAnimation.play();
                }
                else
                    cam.setLocation(pos.addLocal(lookAtOffset));
            
            //keeping track on the previous position of the target
            prevPos.set(targetLocation);

            //the cam looks at the target
            targetLocation.y = targetHeight;
            /*if(direction.equals(Direction.ZPOSITIVE)){
            targetLocation.z+=lookAtAddition;
            }
            else if(direction.equals(Direction.ZNEGATIVE)){
               targetLocation.z-=lookAtAddition; 
            }
            else if(direction.equals(Direction.XPOSITIVE)){
               targetLocation.x+=lookAtAddition;
            }
            else{
               targetLocation.x-=lookAtAddition;
            }*/
            checkGradualRotation();
            targetLocation.addLocal(rotationLerp);
            //Vector3f lookAt = new Vector3f(0,targetLocation.y,targetLocation.z);
            cam.lookAt(targetLocation, initialUpVec);// testing shake by not x move

        }
    }
    
    public void checkGradualRotation(){
        rotationLerp.x = ControllerMath.lerp(rotationLerp.x, rotationGoal.x,.90f);
        rotationLerp.y = ControllerMath.lerp(rotationLerp.y, rotationGoal.y,.90f);
        rotationLerp.z = ControllerMath.lerp(rotationLerp.z, rotationGoal.z,.90f);
        
        if(PlayState.start)
            if(ControllerMath.withinRange(rotationLerp.x,rotationGoal.x,.1f))
                if(ControllerMath.withinRange(rotationLerp.y,rotationGoal.y,.1f))
                    if(ControllerMath.withinRange(rotationLerp.z,rotationGoal.z,.1f));//next line is testing for turns
                        //PlayerListener.bikerControl.setWalkDirection(TrackManager.direction.vector.mult(1.3f));
            
    }
    
    public void setGradualRotation(Vector3f rotationGoal){
        graduallyRotating = true;
        if(rotationGoal.equals(Vector3f.UNIT_Z)){
            this.rotationGoal.x = 0;
            this.rotationGoal.y = 0;
            this.rotationGoal.z = lookAtAddition;
        }
        else if(rotationGoal.equals(RunningAlgsFacility.UNIT_Z_NEG)){
            this.rotationGoal.x = 0;
            this.rotationGoal.y = 0;
            this.rotationGoal.z = -lookAtAddition;
        }
        else if(rotationGoal.equals(Vector3f.UNIT_X)){
            this.rotationGoal.z = 0;
            this.rotationGoal.y = 0;
            this.rotationGoal.x = lookAtAddition;
        }
        else if(rotationGoal.equals(RunningAlgsFacility.UNIT_X_NEG)){
            this.rotationGoal.z = 0;
            this.rotationGoal.y = 0;
            this.rotationGoal.x = -lookAtAddition;
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
    
    public void setCameraHeight(float cameraHeight){
        if(!automaticDuck)
            this.cameraHeight = cameraHeight;
    }
    
    public void setTargetHeight(float height){
        this.targetHeight = height;
    }
    
    public void setLookAtAddition(float addition){
        lookAtAddition = addition;
    }
    public void setVertical(String vert){
        if(vert.equalsIgnoreCase("min"))
            vertical = minVertical;
        else vertical = maxVertical;
    }
    
    public void setVRotation(float vRotation){
        this.vRotation = vRotation;
        
    }
    
    public void checkAutomaticDuck(int currentTile){
        //if(currentTile == 9)
            //cameraHeight = -3f;
        if(currentTile == 8){
            automaticDuck = true;
            cameraHeight = -8f;
        }
        
    }
    
    public void resetAutomaticDuck(){
        automaticDuck = false;
        cameraHeight = -3f;
    }
    
    public void setAnimation(CamAnimation camAnimation){
         this.camAnimation =  camAnimation;
    }
    
    public void setIsAnimated(boolean isAnimated){
        this.isAnimated = isAnimated;
    }
    
    public Vector3f getNormalCamLoc(){
        //rotationLerp.x =0;rotationLerp.y=0;rotationLerp.z=lookAtAddition;
        return pos;//.addLocal(lookAtOffset);
    }
    
    public void reset(){
        resetAutomaticDuck();
        setGradualRotation(Vector3f.UNIT_Z);
        setDefaultHorizontalRotation(FastMath.DEG_TO_RAD*-90f);
        setDefaultVerticalRotation(FastMath.DEG_TO_RAD*45f);
    }
}
