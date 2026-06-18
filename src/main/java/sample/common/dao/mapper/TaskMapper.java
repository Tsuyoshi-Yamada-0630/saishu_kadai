package sample.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import sample.common.dao.entity.Task;

@Mapper
public interface TaskMapper {
	public List<Task> all(String username);
	public Task single(Long id);
	public void create(Task task);
	public void update(Task task);
	public void delete(Long id);
}
