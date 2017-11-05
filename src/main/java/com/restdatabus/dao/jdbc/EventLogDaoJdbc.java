package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.EventLogDao;
import com.restdatabus.events.EventLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * DaoJdbc layer: perform database interactions
 */
@Service
public class EventLogDaoJdbc implements EventLogDao {

    private static final Logger LOG = LoggerFactory.getLogger(EventLogDaoJdbc.class);

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertEventLog;

    @Autowired
    public EventLogDaoJdbc(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;

        insertEventLog = new SimpleJdbcInsert(jdbcTemplate)
                                        .withTableName("event_logs")
                                        .usingColumns("timestamp", "action", "target", "params")
                                        .usingGeneratedKeyColumns("id");
    }

    public EventLog insert(EventLog eventLog) {

        LOG.debug("> insert: {}", eventLog);

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("timestamp", eventLog.getTimestamp().toLocalDateTime());
        parameters.put("action", eventLog.getType().getValue());
        parameters.put("target", eventLog.getPaths().toString());
        parameters.put("params", eventLog.getParams().toString());
        Number newId = insertEventLog.executeAndReturnKey(parameters);

        EventLog persistedEventLog = new EventLog(eventLog);
        persistedEventLog.setId(newId.longValue());

        LOG.debug("< insert: {}", persistedEventLog);

        return persistedEventLog;
    }

}
