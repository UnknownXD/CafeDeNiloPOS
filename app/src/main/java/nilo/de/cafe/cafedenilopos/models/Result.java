package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("transid")
    private int transid;

    @SerializedName("queueID")
    private int queueID;

    public Result(Boolean error, String message, User user, int transid, int queueID) {
        this.error  = error;
        this.message = message;
        this.user = user;
        this.transid= transid;
        this.queueID = queueID;
    }

    public Result(Boolean error, String message, User user, int transid) {
        this.error  = error;
        this.message = message;
        this.user = user;
        this.transid= transid;

    }

    public Result(Boolean error, String message, int queueID) {
        this.error  = error;
        this.message = message;
        this.queueID = queueID;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public int getTransid() {
        return transid;
    }

    public int getQueueID() {
        return queueID;
    }
}