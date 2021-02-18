package com.technoabinash.spchat.models;

public class MessagesModel {

    String mId,message,messageId;
    Long timeStamp;

    public MessagesModel(String mId, String message, String dmId, Long timeStamp) {
        this.mId = mId;
        this.message = message;
        this.messageId = dmId;
        this.timeStamp = timeStamp;
    }public MessagesModel(String mId, String message , Long timeStamp) {
        this.mId = mId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public MessagesModel(String mId, String message) {
        this.mId = mId;
        this.message = message;
    }

    public MessagesModel() {
    }

    public String getDmId() {
        return messageId;
    }

    public void setDmId(String dmId) {
        this.messageId = dmId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
