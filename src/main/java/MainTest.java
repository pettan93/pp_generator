import java.util.*;

/**
 * Created by Petr on 12.06.2017.
 */
public class MainTest {

    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        int samples = 100;
//        int countsThreads = 10;

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        ArrayList<Task> tasks = new ArrayList<>();


        for (Character c : alphabet) {
            for (int i = 0; i < samples; i++) {
                tasks.add(new Task(c, i));
            }
        }

        System.out.println("Count of tasks " + tasks.size());

        TaskPicker taskPicker = new TaskPicker(tasks);
        TaskResultWriter taskResultWriter = new TaskResultWriter();

//        for (int i = 0; i < countsThreads; i++) {
            TaskThread thread1 = new TaskThread("Thread " + 1, taskPicker, taskResultWriter);
            thread1.start();

        TaskThread thread2 = new TaskThread("Thread " + 2, taskPicker, taskResultWriter);
        thread2.start();

        TaskThread thread3 = new TaskThread("Thread " + 3, taskPicker, taskResultWriter);
        thread3.start();

        TaskThread thread4 = new TaskThread("Thread " + 4, taskPicker, taskResultWriter);
        thread4.start();

//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("juch");
//        Zipper.zipFile("test.zip", TaskExecuter.getGeneratedMap());
        Long end = System.currentTimeMillis();
        System.out.println("Hotovo za [" + (end - start) / 1000 + " sekund]");

    }


}
