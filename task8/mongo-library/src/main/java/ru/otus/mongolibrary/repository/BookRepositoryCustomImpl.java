package ru.otus.mongolibrary.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.mongolibrary.domain.Book;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements  BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private final RemarkRepository remarkRepository;

    @Override
    public void deleteByIdWithRecursive(String id) {
        val query = Query.query(Criteria.where("id").is(new ObjectId(id)));
        val book = mongoTemplate.findOne(query, Book.class);
        if (book != null) {
            remarkRepository.findAllRemarksByBook(book).forEach(remarkRepository::delete);
            mongoTemplate.remove(book);
        }
    }

    @Override
    public Book saveWithRecursive(Book book) {
        String oldId = book.getId();
        Book savedBook = mongoTemplate.save(book);
        if (oldId != null) {
            remarkRepository.findAllRemarksByBook(savedBook).forEach(remark -> {
                remark.setBook(savedBook);
                remarkRepository.save(remark);
            });
        }
        return savedBook;
    }
}
