/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.util.ArrayList;

/**
 * @author MattSorrentino
 */

public class Assignment {

    // name of assignment
    private String assignmentName;

    // ID of assignment, from Canvas
    private String assignmentID;

    //long text description of assignment
    private String assignmentDescription;

    //date the assigment becomes available
    private String openDate;
    //date the assigment is due
    private String dueDate;
    //date the assigment closes (no more submissions)
    private String closeDate;

    
    /**
     * Display the number of student who submit the assignment
     * 
     */
    private int submitterCount;
    
    public int getSubmitterCount() {
		return submitterCount;
	}

	public void setSubmitterCount(int submitterCount) {
		this.submitterCount = submitterCount;
	}

	// percentage penalty for late submission
    private int percentPenalty;

    //array of strings containing the 
    private String submissionTypes;

    // List of submissions
    private ArrayList<Submission> submissionsList;

    /**
     * The points of submission
     * 
     */
    private String points;

    public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
    public Assignment(String assignmentName, String assignmentID) {
        
        submissionsList = new ArrayList<>(20);

        this.assignmentName = assignmentName;
        this.assignmentID = assignmentID;
        this.percentPenalty = 0;

    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentID() {
        return assignmentID;
    }

    public String getAssignmentDescription() {
        return assignmentDescription;
    }

    public void setAssignmentDescription(String assignmentDescription) {
        this.assignmentDescription = assignmentDescription;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public int getPercentPenalty() {
        return percentPenalty;
    }

    public void setPercentPenalty(int percentPenalty) {
        this.percentPenalty = percentPenalty;
    }


    public String getSubmissionTypes() {
        return submissionTypes;
    }

    public void setSubmissionTypes(String submissionTypes) {
        this.submissionTypes = submissionTypes;
    }

    public ArrayList<Submission> getSubmissionsList() {
        return submissionsList;
    }

    public void setSubmissionsList(ArrayList<Submission> submissionsList) {
        this.submissionsList = submissionsList;
    }


}
