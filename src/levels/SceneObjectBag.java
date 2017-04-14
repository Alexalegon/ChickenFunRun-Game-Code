/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author Miguel Martrinez
 */
public class SceneObjectBag {
       int size;
ArrayList <Node> sceneObjectsList; 
    public SceneObjectBag() {
        sceneObjectsList = new ArrayList<Node>();
    }
    
    public SceneObjectBag(int size) {
        sceneObjectsList = new ArrayList(size);
    }
    
    public void addPointObject(Node node){
        sceneObjectsList.add(node);
    }
    public Node getPointObject(int index){
        return sceneObjectsList.get(index);
    }
    
    public int getSize(){
        return sceneObjectsList.size();
    }
    
    public ArrayList getPointObjectsList(){
        return sceneObjectsList;
    }
    
    public void clearPointObjectsList(){
        sceneObjectsList.clear();
    }
    
    public void clearObjectsFromWorld(Node world){
        for(Node point: sceneObjectsList)
            world.detachChild(point);
    }
}
