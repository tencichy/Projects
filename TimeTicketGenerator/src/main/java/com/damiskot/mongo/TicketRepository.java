package com.damiskot.mongo;


import com.damiskot.classes.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket,String>{
    Ticket findByTempId(Integer id);
}
