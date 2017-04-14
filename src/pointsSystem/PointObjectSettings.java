/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import guisystem.Gui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Martrinez
 */
public class PointObjectSettings {
int numberOfObjects;
HashMap <ObjectType,PointsNode>objectMap;


public static PointsGroup lowSmallLow;
public static PointsGroup lowMidLow;
public static PointsGroup lowBigLow;
public static PointsGroup lowSmallMid;
public static PointsGroup lowMidMid;
public static PointsGroup lowBigHigh;

public static PointsGroup midSmallLow;
public static PointsGroup midMidLow;
public static PointsGroup midBigLow;
public static PointsGroup midSmallMid;
public static PointsGroup midMidMid;
public static PointsGroup midHighBig;

public static PointsGroup highSmallLow;
public static PointsGroup highMidLow;
public static PointsGroup highBigLow;
public static PointsGroup highSmallMid;
public static PointsGroup highMidMid;
public static PointsGroup highHighBig;

public static ArrayList<PointsGroup> lowLOWPointsGroups = new ArrayList<PointsGroup>();
public static ArrayList<PointsGroup> midLOWPointsGroups = new ArrayList<PointsGroup>();;
public static ArrayList<PointsGroup> highLOWPointsGroups = new ArrayList<PointsGroup>();;

public static ArrayList<PointsGroup> lowMIDPointsGroups = new ArrayList<PointsGroup>();;
public static ArrayList<PointsGroup> midMIDPointsGroups = new ArrayList<PointsGroup>();;
public static ArrayList<PointsGroup> highMIDPointsGroups = new ArrayList<PointsGroup>();;

public static ArrayList<PointsGroup> lowHIGHPointsGroups = new ArrayList<PointsGroup>();;
public static ArrayList<PointsGroup> midHIGHPointsGroups = new ArrayList<PointsGroup>();;
public static ArrayList<PointsGroup> highHIGHPointsGroups = new ArrayList<PointsGroup>();;


Node rootNode;
Node character;
public Gui gui;
 
public PointObjectSettings() {
       objectMap = new HashMap<ObjectType,PointsNode>();
       
    }

    public PointObjectSettings(int numberOfObjects) {
        this.numberOfObjects = numberOfObjects;
        objectMap = new HashMap<ObjectType,PointsNode>(numberOfObjects);
    }
    
    public void setNumberOfObjects(int numberOfObjects){
        this.numberOfObjects = numberOfObjects;
    }
    
    public void setGui(Gui gui){
        this.gui = gui;
    }
    
    public void addLowPointsObject(PointsNode node){
        objectMap.put(ObjectType.LOW, node);
    }
    
    public void addMidPointsObject(PointsNode node){
        objectMap.put(ObjectType.MIDPOINTS, node);
    }
    
    public void addHighPointsObject(PointsNode node){
        objectMap.put(ObjectType.HIGHPOINTS, node);
    }
    
    public void addHarmfulObject(PointsNode node){
        objectMap.put(ObjectType.HARMFUL, node);
    }
    
    public void addTurboObject(PointsNode node){
        objectMap.put(ObjectType.TURBO, node);
    }
    
    public void addHealthObject(PointsNode node){
        objectMap.put(ObjectType.HEALTH, node);
    }
    
    public PointsNode getLowPointsbject(){
        return objectMap.get(ObjectType.LOW);
    }
    
    public PointsNode getMidPointsObject(){
        return objectMap.get(ObjectType.MIDPOINTS);
    }
    
    public PointsNode getHighPointsObject(){
        return objectMap.get(ObjectType.HIGHPOINTS);
    }
    
    public PointsNode getHarmfulObject(){
        return objectMap.get(ObjectType.HARMFUL);
    }
    
    public PointsNode getTurboObject(){
        return objectMap.get(ObjectType.TURBO);
    }
    
    public PointsNode getHealthObject(){
        return objectMap.get(ObjectType.HEALTH);
    }
    
    public PointsNode getObject(String key){
        return objectMap.get(key);
    }
    
    public void clearObjects(){
        objectMap.clear();
    }
    
    public Map getObjectMap(){
        return objectMap;
    }
    
    public void setRootNode8Character(Node rootNode, Node character){
        this.rootNode = rootNode;
        this.character = character;
    }
    public void setRootNode4Group(PointsGroup group){
        group.setRootNode8Character(rootNode, character);
    }
    
