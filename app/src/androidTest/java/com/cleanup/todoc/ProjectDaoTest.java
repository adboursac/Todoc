package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;

import com.cleanup.todoc.database.TaskViewModel;
import com.cleanup.todoc.database.TodocRoomDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProjectDaoTest {
    private ProjectDao mProjectDao;
    private TodocRoomDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = androidx.test.core.app.ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TodocRoomDatabase.class).build();
        mProjectDao = db.projectDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        //Adding a new project
        Project project1 = new Project("Project 1", 0xFFA3CED2);
        Project project2 = new Project("Project 2", 0xFFB4CDBA);
        mProjectDao.insert(project1);
        mProjectDao.insert(project2);
        // TEST
        List<Project> projectList = LiveDataTestUtil.getValue(db.projectDao().fetchAllProjects());
        assertEquals(2, projectList.size());
        assertEquals(1L, projectList.get(0).getId());
        assertEquals(project1.getName(), projectList.get(0).getName());
        assertEquals(2L, projectList.get(1).getId());
        assertEquals(project2.getName(), projectList.get(1).getName());
    }

    @Test
    public void insertAndDeleteProject() throws InterruptedException {
        //Adding a new project
        Project projectToDelete = new Project("Project to delete", 0xFFA3CED2);
        long projectId = mProjectDao.insert(projectToDelete);
        //TEST
        mProjectDao.delete(projectId);
        List<Project> projectList = LiveDataTestUtil.getValue(db.projectDao().fetchAllProjects());
        assertEquals(0, projectList.size());
    }

    @Test
    public void getProjectByIdTest() throws InterruptedException {
        //Adding a new project
        Project project1 = new Project("Project 1", 0xFFA3CED2);
        mProjectDao.insert(project1);
        final Task task1 = new Task(1, "task 1", new Date().getTime());
        final Task task2 = new Task(2, "task 2", new Date().getTime());

        List<Project> projects = LiveDataTestUtil.getValue(db.projectDao().fetchAllProjects());
        assertEquals("Project 1",
                TaskViewModel.getProjectById(task1.getProjectId(), projects).getName());
        assertNull(TaskViewModel.getProjectById(task2.getProjectId(), projects));
    }
}
