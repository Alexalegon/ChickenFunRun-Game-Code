/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import obstaclesystem.HitInfo;
import pointsSystem.PointObjectSettings;

/**
 *
 * @author Miguel Martrinez
 */
public class Stats {
float lowPointsConsumed = 0;
float midPointsConsumed = 0;
float highPointsConsumed = 0;
int lowPointsCount = 0;
int midPointsCount = 0;
int highPointsCount = 0;
int totalPointsPossible = 0;
int totalGroups = 0;
float score = 0;
float lowPointsConsumedPercentage = 0;
float midPointsConsumedPercentage = 0;
float highPointsConsumedPercentage = 0;
float totalPointsConsumedPercentage = 0;
float distanceTraveled = 0;
int distanceHighFlag = 0;//flag ensures that distanceHigh effect ocurrs only once per run
private int currentLife = 0;
private HitInfo hitInfo;
    public Stats() {
    }
    public void addToLowPointCount(int lowPoints){
        lowPointsCount+= lowPoints;
        updateConsumedPercentages();
    }
    public void addToMidPointCount(int midPoints){
        midPointsCount+= midPoints;
        updateConsumedPercentages();
    }
    public void addToHighPointCount(int highPoints){
        highPointsCount+= highPoints;
        updateConsumedPercentages();
    }
    public void addlowPointsConsumed(){
        lowPointsConsumed++;
        updateConsumedPercentages();
        
        StatsManager.update(PointObjectSettings.ObjectType.LOW);
    }
    public void addMidPointsConsumed(){
        midPointsConsumed++;
        updateConsumedPercentages();
        
    }
    public void addHighPointsConsumed(){
        highPointsConsumed++;
        updateConsumedPercentages();
        
    }
    
    public void clearStats(){
    lowPointsConsumed = 0;
    midPointsConsumed = 0;
    highPointsConsumed = 0;
    lowPointsCount = 0;
     midPointsCount = 0;
    highPointsCount = 0;
    totalPointsPossible = 0;
    totalGroups = 0;
    score = 0;
    lowPointsConsumedPercentage = 0;
    midPointsConsumedPercentage = 0;
    highPointsConsumedPercentage = 0;
    totalPointsConsumedPercentage = 0;
    distanceTraveled = 0;
    currentLife = 0;
    }
    public void setTotalPointsPossible(int totalPointsPossible){
        this.totalPointsPossible += totalPointsPossible;
    }
    public void setTotalGroups(){
        totalGroups++;
    }
    public void setScore(int points){
        score+=points;
    }
    
    public void updateConsumedPercentages(){
        if(lowPointsCount != 0)
        lowPointsConsumedPercentage = lowPointsConsumed/lowPointsCount;
        if(midPointsCount != 0)
        midPointsConsumedPercentage = midPointsConsumed/midPointsCount;
        if(highPointsCount != 0)
        highPointsConsumedPercentage = highPointsConsumed/highPointsCount;
        if(totalPointsPossible != 0)
        totalPointsConsumedPercentage = score/totalPointsPossible;
    }
    
    public void addDistanceTraveled(float distance){
        distanceTraveled += distance;
        if((int)distanceTraveled > StatsManager.distanceAllTimeHigh){
            StatsManager.setDistanceAllTimeHigh((int) distanceTraveled,distanceHighFlag);
            distanceHighFlag++;
        }
    }
    
    public void goToNextLife(){
        currentLife++;
    }
    
    public void setHitInfo(HitInfo hitInfo){
        this.hitInfo = hitInfo;
    }
    
    public HitInfo getHitInfo(){
        return hitInfo;
    }
    
    public int getCurrentLife(){
        return currentLife;
    }
    
   
    public void printStats(){
        System.out.println("Low Points percentage " + lowPointsConsumedPercentage);
        System.out.println("Mid Points percentage " + midPointsConsumedPercentage);
        System.out.println("High Points percentage " + highPointsConsumedPercentage);
        System.out.println("Total Points percentage " + totalPointsConsumedPercentage);
        System.out.println("Total groups  " + totalGroups);
        System.out.println("Distance Traveled " + distanceTraveled);
    }
}
