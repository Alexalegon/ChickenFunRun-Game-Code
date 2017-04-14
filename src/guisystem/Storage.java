/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

import java.util.HashMap;
import missions.MissionManager;

/**
 *
 * @author Miguel Martrinez
 */
public abstract class Storage {
    
  public static final String SCORE = "score"; 
  public static final String CHICKENCOUNT = "chickencount";
  public static final String SINGLERUNHIGH = "singlerunhigh";
  public static final String MISSIONSCREEN = "missionscreen";
  public static final String FIRSTMISSION = "firstmission";
  public static final String SECONDMISSION = "secondmission";
  public static final String THIRDMISSION = "thirdmission";
  public static final String MISSIONSCOMPLETED = "missionscompleted";
  public int points = 0;
  public int chickenCount = 0;
  public static int distanceHigh = 3450;
  public int screen = 0;
  public int firstMissionFlag = 0;
  public int secondMissionFlag = 0;
  public int thirdMissionFlag = 0;
  public int missionsCompleted = 0;
  public HashMap<String,Integer> map;
  
  public void initMap(){
      map = new HashMap<String,Integer>();
      map.put(SCORE, points);
      map.put(MISSIONSCREEN, screen);
      map.put(FIRSTMISSION, firstMissionFlag);
      map.put(SECONDMISSION, secondMissionFlag);
      map.put(THIRDMISSION, thirdMissionFlag);
      map.put(MISSIONSCOMPLETED, missionsCompleted);
  }
  public void mapValue(String string, int value){
    if(string.equals(FIRSTMISSION))
        firstMissionFlag = value;
    else if(string.equals(SECONDMISSION))
        secondMissionFlag = value;
    else if(string.equals(THIRDMISSION))
        thirdMissionFlag = value;
    else if(string.equals(MISSIONSCREEN))
        screen = value;
    else if(string.equals(MISSIONSCOMPLETED))
        missionsCompleted = value;
    else if(string.equals(CHICKENCOUNT))
        chickenCount = value;
   
    }
  
  public void saveMissions(){
       
      screen = MissionManager.currentScreen;
      firstMissionFlag = MissionManager.missionScreens.get(MissionManager.currentScreen).getMissionProgress(0);
      secondMissionFlag = MissionManager.missionScreens.get(MissionManager.currentScreen).getMissionProgress(1);
      thirdMissionFlag = MissionManager.missionScreens.get(MissionManager.currentScreen).getMissionProgress(2);
      missionsCompleted = screen * 3 + firstMissionFlag + secondMissionFlag + thirdMissionFlag;
  }
  
  public int getMissionValue(String string){
      if(string.equals(MISSIONSCOMPLETED))
          return missionsCompleted;
      else if(string.equals(MISSIONSCREEN))
          return screen;
      else if(string.equals(FIRSTMISSION))
          return firstMissionFlag;
      else if(string.equals(SECONDMISSION))
          return secondMissionFlag;
      else if(string.equals(THIRDMISSION)) 
          return thirdMissionFlag;
      else throw new  AssertionError("Mission field does not exist");
  }
  
  public void loadPoints(String file, int value){
      if(file.equals(SCORE))
          points = value;
      else if(file.equals(CHICKENCOUNT))
          chickenCount = value;
      else if(file.equals(SINGLERUNHIGH))
          distanceHigh = value;
          
      
  }
  
  public void loadMissions(){
      MissionManager.currentScreen = screen;
      MissionManager.missionScreens.get(screen).loadMissionSavings(firstMissionFlag,secondMissionFlag,thirdMissionFlag);
  }
  public void load(){}
  public void save(){}
}
