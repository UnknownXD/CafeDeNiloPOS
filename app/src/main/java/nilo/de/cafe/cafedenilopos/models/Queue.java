package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

public class Queue {
    @SerializedName("created_at")
    int created_at;
    @SerializedName("prepare_time")
    String prepare_time;
    @SerializedName("queue_number")
    int queue_number;

    public Queue(int queue_number, int created_at, String prepare_time) {
        this.queue_number = queue_number;
        this.created_at = created_at;
        this.prepare_time = prepare_time;
    }

    public Queue(String prepare_time) {
        this.prepare_time = prepare_time;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getCreated_at() {
        return this.created_at;
    }

    public int getQueue_number() {
        return this.queue_number;
    }

    public void setQueue_number(int queue_number) {
        this.queue_number = queue_number;
    }

    public String getPrepare_time() {
        return this.prepare_time;
    }

    public void setPrepare_time(String prepare_time) {
        this.prepare_time = prepare_time;
    }
}
