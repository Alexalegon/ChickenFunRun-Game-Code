/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class ObstacleSystem {
    Node character;
    Node worldNode;
    ObstaclePool pool;
    public static ArrayList<ObstacleRoad> obstacleRoads = new ArrayList<ObstacleRoad>();
    public static int currentRoad = 0;
    public ObstacleSystem(Node character,Node worldNode,ObstaclePool pool,int roads) {
        this.character = character;
        this.worldNode = worldNode;
        this.pool = pool;
        for(int i = 0; i < roads; i++){
            ObstacleRoad obsRoad = new ObstacleRoad(character,worldNode,pool,i);
            obstacleRoads.add(obsRoad);
        }
    }
    
    public void setRoad(Direction direction,int index,ObstacleNode[] array, Vector3f[] tracks){
        obstacleRoads.get(index).setDirection(direction);
        obstacleRoads.get(index).setTracks(tracks);
        for(int i = 0; i < array.length; i++)
        obstacleRoads.get(index).addStaticObstacle(array[i]);
    }
    
    public void setCurrentRoad(int currentRoad){
        this.currentRoad = currentRoad;
    }
    
    public static ObstacleRoad getCurrentRoad(){
        return obstacleRoads.get(currentRoad);
    }
    
    public void clearObstacleRoads(int index){
        if(index == 0)
            obstacleRoads.get(obstacleRoads.size()-1).clearRoad();
        else
            obstacleRoads.get(index-1).clearRoad();
    }
    
    public void reset(){
        for(ObstacleRoad road: obstacleRoads)
            road.clearRoad();
        currentRoad =0;
    }
    
    public boolean run(){
        return obstacleRoads.get(currentRoad).run();
        
    }
    
    public void runTutorial(){
        obstacleRoads.get(currentRoad).runTutorial();
    }
    
    public ObstaclePool getPool(){
        return pool;
    }
    
    
}
