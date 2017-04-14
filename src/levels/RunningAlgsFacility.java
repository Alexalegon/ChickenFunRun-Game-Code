/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.math.Vector3f;
import obstaclesystem.ObstacleSystem;
import pointsSystem.PointObjectSettings.Direction;
import pointsSystem.PointSystem;

/**
 *
 * @author Miguel Martrinez
 */
public final class RunningAlgsFacility {
    public static PositiveZRunningAlg zPosAlg;
    public static PositiveXRunningAlg xPosAlg;
    public static NegativeZRunningAlg zNegAlg;
    public static NegativeXRunningAlg xNegAlg;
    
    public static final Vector3f UNIT_Z_NEG= Vector3f.UNIT_Z.mult(-1f);
    public static final Vector3f UNIT_X_NEG= Vector3f.UNIT_X.mult(-1f);
    public static final Vector3f UNIT_Y_NEG= Vector3f.UNIT_Y.mult(-1f);
   
    private RunningAlgsFacility(){
    }
    
    public static void initAlgs(PointSystem pointSystem,ObstacleSystem obstacleSystem,PrefabPool pool){
        zPosAlg = new PositiveZRunningAlg(pointSystem,obstacleSystem,pool);
        xPosAlg = new PositiveXRunningAlg(pointSystem,obstacleSystem,pool);
        zNegAlg = new NegativeZRunningAlg(pointSystem,obstacleSystem,pool);
        xNegAlg = new NegativeXRunningAlg(pointSystem,obstacleSystem,pool);
    }
    
    public static RunningAlg getAlgByDirection(Direction direction){
        if(direction.equals(Direction.ZPOSITIVE))
            return zPosAlg;
        else if(direction.equals(Direction.XPOSITIVE))
            return xPosAlg;
        else if(direction.equals(Direction.ZNEGATIVE))
            return zNegAlg;
        else
            return xNegAlg;
    }
}
