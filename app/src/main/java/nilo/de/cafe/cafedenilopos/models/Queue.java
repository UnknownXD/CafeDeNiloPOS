package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

public class Queue
{
        @SerializedName("queue_number")
        int queue_number;
        @SerializedName("created_at")
        int created_at;

        public Queue(int queue_number, int created_at) {
            this.queue_number= queue_number;
            this.created_at = created_at;
        }


    public Queue(int queue_number) {

        this.queue_number =queue_number;
    }


    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getCreated_at() {
        return created_at;
    }


    public int getQueue_number() {
        return queue_number;
    }

    public void setQueue_number(int queue_number) {
        this.queue_number = queue_number;
    }
}
