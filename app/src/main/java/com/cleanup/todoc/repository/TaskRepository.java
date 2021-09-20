package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    public LiveData<List<Task>> fetchAllTasks() {
        return mTaskDao.fetchAllTasks();
    }

    public void insert(Task task) {
        mTaskDao.insert(task);
    }

    public void delete(Task task) {
        mTaskDao.delete(task.getId());
    }
}
