package com.example.ashwingiri.task_scheduler_db.Holder;

/**
 * Created by Ashwin Giri on 3/28/2018.
 */

public class Todo {
    String task;
    boolean checked;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Todo(String task, boolean checked) {
        this.task = task;
        this.checked = checked;
    }
}
