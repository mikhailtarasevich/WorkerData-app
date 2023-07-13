package com.mikhail.tarasevich.workerdataapp.repository.impl;

import com.mikhail.tarasevich.workerdataapp.repository.CrudRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractCrudRepositoryImpl<E> implements CrudRepository<E> {

    protected final SessionFactory sessionFactory;

    protected final Class<E> clazz;

    protected final String uniqueNameParameter;

    protected AbstractCrudRepositoryImpl(SessionFactory sessionFactory, Class<E> clazz, String uniqueNameParameter) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
        this.uniqueNameParameter = uniqueNameParameter;
    }

    @Override
    public E save(E entity) {

        Session session = sessionFactory.getCurrentSession();
        session.save(entity);

        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(long id) {

        Session session = sessionFactory.getCurrentSession();

        return Optional.of(session.get(clazz, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findAll() {

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
        Root<E> root = query.from(clazz);
        query.select(root).orderBy(criteriaBuilder.asc(root.get("id")));

        return session.createQuery(query).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
        Root<E> root = query.from(clazz);
        Predicate emailPredicate = criteriaBuilder.equal(root.get(uniqueNameParameter), name);
        query.select(root).where(emailPredicate);
        session.createQuery(query).uniqueResultOptional();

        return session.createQuery(query).uniqueResultOptional();
    }

    @Override
    public void update(E entity) {

        Session session = sessionFactory.getCurrentSession();
        session.update(entity);
    }

    @Override
    public boolean deleteById(long id) {

        Session session = sessionFactory.getCurrentSession();

        try {
            session.delete(session.get(clazz, id));
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

}
