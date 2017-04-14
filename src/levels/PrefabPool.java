/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.scene.Node;
import guisystem.HomeScreen;
import java.util.ArrayList;

/**
 *
 * @author Miguel Martrinez
 */
public class PrefabPool {
int size;
static int currentIndex = 0;
boolean rebuildList;
public static Node wallNode;
ArrayList<PrefabRoad> prefabs = new ArrayList<PrefabRoad>();
    public PrefabPool(int size) {
        this.size = size;
        rebuildList = false;
    }
    
    public void addPrefab(PrefabRoad prefab){
        prefabs.add(prefab);
        
    }
    
    public PrefabRoad releasePrefab(){
        
        for(int i = currentIndex; i < prefabs.size();i++){
            if(prefabs.get(i).inUse == false){System.out.println("Prefab released:   "+ i);
                if(i == prefabs.size()-1)
                currentIndex = 0;
                else
                    currentIndex = i;
                prefabs.get(i).enable();
                return prefabs.get(i);
            }
        }
        return null;
    }
    //wall objects kept separate from prefabs
    public void addWall(Node node){
        wallNode = node;
        node.setName("wall");
    }
    //release wall to ensure correct turn
    //wall is separate from prefab road
    public Node releaseWall(){
        return wallNode;
    }

public void rebuildTheList(){
    
    for(PrefabRoad prefab: prefabs){
        if(prefab.inUse == true)
            prefabs.remove(prefab);
        }
    }   

public void putPrefabBackInPool(PrefabRoad prefab){
    prefabs.add(prefab);
}

public static void resetPool(){
    currentIndex = 0;
    HomeScreen.hideWall();
}
}

