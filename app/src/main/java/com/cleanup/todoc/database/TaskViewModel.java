package com.cleanup.todoc.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskRepository mRepository;
    private final Executor mExecutor;

    public TaskViewModel(TaskRepository taskRepository, Executor executor) {
        mRepository = taskRepository;
        mExecutor = executor;
    }

    public LiveData<List<Task>> getAllTasks() {
        return mRepository.getAllTasks();
    }

    public void insert(Task task) {
        mExecutor.execute(() -> {
            mRepository.insert(task);
        });
    }

    public void delete(Task task) {
        mExecutor.execute(() -> {
            mRepository.delete(task);
        });
    }
}
