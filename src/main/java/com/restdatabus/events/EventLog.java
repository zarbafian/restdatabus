package com.restdatabus.events;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventLog {

    private Long id;

    private EventLogType type;

    private OffsetDateTime timestamp;

    private List<String> paths;

    private List<Object> params;

    public EventLog(EventLog eventLog) {

        this();

        this.id = eventLog.getId();
        this.type = eventLog.getType();
        this.timestamp = OffsetDateTime.from(eventLog.getTimestamp());

        this.getPaths().addAll(eventLog.getPaths());
        this.getParams().addAll(eventLog.getParams());
    }

    public EventLog() {
        this.paths = new ArrayList<>();
        this.params = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "EventLog{" +
                "id=" + id +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", paths=" + paths +
                ", params=" + params +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventLogType getType() {
        return type;
    }

    public void setType(EventLogType type) {
        this.type = type;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
