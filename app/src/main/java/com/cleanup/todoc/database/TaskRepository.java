package com.cleanup.todoc.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private final TaskDao mTaskDao;

    public TaskRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    LiveData<List<Task>> getAllTasks() {
        return mTaskDao.getAllTasks();
    }

    void insert(Task task) {
        mTaskDao.insert(task);
    }

    void delete(Task task) {
        mTaskDao.delete(task.getId());
    }
}
