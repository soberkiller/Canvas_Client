/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

/**
 *
 * @author MattSorrentino
 */
public class CanvasClient {

    /**
     * @param args the command line arguments
     */
    
    private static Course currentCourse;
    
    public static Course getCurrentCourse()
    {
        return currentCourse;
    }
    
    public void setCurrentCourse(Course currentCourse)
    {
        this.currentCourse = currentCourse;
    }
    
    
    
    public static void main(String[] args) {
        GUI myGUI = new GUI();
    }
    
}
