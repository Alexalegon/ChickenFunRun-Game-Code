/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import java.util.ArrayList;
import guisystem.Stats;
import guisystem.StatsManager;
import java.util.HashMap;
import mygame.GameState;
import mygame.GameStateManager;

/**
 *
 * @author Miguel Martrinez
 */
public class PointSystem {
    PointObjectSettings pointObjectSettings;
    ArrayList<PointsGroup>groupList;
    PointSpawningAlg spawningAlg;
   int totalPointsPossible = 0;
   int lowPointCount = 0;
   int midPointCount = 0;
   int highPointCount = 0;
   int lowPointsConsumed = 0;
   int midPointsConsumed = 0;
   int highPointsConsumed = 0;
    int[] roads = new int[6];
    PointsRoad[] pointRoads;
    int firstLiveGroup = 0;
    PointsSystemCallback callback;
    int currentRoad = 0;
    public PointSystem() {
        groupList = new ArrayList<PointsGroup>();
        pointRoads = new PointsRoad[4];
        for(int i = 0; i < 4; i++){
            pointRoads[i] = new PointsRoad();
        }
        initCallback();
    }
    
    
    public PointSystem(int roadMax){
        
        groupList = new ArrayList<PointsGroup>();
    }
    
    public PointSystem(PointObjectSettings pointObjSett){
        this.pointObjectSettings = pointObjSett;
        groupList = new ArrayList<PointsGroup>();
        pointRoads = new PointsRoad[4];
        for(int i = 0; i < 4; i++){
            pointRoads[i] = new PointsRoad();
        }
        initCallback();
    }
    
    public void initCallback(){
        callback = new PointsSystemCallback() {

            @Override
            public void addToPointObjectsCount(int pointValue) {
                if(pointValue == 1)
                StatsManager.getCurrentRunStats().addlowPointsConsumed();
                else if(pointValue == 2)
                    StatsManager.getCurrentRunStats().addMidPointsConsumed();
                else
                    StatsManager.getCurrentRunStats().addHighPointsConsumed();
                StatsManager.getCurrentRunStats().setScore(pointValue);
            }
        };
    }
    
    public void setCurrentRoad(int index){
        currentRoad = index;
    }
    
    public void setRoad(int roadIndex, int roadSize){
        int groupIndex = groupList.size();
      
        pointRoads[roadIndex].setSize(roadSize);
        for(int i = 0; i < roadSize; i++){
            pointRoads[roadIndex].addGroupIndex(i, groupIndex++);
        }
        
    }
    
    public void setRoad(int index, HashMap<Integer,PointsGroup> pointsMap){
        pointRoads[index].addPointsMap(pointsMap);
    }
    
    public void clearPointsRoads(int roadIndex){
        if(roadIndex == 0){
            for(int i = pointRoads.length-2; i < pointRoads.length;i++){
                 int[] indexes = pointRoads[i].getGroupIndexes();
            for(int j= 0; j < pointRoads[i].getSize(); j++)
                groupList.get(indexes[j]).clearGroup();
            }
            
        }
        else{
        for(int i = roadIndex-1; i < roadIndex; i++){
            int[] indexes = pointRoads[i].getGroupIndexes();
            for(int j= 0; j < pointRoads[i].getSize(); j++){
                System.out.println("indexes " + j+"    "+ indexes[j]);
                System.out.println(groupList.size());
                groupList.get(indexes[j]).clearGroup();
                firstLiveGroup = indexes[j];
            }
        }
        }
    }
    
    public void clearRoad(int index){
        if(index == 0)
        pointRoads[pointRoads.length-1].clearRoad();
        else
            pointRoads[index-1].clearRoad();
    }
    
    public void setPointObjectSettings(PointObjectSettings pointObjSett){
        this.pointObjectSettings = pointObjSett;
    }
    
    public void setPointSpawningAlg(PointSpawningAlg alg){
        spawningAlg = alg;
    }
    
   
    
    public PointSpawningAlg getPointSpawningAlg(){
        return spawningAlg;
    }
    
