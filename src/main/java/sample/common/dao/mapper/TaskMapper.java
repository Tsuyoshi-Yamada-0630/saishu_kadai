package sample.common.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; 
import sample.common.dao.entity.Task;

@Mapper
public interface TaskMapper {
    public List<Task> all(
        @Param("username") String username, 
        @Param("limit") int limit, 
        @Param("offset") int offset
    );
    public long count(@Param("username") String username);

    public Task single(@Param("id") Long id, @Param("username") String username);
    
    public void create(Task task);
    
    public void update(Task task);

    public void delete(@Param("id") Long id, @Param("username") String username);
}