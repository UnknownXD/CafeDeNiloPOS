package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class QueueOrderList {

    @SerializedName("queueorder")
    private ArrayList<QueueOrder> queueorderList;

    public ArrayList<QueueOrder> getQueueorderList() {
        return queueorderList;
    }

    public void setQueueOrderArrayList(ArrayList<QueueOrder> queueOrderArrayList) {
        this.queueorderList = queueOrderArrayList;
    }
}
