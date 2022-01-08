package ru.otus.jdbclibrary.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.jdbclibrary.domain.Author;
import ru.otus.jdbclibrary.domain.Book;
import ru.otus.jdbclibrary.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbc;
    private final JdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbc) {
        this.namedParameterJdbc = namedParameterJdbc;
        this.jdbc = namedParameterJdbc.getJdbcOperations();
    }

    @Override
    public long count() {
        return jdbc.queryForObject("select count(*) from book", Long.class);
    }

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("title", book.getTitle());
        KeyHolder kh = new GeneratedKeyHolder();
        namedParameterJdbc.update("insert into book(author_id, genre_id, title) values(:author_id, :genre_id, :title)",
                params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        namedParameterJdbc.update("update book set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of("id", book.getId(),
                        "title", book.getTitle(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        return namedParameterJdbc.queryForObject("select b.id as book_id, b.title, a.id as author_id, a.firstname, a.surname, g.id as genre_id, g.name as genre_name\n" +
                        "from book b\n" +
                        "join author a on a.id = b.author_id\n" +
                        "join genre g on g.id = b.genre_id\n" +
                        "where b.id = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id as book_id, b.title, a.id as author_id, a.firstname, a.surname, g.id as genre_id, g.name as genre_name\n" +
                        "from book b\n" +
                        "join author a on a.id = b.author_id\n" +
                        "join genre g on g.id = b.genre_id\n",
                new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbc.update("delete from book where id = :id",
                Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(rs.getLong("book_id"),
                    rs.getString("title"),
                    new Author(rs.getLong("author_id"), rs.getString("firstname"), rs.getString("surname")),
                    new Genre(rs.getLong("genre_id"), rs.getString("genre_name"))
            );
        }
    }
}
