package com.jnu.myapplication.data;

//
//public class BookItem {
//    public BookItem(String TITLE, String AUTHORS, String TRANSLATORS, String PUBLISHER, String PUB_TIME, String ADD_TIME, String ISBN, String HAS_COVER, String READING_STATUS, String BOOKSHELF_ID, String NOTES, String WEBSITE, String LABEL_ID) {
//        this.TITLE = TITLE;
//        this.AUTHORS = AUTHORS;
//        this.TRANSLATORS = TRANSLATORS;
//        this.PUBLISHER = PUBLISHER;
//        this.PUB_TIME = PUB_TIME;
//        this.ADD_TIME = ADD_TIME;
//        this.ISBN = ISBN;
//        this.HAS_COVER = HAS_COVER;
//        this.READING_STATUS = READING_STATUS;
//        this.BOOKSHELF_ID = BOOKSHELF_ID;
//        this.NOTES = NOTES;
//        this.WEBSITE = WEBSITE;
//        this.LABEL_ID = LABEL_ID;
//    }
//
//    public String getTITLE() {
//        return TITLE;
//    }
//
//    public void setTITLE(String TITLE) {
//        this.TITLE = TITLE;
//    }
//
//    public String getAUTHORS() {
//        return AUTHORS;
//    }
//
//    public void setAUTHORS(String AUTHORS) {
//        this.AUTHORS = AUTHORS;
//    }
//
//    public String getTRANSLATORS() {
//        return TRANSLATORS;
//    }
//
//    public void setTRANSLATORS(String TRANSLATORS) {
//        this.TRANSLATORS = TRANSLATORS;
//    }
//
//    public String getPUBLISHER() {
//        return PUBLISHER;
//    }
//
//    public void setPUBLISHER(String PUBLISHER) {
//        this.PUBLISHER = PUBLISHER;
//    }
//
//    public String getPUB_TIME() {
//        return PUB_TIME;
//    }
//
//
//// 综合
//    public String getPubText(){//作者+出版社
//        return getAUTHORS() + "著，"+ getPUBLISHER();
//    }
//    public String getPubTime(){
//        return getPUB_TIME();
//    }
////
//
//    public void setPUB_TIME(String PUB_TIME) {
//        this.PUB_TIME = PUB_TIME;
//    }
//
//    public String getADD_TIME() {
//        return ADD_TIME;
//    }
//
//    public void setADD_TIME(String ADD_TIME) {
//        this.ADD_TIME = ADD_TIME;
//    }
//
//    public String getISBN() {
//        return ISBN;
//    }
//
//    public void setISBN(String ISBN) {
//        this.ISBN = ISBN;
//    }
//
//    public String getHAS_COVER() {
//        return HAS_COVER;
//    }
//
//    public void setHAS_COVER(String HAS_COVER) {
//        this.HAS_COVER = HAS_COVER;
//    }
//
//    public String getREADING_STATUS() {
//        return READING_STATUS;
//    }
//
//    public void setREADING_STATUS(String READING_STATUS) {
//        this.READING_STATUS = READING_STATUS;
//    }
//
//    public String getBOOKSHELF_ID() {
//        return BOOKSHELF_ID;
//    }
//
//    public void setBOOKSHELF_ID(String BOOKSHELF_ID) {
//        this.BOOKSHELF_ID = BOOKSHELF_ID;
//    }
//
//    public String getNOTES() {
//        return NOTES;
//    }
//
//    public void setNOTES(String NOTES) {
//        this.NOTES = NOTES;
//    }
//
//    public String getWEBSITE() {
//        return WEBSITE;
//    }
//
//    public void setWEBSITE(String WEBSITE) {
//        this.WEBSITE = WEBSITE;
//    }
//
//    public String getLABEL_ID() {
//        return LABEL_ID;
//    }
//
//    public void setLABEL_ID(String LABEL_ID) {
//        this.LABEL_ID = LABEL_ID;
//    }
//
//    public String TITLE;
//    public String AUTHORS;
//    public String TRANSLATORS ;
//    public String PUBLISHER;
//    public String PUB_TIME;
//    public String ADD_TIME;
//    public String ISBN;
//    public String HAS_COVER;
//    public String READING_STATUS;
//    public String BOOKSHELF_ID;
//    public String NOTES;
//    public String WEBSITE ;
//    public String LABEL_ID ;
//}
//book类
// 书名 图像 作者 译者 出版社 发布时间 ISBN
public class BookItem {
    private String title;
    private int image_R_id;
    public BookItem(String name, int id){
        title=name;
        image_R_id=id;
    }
        public void setTITLE(String TITLE) {
        this.title = TITLE;
    }
    public String getTitle(){
        return title;
    }
    public String getPubText(){
        return "getPubText";
    }
    public String getPubTime(){
        return "getPubTime";
    }
    public int getCoverResourceId(){
        return image_R_id;
    }
}
