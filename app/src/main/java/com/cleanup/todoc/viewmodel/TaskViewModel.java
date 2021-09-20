package com.cleanup.todoc.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod mSortMethod = SortMethod.NONE;

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
    }

    public LiveData<List<Task>> fetchAllTasks() {
        return mTaskRepository.fetchAllTasks();
    }

    public LiveData<List<Project>> fetchAllProjects() {
        return mProjectRepository.fetchAllProjects();
    }

    public void insertTask(Task task) {
        mExecutor.execute(() -> {
            mTaskRepository.insert(task);
        });
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> {
            mTaskRepository.delete(task);
        });
    }

    @NonNull
    public SortMethod getSortMethod() {
        return mSortMethod;
    }

    public void setSortMethod(int menuItemId) {
        switch (menuItemId) {
            case R.id.filter_alphabetical:
                mSortMethod = SortMethod.ALPHABETICAL;
                break;
            case R.id.filter_alphabetical_inverted:
                mSortMethod = SortMethod.ALPHABETICAL_INVERTED;
                break;
            case R.id.filter_oldest_first:
                mSortMethod = SortMethod.OLD_FIRST;
                break;
            case R.id.filter_recent_first:
                mSortMethod = SortMethod.RECENT_FIRST;
                break;
            default:
                Log.e("TaskViewModel", "Didn't found matching menu item id");
        }
    }

    /**
     * Returns the project with the given unique identifier, or null if no project with that
     * identifier can be found.
     *
     * @param projectId   the unique identifier of the project to return
     * @param projectList the current list of projects
     * @return the project with the given unique identifier, or null if it has not been found
     */
    public static Project getProjectById(long projectId, List<Project> projectList) {
        for (Project project : projectList) {
            if (project.getId() == projectId) return project;
        }
        return null;
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

    public void updateTaskSortOrder(List<Task> tasks) {
        switch (mSortMethod) {
            case ALPHABETICAL:
                Collections.sort(tasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new Task.TaskOldComparator());
                break;
        }
    }
}
