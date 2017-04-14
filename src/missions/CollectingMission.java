/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import de.lessvoid.nifty.effects.EffectEventId;
import guisystem.Gui;

/**
 *
 * @author Miguel Martrinez
 */
public class CollectingMission extends Mission{
Collectibles collectible;
    public CollectingMission(String name, MissionType type, int goal) {
        super(name, type, goal);
    }

    @Override
    public void run() {
        super.run(); 
        if(isCompleted)
            return;
        count = collectible.pollStats();
        if(count >= goal){
            isCompleted = true;
            Gui.nifty.getScreen("hud").findElementById("missionAccomplished").startEffect(EffectEventId.onShow);
        }
    }

    @Override
    public void setTargetObject(String target) {
        super.setTargetObject(target);
        collectible = Collectibles.throughName(target);
    }
    
}
