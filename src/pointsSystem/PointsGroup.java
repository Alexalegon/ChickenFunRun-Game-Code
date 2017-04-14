/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import audiosystem.AudioManager;
import pointsSystem.PointObjectBag;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import guisystem.Gui;
import guisystem.Hud;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import levels.SplineFactory;
import mygame.CustomNode;
import maincharacter.PlayerListener;
import obstaclesystem.BoundingDebugger;
import pointsSystem.PointObjectSettings.Direction;
import pointsSystem.PointObjectSettings.ObjectType;
import pointsSystem.PointObjectSettings.PointGroupSettings;
import pointsSystem.PointObjectSettings.PointGroupSettings.GroupSize;
import pointsSystem.PointObjectSettings.PointGroupSettings.GroupType;
import pointsSystem.PointObjectSettings.PointGroupShape;
import pointsSystem.PointObjectsFactory.RotationControl;

/**
 *
 * @author Miguel Martrinez
 */
public class PointsGroup implements Cloneable{
 PointObjectBag pointObjectBag;
 HashMap<ObjectType,PointsNode> map;
 GroupSize size;
GroupType type;
Node rootNode;
Node character;
Gui gui;
Hud hud;
boolean flag = true;
int check = 0;
int totalPoints = 0;
int lowPointObjects = 0;
int midPointObjects = 0;
int highPointObjects = 0;
PointsSystemCallback callback;
PointGroupShape shape = PointGroupShape.NORMAL;
    public PointsGroup() {
        pointObjectBag = new PointObjectBag();
    }
    
    public PointsGroup(HashMap<ObjectType,PointsNode> map, PointObjectBag bag, GroupSize size, GroupType type){
        this.map = map;
        this.pointObjectBag = bag;
        this.size = size;
        this.type = type;
        setGroupPointsTotal();
       
    }
    
   
    
    public void setGui(Gui gui){
        this.gui = gui;
        hud = gui.getHud();
    }
    
    public void setGroupPointsTotal(){
        for(PointsNode pointsNode: pointObjectBag.pointObjectsList){
            totalPoints += pointsNode.getPointValue();
            if(pointsNode.getPointValue() == 1)
                lowPointObjects++;
            else if(pointsNode.getPointValue() == 2)
                midPointObjects++;
            else
                highPointObjects++;
        }  
    }
    
    public void setRootNode8Character(Node rootNode, Node character){
        this.rootNode = rootNode;
        this.character = character;
    }
    public void setMap(HashMap newMap){
        map = newMap;
    }
    
    public void setPointObjectBag(PointObjectBag pointObjBag){
        pointObjectBag = pointObjBag;
    }
    
    public void setShape(PointGroupShape shape){
        this.shape = shape;
    }
    
    public void setVectorLocation(Direction direction, Vector3f initLocation, float cushion){
        Vector3f newLocation = initLocation.clone();newLocation.y+=3f;
        if(shape.equals(PointGroupShape.NORMAL)){
        SplineFactory.curve.arrangeObjectsFromMid(pointObjectBag.pointObjectsList, 55f, 0f, newLocation, direction);
        /*for(PointsNode node : pointObjectBag.pointObjectsList){
            
            if(direction.equals(Direction.ZPOSITIVE))
            node.setLocalTranslation(newLocation.x, newLocation.y, newLocation.z+=cushion);
            else if(direction.equals(Direction.ZNEGATIVE))
              node.setLocalTranslation(newLocation.x, newLocation.y, newLocation.z-=cushion); 
            else if(direction.equals(Direction.XPOSITIVE))
              node.setLocalTranslation(newLocation.x+=cushion, newLocation.y, newLocation.z);
            else 
              node.setLocalTranslation(newLocation.x-=cushion, newLocation.y, newLocation.z);
            }*/
        }
        else{
            SplineFactory.curve.arrangeObjectsFromMid(pointObjectBag.pointObjectsList, 55f, 5f, newLocation, direction);
        }
    }
    
    public void attachToRootNode(){
        for(PointsNode node : pointObjectBag.pointObjectsList)
            rootNode.attachChild(node.getNode());
    }
    public void detach(){
        for(PointsNode node : pointObjectBag.pointObjectsList)
            rootNode.detachChild(node.getNode());
    }
    
    
    public void setPointsSystemCallback(PointsSystemCallback callback){
        this.callback = callback;
    }
  
    public void checkCollision(){
        
        if(check >= pointObjectBag.pointObjectsList.size())
               return;
       ArrayList<PointsNode> list = new ArrayList<PointsNode>();
        for(PointsNode pointsNode : pointObjectBag.pointObjectsList){
          
              if(pointsNode.isNodeEnabled()){
            Node node = pointsNode.getNode();
            if(PlayerListener.bikerControl.checkIntersection(node.getWorldBound(),"points")){
                
                //hud.addPoints(pointsNode.getPointValue());
                //hud.showScore();
                callback.addToPointObjectsCount(pointsNode.getPointValue());
                check++;
                AudioManager.pointsSound.playInstance();
                //System.out.println("check: " + check);
                rootNode.detachChild(node);
                pointsNode.detachDebugger();
                list.add(pointsNode);
                }
            
               }
          
        }
        if(list.size() > 0)
            for(PointsNode pointNode: list){
                pointNode.disableNode();
            }
        }
    
    public void clearGroup(){
        for(PointsNode pointsNode: pointObjectBag.pointObjectsList){
            Node node = pointsNode.getNode();
            rootNode.detachChild(node);
            pointsNode.enableNode();
        }
        check = 0;
    }
    
    public void resumeRotations(){
       for(PointsNode pointsNode: pointObjectBag.pointObjectsList){
            pointsNode.getNode().getControl(RotationControl.class).setEnabled(true);
        } 
    }
    
    public void pauseRotations(){
        for(PointsNode pointsNode: pointObjectBag.pointObjectsList){
            pointsNode.getNode().getControl(RotationControl.class).setEnabled(false);
        }
    }
        
    public int getTotalPoints(){
        return totalPoints;
    }
    
    public int getLowPointCount(){
    return lowPointObjects;
}
          
    public int getMidPointCount(){
        return midPointObjects;
    }
    
    public int getHighPointCount(){
        return highPointObjects;
    }
    public HashMap<ObjectType,PointsNode> getMap(){
        return map;
    }
    
    public PointObjectBag getPointObjectBag(){
        return pointObjectBag;
    }
    
    public GroupSize getGroupSize() {
       return size;
    }
    
    public GroupType getGroupType(){
        return type;
    }

    @Override
    public PointsGroup clone() {
       try{
           HashMap<ObjectType,PointsNode> clonedMap = new HashMap<ObjectType,PointsNode>();
           PointObjectBag pointObjectBag = this.pointObjectBag.clone();
        PointsGroup cloned = (PointsGroup) super.clone();
        for(Entry<ObjectType,PointsNode> entry : map.entrySet()){
            clonedMap.put(entry.getKey(), entry.getValue().clone());
        }
        cloned.setMap(clonedMap);
        cloned.setPointObjectBag(pointObjectBag);
        return cloned;
       }catch(CloneNotSupportedException e){
           throw new AssertionError();
       }
        //return null; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
