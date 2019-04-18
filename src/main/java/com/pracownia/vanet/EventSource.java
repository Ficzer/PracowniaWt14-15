package com.pracownia.vanet;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class EventSource
{
    private int id;
    private String name;
    private String description;
    private Point localization;
    private Date eventDate;
    private Double range;
    private EventType eventType;

    public EventSource(int id, String name, String description, Point localization, Date eventDate, Double range, EventType eventType)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.localization = localization;
        this.eventDate = eventDate;
        this.range = range;
        this.eventType = eventType;
    }


    public Event getEvent()
    {
        Event event = new Event();
        event.setId(id);
        event.setEventType(eventType);
        event.setEventDate(eventDate);
        event.setMessage("Event id: " + event.getId() + "\r\n" +
                            "Localisation: " + localization + "\r\n" +
                            "Event type: " + eventType + "\r\n" +
                            "Event Date: " + eventDate + "\r\n");

        return event;
    }

    public boolean isInRange(Point vehicleLocalisation)
    {
        Double distance = Math.sqrt(Math.pow(localization.getX() - vehicleLocalisation.getX(), 2) +
                Math.pow(localization.getY() - vehicleLocalisation.getY(), 2));

        if(distance < range)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