    public void setRoadMax(int roadMax){
       
    }
    
    
    
    public PointObjectSettings getPointObjectSettings(){
        return pointObjectSettings;
    }
    
    public ArrayList<PointsGroup> getGroupList(){
        return groupList;
    }
    
    public void addGroup(PointsGroup group){
        group.setPointsSystemCallback(callback);
        //groupList.add(group);
        StatsManager.getCurrentRunStats().setTotalPointsPossible(group.getTotalPoints()); 
        StatsManager.getCurrentRunStats().addToLowPointCount(group.getLowPointCount());
        StatsManager.getCurrentRunStats().addToMidPointCount(group.getMidPointCount());
        StatsManager.getCurrentRunStats().addToHighPointCount(group.getHighPointCount());
        StatsManager.getCurrentRunStats().setTotalGroups();
    }
    
    public void clearGroupsList(){
        groupList.clear();
    }
    
    public void run(){
      // for(int i = firstLiveGroup; i < groupList.size(); i++){
       //    groupList.get(i).checkCollision();
      // } 
        pointRoads[currentRoad].run();
}
    
    public void runThroughRoad(){
        pointRoads[currentRoad].runThroughGroups();
    }
    
    public void attachGroups(int tile){
        GameState state =  GameStateManager.getCurrentState();
        /*if(state.equals(GameState.TUTORIAL))
            throw new AssertionError("Gamestate == tutorial");
        else if(state.equals(GameState.INTRO))
            throw new AssertionError("GameState == intro");
        else if(state.equals(GameState.HOME))
            throw new AssertionError("GameState == home");
        else if(state.equals(GameState.GAMEOVER))
            throw new AssertionError("GameState == gameover");
        else if(state.equals(GameState.PAUSE))
            throw new AssertionError("GameState == pause");
        else if(state.equals(GameState.RUN))
            throw new AssertionError("GameState == run");*/
        if(GameStateManager.getCurrentState().equals(GameState.TUTORIAL)
                || GameStateManager.getCurrentState().equals(GameState.RESUMETUTORIAL)
                || GameStateManager.getCurrentState().equals(GameState.TUTORIALINTRO))
            return;
        
            pointRoads[currentRoad].attachGroups(tile);
    }
    
    public void attachGroupsNextRoad(int tile){
        if(GameStateManager.getCurrentState().equals(GameState.TUTORIAL)
                || GameStateManager.getCurrentState().equals(GameState.RESUMETUTORIAL))
            return;
        if(currentRoad == pointRoads.length-1)
            pointRoads[0].attachGroups(tile);
        else
            pointRoads[currentRoad +1].attachGroups(tile);
    }
    
    public void detachGroups(int tile){
        if(!GameStateManager.currentGameState.equals(GameState.TUTORIAL)
               && !GameStateManager.currentGameState.equals(GameState.RESUMETUTORIAL))
       
            pointRoads[currentRoad].detachGroups(tile);
        
    }
    
    public void detachLastRoad(){
        if(pointRoads[pointRoads.length-1].pointsMap != null)
        pointRoads[pointRoads.length-1].detachAllGroups();
    }
    
    public void detachRoad(){
        if(pointRoads[currentRoad-1].pointsMap != null)
        pointRoads[currentRoad-1].detachAllGroups();
    }
    
    public void reset(){
        for(PointsRoad road: pointRoads)
            road.clearRoad();
        currentRoad=0;
    }
    
    public PointsRoad getCurrentRoad(){
        
       return pointRoads[currentRoad];
    }
    
    public int getTotalPointsPossible(){
        return totalPointsPossible;
    }
    public int getLowPointCount(){
        return lowPointCount;
    }
    public int getMidPointCount(){
        return midPointCount;
    }
    public int getHighPointCount(){
        return highPointCount;
    }
    public int getLowPointsConsumed(){
        return lowPointsConsumed;
    }
    public int getMidPointsConsumed(){
        return midPointsConsumed;
    }
    public int getHighPointsConsumde(){
        return highPointsConsumed;
    }
}
