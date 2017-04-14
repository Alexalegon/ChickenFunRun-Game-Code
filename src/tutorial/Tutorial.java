/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import guisystem.NiftyCommunicator;
import maincharacter.PlayerListener;
import mygame.Main;
import mygame.PlayState;

/**
 *
 * @author Miguel Martrinez
 */
public class Tutorial implements ScreenController,NiftyCommunicator{
static Nifty nifty;
static Element instruction;
static Element instructionText;
static Screen tutScreen;
    public Tutorial() {
    }
    
    public void init(Nifty nifty){
        this.nifty = nifty;
        tutScreen = nifty.getScreen("tutorialScreen");
        instructionText = tutScreen.findElementById("tutorialText");
        instructionText.setVisible(false);
        instruction = tutScreen.findElementById("image");
        instruction.setVisible(false);
        TutorialStep.INTRO.setImage(nifty.createImage("Interface/resume.png", true));
        TutorialStep.TILT.setImage(nifty.createImage("Interface/tiltSymbol.png", true));
        TutorialStep.SLIDE.setImage(nifty.createImage("Interface/tutorialArrowDown.png", true));
        TutorialStep.JUMP.setImage(nifty.createImage("Interface/tutorialArrowUp.png", true));
        TutorialStep.CATCH.setImage(nifty.createImage("Interface/tap.png", true));
        TutorialStep.TURNROAD.setImage(nifty.createImage("Interface/tutorialArrowLeft.png", true));
        TutorialStep.TURNROAD.setExtraImage(nifty.createImage("Interface/tutorialArrowRight.png", true));
    }
    
    public static void showInstruction(TutorialStep step, NiftyImage image, String text){
        if(step.equals(TutorialStep.FREE)){
            instruction.setVisible(false);
            instructionText.setVisible(false);
            return;
        }
        instructionText.setVisible(true);
        instruction.setVisible(true);
        instruction.getRenderer(ImageRenderer.class).setImage(image);
        instructionText.getRenderer(TextRenderer.class).setText(text);
        
    }
    
  public static void hideInstruction(){
      instruction.setVisible(false);
      instructionText.setVisible(false);
  }

    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        
    }

    @Override
    public void onStartScreen() {
        
    }

    @Override
    public void onEndScreen() {
        
    }

    @Override
    public String getFont() {
        if(Main.screenDensity < 2f)
            return "Interface/Fonts/FiraSansHeavysmall.fnt";
        else
            return "Interface/Fonts/FiraSansHeavy.fnt";
    }

    @Override
    public String getXLFont() {
        if(Main.screenDensity < 2f)
            return "Interface/Fonts/FiraSansHeavyMid.fnt";
        //else if(Main.screenDensity < 3.5f)
            //return "Interface/FontsFiraSansHeavy.fnt";
        else
            return "Interface/Fonts/FiraSansHeavyXL.fnt";
    }

    @Override
    public void showPopup(String type) {
        PlayState.main.gui.showPopup(type);
    }

    @Override
    public void closePopup(String type) {
        PlayState.main.gui.closePopup(type);
    }

    @Override
    public void toggleAudio(String type) {
        PlayState.main.gui.toggleAudio(type);
    }

    @Override
    public void setElementLocationX() {
        PlayState.main.gui.setElementLocationX();
    }

    @Override
    public void setElementLocationY() {
        PlayState.main.gui.setElementLocationY();
    }

    @Override
    public String getMissionAX() {
        return PlayState.main.gui.getMissionAX();
    }

    @Override
    public String getMissionAY() {
       return PlayState.main.gui.getMissionAY();
    }

    @Override
    public void playTutorial(String isSettings) {
        PlayState.main.gui.playTutorial(isSettings);
    }

    @Override
    public void pause(String ad) {
        PlayState.main.gui.pause(ad);
    }

    @Override
    public void gameOver() {
        PlayState.main.gui.gameOver();
    }

    @Override
    public void resumePlay() {
        PlayState.main.gui.resumePlay();
    }

    @Override
    public void goToHomeScreen(String string) {
        PlayState.main.gui.goToHomeScreen(string);
    }

    @Override
    public void buyItem(String item) {
        PlayState.main.gui.buyItem(item);
    }

    @Override
    public void goToStore(String source) {
        PlayState.main.gui.goToStore(source);
    }

    @Override
    public String getPrice(String string) {
        return PlayState.main.gui.getPrice(string);
       
    }

    @Override
    public void continueLife() {
        PlayState.main.gui.continueLife();
    }

    @Override
    public void goToWebsite() {
        PlayState.main.gui.goToWebsite();
    }
    
}
