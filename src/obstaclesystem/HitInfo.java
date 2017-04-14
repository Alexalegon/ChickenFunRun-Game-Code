/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import com.jme3.math.Vector3f;

/**
 *
 * @author Miguel Martrinez
 */
public class HitInfo {
    private Vector3f location;
    private HitType hitType;
    public HitInfo(){
        
    }
    
    public void setHitType(HitType hitType){
        this.hitType = hitType;
    }
    public void setLocation(Vector3f location){
        this.location = location;
    }
    public HitType getHitType(){
        return hitType;
    }
    
    public void clear(){
        this.location = null;
    }
    
    public enum HitType{
        OBSTACLE,HAY;
    }
}
