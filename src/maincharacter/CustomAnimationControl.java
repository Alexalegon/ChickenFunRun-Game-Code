/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maincharacter;

import cameras.CamAnimation;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Miguel Martrinez
 */
public class CustomAnimationControl extends AbstractControl{
    public CamAnimation animation;
    public void setAnimation(CamAnimation animation){
        this.animation = animation;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(animation != null)
        animation.setEnabled(enabled);
    }

   

    @Override
    protected void controlUpdate(float tpf) {
        animation.play();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
}
