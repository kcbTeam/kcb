package com.kcb.student.model.test;

public class Test {
    
    private String testName;
    private int questionNum;
    private String date;
    
    public Test(){
        
    }
    
    public Test(String testName,int questionNum,String date){
        this.testName=testName;
        this.questionNum=questionNum;
        this.date=date;
    }
    
    public void setName(String testname){
        testName=testname;
    }
    
    public String getName(){
        return testName;
    }
    
    public void setSum(int num){
        questionNum=num;
    }
    
    public int getSum(){
        return questionNum;
    }
    
    public void setDate(String mDate){
        date=mDate;
    }
    
    public String getDate(){
        return date;
    }
    
}
