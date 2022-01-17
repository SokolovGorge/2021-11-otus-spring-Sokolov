package ru.otus.ormlibrary.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.ormlibrary.models.Remark;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class RemarkRepositoryJpa implements  RemarkRepository {

    @PersistenceContext
    private final EntityManager em;

    public RemarkRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Remark> findAllByBook(long bookId) {
        TypedQuery<Remark> query = em.createQuery("select e from Remark e where e.book.id = :bookId", Remark.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Remark save(Remark remark) {
        if (null == remark.getId()) {
            em.persist(remark);
            return remark;
        } else {
            return em.merge(remark);
        }
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Remark a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Remark> findById(long id) {
        return Optional.ofNullable(em.find(Remark.class, id));
    }
}
