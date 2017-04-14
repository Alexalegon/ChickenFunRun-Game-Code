/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

/**
 *
 * @author Miguel Martrinez
 */
public enum ObstacleDescription {
    DUCKROCK("DUCKROCK"),NOPAL("NOPAL"),FIREPLACE("FIREPLACE"),QUICKSAND("QUICKSAND"),WAGON("WAGON"),JUMPROCK("JUMPROCK");
    
    public String name;
   
    private ObstacleDescription(String string){
        name = string;
    }
    
    public static ObstacleDescription throughName(String name){
        if(name.equalsIgnoreCase(DUCKROCK.name))
            return DUCKROCK;
        else if(name.equalsIgnoreCase(NOPAL.name))
            return NOPAL;
        else if(name.equalsIgnoreCase(FIREPLACE.name))
            return FIREPLACE;
        else if(name.equalsIgnoreCase(QUICKSAND.name))
            return QUICKSAND;
        else if(name.equalsIgnoreCase(WAGON.name))
            return WAGON;
        else if(name.equalsIgnoreCase(JUMPROCK.name))
            return JUMPROCK;
        else 
            return null;
    }
}
