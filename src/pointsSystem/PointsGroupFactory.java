/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import pointsSystem.PointsGroup;
import pointsSystem.PointObjectBag;
import com.jme3.scene.Node;
import java.util.HashMap;
import pointsSystem.PointObjectSettings.ObjectType;
import pointsSystem.PointObjectSettings.PointGroupSettings;
import pointsSystem.PointObjectSettings.PointGroupSettings.GroupSize;
import pointsSystem.PointObjectSettings.PointGroupSettings.GroupType;

/**
 *
 * @author Miguel Martrinez
 */
public final class PointsGroupFactory {
     private static PointGroupSettings.GroupSize size;
     private static PointGroupSettings.GroupType type;
      static HashMap<ObjectType,PointsNode> inputMap;
     private static HashMap<ObjectType,PointsNode> outputMap;
    static PointObjectBag pointObjectBag;

    private PointsGroupFactory() {
        
    }
    
    public static final PointsGroup PointsGroupFactory(HashMap<ObjectType,PointsNode> inputMap,GroupSize size, GroupType type) {
        PointsGroupFactory.size = size;
        PointsGroupFactory.type = type;
        pointObjectBag = new PointObjectBag();
        PointsGroupFactory.inputMap = inputMap;
        outputMap = new HashMap<ObjectType,PointsNode>();
        if(size.equals(GroupSize.SINGLE)){
            if(!type.equals(GroupType.ALLHARMFUL) || !type.equals(GroupType.ALLLOW) || !type.equals(GroupType.ALLMIDPOINTS)
                    || !type.equals(GroupType.ALLHIGHPOINTS))
                throw new RuntimeException("Single GroupSize, so GroupType can't be mixed");
            else
                outputMap = new HashMap<ObjectType,PointsNode>(size.size,1.0f);
        }
        else if(size.equals(GroupSize.SMALL)){
            if(type.equals(GroupType.MIXLOWMIDHIGHHARM))
                throw new RuntimeException("Small GroupSize, so GroupType must be of 3 compomemts or less");
            else
            outputMap = new HashMap<ObjectType,PointsNode>(size.size,1.0f);
        }
        else if(size.equals(GroupSize.MIDSIZE))
            outputMap = new HashMap<ObjectType,PointsNode>(size.size,1.0f);
        else if(size.equals(GroupSize.BIG))
            outputMap = new HashMap<ObjectType,PointsNode>(size.size,1.0f);
        else 
            outputMap = new HashMap<ObjectType,PointsNode>(size.size,1.0f);
        return setUpGroup();
    }
    
