/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

/**
 * Class for handling assignment submissions.
 *
 * @author MattSorrentino
 */

public class SubmissionGrader {

    private boolean didCompile;
    private boolean didRun;
    private boolean outputMatches;
    private boolean didPassPlagarismCheck;

    public SubmissionGrader(Submission submission) {

    }


    private boolean runCode() {
        //Runtime r = new Runtime();
        return true;
    }

    private boolean checkPlagarism() {
        return true;
    }

    private boolean applyGrade() {
        return true;
    }

}
