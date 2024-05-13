package com.flip.kafka.db1.entities.consumer;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "journals")
public class ConsumerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userEventData;
    private Date createdOn;

    public String getUserEventData() {
        return userEventData;
    }

    public void setUserEventData(String userEventData) {
        this.userEventData = userEventData;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
