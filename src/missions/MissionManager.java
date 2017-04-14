/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import java.util.ArrayList;
import obstaclesystem.ObstacleDescription;

/**
 *
 * @author Miguel Martrinez
 */
public class MissionManager {
   public static ArrayList<MissionScreen> missionScreens;
   public static int currentScreen;
   static int screenLimit = 0;
    static{
        initMissionScreen1();
        initMissionScreen2();
        initMissionScreen3();
        initMissionScreen4();
        //initMissionScreen5();
        screenLimit = missionScreens.size()-1;
    }
    
    public static void initMissionScreen1(){
        missionScreens = new ArrayList<MissionScreen>();
        Mission mission1 = new JumpingMission("QuicksandJMission1",MissionType.JUMPING,10);
        mission1.setTargetObject(ObstacleDescription.QUICKSAND.name);
        mission1.setMissionGuiDescription("Jump over 10 Quicksands");
        Mission mission2 = new JumpingMission("JumpRockMission2",MissionType.JUMPING,10);
        mission2.setTargetObject(ObstacleDescription.JUMPROCK.name);
        mission2.setMissionGuiDescription("Jump over 10 Rocks");
        Mission mission3 = new CollectingMission("ChickenMission1",MissionType.COLLECTING,5);
        mission3.setTargetObject(Collectibles.CHICKEN.name);
        mission3.setMissionGuiDescription("Catch 5 Chickens");
        MissionScreen screen = new MissionScreen(mission1,mission2,mission3);
        missionScreens.add(screen);
    }
    public static void initMissionScreen2(){
        Mission mission1 = new DuckingMission("DuckrockDMission1",MissionType.DUCKING,20);
        mission1.setTargetObject(ObstacleDescription.DUCKROCK.name);
        mission1.setMissionGuiDescription("Slide under 20 mini arches");
        Mission mission2 = new CollectingMission("PointsMission1",MissionType.COLLECTING,1000);
        mission2.setTargetObject(Collectibles.POINTS.name);
        mission2.setMissionGuiDescription("Collect 1,000 points");
        Mission mission3 = new DuckingMission("WagonMission1",MissionType.DUCKING,20);
        mission3.setTargetObject(ObstacleDescription.WAGON.name());
        mission3.setMissionGuiDescription("Slide under 20 Wagons");
        MissionScreen screen = new MissionScreen(mission1,mission2,mission3);
        missionScreens.add(screen);
    }
    public static void initMissionScreen3(){
        Mission mission1 = new JumpingMission("FireplaceMission1",MissionType.JUMPING,25);
        mission1.setTargetObject(ObstacleDescription.FIREPLACE.name);
        mission1.setMissionGuiDescription("Jump over 25 Fires");
        Mission mission2 = new JumpingMission("NopalMission1", MissionType.JUMPING,25);
        mission2.setTargetObject(ObstacleDescription.NOPAL.name);
        mission2.setMissionGuiDescription("Jump over 25 Cactus");
        Mission mission3 = new CollectingMission("CickenMission2",MissionType.COLLECTING,20);
        mission3.setTargetObject(Collectibles.CHICKEN.name);
        mission3.setMissionGuiDescription("Catch 20 Chickens");
        MissionScreen screen = new MissionScreen(mission1,mission2,mission3);
        missionScreens.add(screen);
    }
    public static void initMissionScreen4(){
        Mission mission1 = new CollectingMission("PointsMission2",MissionType.COLLECTING,15000);
        mission1.setTargetObject(Collectibles.POINTS.name);
        mission1.setMissionGuiDescription("Collect 15,000 points");
        Mission mission2 = new DuckingMission("DuckrockMission2",MissionType.DUCKING,50);
        mission2.setTargetObject(ObstacleDescription.DUCKROCK.name);
        mission2.setMissionGuiDescription("Slide under 50 arches");
        Mission mission3 = new CollectingMission("ChickenMission3",MissionType.COLLECTING,60);
        mission3.setTargetObject(Collectibles.CHICKEN.name);
        mission3.setMissionGuiDescription("Catch 60 chickens");
        MissionScreen screen = new MissionScreen(mission1,mission2,mission3);
        missionScreens.add(screen);
    }
    public static void initMissionScreen5(){
        
    }
    public static void run(){
        if(missionScreens.get(currentScreen).run() == 0){
            if(currentScreen < screenLimit)
                currentScreen++;
        }
    }
    
    public static String updateMissionScreen(MissionIndex missionIndex, String missionProgress){
        return missionScreens.get(currentScreen).missions.get(missionIndex.number).updateMissionGui(missionProgress);
    }
    public static boolean isMissionComplete(MissionIndex missionIndex){
        return missionScreens.get(currentScreen).missions.get(missionIndex.number).isCompleted();
    }
    
}
