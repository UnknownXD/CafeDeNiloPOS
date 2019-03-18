package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

public class QueueOrder
{
        @SerializedName("item_name")
        String item_name;
        @SerializedName("quantity")
        int quantity;
    @SerializedName("queue_number")
    int queue_number;

        public QueueOrder(int queue_number, String item_name, int quantity) {
            this.queue_number= queue_number;
            this.item_name = item_name;
            this.quantity= quantity;
        }

    public QueueOrder(String item_name, int quantity) {

        this.item_name = item_name;
        this.quantity= quantity;
    }

    public QueueOrder(int queue_number) {
        this.queue_number= queue_number;
    }



    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQueue_number() {
        return queue_number;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQueue_number(int queue_number) {
        this.queue_number = queue_number;
    }

    public String getItem_name() {
        return item_name;
    }

}
