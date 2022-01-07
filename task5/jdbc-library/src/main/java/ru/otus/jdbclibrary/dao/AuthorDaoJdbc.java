package ru.otus.jdbclibrary.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.jdbclibrary.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc  implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;
    private final JdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbc) {
        this.namedParameterJdbc = namedParameterJdbc;
        this.jdbc = namedParameterJdbc.getJdbcOperations();
    }

    @Override
    public long count() {
        return jdbc.queryForObject("select count(*) from author", Long.class);
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstname", author.getFirstName());
        params.addValue("surname", author.getSurName());
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbc.update("insert into author(firstname, surname) values(:firstname, :surname)",
                params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void update(Author author) {
        namedParameterJdbc.update("update author set firstname = :firstname, surname = :surname where id = :id",
                Map.of("id", author.getId(), "firstname", author.getFirstName(), "surname", author.getSurName()));
    }

    @Override
    public Author getById(long id) {
        return namedParameterJdbc.queryForObject("select id, firstname, surname from author where id = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, firstname, surname from author", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbc.update("delete from author where id = :id",
                Map.of("id", id));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"),
                    rs.getString("firstname"),
                    rs.getString("surname"));
        }
    }
}
