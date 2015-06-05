package com.kcb.student.model.checkin;

public class CheckIn {
    
    private String date;
    private int checkinresult;
    
    public CheckIn(String date,int checkinresult){
        this.date=date;
        this.checkinresult=checkinresult;
    }
    
    public String getDate(){
        return date;
    }
    
    /*
     * if check in,return 1;
     * if not,return 0.
     */
    public int getCheckInResult(){
        return checkinresult;
    }
}
