/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author Miguel Martrinez
 */
public interface ControllerSystem {
    public void walk();
    public void stopWalking();
    public void jump();
    public void duck();
    public void turnRight();
    public void turnLeft();
}
