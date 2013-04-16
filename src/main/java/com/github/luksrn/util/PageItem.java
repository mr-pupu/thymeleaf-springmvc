package com.github.luksrn.util;

public class PageItem {
	
	private int number;
	
    private boolean current;
    
    public PageItem(int number, boolean current){
        this.number = number;
        this.current = current;
    }
     
    public int getNumber(){
        return this.number;
    }
     
    public boolean isCurrent(){
        return this.current;
    }
}
