/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aicharacters;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import levels.PathConstructor;
import levels.Road;
import levels.RunningAlgsFacility;
import maincharacter.PlayerListener;
import mygame.PlayState;
import obstaclesystem.ObstacleNode;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public class AiManager {
    public static float speed = 50f;
    
    public static void runAi(Road road){
        if(road.getCurrentTile() < 8){
            //RunningAlgsFacility.getAlgByDirection(road.getDirection()).runAi(road,speed-=(PlayState.tpf/3));
            checkObstacleCollision(road.getDirection());
            checkHit();
        }
        else{
            ChickenListener.walkDistance = 0;
            speed  = 60f;
        }
    }
    
    public static void checkObstacleCollision(Direction direction){
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(ChickenListener.chicken.getLocalTranslation(),direction.vector);
        for(ObstacleNode obstacle : PathConstructor.getCurrentObstacleRoad().getstaticObstacles()){
            obstacle.getNode().collideWith(ray, results);
            for(int i = 0; i < results.size();i++){
                float distance = results.getCollision(i).getDistance();
                if(distance < 20f){
                    System.out.println("COlision!!!!!!!!     " + distance);
                    ChickenListener.simpleControl.jump();
                }
            }
        }
    }
    
    public static void checkHit(){
        CollisionResults results = new CollisionResults();
        ChickenListener.chicken.collideWith(PlayerListener.character.getWorldBound(), results);
        if(results.size() > 0)
            System.out.println("YESSSSSSSSSSSSSSSSSSSSSS");
    }
}
