package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.EventLogDao;
import com.restdatabus.events.EventLog;
import com.restdatabus.events.EventLogType;
import com.restdatabus.web.api.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.*;

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

    @Override
    public List<EventLog> findAll() {

        LOG.debug("> findAll");

        List<EventLog> eventLogs = jdbcTemplate.query(
                "SELECT el.id, el.action, el.timestamp, el.target, el.params FROM event_logs el",
                new EventLogRowMapper()
        );

        return eventLogs;
    }

    private static class EventLogRowMapper implements RowMapper<EventLog> {

        @Override
        public EventLog mapRow(ResultSet rs, int rowNum) throws SQLException {

            Long id = rs.getLong(1);
            String action = rs.getString(2);
            Timestamp timestamp = rs.getTimestamp(3);
            String target = rs.getString(4);
            String params = rs.getString(5);

            EventLog eventLog = new EventLog();
            eventLog.setId(id);
            eventLog.setType(EventLogType.fromValue(action));
            eventLog.setTimestamp(OffsetDateTime.ofInstant(timestamp.toInstant(), Constants.TIME_ZONE));
            eventLog.setPaths(parseArray(target));

            // TODO: parse params
            eventLog.setParams(Arrays.asList(params));

            return eventLog;
        }
    }

    private static List<String> parseArray(String string) {

        if(string.length() <= 2) {
            return new ArrayList<>();
        }

        return Arrays.asList(string.substring(1, string.length() - 1).split(", "));
    }
}
