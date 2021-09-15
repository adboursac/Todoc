package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.database.ProjectRepository;
import com.cleanup.todoc.database.TaskRepository;
import com.cleanup.todoc.database.TodocRoomDatabase;
import com.cleanup.todoc.database.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository provideTaskRepository(Context context) {
        TodocRoomDatabase database = TodocRoomDatabase.getDatabase(context);
        return new TaskRepository(database.taskDao());
    }

    public static ProjectRepository provideProjectRepository(Context context) {
        TodocRoomDatabase database = TodocRoomDatabase.getDatabase(context);
        return new ProjectRepository(database.projectDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskRepository taskRepository = provideTaskRepository(context);
        ProjectRepository projectRepository = provideProjectRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(taskRepository, projectRepository, executor);
    }
}
