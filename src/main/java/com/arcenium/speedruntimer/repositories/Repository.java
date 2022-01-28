package com.arcenium.speedruntimer.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO implement persistence

public interface Repository <E, I>{
//    static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryJPA");

    List<E> findAll();
    List<E> findAllById(I i);
    boolean remove(E e);


    default boolean removeAll(final List<E> collectionOfE){
        for(final E e : collectionOfE){
            if(!remove(e)){
                return false;
            }
        }
        return true;
    }

    default E save(final E e) {
//        EntityManager em = Repository.emf.createEntityManager();
//        em.getTransaction().begin();
//        em.merge(e);
//        em.getTransaction().commit();
//        em.close();
        return e;
    }

    default List<E> saveAll(final List<E> collectionOfe) {
        final List<E> results = new ArrayList<>();
        for (final E e : collectionOfe) {
            results.add(save(e));
        }
        return results;
    }
}
