/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.bullet.BulletAppState;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import java.util.HashMap;
import levels.PrefabRoad.RoadFields;

/**
 *
 * @author Miguel Martrinez
 */
public class RoadGenerator {
HashMap<String,Node> map;

    public RoadGenerator() {
       
    }
    public void setTiles(HashMap<String,Node> map){
        this.map = map;
    }
    public PrefabRoad generatePrefabRoad(RoadLimits limits){
        
        PrefabRoad prefab = new PrefabRoad();
        RoadFields fields = prefab.new RoadFields();
        fields.setLimits(limits);
        prefab.setRoadFields(fields);
        for(int i = 0; i < limits.min; i++){
            int random = FastMath.rand.nextInt(18);
            if(i == limits.min -1)
                prefab.addTile(map.get("transition").clone(false),"transition");
            else{
                if(random <= 2)
                    prefab.addTile(map.get("node").clone(false),"node");
               else if(random <= 5)
                    prefab.addTile(map.get("tile2").clone(false),"tile2");
                else if(random <= 8)//
                    prefab.addTile(map.get("tile3").clone(false),"tile3");
                else if(random <= 11)//
                    prefab.addTile(map.get("tile5").clone(false),"tile5");
                else if(random <= 14)
                    prefab.addTile(map.get("tile6").clone(false), "tile6");
                else if(random <= 17)
                    prefab.addTile(map.get("treesTile").clone(false), "treesTile");
            }
        }
        prefab.setControls();
        return prefab;
    }
}
