package sample.common.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.common.dao.entity.Task;
import sample.common.dao.mapper.TaskMapper;
import sample.common.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<Task> all(String username, int page) {
        int limit = 10; // 1ページ10件表示
        int offset = (page - 1) * limit; // 読み飛ばす件数の計算
        return taskMapper.all(username, limit, offset);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long count(String username) {
        return taskMapper.count(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Task single(Long id, String username) {
        return taskMapper.single(id, username);
    }
    
    @Override
    public void create(Task task) {
        taskMapper.create(task);
    }
    
    @Override
    public void update(Task task) {
        taskMapper.update(task);
    }
    
    @Override
    public void delete(Long id, String username) {
        taskMapper.delete(id, username);
    }
}