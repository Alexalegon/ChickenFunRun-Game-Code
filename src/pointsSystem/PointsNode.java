/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import mygame.CustomNode;
import mygame.PlayState;
import obstaclesystem.BoundingDebugger;

/**
 *
 * @author Miguel Martrinez
 */
public class PointsNode implements CustomNode{
private int pointValue;
private Node node;
private boolean nodeEnabled =  true;
int boundIndex = 0;
BoundingBox box;
Geometry geom;
    public PointsNode(Node node) {
        this.node = node;
    }
    public PointsNode(int pointValue){
        this.pointValue = pointValue;
    }
   
    
    public PointsNode(Node node, int pointValue){
        this.node = node;
        this.pointValue = pointValue;
        setBounds();
    }
    
    public void setPointsValue(int pointValue){
        this.pointValue = pointValue;
    }
    
    public void setNode(Node node){
        this.node = node;
    }
    
    public void setBounds(){
        box = (BoundingBox) node.getWorldBound();
        //box.setXExtent(2f);
        //box.setZExtent(2f);
        //node.setModelBound(box);
        boundIndex = BoundingDebugger.addGeometry(5f,box.getYExtent(),5f);
         Box box2 = new Box(box.getXExtent(),box.getYExtent(),box.getZExtent());
         geom = new Geometry("g",box2);
        addMaterial(geom);
        //PlayState.rootNode.attachChild(geom);
    }
     public static void addMaterial(Geometry geom){
        Material mat = new Material(PlayState.assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Pink);
        geom.setMaterial(mat);
    }
    public void disableNode(){
        nodeEnabled = false;
    }
    
    public void enableNode(){
        nodeEnabled = true;
    }
    
    public boolean isNodeEnabled(){
        return nodeEnabled;
    }
    
    public void detachDebugger(){
        if(boundIndex < 5000)
           BoundingDebugger.detachGeometry(boundIndex);
    }
    
@Override
    public void setLocalTranslation(float x, float y, float z){
        node.setLocalTranslation(x, y, z);
        Vector3f location = new Vector3f(x,y,z);
        if(boundIndex < 5000)
             BoundingDebugger.setLocalTranslation(boundIndex, location);
        //BoundingDebugger.setLocalTranslation(boundIndex,location);
        //geom.setLocalTranslation(location);
    }
    public int getPointValue(){
        return pointValue;
    }
    public Node getNode(){
        return node;
    }

    @Override
    protected PointsNode clone()  {
        Node clonedNode = (Node)node.clone(false);
        PointsNode cloned = new PointsNode(clonedNode,pointValue);
        return cloned;
        
       
        
    }

    @Override
    public void setLocalTranslation(Vector3f location) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLocalRotation(Quaternion quaterninon) {
       node.setLocalRotation(quaterninon);
       
    }

    
}
