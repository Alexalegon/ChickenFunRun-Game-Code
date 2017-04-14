/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.math.FastMath;
import pointsSystem.PointObjectSettings.Direction;

/**
 *
 * @author Miguel Martrinez
 */
public final class DirectionManager {
    static Direction direction;
    static boolean isDirectionZ;
    
    public static void setInitDirection(Direction direction){
        DirectionManager.direction = direction;
        if(direction.equals(Direction.ZNEGATIVE)|| direction.equals(Direction.ZPOSITIVE))
            isDirectionZ = true;
        else
            isDirectionZ = false;
    }
    public static void goToNextDirection(){
          float f = FastMath.nextRandomFloat();
        if(isDirectionZ){
            if(f >= .5f)
        direction = Direction.XPOSITIVE;
            else
                direction = Direction.XNEGATIVE;
        }
        else{
            if(f >= .5f)
            direction = Direction.ZNEGATIVE;
            else
                direction = Direction.ZPOSITIVE;
        }
        isDirectionZ = !isDirectionZ;
    }
}
