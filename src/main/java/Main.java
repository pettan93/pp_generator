import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Petr on 12.06.2017.
 */
public class Main {


    public static void main(String[] args) {


        Long start = System.currentTimeMillis();
        int samples = 100;
        int countsThreads = 5;


        final Set<TaskThread>[] zipThreadTasks = new Set[]{new HashSet<>()};
        TaskPicker taskPicker = new TaskPicker(TaskFactory.createTasks(samples));
        TaskResultWriter taskResultWriter = new TaskResultWriter();


        // responsible for starting the zip phase
        Runnable endActionBarrier = () -> {
            for (TaskThread taskThread : zipThreadTasks[0]) {
                try {
                    taskThread.getScatterZipOutputStream().writeTo(Zipper.zipArchiveOutputStream);
                    taskThread.getScatterZipOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Zipper.closeZipping();
            Long end = System.currentTimeMillis();
            System.out.println("Hotovo za [" + (end - start) / 1000 + " sekund]");
        };

        CyclicBarrier endBarrier = new CyclicBarrier(countsThreads, endActionBarrier);
        // barrier for changing tasks after the generating phase
        Runnable changeActionBarrier = () -> {
            taskPicker.setTasks(new ArrayList<>(TaskResultWriter.generatedTasks));
            TaskResultWriter.generatedTasks = new ArrayList<>();

            Zipper.startZipping("test.zip");
            zipThreadTasks[0] = ThreadFactory.createThreads(countsThreads, taskPicker, taskResultWriter, endBarrier);

        };

        CyclicBarrier changeBarrier = new CyclicBarrier(countsThreads, changeActionBarrier);
        ThreadFactory.createThreads(countsThreads, taskPicker, taskResultWriter, changeBarrier);




    }


}
