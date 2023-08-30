package com.distribuida.app.books.repo;

import com.distribuida.app.books.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class BookRepository {
    @PersistenceContext(unitName = "demo")
    private EntityManager em;


    public List<Book> findAll() {
        return this.em.createQuery("SELECT i FROM Book i", Book.class).getResultList();
    }

    public Book findById(Integer id) {
        return this.em.find(Book.class, id);
    }

    public Book create(Book instrument) {
        this.em.persist(instrument);
        return instrument;
    }

    public Book update(Book instrument) {
        return this.em.merge(instrument);
    }

    public void delete(Integer id) {
        Book instrument = this.em.find(Book.class, id);
        this.em.remove(instrument);
    }

}
