/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.bounding.BoundingBox;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 *
 * @author Miguel Martrinez
 */
public class PrefabRoad {
RoadFields roadFields;
Node motherNode;
int index = 0;

ArrayList<Node> tileList = new ArrayList<Node>();
ArrayList<String> tileNames = new ArrayList<String>();
ArrayList<Float> zDimensions = new ArrayList<Float>();
ArrayList<Float> xDimensions = new ArrayList<Float>();
boolean inUse = false;
    public PrefabRoad() {
        roadFields = new RoadFields();
        motherNode = new Node();
    }
    
    public void setRoadFields(RoadFields roadFields){
            this.roadFields = roadFields;
            fillRoadFields();
        }
    
    public void addTile(Node node, String tileName){
        tileList.add(index, node);
        index++;
        tileNames.add(tileName);
        motherNode.attachChild(node);
        
        //if(tileList.size() == 1){
            if(tileName .equalsIgnoreCase("tile3")){
            Geometry geom = (Geometry)node.getChild("Cube1");
              
            //geom.getMesh().updateBound();
            }
            BoundingBox bound = (BoundingBox) node.getWorldBound();
            //if(bound.getZExtent() > 64f)
                //throw new AssertionError(tileName + "    " +bound.getZExtent());
            //System.out.println(tileName + "     " + bound.getZExtent());
            //System.out.println(tileName + "     " + bound.getXExtent());
        //roadFields.setTileDimensions(bound.getZExtent()*2);
            roadFields.setTileDimensions(bound.getZExtent()*2f,bound.getXExtent()*2f);
            zDimensions.add(bound.getZExtent()*2f);
            xDimensions.add(bound.getXExtent()*2f);
       // }
    }
    
    public void setControls(){
        for(Node node: tileList){
        CollisionShape shape = CollisionShapeFactory.createMeshShape(node);
       RigidBodyControl control = new RigidBodyControl(shape,0);
       
        node.addControl(control);
        
        }
    }
    
    public void enable(){
        this.inUse = true;
    }
    public void disactivate(){
        this.inUse = false;
    }
   
    public Node getMothernode(){
        return motherNode;
    }
    
    public void fillRoadFields(){
        roadFields.firstTile = 0;
        roadFields.middleTile = roadFields.numOfTiles/2;
        roadFields.lastTile = roadFields.numOfTiles-1;
    }
    public void fillFinalRoadFields(){
        roadFields.firstTileLoc = tileList.get(roadFields.firstTile).getLocalTranslation();
        roadFields.middleTileLoc = tileList.get(roadFields.middleTile).getLocalTranslation();
        roadFields.lastTileLoc = tileList.get(roadFields.lastTile).getLocalTranslation();
        ArrayList<Vector3f> tileLocList = new ArrayList<Vector3f>();
        for(Node node: tileList)
            tileLocList.add(node.getLocalTranslation());
        roadFields.tileLocList = tileLocList;
    }
    public RoadFields getRoadFields(){
        return roadFields;
    }

    public class RoadFields{
        int firstTile;
        int middleTile;
        int lastTile;
        int numOfTiles;
        public float tileDimensionsZ;
        public float tileDimensionsX;
        RoadLimits limits;
        ArrayList<Vector3f> tileLocList;
        public Vector3f firstTileLoc = new Vector3f();
        Vector3f middleTileLoc = new Vector3f();
        Vector3f lastTileLoc = new Vector3f();
        
        public RoadFields(){
            
        }
        
        
        public void setLimits(RoadLimits limits){
            this.limits = limits;
            this.numOfTiles = limits.min;
        }
        
        public void setTileDimensions(float dimensionsZ,float dimensionsX){
            this.tileDimensionsZ = dimensionsZ;
            this.tileDimensionsX = dimensionsX;
        }
        
       public void setFirstTileLoc(float x, float y, float z){
            this.firstTileLoc.x = x;
            this.firstTileLoc.y = y;
            this.firstTileLoc.z = z;
        }
       public void setMiddleTileLoc(float x, float y, float z){
            this.middleTileLoc.x = x;
            this.middleTileLoc.y = y;
            this.middleTileLoc.z = z;
        }
       public void setLastTileLoc(float x, float y, float z){
            this.lastTileLoc.x = x;
            this.lastTileLoc.y = y;
            this.lastTileLoc.z = z;
        }
       
       public ArrayList<Vector3f> getTileLocations(){
        return tileLocList;
        }
    }
    
    
}
