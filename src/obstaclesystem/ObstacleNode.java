/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.HashMap;
import levels.RotationQuats;
import mygame.CustomNode;
import mygame.PlayState;
import obstaclesystem.ObstacleSettings.ObstacleTypes;
import pointsSystem.PointObjectsFactory;

/**
 *
 * @author Miguel Martrinez
 */
public final class ObstacleNode implements CustomNode{
float harmPoints;
int[] debugIndeces = new int[3];
Node node,geomNode;
Geometry g;
boolean enabled = true;
ObstacleSettings.ObstacleTypes type;
public ObstacleDescription description;
Vector3f locationOffset = Vector3f.ZERO;
float quicksandX, quicksandZ;
boolean isPassed = false;

ArrayList<BoundingBox>boundingBoxes = new ArrayList<BoundingBox>();;
ArrayList<Vector3f> centerOffsets = new ArrayList<Vector3f>();
ArrayList<Vector3f> extents = new ArrayList<Vector3f>();
ArrayList<String> names = new ArrayList<String>();
HashMap<String,Vector3f> offsetsMap = new HashMap<String,Vector3f>();
    public ObstacleNode(){
        
    }
    //public ObstacleNode(float harmPoints){
        //this.harmPoints = harmPoints;
    //}
    //public ObstacleNode(Node node){
      //  this.node = node;
    //}
    public ObstacleNode(float harmPoints,Node node,ObstacleTypes type, ObstacleDescription description) {
        this.harmPoints = harmPoints;
        this.node = node;
        this.type = type;
        
        this.description = description;//boundingBoxes.add(new BoundingBox());
        if(type.equals(ObstacleSettings.ObstacleTypes.Ducking))
            setBoundingVolume();
        else if(description.equals(ObstacleDescription.FIREPLACE))
            setFireplaceBounds();
        else if(description.equals(ObstacleDescription.QUICKSAND))
            setQuicksandBounds();
        else if(description.equals(ObstacleDescription.JUMPROCK))
            setJumpRockBounds();
    }
    
    public void setHarmPoints(float harmPoints){
        this.harmPoints = harmPoints;
    }
    public void setNode(Node node){
        this.node = node;
    }
    
    public void enable(){
      this.enabled = true;  
    }
    public void setDescription(ObstacleDescription description){
        this.description = description;
        
    }
    
    public void setLocationOffset(Vector3f offset){
        locationOffset = offset;
    }
    
    public void disable(){
        this.enabled = false;
    }
    
    public boolean isPassed(){
        return isPassed;
    }
    
    public void setisPassed(boolean passed){
        isPassed = passed;
    }
    
@Override
    public void setLocalTranslation(Vector3f vector){
        node.setLocalTranslation(vector);
        resetBoundingVolume();
    }
    
@Override
    public void setLocalTranslation(float x, float y, float z){
        Vector3f location = node.getLocalTranslation();
        //node.setLocalTranslation(location.x + x, location.y + y, location.z + z);
        Vector3f newLoc = new Vector3f(x,y,z);
        node.setLocalTranslation(newLoc.addLocal(locationOffset));
        resetBoundingVolume();
    }
    
    public void printBoundingVolumesTranslations(){
       for(BoundingBox box: boundingBoxes)
       System.out.print(box.getCenter());
    }
    
    public Node getNode(){
        return node;
    }
    
    public float getHarmPoints(){
        return harmPoints;
    }
    
    public Vector3f getLocationOffset(){
        return locationOffset;
    }
    
    public void setFireplaceBounds(){
        //BoundingBox box = (BoundingBox)node.getChild("fire").getWorldBound().clone();
       // box.setCenter(node.getChild("fire").getWorldBound().getCenter());
        //box.setYExtent(box.getYExtent()/5f);
        boundingBoxes.add((BoundingBox)node.getChild("wood").getWorldBound());
        ///boundingBoxes.add(box);
    }
    public void setQuicksandBounds(){
        BoundingBox original = (BoundingBox) node.getWorldBound();
        BoundingBox box = new BoundingBox(node.getLocalTranslation(),original.getXExtent(),2.25f,original.getZExtent()); 
        quicksandX = box.getXExtent();
        quicksandZ = box.getZExtent();
        boundingBoxes.add(box);
    }
    public void setJumpRockBounds(){
        boundingBoxes.add((BoundingBox)node.getWorldBound());
    }
    
