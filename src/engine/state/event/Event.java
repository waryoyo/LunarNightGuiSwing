package engine.state.event;

import engine.state.GameState;

import java.util.Scanner;

public abstract class Event implements GameState {
     private final String EVENT_NAME;
     protected final EventManager eventManager;
     protected int id;
     public Event(String EVENT_NAME, EventManager eventManager) {
         this.EVENT_NAME = EVENT_NAME;
         this.eventManager = eventManager;
     }

     protected void setActiveEvent(Event event){
         eventManager.ActiveEvent = event;
     }
//     public abstract Event start(Scanner sc);

 }
