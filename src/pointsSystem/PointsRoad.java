/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Miguel Martrinez
 */
public class PointsRoad {
   ArrayList<PointsGroup> groups = new ArrayList<PointsGroup>();
   HashMap<Integer,PointsGroup> pointsMap;
int size;
int[] array = new int[3];
    public PointsRoad() {
    }
    
    public void setSize(int size){
        this.size = size;
    }
   
    public void addGroupIndex(int index,int groupIndex){
        array[index] = groupIndex;
    }
    
    public int getSize(){
        return size;
    }
    
    public int[] getGroupIndexes(){
        return array;
    }
    
    public void addPointsMap(HashMap<Integer,PointsGroup> pointsMap){
        this.pointsMap = pointsMap;
    }
    
    public void addPointsGroup(PointsGroup group){
        groups.add(group);
    }
    
    public void run(){
        for(PointsGroup group: pointsMap.values())
            group.checkCollision();
    }
    
    public void runThroughGroups(){
        
    }
    
    public void attachGroups(int tile){
        //System.out.println("tile number     " + tile);
        if(pointsMap.get(tile) != null)
            pointsMap.get(tile).attachToRootNode();
    }
    
    public void detachGroups(int tile){
        if(pointsMap.get(tile) != null)
        pointsMap.get(tile).detach();
    }
    
    public void detachAllGroups(){
        for(PointsGroup group: pointsMap.values())
            group.clearGroup();
    }
    
    public void clearRoad(){
        if(pointsMap != null)
            if(!pointsMap.isEmpty())
                for(PointsGroup group: pointsMap.values())
                    group.clearGroup();
        groups.clear();
    }
    
    public void resumeRotations(){
         if(isRoadEmpty())
            return;
        for(PointsGroup group: pointsMap.values())
            group.resumeRotations();
    }
    
    public void pauseRotations(){
        if(isRoadEmpty())
            return;
        for(PointsGroup group: pointsMap.values())
            group.pauseRotations();
    }
    
    public boolean isRoadEmpty(){
        return pointsMap == null || pointsMap.isEmpty();
    }
}
