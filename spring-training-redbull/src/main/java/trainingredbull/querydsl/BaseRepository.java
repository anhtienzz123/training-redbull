package trainingredbull.querydsl;

import java.util.List;

public interface BaseRepository<T, ID> {

    List<T> findAll();
    
    T findById(ID id);
    
    Long create(T t);
    
    void update(T t);
    
    void deleteById(ID id);
    
    boolean existsById(ID id);
}
