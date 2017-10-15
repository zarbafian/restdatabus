package com.restdatabus.dao.jdbc;

import com.restdatabus.dao.AccountDao;
import com.restdatabus.security.model.Account;
import com.restdatabus.security.model.Person;
import com.restdatabus.security.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountDaoJdbcImpl implements AccountDao {

    private static final Logger LOG = LoggerFactory.getLogger(AccountDaoJdbcImpl.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account findByUsername(String username) {

        LOG.debug("> findByUsername: {}", username);

        List<Account> results = jdbcTemplate.query(
                "SELECT " +
                        "a.id, a.username, a.password, a.enabled, a.expired, " +
                        "a.credentials_expired, a.locked, a.created_by, a.created_at, " +
                        "a.updated_by, a.updated_at " +
                        "FROM account a WHERE username=?",
                new Object[]{ username },
                new AccountRowMapper()
        );

        LOG.debug("= findByUsername - found: {}", results);

        if(results.isEmpty()) {
            return null;
        }

        Account loadedAccount = results.get(0);

        List<Role> roles = getRoles(loadedAccount);
        Person person = getPerson(loadedAccount);

        loadedAccount.setRoles(roles);
        loadedAccount.setPerson(person);

        return results.get(0);
    }

    public Person getPerson(Account account) {

        LOG.debug("> getPerson: {}", account.getUsername());

        List<Person> results = jdbcTemplate.query(
                "SELECT p.id, p.name, p.account_id FROM person p WHERE p.account_id=?",
                new Object[]{ account.getId() },
                new PersonRowMapper()
        );

        LOG.debug(">= getPerson: found {} results", results.size());

        if(results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    public List<Role> getRoles(Account account) {

        LOG.debug("> getRoles: {}", account.getUsername());

        List<Role> results = jdbcTemplate.query(
                "SELECT " +
                        "r.id, r.code, r.label, r.ordinal, r.effective_at, " +
                        "r.expires_at, r.created_at " +
                        "FROM account_role ar " +
                        "JOIN role r on ar.role_id=r.id " +
                        "WHERE ar.account_id=?",
                new Object[]{ account.getId() },
                new RoleRowMapper()
        );

        LOG.debug("< getRoles: found {} roles", results.size());

        return results;
    }

    private static class AccountRowMapper implements RowMapper<Account> {

        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

            Long id = rs.getLong(1);
            String username = rs.getString(2);
            String pwd = rs.getString(3);
            boolean enabled = rs.getBoolean(4);
            boolean expired = rs.getBoolean(5);
            boolean credentialsExpired = rs.getBoolean(6);
            boolean locked = rs.getBoolean(7);
            Long createdBy = rs.getLong(8);
            LocalDateTime creatdeAt = rs.getObject(9, LocalDateTime.class);
            Long updatedBy = rs.getLong(10);
            LocalDateTime updatdeAt = rs.getObject(11, LocalDateTime.class);

            Account account = new Account();
            account.setId(id);
            account.setUsername(username);
            account.setPassword(pwd);
            account.setEnabled(enabled);
            account.setExpired(expired);
            account.setCredentialsExpired(credentialsExpired);
            account.setLocked(locked);
            account.setCreatedBy(createdBy);
            account.setCreatedAt(creatdeAt);
            account.setUpdatedBy(updatedBy);
            account.setUpdatedAt(updatdeAt);

            return account;
        }
    }

    private static class RoleRowMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int rowNum) throws SQLException {

            Long id = rs.getLong(1);
            String code = rs.getString(2);
            String label = rs.getString(3);
            int ordinal = rs.getInt(4);
            LocalDateTime effectiveAt = rs.getObject(5, LocalDateTime.class);
            LocalDateTime expiresAt = rs.getObject(6, LocalDateTime.class);
            LocalDateTime creatdeAt = rs.getObject(7, LocalDateTime.class);

            Role role = new Role();
            role.setId(id);
            role.setCode(code);
            role.setLabel(label);
            role.setOrdinal(ordinal);
            role.setEffectiveAt(effectiveAt);
            role.setExpiresAt(expiresAt);
            role.setCreatedAt(creatdeAt);

            return role;
        }
    }

    private static class PersonRowMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {

            Long id = rs.getLong(1);
            String name = rs.getString(2);
            Long accountId = rs.getLong(3);

            Person person = new Person();
            person.setId(id);
            person.setName(name);
            person.setAccountId(accountId);

            return person;
        }
    }
}
