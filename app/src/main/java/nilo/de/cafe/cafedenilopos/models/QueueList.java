package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class QueueList {

    @SerializedName("queue")
    private ArrayList<Queue> queueList;

    public ArrayList<Queue> getQueueList() {
        return queueList;
    }

    public void setQueueArrayList(ArrayList<Queue> queueArrayList) {
        this.queueList = queueArrayList;
    }
}
