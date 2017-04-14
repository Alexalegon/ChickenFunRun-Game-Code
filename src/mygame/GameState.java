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
public enum GameState {
    HOME(),INTRO(),RUN(), PAUSE(),GAMEOVER(),RESUMEPLAY,TUTORIAL,RESUMETUTORIAL(),TUTORIALINTRO();
    
    boolean intro;
    boolean resumeLife = false;
    
    public void setIntro(boolean intro){
        this.intro = intro;
    }
    
    public boolean getIntro(){
        return intro;
    }
    
    public void setResumeLife(boolean resume){
        resumeLife = resume;
    }
    
    public boolean getResumeLife(){
        return resumeLife;
    }
}
