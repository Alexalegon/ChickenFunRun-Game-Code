/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import levels.PathConstructor;

/**
 *
 * @author Miguel Martrinez
 */
public abstract class Mission {
String name;
String target;
MissionType type;
int goal;
int count = 0;
boolean isCompleted = false;
String guiDescription;
    public Mission(String name,MissionType type,int goal) {
        this.name = name;
        this.type = type;
        this.goal = goal;
    }
    
    public void setMissionGuiDescription(String guiDescription){
        this.guiDescription = guiDescription;
    }
    
    public void setTargetObject(String target){
        this.target = target;
    }
    
    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
    
    public boolean isCompleted(){
        return isCompleted;
    }
    
    public void loadMissionProgress(int progress){
        count = progress;
        if(count >= goal)
            isCompleted = true;
    }
    
    public int getMissionProgress(){
        return count;
    }
    
    public String updateMissionGui(String missionProgress){
        if(missionProgress.equalsIgnoreCase("missionText"))
            return guiDescription;
        return String.valueOf(count)+ "/" + String.valueOf(goal);
    };
    
    public void run(){
        PathConstructor.getCurrentObstacleRoad().run();
    }
    
}
