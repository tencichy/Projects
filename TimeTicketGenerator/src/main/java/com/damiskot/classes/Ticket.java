package com.damiskot.classes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
 @Id
 private String id;
 private Integer tempId;
 private LocalTime expireTime;

    public Ticket(Integer tempId, LocalTime expireTime) {
        this.tempId = tempId;
        this.expireTime = expireTime;
    }
}