    public void setBoundingVolume(){BoundingBox box = (BoundingBox)node.getWorldBound();
        if(description.equals(ObstacleDescription.DUCKROCK)){
            
          
          //box.getExtent(extents);
          Vector3f leftCenter = new Vector3f();
          leftCenter.x += 6.5f;
          leftCenter.y -= 1.5f;
          //for(int i = 0; i < 100f; i++)
              //System.out.println(box.getCenter());
          extents.add(new Vector3f(3.5f,box.getYExtent(),2f));
          BoundingBox leftBox = new BoundingBox(box.getCenter().clone().add(leftCenter),extents.get(0));
          Box b = new Box(3.5f,box.getYExtent(),2f);
          debugIndeces[0] = BoundingDebugger.addGeometry(3.5f,box.getYExtent(),2f);
          
          Vector3f rightCenter = new Vector3f();
          rightCenter.x -= 7.5f;
          rightCenter.y -=1.5f;
          extents.add(new Vector3f(3.5f,box.getYExtent(),2f));
          BoundingBox rightBox = new BoundingBox(box.getCenter().clone().add(rightCenter),extents.get(1));
          debugIndeces[1] = BoundingDebugger.addGeometry(3.5f,box.getYExtent(),2f);
          
          Vector3f topCenter = new Vector3f();
          topCenter.y += 1f;//box.getYExtent() * .02f;
          float topYExtent = box.getYExtent()*.125f;
          extents.add(new Vector3f(box.getXExtent(),.8f,1.5f));
          BoundingBox topBox = new BoundingBox(box.getCenter().clone().add(topCenter),extents.get(2));
          debugIndeces[2] = BoundingDebugger.addGeometry(box.getXExtent(),.5f,1.5f);
          
         boundingBoxes.add(leftBox);
         boundingBoxes.add(rightBox);
         boundingBoxes.add(topBox);
         centerOffsets.add(leftCenter);
         centerOffsets.add(rightCenter);
         centerOffsets.add(topCenter);
         names.add("left");
         names.add("right");
         names.add("top");
         offsetsMap.put(names.get(0),centerOffsets.get(0));
         offsetsMap.put(names.get(1),centerOffsets.get(1));
        offsetsMap.put(names.get(2),centerOffsets.get(2));
        }
        else if(description.equals(ObstacleDescription.WAGON)){
            //BoundingBox box = (BoundingBox)node.getWorldBound();
            
            Vector3f leftCenter = new Vector3f();
            leftCenter.x += 3.5;//4.5f
            extents.add(new Vector3f(3f,box.getYExtent(),box.getZExtent()));
            BoundingBox leftBox = new BoundingBox(box.getCenter().clone().add(leftCenter),extents.get(0));
            debugIndeces[0] = BoundingDebugger.addGeometry(extents.get(0).x,extents.get(0).y,extents.get(0).z);
            boundingBoxes.add(leftBox);
            centerOffsets.add(leftCenter);
            names.add("left");
            offsetsMap.put(names.get(0),centerOffsets.get(0));
            
            Vector3f rightCenter = new Vector3f();
            rightCenter.x -=7.5f;
            extents.add(new Vector3f(3f,box.getYExtent(),box.getZExtent()));
            BoundingBox rightBox = new BoundingBox(box.getCenter().clone().add(rightCenter),extents.get(1));
            debugIndeces[1] = BoundingDebugger.addGeometry(extents.get(1).x,extents.get(1).y,extents.get(1).z);
            boundingBoxes.add(rightBox);
            centerOffsets.add(rightCenter);
            names.add("right");
            offsetsMap.put(names.get(1),centerOffsets.get(1));
            
            Vector3f topCenter = new Vector3f();
            topCenter.y += 1f;
            extents.add(new Vector3f(box.getXExtent(),4f,box.getZExtent()));
            BoundingBox topBox = new BoundingBox(box.getCenter().clone().add(topCenter),extents.get(2));
            debugIndeces[2] = BoundingDebugger.addGeometry(extents.get(2).x, extents.get(2).y, extents.get(2).z);
            boundingBoxes.add(topBox);
            centerOffsets.add(topCenter);
            names.add("top");
            offsetsMap.put(names.get(2),centerOffsets.get(2));
            
            
        }
        
         resetGeometryBounds();
         for(int k: debugIndeces){
             //BoundingDebugger.list.get(k).setLocalTranslation(box.getCenter());
             //BoundingDebugger.nodeList.get(k).attachChild(BoundingDebugger.getGeometry(k));
         }
    }
    
    public void resetBoundingVolume(){
        int i = 0;
        if(boundingBoxes.isEmpty() || description.equals(ObstacleDescription.FIREPLACE))
            return;
        else if(description.equals(ObstacleDescription.QUICKSAND) || description.equals(ObstacleDescription.JUMPROCK)){
            boundingBoxes.get(0).setCenter(node.getLocalTranslation());
            return;
        }
        for(BoundingBox boxes: boundingBoxes){
            Vector3f center = new Vector3f();
            centerOffsets.get(i).add(node.getWorldBound().getCenter(), center);
            boxes.setCenter(center);
            BoundingDebugger.setLocalTranslation(debugIndeces[i], node.getLocalTranslation());
           //System.out.println("Center"+center);
           //System.out.println(node.getLocalTranslation());
            i++;
        }
    }
    
