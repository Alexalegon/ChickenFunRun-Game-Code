/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import com.jme3.math.FastMath;
import java.util.ArrayList;

/**
 *
 * @author Miguel Martrinez
 */
public class ObstacleSettings {
    ArrayList<ObstacleNode> staticObs = new ArrayList<ObstacleNode>();
    ArrayList<ObstacleNode> aiObs = new ArrayList<ObstacleNode>();
    public ObstacleSettings() {
    }
    
    public void addStaticObstacle(ObstacleNode obstacle){
        staticObs.add(obstacle);
    }
    
    public void addAiObstacle(ObstacleNode obstacle){
        aiObs.add(obstacle);
    }
    
    public ArrayList<ObstacleNode> getStaticObs(){
        return staticObs;
    }
    
    public ArrayList<ObstacleNode> getAiObs(){
        return aiObs;
    }
    
    public enum ObstacleTypes{
        Ducking,Jumping,Climbing;
    }
    
    public enum ObstaclePlacingConst{
        One(new int[]{1,3,5}),Two(new int[]{1,3,5,6}),Three(new int[]{2,4,5,7,8}),Four(new int[]{2,5,8}),
        Five(new int[]{1,3,4,6,7,8}),Six(new int[]{1,2,3,4,5,6,7,8}),Tutorial(new int[]{2,4});
        
        public int[] array;
        public int[] completeArray = new int[]{1,2,3,4,5,6,7,8};
        private ObstaclePlacingConst(int[] array){
            this.array = array;
            //this.array = completeArray;
        }
    }
    
    public enum ObstacleAndPointMatches{
        Zero(0),One(1),Two(2),All(3);
        
        public int matches;
        private ObstacleAndPointMatches(int matches){
            this.matches = matches;
        }
    }
    
    public enum ObstacleAlg{
        LowDifficulty(),MidDifficulty(),HighDifficulty();
       
        int[] array;
        private ObstacleAlg(){
           
       } 
        
        public ObstaclePlacingConst getTutorialLocations(){
            return ObstaclePlacingConst.Tutorial;
        }
        
        public ObstaclePlacingConst getLocations(){
         int seed = FastMath.rand.nextInt(6);
         if(seed == 0)
             return ObstaclePlacingConst.One;
         else if(seed == 1)
             return ObstaclePlacingConst.Two;
         else if(seed == 2)
             return ObstaclePlacingConst.Three;
         else if(seed == 3)
             return ObstaclePlacingConst.Four;
         else if(seed == 4)
             return ObstaclePlacingConst.Five;
         else 
             return ObstaclePlacingConst.Six;
        }
       
        public ObstacleAndPointMatches getObstacleAndPointMatches(){
            int seed = FastMath.rand.nextInt(4);
            if(seed == 0)
                return ObstacleAndPointMatches.Zero;
            else if(seed == 1)
                return ObstacleAndPointMatches.One;
            else if(seed == 2)
                return ObstacleAndPointMatches.Two;
            else 
                return ObstacleAndPointMatches.All;
        }
    }
}
