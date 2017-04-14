/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guisystem;

/**
 *
 * @author Miguel Martrinez
 */
public interface NiftyCommunicator {
    public String getFont();
    public String getXLFont();
    public void showPopup(String type);
    public void closePopup(String type);
    public void toggleAudio(final String type);
    public void setElementLocationX();
    public void setElementLocationY();
    public String getMissionAX();
    public String getMissionAY();
    public void playTutorial(String isSettings);
    public void pause(String ad);
    public void gameOver();
    public void resumePlay();
    public void goToHomeScreen(String string);
    public void goToStore(String source);
    public String getPrice(String string);
    public void continueLife();
    public void buyItem(String item);
    public void goToWebsite();
}
