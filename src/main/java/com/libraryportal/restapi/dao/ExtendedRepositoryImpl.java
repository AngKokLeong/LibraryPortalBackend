package com.libraryportal.restapi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.libraryportal.restapi.entity.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

//codes referenced from https://baeldung.com/spring-data-jpa-method-in-all-repositories/

/* 
public class ExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID> implements BookRepository<T, ID>{

    private EntityManager entityManager;
    
    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        //TODO Auto-generated constructor stub
        this.entityManager = entityManager;
    }

    @Override
    public Page<Book> findBookByTitleContaining(String title, Pageable pageable) {
        // TODO Auto-generated method stub
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cQuery = builder.createQuery(getDomainClass());

        Root<T> root = cQuery.from(getDomainClass());

        cQuery
            .select(root)
            .where(builder
                    .like(root.<String>get("title"), "%" + title + "%"));
        
        TypedQuery<T> query = entityManager.createQuery(cQuery);

        return query.getResultList();
    }

}
*/