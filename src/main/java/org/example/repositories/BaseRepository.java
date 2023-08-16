package org.example.repositories;

import java.util.List;

public interface BaseRepository<TEntity,TKey> {

    TEntity add(TEntity entity);

    TEntity getOne(TKey id);

    List<TEntity> getAll();

    boolean update(TKey id, TEntity entity);

    boolean delete(TKey id);
}
