package com.kcb.student.model.checkin;

import java.util.Date;

public class CheckIn {
    
    private Date date;
    private int checkinresult;
    
    public CheckIn(Date date,int checkinresult){
        this.date=date;
        this.checkinresult=checkinresult;
    }
    
    public Date getDate(){
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
