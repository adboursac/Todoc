package com.cleanup.todoc;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;

import com.cleanup.todoc.database.TodocRoomDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaskDaoTest {

    private TaskDao mTaskDao;
    private ProjectDao mProjectDao;
    private TodocRoomDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = androidx.test.core.app.ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodocRoomDatabase.class).build();
        mTaskDao = db.taskDao();
        mProjectDao = db.projectDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        //Adding a new task
        Project project1 = new Project("Projet 1", 0xFFA3CED2);
        mProjectDao.insert(project1);
        Task task1 = new Task(1, "task 1", new Date().getTime());
        Task task2 = new Task(1, "task 2", new Date().getTime());
        mTaskDao.insert(task1);
        mTaskDao.insert(task2);
        // TEST
        List<Task> taskList = LiveDataTestUtil.getValue(db.taskDao().fetchAllTasks());
        assertEquals(2, taskList.size());
        assertEquals(1L, taskList.get(0).getId());
        assertEquals(task1.getName(), taskList.get(0).getName());
        assertEquals(2L, taskList.get(1).getId());
        assertEquals(task2.getName(), taskList.get(1).getName());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        //Adding a new task
        Project project1 = new Project("Projet 1", 0xFFA3CED2);
        mProjectDao.insert(project1);
        Task taskToDelete = new Task(1, "task 1", new Date().getTime());
        long taskId = mTaskDao.insert(taskToDelete);
        //TEST
        mTaskDao.delete(taskId);
        List<Task> taskList = LiveDataTestUtil.getValue(db.taskDao().fetchAllTasks());
        assertTrue(taskList.isEmpty());
    }
}
