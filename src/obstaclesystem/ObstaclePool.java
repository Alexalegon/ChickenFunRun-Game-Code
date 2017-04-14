/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import java.util.ArrayList;

/**
 *
 * @author Miguel Martrinez
 */
public class ObstaclePool {
    int[] staticIndexes;
    ArrayList<ObstacleNode> staticTemplate = new ArrayList<ObstacleNode>();
    ArrayList<ObstacleNode> aiTemplate = new ArrayList<ObstacleNode>();

    ArrayList<ObstacleNode[]> staticObsList = new ArrayList<ObstacleNode[]>();
    ArrayList<ObstacleNode[]> aiObsList = new ArrayList<ObstacleNode[]>();
    public ObstaclePool() {
    }
    
    public void addToStaticTemplate(ObstacleNode obstacle){
        staticTemplate.add(obstacle);
    }
    
    public void addToAiTemplate(ObstacleNode obstacle){
        aiTemplate.add(obstacle);
    }
    
    public void addObstacleSettings(ObstacleSettings settings){
        staticTemplate = settings.getStaticObs();
        aiTemplate = settings.getAiObs();
    }
    
    public void initStaticPool(int staticSize){
        staticIndexes = new int[staticTemplate.size()];
        for(int i = 0; i < staticTemplate.size(); i++ ){
            staticIndexes[i] = 0;
            ObstacleNode  obstacle = staticTemplate.get(i);
            ObstacleNode[] array = new ObstacleNode[staticSize];
            for(int j = 0; j < staticSize; j++){
                array[j] = obstacle.clone();
            }
            staticObsList.add(array);
        }
    }
    
    public ObstacleNode releaseStatic(ObstacleSettings.ObstacleTypes type){
        int i = 0;
        for(ObstacleNode obstacle: staticTemplate){
            if(obstacle.type.equals(type)){
                for(int j = staticIndexes[i]; j < staticObsList.get(i).length; j++ ){
                    if(staticObsList.get(i)[staticIndexes[i]].enabled){
                staticObsList.get(i)[staticIndexes[i]].disable();
                ObstacleNode node = staticObsList.get(i)[j];
                staticIndexes[i]++;
                if(j == staticObsList.get(i).length -1)
                    staticIndexes[i] = 0;
                return node;
                    }
                }
                 
            }
            i++;
        }
        return null;
    }
    
     public ObstacleNode releaseStatic(int index){
        int i = 0;
        for(ObstacleNode obstacle: staticTemplate){
            if(i == index){
                for(int j = staticIndexes[i]; j < staticObsList.get(i).length; j++ ){
                    if(staticObsList.get(i)[staticIndexes[i]].enabled){
                staticObsList.get(i)[staticIndexes[i]].disable();
                ObstacleNode node = staticObsList.get(i)[j];
                staticIndexes[i]++;
                if(j == staticObsList.get(i).length -1)
                    staticIndexes[i] = 0;
                return node;
                    }
                }
                 
            }
            i++;
        }
        return null;
    }
     
    
    public void putBackStatic(ObstacleNode obstacle){
        obstacle.enable();
    }
    
    public int getSaticTemplateSize(){
        return staticTemplate.size();
    }
}
