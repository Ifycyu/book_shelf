package com.jnu.myapplication.data;

import androidx.annotation.NonNull;


public class BookShelf {

    private String title;
    private int cnt; // # of books on this bookshelf

    private boolean internalBookShelf;




    @NonNull
    @Override
    public String toString() {
        if(title != null){
            if(internalBookShelf){
                return title;
            }else {
                return title + " (" + cnt + ")";
            }
        }else{
            return " (" + cnt + ")";
        }
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public boolean isInternalBookShelf() {
        return internalBookShelf;
    }

    public void setInternalBookShelf(boolean internalBookShelf) {
        this.internalBookShelf = internalBookShelf;
    }



}
