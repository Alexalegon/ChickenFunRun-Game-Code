/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import com.jme3.math.FastMath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import levels.RoadLimits.RoadSize;



/**
 *
 * @author Miguel Martrinez
 */
public class PointSpawningAlg {
private int roads;
private int totalGroups;
private PointSettings pointSettings;
private RoadSize roadSize;
    public PointSpawningAlg(PointSettings pointSettings) {
        this.pointSettings = pointSettings;
        
    }
    
    public PointSpawningAlg(int roads){
        this.roads = roads;
    }
    
    public void setRoadSize(RoadSize roadSize){
        this.roadSize = roadSize;
    }
    
    public void run(){
        if(roadSize.equals(RoadSize.SHORT)){
            
        }
    }
    
    public void setPointSettings(PointSettings pointSettings){
        this.pointSettings = pointSettings;
    }
    
    public PointSettings getPointSettings(){
        return pointSettings;
    }
    
    public void setSpecial(){
    }
   
    public enum PointSettings{
    LOW(.30f,.34f,.32f, PointObjectSettings.getLowPointGroups(),PointObjectSettings.getLowMidPointGroups(),PointObjectSettings.getLowHighPointGroups()),
    MEDIUM(0,0,0,PointObjectSettings.getMidPointGroups(),PointObjectSettings.getMidMidPointGroups(),PointObjectSettings.getMidHighPointGroups()),
    HIGH(0,0,0,PointObjectSettings.getHighPointGroups(),PointObjectSettings.getHighMidPointGroups(),PointObjectSettings.getHighHighPointGroups());
    
    float random;
    float percent;
    float low;
    float mid;
    float high;
    float harm;
    float special;
    ArrayList<PointsGroup> normalList;
    ArrayList<PointsGroup> midList;
    ArrayList<PointsGroup> highList;
   // ArrayList<Float> floatMap;
    HashMap<Integer,Float> floatMap = new HashMap<Integer,Float>();
    Set<Entry<Integer,Float>> entrySet;
    HashMap<Integer, String> stringMap;
    
    PointsGroupPool normalPool;
    PointsGroupPool midPool;
    PointsGroupPool highPool;
    
    
    private PointSettings(float low, float mid, float high, ArrayList<PointsGroup> normalList,
            ArrayList<PointsGroup> midList, ArrayList<PointsGroup> highList){
        this.low = low;
        this.mid = mid;
        this.high = high;
        
        this.normalList = normalList;
        this.midList = midList;
        this.highList = highList;
        
        normalPool = new PointsGroupPool(normalList,25);
        midPool = new PointsGroupPool(midList,25);
        highPool = new PointsGroupPool(highList,25);
        //floatMap = new ArrayList<Float>();
        sort();
    }
    private PointSettings(){
        
    }
    
    public void sort(){
        if(low > mid){
            if(low > high){
                floatMap.put(0,low);
                if(mid > high){
                    floatMap.put(1,mid);
                    floatMap.put(2,high);
                }
                else{
                    floatMap.put(1,high);
                    floatMap.put(2,mid);
                }
            }
            else{
                floatMap.put(0,high);
                floatMap.put(1,low);
                floatMap.put(2,mid);
            }
        }
        else{
            if(mid > high){
                floatMap.put(0,mid);
                if(high > low){
                    floatMap.put(1,high);
                    floatMap.put(2,low);
                }
                else{
                    floatMap.put(1,low);
                    floatMap.put(2,high);
                }
            }
            else{
                floatMap.put(0,high);
                floatMap.put(1,mid);
                floatMap.put(2,low);
            }
            
        }
        entrySet = floatMap.entrySet();
     
    }
    
    public void setPointsGroups(){
        
    }

    public void setHarm(float harm){
        
    }
    public PointsGroup generatePointsGroup(){
        PointsGroup group;
        random = FastMath.nextRandomFloat();
        if(random < floatMap.get(0)){
           group = randomGroup(0);
            
        }
        else if(random < floatMap.get(0) + floatMap.get(1)){
                group = randomGroup(1);
                }
                else{
                        group = randomGroup(2);
                        }
        return group;
    }
    public PointsGroup randomGroup( int index){
        PointsGroup group;
        int  random; 
        if(floatMap.get(index) == low){
           random = FastMath.nextRandomInt(1,3);
            group = normalPool.releaseGroup(normalList.get(random-1).getGroupType());
        }
        else if(floatMap.get(index) == mid){
            random = FastMath.nextRandomInt(1,2);
            group = midPool.releaseGroup(midList.get(random-1).getGroupType());
        }
        else{
            
            group = highPool.releaseGroup(highList.get(0).getGroupType());
        }
             
        return group;
    }
} 

}


