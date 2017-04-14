/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Miguel Martrinez
 */
public class MissionScreen {
    ArrayList<Mission> missions = new ArrayList<Mission>();
    int totalMissions;
    String name;
    public MissionScreen(Mission...m){
        missions.addAll(Arrays.asList(m));
        totalMissions = missions.size();
    }
    
    public int run(){
        return runScreen();
    }
    
    private int runScreen(){
        int missionsLeft = 0;
        for(Mission mission: missions){
            if(!mission.isCompleted()){
                mission.run();
                missionsLeft++;
            }
        }
        return missionsLeft;
    }
    
    public int getMissionProgress(int index){
        if(index > missions.size()-1 ) 
            return 0;
        return missions.get(index).getMissionProgress();
           
    }
    
    public void loadMissionSavings(int first,int second, int third){
        
        missions.get(0).loadMissionProgress(first);
        if(missions.size() < 2)
            return;
        
        missions.get(1).loadMissionProgress(second);
        if(missions.size() < 3)
            return;
        
        missions.get(2).loadMissionProgress(third);
    }
}
