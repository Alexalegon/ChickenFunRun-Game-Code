/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import de.lessvoid.nifty.effects.EffectEventId;
import missions.Collectibles;
import mygame.Main;
import pointsSystem.PointObjectSettings;

/**
 *
 * @author Miguel Martrinez
 */
public class StatsManager {
static Storage storage;
static int lowPointsConsumed = 0;
static int midPointsConsumed = 0;
static int highPointsConsumed = 0;
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
static int chickenCount = 0;
static int distanceAllTimeHigh = 0;
static Stats currentRunStats;

    public static void initNewRun(){
        currentRunStats = new Stats();
    }
    
    public static Stats getCurrentRunStats(){
    return currentRunStats;
    }
    //Called individually every time object intersected
    public static void update(PointObjectSettings.ObjectType pointType){
        if(pointType.equals(PointObjectSettings.ObjectType.LOW)){
            lowPointsConsumed++;
            Hud.showBronzePoints();
        }
        else if(pointType.equals(PointObjectSettings.ObjectType.CHICKEN)){
            chickenCount++;
            Hud.showChickenCount();
        }
    }
    //called by levelmanager.update
    public static void update(){
        Hud.showDistance();
    }
    //Guiflag ensures ditance high notice happens only once per run
    public static void setDistanceAllTimeHigh(int record,int guiFlag){
        distanceAllTimeHigh = record;
        if(guiFlag==0)
            Gui.nifty.getScreen("hud").findElementById("distanceHigh").startEffect(EffectEventId.onShow);
    }
    
    public static int getPoints(String file){
        if(file.equals(Storage.SCORE) || file.equals(Collectibles.POINTS.name))
        return lowPointsConsumed;
        else if(file.equals(Storage.CHICKENCOUNT))
            return chickenCount;
        else if(file.equals(Storage.SINGLERUNHIGH)){
            return distanceAllTimeHigh;
        }
        return 0;
    }
    
    public static int getChickenCount(){
        return chickenCount;
    }
    
    public static void chickenUsed(int used){
        chickenCount-=used;
        Hud.showChickenCount();
    }
    public static void chickenBought(int bought){
        chickenCount+=bought;
        Hud.showChickenCount();
    }
    public static void pointsUsed(int points){
        lowPointsConsumed-=points;
        Hud.showBronzePoints();
    }
    
    public static void save(){
        
    }
    
    public static void load(){
        storage = Main.communication.loadSavedSettings();
        lowPointsConsumed = storage.points;
        Hud.showBronzePoints();
        chickenCount = storage.chickenCount;
        Hud.showChickenCount();
        distanceAllTimeHigh = Storage.distanceHigh;
        
    }
}
