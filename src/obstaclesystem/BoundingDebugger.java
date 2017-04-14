/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;


import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class BoundingDebugger {
    static ArrayList<Geometry> list =  new ArrayList<Geometry>();
    static ArrayList<Node> nodeList = new ArrayList<Node>();
    
    public static int addGeometry(float xExtent, float yExtent, float zExtent){
        Box box = new Box(xExtent,yExtent,zExtent);
        Geometry geom = new Geometry("g",box);
        Node node = new Node();
        //node.attachChild(geom);
        addMaterial(geom);
        list.add(geom);
        nodeList.add(node);
        return list.size()-1;
    }
    
    public static void setLocalTranslation(int index,Vector3f location){
        //if(index > list.size())
            //return;
        nodeList.get(index).setLocalTranslation(location);
        PlayState.rootNode.attachChild(nodeList.get(index));
    }
    
    public static void rotateGeometry(int index, Quaternion quat){
        Vector3f clone = list.get(index).getLocalTranslation().clone();
        nodeList.get(index).setLocalRotation(quat); 
    }
    
    public static void addMaterial(Geometry geom){
        Material mat = new Material(PlayState.assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Pink);
        geom.setMaterial(mat);
    }
    
    public static Geometry getGeometry(int index){
        return list.get(index);
    }
    
    public static void detachGeometry(int index){
        PlayState.rootNode.detachChild(list.get(index));
    }
}
