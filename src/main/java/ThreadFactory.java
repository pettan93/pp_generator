import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by ON on 16.06.2017.
 */
public class ThreadFactory {

    private static Set<TaskThread> taskThreads = new HashSet<>();

    public static Set<TaskThread> createThreads(int numberOfThreads, TaskPicker taskPicker, TaskResultWriter taskResultWriter, CyclicBarrier cyclicBarrier) {
        for (int i = 0; i < numberOfThreads; i++) {
            TaskThread thread = new TaskThread("Thread " + i, taskPicker, taskResultWriter, cyclicBarrier);
            thread.start();
            taskThreads.add(thread);

        }

        return taskThreads;
    }
}
