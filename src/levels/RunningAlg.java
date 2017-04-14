/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package levels;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.CustomNode;
import pointsSystem.PointObjectSettings.Direction;


/**
 *
 * @author Miguel Martrinez
 */
public interface RunningAlg {
    public boolean passedCheckpoint(Vector3f characterLoc, Vector3f checkPoint);
    public int runTravelingRoad(Vector3f characterLoc, Vector3f checkPoint);
    public Vector3f[] getGroupLocations(float length, int divisor);
    public Vector3f calculateRoadChangingFlag(float offset, float dimensions, Vector3f firstTile);
    public float getLength();
    public float calculateDistanceTraveled(Vector3f characterLoc, Vector3f startingLoc, float offset);
    public void fillRoadTiles(BulletAppState bulletAppState,CollisionShape shape,Node node, Road road, Vector3f firstTile, int numberOfTiles, float dimensions);
    public void setRoadFields(Node nodes, Road road, int i, int numberOfTiles,float dimensions);
    public void showRoad(boolean initiate,Node node, BulletAppState bulletAppState, Road road, Vector3f location);
    public boolean withinProximityOfObstacle(Vector3f characterLoc, Vector3f obsLoc);
    public boolean withinProximityOfObstacle(Vector3f goal, Vector3f startingLoc, float proximity);
    public Vector3f changeTrackRight(Vector3f playerLoc);
    public Vector3f changeTrackLeft(Vector3f plyerLoc);
    public Direction getDirection();
    public Vector3f setLeftTrack(Vector3f middleLane);
    public Vector3f setRightTrack(Vector3f middleLane);
    public Vector3f changeTrack(Vector3f playerLoc, int index);
    public Vector3f changeObstacleTrack(Vector3f obstacleLoc, int trackIndex, int roadIndex);
    public void rotate(Node node);
    public void rotate(CustomNode node);
    public void setAccViewDirection(Vector3f viewDirection, float[] linearAcc);
    public int canTurnToChangeRoad(Vector3f characterLoc, Vector3f checkPoint);
    public void checkTrackBounds(Vector3f characterLoc);
    public void setPlayerLocTroughGravity(Vector3f playerLoc,float[] gravity, float[] gravityChange, float gravityBounds);
    public void setWall(Node node,Vector3f location, Direction nextDirection);
    public void clearAddition();
    public void runAi(Road road,float speed);
    public boolean withinRange(Vector3f start, Vector3f target, float range);
    public Vector3f getTracks(Vector3f tracks,Vector3f tile);
    public String getTurnDirection(Direction nextDirection);
    public void spawnAhead(float addition);
}
