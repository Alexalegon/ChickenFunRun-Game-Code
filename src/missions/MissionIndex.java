/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

/**
 *
 * @author Miguel Martrinez
 */
public enum MissionIndex {
    ONE(0,"mission1"),TWO(1,"mission2"),THREE(2,"mission3");
    
    public String name;
    public int number;
    private MissionIndex(int number, String name){
        this.number = number;
        this.name = name;
    }
}
