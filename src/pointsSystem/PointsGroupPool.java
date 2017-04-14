/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointsSystem;

import java.util.ArrayList;
import java.util.HashMap;
import mygame.Pool;
import pointsSystem.PointObjectSettings.PointGroupSettings.GroupType;
import pointsSystem.PointSpawningAlg.PointSettings;

/**
 *
 * @author Miguel Martrinez
 */
public class PointsGroupPool {
    
            ArrayList<PointsGroup[]> groupsArray = new ArrayList<PointsGroup[]>();
            ArrayList<PointsGroup> list;
            int[] indexes;
            
            public PointsGroupPool(ArrayList<PointsGroup> list, int size ) {
                this.list = list;
                indexes = new int[list.size()];
                for(int i = 0; i < list.size(); i++){
                    indexes[i] = 0;
                    PointsGroup[] array = new PointsGroup[size];
                    for(int j = 0; j < size; j++){
                        PointsGroup group = list.get(i).clone();
                        array[j] = group;
                        groupsArray.add(array);
                            }
                }
            }
            
            public PointsGroup releaseGroup(GroupType type){
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).type.equals(type)){ 
                        PointsGroup group = groupsArray.get(i)[indexes[i]];
                        group.resumeRotations();
                        indexes[i]++;
                        if(indexes[i] >= groupsArray.get(i).length)
                            indexes[i] = 0;
                        return group;
                    }
                }
               
                return null;
            }
        
    
}