    /* public void initAllLowPointsGroup() {
        
        singleLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SINGLE, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(singleLow);
        singleLow.setGui(gui);
        lowPointsGroups.add(singleLow);
        
        smallLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SMALL, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(smallLow);
        smallLow.setGui(gui);
        lowPointsGroups.add(smallLow);
       
        midLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(midLow);
        midLow.setGui(gui);
        lowPointsGroups.add(midLow);
        
        bigLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(bigLow);
        bigLow.setGui(gui);
        lowPointsGroups.add(bigLow);
       
        xlBigLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.XLBIG, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(xlBigLow);
        xlBigLow.setGui(gui);
        lowPointsGroups.add(xlBigLow);
    } 
       
    
     /*public void initAllMidGroups(int size){
        singleMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SINGLE, PointGroupSettings.GroupType.ALLMIDPOINTS);
        setRootNode4Group(singleMid);
        singleMid.setGui(gui);
        //midPointsGroups.add(singleMid);
        
        smallMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.MIXLOWMID);
        setRootNode4Group(smallMid);
        smallMid.setGui(gui);
        midPointsGroups.add(smallMid);
       
        midMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.MIXLOWMID);
        setRootNode4Group(midMid);
        midMid.setGui(gui);
        midPointsGroups.add(midMid);
        
        bigMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.ALLMIDPOINTS);
        setRootNode4Group(bigMid);
        bigMid.setGui(gui);
        //midPointsGroups.add(bigMid);
       
        xlBigMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.XLBIG, PointGroupSettings.GroupType.ALLMIDPOINTS);
        setRootNode4Group(xlBigMid);
        xlBigMid.setGui(gui);
        //midPointsGroups.add(xlBigMid);
    }*/
     
     /* public void initAllHighGroups(int size){
        singleHigh = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SINGLE, PointGroupSettings.GroupType.ALLHIGHPOINTS);
        setRootNode4Group(singleHigh);
        singleHigh.setGui(gui);
        //highPointsGroups.add(singleHigh);
        
        smallHigh = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SMALL, PointGroupSettings.GroupType.ALLHIGHPOINTS);
        setRootNode4Group(smallHigh);
        smallHigh.setGui(gui);
        //highPointsGroups.add(smallHigh);
       
        midHigh = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.MIXLOWMIDHIGH);
        setRootNode4Group(midHigh);
        midHigh.setGui(gui);
        highPointsGroups.add(midHigh);
        
        bigHigh = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.XLBIG, PointGroupSettings.GroupType.MIXLOWMIDHIGH);
        setRootNode4Group(bigHigh);
        bigHigh.setGui(gui);
        highPointsGroups.add(bigHigh);
       
        xlBigHigh = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.XLBIG, PointGroupSettings.GroupType.ALLHIGHPOINTS);
        setRootNode4Group(xlBigHigh);
        xlBigHigh.setGui(gui);
        //highPointsGroups.add(xlBigHigh);
    }*/
      
      //public void initAllHarmful8Special(){
        //  singleHarmful = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SINGLE, PointGroupSettings.GroupType.ALLHARMFUL);
        //setRootNode4Group(singleHarmful);
       // singleHarmful.setGui(gui);
        
        //smallHarmful = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SMALL, PointGroupSettings.GroupType.ALLHARMFUL);
        //setRootNode4Group(smallHarmful);
        //smallHarmful.setGui(gui);
        
      //}
      
      public void initLOWPointsSettingGroups(){
          lowSmallLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.SMALL, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(lowSmallLow);
        lowSmallLow.setGui(gui);
        lowLOWPointsGroups.add(lowSmallLow);
       
        lowMidLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(lowMidLow);
        lowMidLow.setGui(gui);
        lowLOWPointsGroups.add(lowMidLow);
        
        lowBigLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(lowBigLow);
        lowBigLow.setGui(gui);
        lowLOWPointsGroups.add(lowBigLow);
        
        lowSmallMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.MIXLOWMID);
        setRootNode4Group(lowSmallMid);
        lowSmallMid.setGui(gui);
        midLOWPointsGroups.add(lowSmallMid);
       
        lowMidMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.MIXLOWMID);
        setRootNode4Group(lowMidMid);
        lowMidMid.setGui(gui);
        midLOWPointsGroups.add(lowMidMid);
        
        lowBigHigh = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.XLBIG, PointGroupSettings.GroupType.MIXLOWMIDHIGH);
        setRootNode4Group(lowBigHigh);
        lowBigHigh.setGui(gui);
        highLOWPointsGroups.add(lowBigHigh);
      }
      
       public void initMIDPointsSettingGroups(){
          midSmallLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.MIXLOWMID);
        setRootNode4Group(midSmallLow);
        midSmallLow.setGui(gui);
        lowMIDPointsGroups.add(midSmallLow);
       
        midMidLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.ALLLOW);
        setRootNode4Group(midMidLow);
        midMidLow.setGui(gui);
        lowLOWPointsGroups.add(midMidLow);
        
        midBigLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.MIXLOWMID);
        setRootNode4Group(midBigLow);
        midBigLow.setGui(gui);
        lowLOWPointsGroups.add(midBigLow);
        
        midSmallMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.ALLMIDPOINTS);
        setRootNode4Group(midSmallMid);
        lowSmallMid.setGui(gui);
        midLOWPointsGroups.add(midSmallMid);
       
        midMidMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.ALLMIDPOINTS);
        setRootNode4Group(midMidMid);
        midMidMid.setGui(gui);
        midLOWPointsGroups.add(midMidMid);
        
        midHighBig = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.XLBIG, PointGroupSettings.GroupType.MIXLOWMIDHIGH);
        setRootNode4Group(midHighBig);
        midHighBig.setGui(gui);
        highLOWPointsGroups.add(midHighBig);
      }
      
        public void initHIGHPointsSettingGroups(){
          highSmallLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.MIXLOWMID);
        setRootNode4Group(highSmallLow);
        highSmallLow.setGui(gui);
        lowHIGHPointsGroups.add(highSmallLow);
       
        highMidLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.MIXLOWHIGH);
        setRootNode4Group(highMidLow);
        highMidLow.setGui(gui);
        lowHIGHPointsGroups.add(highMidLow);
        
        highBigLow = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.MIXLOWMIDHIGH);
        setRootNode4Group(highBigLow);
        highBigLow.setGui(gui);
        lowHIGHPointsGroups.add(highBigLow);
        
        highSmallMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.MIDSIZE, PointGroupSettings.GroupType.ALLHIGHPOINTS);
        setRootNode4Group(highSmallMid);
        highSmallMid.setGui(gui);
        midHIGHPointsGroups.add(highSmallMid);
       
        highMidMid = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.BIG, PointGroupSettings.GroupType.ALLMIDPOINTS);
        setRootNode4Group(highMidMid);
        highMidMid.setGui(gui);
        midHIGHPointsGroups.add(highMidMid);
        
        highHighBig = PointsGroupFactory.PointsGroupFactory(objectMap, PointGroupSettings.GroupSize.XLBIG, PointGroupSettings.GroupType.MIXMIDHIGH);
        setRootNode4Group(highHighBig);
        midHighBig.setGui(gui);
        highHIGHPointsGroups.add(highHighBig);
      }
       
      public static ArrayList<PointsGroup> getLowPointGroups(){
          return lowLOWPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getLowMidPointGroups(){
          return midLOWPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getLowHighPointGroups(){
          return highLOWPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getMidPointGroups(){
          return lowMIDPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getMidMidPointGroups(){
          return midMIDPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getMidHighPointGroups(){
          return highMIDPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getHighPointGroups(){
          return lowHIGHPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getHighMidPointGroups(){
          return midHIGHPointsGroups;
      }
      
      public static ArrayList<PointsGroup> getHighHighPointGroups(){
          return highHIGHPointsGroups;
      }
      
      public enum PointGroupShape{
          NORMAL,DIAGONAL,SPLINE
      }
   
      public enum ObjectType{
          LOW,MIDPOINTS,HIGHPOINTS,HARMFUL,TURBO,HEALTH,CHICKEN
          }
     
      public static  final class PointGroupSettings{
         
          public enum GroupSize{
              SINGLE(1), SMALL(3), MIDSIZE(5), BIG(9), XLBIG(15);
              final int size;
              
              private GroupSize(int size){
                  this.size = size;
              }
              
              public String set() throws Exception{throw new Exception();}
          }
          
          public enum GroupType{
              ALLLOW,ALLMIDPOINTS,ALLHIGHPOINTS,ALLHARMFUL,MIXLOWHARM,MIXMIDHARM,MIXHIGHHARM,
              MIXLOWMID,MIXLOWHIGH,MIXMIDHIGH,MIXLOWMIDHIGH,MIXLOWMIDHIGHHARM;
          }
         /* public static final String COMMON = "COMMON";
          public static final String MIDPOINTS = "MIDPOINTS";
          public static final String HIGHPOINTS = "HIGHPOINTS";
          public static final String HARMFUL = "HARMFUL";
          public static final String TURBO = "TURBO";
          public static final String HEALTH= "HEALTH";
          public static final int SINGLE = 1;
          //public static final int SMALL = 3;
          public static final int MID = 5;
          public static final int BIG = 7;
          */
      }

    public enum Direction{
        XPOSITIVE(Vector3f.UNIT_X),YPOSITIVE(Vector3f.UNIT_Y),ZPOSITIVE(Vector3f.UNIT_Z),
        XNEGATIVE(Vector3f.UNIT_X.mult(-1f)),YNEGATIVE(Vector3f.UNIT_Y.mult(-1f)),ZNEGATIVE(Vector3f.UNIT_Z.mult(-1f));
        
        public Vector3f vector;
        private Direction(Vector3f vector){
            this.vector = vector;
        }
    }
      
}
