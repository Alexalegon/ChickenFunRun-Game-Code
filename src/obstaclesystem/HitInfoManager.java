/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obstaclesystem;

import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 *
 * @author Miguel Martrinez
 */
public final class HitInfoManager {
    public static ArrayList<HitInfo> hitInfoPool = new ArrayList<HitInfo>();
    public static int currentIndex;
    
    public static HitInfo releaseHitInfo(Vector3f location){
        HitInfo hitInfo = hitInfoPool.get(currentIndex);
        hitInfo.clear();
        hitInfo.setLocation(location);
        currentIndex++;
        if(currentIndex > 1)
            currentIndex = 0;
        return hitInfo;
    }
    
}
