/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import guisystem.StatsManager;

/**
 *
 * @author Miguel Martrinez
 */
public enum Collectibles {
    POINTS("POINTS"),CHICKEN("CHICKEN");
    public String name;
    private Collectibles(String name){
        this.name = name;
    }
    public static Collectibles throughName(String name){
        if(name.equalsIgnoreCase(POINTS.name))
            return POINTS;
        return CHICKEN;
    }
    
    public int pollStats(){
        if(name.equals(POINTS.name))
            return StatsManager.getPoints(name);
        else if(name.equals(CHICKEN.name))
            return StatsManager.getChickenCount();
        else return 0;
    }
}
