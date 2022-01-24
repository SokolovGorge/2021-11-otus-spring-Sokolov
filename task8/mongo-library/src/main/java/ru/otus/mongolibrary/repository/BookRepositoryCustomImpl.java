package ru.otus.mongolibrary.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.mongolibrary.domain.Book;
import ru.otus.mongolibrary.domain.Remark;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements  BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteByIdWithRecursive(String id) {
        val query = Query.query(Criteria.where("book.id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Remark.class);
    }

    @Override
    public Book saveWithRecursive(Book book) {
        String oldId = book.getId();
        Book savedBook = mongoTemplate.save(book);
        if (oldId != null) {
            val query = Query.query(Criteria.where("book.id").is(new ObjectId(oldId)));
            val update = new Update().set("book", savedBook);
            mongoTemplate.updateMulti(query, update, Remark.class);
        }
        return savedBook;
    }
}
