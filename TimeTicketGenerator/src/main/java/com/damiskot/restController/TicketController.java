package com.damiskot.restController;


import com.damiskot.classes.Barcode;
import com.damiskot.classes.Id;
import com.damiskot.classes.Ticket;
import com.damiskot.mongo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@RestController
public class TicketController {

  private TicketRepository repository;
  private Barcode barcode;
  private Id id;

   @Autowired
   public TicketController(TicketRepository repository, Barcode barcode, Id id) {
        this.repository = repository;
        this.barcode = barcode;
        this.id = id;
    }

  @RequestMapping(value = "/generateTicket",method = RequestMethod.GET)
  public void generate(@RequestParam byte hour , @RequestParam byte minute) throws IOException, ParseException {
      String myDateString = hour + ":" + minute;
      LocalTime localTime = LocalTime.parse(myDateString, DateTimeFormatter.ofPattern("HH:mm"));
      Integer tempId = id.generate();
      repository.save(new Ticket(tempId,localTime));
      Ticket tempTicket = repository.findByTempId(tempId);
      barcode.saveBarcode("code128",tempTicket.getId(),false);
  }

  @RequestMapping(value = "/isExpire",method = RequestMethod.GET)
  public Boolean isExpire(@RequestParam String id){
      Optional<Ticket> oTicket = repository.findById(id);
      Ticket tempTicket = oTicket.get();
      boolean returnVal = tempTicket.getExpireTime().isBefore(LocalTime.now());
      if(returnVal) {
          repository.delete(tempTicket);
      }
      return returnVal;
  }

  @RequestMapping(value = "/deleteAllTickets",method = RequestMethod.GET)
  public void deleteAllTickets(){
    repository.deleteAll();
  }


  @RequestMapping(value = "/resetTicketId",method = RequestMethod.GET)
  public void resetId() throws IOException {
   Id id = new Id();
   id.resetId();
  }

  @RequestMapping(value = "/deleteTempBarcodeFile",method = RequestMethod.GET)
  public void deleteTempBarcode(){
  Barcode barcode = new Barcode();
  barcode.deleteTempBarcode();
  }

  @RequestMapping(value = "/getAllTickets",method = RequestMethod.GET)
  public List<Ticket> getAllTickets(){
   return repository.findAll();
  }

  @RequestMapping(value = "/resetAllTicketsData",method = RequestMethod.GET)
  public void resetAll() throws IOException {
      repository.deleteAll();
      Id id = new Id();
      id.resetId();
      Barcode barcode = new Barcode();
      barcode.deleteTempBarcode();
  }
}