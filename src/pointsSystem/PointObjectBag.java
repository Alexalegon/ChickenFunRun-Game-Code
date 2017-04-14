/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Martrinez
 */
public class PointObjectBag implements Cloneable{
    int size;
ArrayList <PointsNode> pointObjectsList; 
    @Override
    protected PointObjectBag clone()  {
        try { 
            PointObjectBag cloned = (PointObjectBag)super.clone();
            ArrayList<PointsNode> list = new ArrayList<PointsNode>();
            for(PointsNode node: cloned.pointObjectsList){
                list.add(node.clone());
            }
            cloned.setPointObjectList((list));
            return cloned;
        } catch (CloneNotSupportedException ex) {
           throw new AssertionError("clone error"); //Logger.getLogger(PointObjectBag.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PointObjectBag() {
        pointObjectsList = new ArrayList<PointsNode>();
    }
    
    public PointObjectBag(int size) {
        pointObjectsList = new ArrayList(size);
    }
    
    public void addPointObject(PointsNode node){
        pointObjectsList.add(node);
    }
    
    public void setPointObjectList(ArrayList<PointsNode> pointObjectList){
        this.pointObjectsList = pointObjectList;
    }
    public PointsNode getPointObject(int index){
        return pointObjectsList.get(index);
    }
    
    public int getSize(){
        return pointObjectsList.size();
    }
    
    public ArrayList<PointsNode> getPointObjectsList(){
        return pointObjectsList;
    }
    
    public void clearPointObjectsList(){
        pointObjectsList.clear();
    }
    
    public void clearObjectsFromWorld(Node world){
        for(PointsNode point: pointObjectsList)
            world.detachChild(point.getNode());
    }
}
