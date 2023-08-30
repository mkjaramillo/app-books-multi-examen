package com.distribuida.books.authors.repo;

import com.distribuida.books.authors.db.Author;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class AuthorRepository  {
    @PersistenceContext(unitName = "demo")
    private EntityManager em;


    public List<Author> findAll() {
        return this.em.createQuery("SELECT i FROM Author i", Author.class).getResultList();
    }

    public Author findById(Integer id) {
        return this.em.find(Author.class, id);
    }

    public Author create(Author instrument) {
        this.em.persist(instrument);
        return instrument;
    }

    public Author update(Author instrument) {
        return this.em.merge(instrument);
    }

    public void delete(Integer id) {
        Author instrument = this.em.find(Author.class, id);
        this.em.remove(instrument);
    }



}