    private static final PointsGroup setUpGroup(){
        if(type.equals(GroupType.ALLLOW)){  
            outputMap.put(ObjectType.LOW,inputMap.get(ObjectType.LOW));
             for(int i = 0; i < size.size; i++)
              pointObjectBag.addPointObject(inputMap.get(ObjectType.LOW));
        }
           
        if(type.equals(GroupType.ALLMIDPOINTS)){
            outputMap.put(ObjectType.MIDPOINTS,inputMap.get(ObjectType.MIDPOINTS));
             for(int i = 0; i < size.size; i++)
                pointObjectBag.addPointObject(inputMap.get(ObjectType.MIDPOINTS));
        }
        if(type.equals(GroupType.ALLHIGHPOINTS)){
            outputMap.put(ObjectType.HIGHPOINTS,inputMap.get(ObjectType.HIGHPOINTS));
             for(int i = 0; i < size.size; i++)
                pointObjectBag.addPointObject(inputMap.get(ObjectType.HIGHPOINTS));
        }
        if(type.equals(GroupType.ALLHARMFUL)){
            outputMap.put(ObjectType.HARMFUL,inputMap.get(ObjectType.HARMFUL));
             for(int i = 0; i < size.size; i++)
                pointObjectBag.addPointObject(inputMap.get(ObjectType.HARMFUL));
        }
        if(type.equals(GroupType.MIXLOWHARM)){
            outputMap.put(ObjectType.LOW,inputMap.get(ObjectType.LOW));
            outputMap.put(ObjectType.HARMFUL,inputMap.get(ObjectType.HARMFUL));
             for(int i = 0; i < size.size; i++){
                 if(i == size.size/2)
                  pointObjectBag.addPointObject(inputMap.get(ObjectType.HARMFUL)); 
                 else
                     pointObjectBag.addPointObject(inputMap.get(ObjectType.LOW));
             }    
        }
        if(type.equals(GroupType.MIXLOWMID)){
            outputMap.put(ObjectType.LOW,inputMap.get(ObjectType.LOW));
            outputMap.put(ObjectType.MIDPOINTS,inputMap.get(ObjectType.MIDPOINTS));
            for(int i = 0; i < size.size; i++){
                
                if(i % 2 == 0)
                 pointObjectBag.addPointObject(inputMap.get(ObjectType.LOW));
                 else
                    pointObjectBag.addPointObject(inputMap.get(ObjectType.MIDPOINTS));
            }
        }
        if(type.equals(GroupType.MIXLOWHIGH)){
            outputMap.put(ObjectType.LOW,inputMap.get(ObjectType.LOW));
            outputMap.put(ObjectType.HIGHPOINTS,inputMap.get(ObjectType.HIGHPOINTS));
              for(int i = 0; i < size.size; i++){
                if(i % 2 == 0)
                  pointObjectBag.addPointObject(inputMap.get(ObjectType.LOW)); 
                 else
                     pointObjectBag.addPointObject(inputMap.get(ObjectType.HIGHPOINTS));
            }
        }
        if(type.equals(GroupType.MIXMIDHIGH)){
            outputMap.put(ObjectType.MIDPOINTS,inputMap.get(ObjectType.MIDPOINTS));
            outputMap.put(ObjectType.HIGHPOINTS,inputMap.get(ObjectType.HIGHPOINTS));
             for(int i = 0; i < size.size; i++){
                if(i % 2 == 0)
                  pointObjectBag.addPointObject(inputMap.get(ObjectType.HIGHPOINTS)); 
                 else
                     pointObjectBag.addPointObject(inputMap.get(ObjectType.HIGHPOINTS));
            }
        }
        if(type.equals(GroupType.MIXMIDHARM)){
            outputMap.put(ObjectType.MIDPOINTS,inputMap.get(ObjectType.MIDPOINTS));
            outputMap.put(ObjectType.HARMFUL,inputMap.get(ObjectType.HARMFUL));
             for(int i = 0; i < size.size; i++){
                 if(i == size.size/2)
                  pointObjectBag.addPointObject(inputMap.get(ObjectType.MIDPOINTS)); 
                 else
                    pointObjectBag.addPointObject(inputMap.get(ObjectType.HARMFUL));
             } 
        }
        if(type.equals(GroupType.MIXHIGHHARM)){
            outputMap.put(ObjectType.HIGHPOINTS,inputMap.get(ObjectType.HIGHPOINTS));
            outputMap.put(ObjectType.HARMFUL,inputMap.get(ObjectType.HARMFUL));
             for(int i = 0; i < size.size; i++){
                 if(i == size.size/2)
                  pointObjectBag.addPointObject(inputMap.get(ObjectType.HARMFUL)); 
                 else
                     pointObjectBag.addPointObject(inputMap.get(ObjectType.HIGHPOINTS));
             } 
        }
        if(type.equals(GroupType.MIXLOWMIDHIGH)){
            outputMap.put(ObjectType.LOW,inputMap.get(ObjectType.LOW));
            outputMap.put(ObjectType.MIDPOINTS,inputMap.get(ObjectType.MIDPOINTS));
            outputMap.put(ObjectType.HIGHPOINTS,inputMap.get(ObjectType.HIGHPOINTS));
             for(int i = 1; i <= size.size; i++){
                if(i % 2 == 0)
                  pointObjectBag.addPointObject(inputMap.get(ObjectType.LOW)); 
                else if(i % 3 == 0)
                     pointObjectBag.addPointObject(inputMap.get(ObjectType.MIDPOINTS));
                else
                    pointObjectBag.addPointObject(inputMap.get(ObjectType.HIGHPOINTS));
            }
        }
        if(type.equals(GroupType.MIXLOWMIDHIGHHARM)){
            outputMap.put(ObjectType.LOW,inputMap.get(ObjectType.LOW));
            outputMap.put(ObjectType.MIDPOINTS,inputMap.get(ObjectType.MIDPOINTS));
            outputMap.put(ObjectType.HIGHPOINTS,inputMap.get(ObjectType.HIGHPOINTS));
            outputMap.put(ObjectType.HARMFUL,inputMap.get(ObjectType.HARMFUL));
             for(int i = 1; i <= size.size; i++){
                if(i % 4 == 0)
                  pointObjectBag.addPointObject(inputMap.get(ObjectType.LOW)); 
                else if(i % 3 == 0)
                    pointObjectBag.addPointObject(inputMap.get(ObjectType.MIDPOINTS));
                else if (i % 2 == 0)
                    pointObjectBag.addPointObject(inputMap.get(ObjectType.HIGHPOINTS));
                else
                    pointObjectBag.addPointObject(inputMap.get(ObjectType.HARMFUL));
            }
        }
        return new PointsGroup(outputMap, pointObjectBag,size, type);
    }
    
}