    public void resetGeometryBounds(){
        int i = 0;
        if(boundingBoxes.isEmpty())
            return;
        for(BoundingBox boxes: boundingBoxes){
            Vector3f center = new Vector3f();
            centerOffsets.get(i).add(node.getWorldBound().getCenter(), center);
            boxes.setCenter(center);
            BoundingDebugger.getGeometry(debugIndeces[i]).setLocalTranslation(center);
            i++;
        }
    }
    
    public ArrayList<BoundingBox> getBoundingVolumes(){
        return boundingBoxes;
    }
    
    public ObstacleDescription getDescription(){
        return description;
    }
    
    public ObstacleTypes getType(){
        return type;
    }
    
    public ObstacleNode clone(){
        ObstacleNode clone = new ObstacleNode(harmPoints,node.clone(false),type,description);
        clone.setLocationOffset(locationOffset);
        //clone.setNode(node.clone(false));
        return clone;
    }

    @Override
    public void setLocalRotation(Quaternion quaternion) {
        node.setLocalRotation(quaternion);
        if(description.equals(ObstacleDescription.QUICKSAND)){
            if(quaternion.equals(RotationQuats.ROTATIONQUAT360) || quaternion.equals(RotationQuats.ROTATONQUAT180)){
                boundingBoxes.get(0).setXExtent(quicksandX);
                 boundingBoxes.get(0).setZExtent(quicksandZ);
            }
            else{
                boundingBoxes.get(0).setXExtent(quicksandZ);
                boundingBoxes.get(0).setZExtent(quicksandX);
            }
        }
        else if(description.equals(ObstacleDescription.DUCKROCK) || description.equals(ObstacleDescription.WAGON)){ 
            Vector3f newCenter; 
            for(int i = 0; i < boundingBoxes.size(); i++){
                if(quaternion.equals(RotationQuats.ROTATIONQUAT360) || quaternion.equals(RotationQuats.ROTATONQUAT180)){
                    boundingBoxes.get(i).setXExtent(extents.get(i).x);
                    boundingBoxes.get(i).setYExtent(extents.get(i).y);
                    boundingBoxes.get(i).setZExtent(extents.get(i).z);
                   
                    if(quaternion.equals(RotationQuats.ROTATONQUAT180)){
                        if(names.get(i).equalsIgnoreCase("left")){
                            newCenter = node.getWorldBound().getCenter().add(offsetsMap.get("left").negate());
                            
                                 
                        }
                        else if(names.get(i).equalsIgnoreCase("right")){
                        newCenter = node.getWorldBound().getCenter().add(offsetsMap.get("right").negate());
                        }
                        else{
                            newCenter = node.getWorldBound().getCenter().add(offsetsMap.get("top"));
                        }
                    }
                    else{
                        if(names.get(i).equalsIgnoreCase("top"))
                            newCenter = node.getWorldBound().getCenter().add(offsetsMap.get("top"));
                        else
                            newCenter= node.getWorldBound().getCenter().add(offsetsMap.get(names.get(i)));
                    }
                    
                    
                    boundingBoxes.get(i).setCenter(newCenter);
                    BoundingDebugger.rotateGeometry(debugIndeces[i],quaternion);
                   
                    }        
                else{
                    boundingBoxes.get(i).setXExtent(extents.get(i).z);
                    boundingBoxes.get(i).setYExtent(extents.get(i).y);
                    boundingBoxes.get(i).setZExtent(extents.get(i).x);
                    if(quaternion.equals(RotationQuats.ROTATIONQUAT90)){
                        if(names.get(i).equalsIgnoreCase("left")){
                        newCenter = switchVectorXZ(offsetsMap.get("left").negate(),node.getWorldBound().getCenter());
                    }
                        else if(names.get(i).equalsIgnoreCase("right")){
                        newCenter = switchVectorXZ(offsetsMap.get("right").negate(),node.getWorldBound().getCenter());
                    }
                        else{
                        newCenter = switchVectorXZ(offsetsMap.get("top"),node.getWorldBound().getCenter());
                    }
                  }
                    else{
                        newCenter = switchVectorXZ(offsetsMap.get(names.get(i)),node.getWorldBound().getCenter());
                    }
                    
                    boundingBoxes.get(i).setCenter(newCenter);
                    BoundingDebugger.rotateGeometry(debugIndeces[i], quaternion);
                   
                }
            }
        }
    }
    
    public Vector3f switchVectorXZ(Vector3f offset, Vector3f center){
        return new Vector3f(offset.z + center.x,offset.y + center.y, offset.x + center.z);
    }
    
    public void rotateOffsets(Quaternion quat){
        if(quat.equals(RotationQuats.ROTATIONQUAT90));
    }
}
