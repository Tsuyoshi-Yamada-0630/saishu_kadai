package sample.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.common.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskMapper taskMapper;
	
	@Override
	public List<Task> all (String username){
		return taskMapper.all(username);
	}
	
	@Override
	public Task single(Long id){
		return taskMapper.single(id);
	}
	
	@Override
	public void create(Task task){
		taskMapper.create(task);
	}
	
	@Override
	public void update(Task task){
		taskMapper.update(task);
	}
	
	@Override
	public void delete(Long id){
		taskMapper.delete(id);
	}

}
