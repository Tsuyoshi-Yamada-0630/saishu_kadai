package sample.common.service;

import java.util.List;

import sample.common.dao.entity.Task;

public interface TaskService {
	public List<Task> all (String username);
	public Task single(Long id);
	public void create(Task task);
	public void update(Task task);
	public void delete(Long id);
}
