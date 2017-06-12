import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Petr on 12.06.2017.
 */
public class MainTest {

    public static void main(String[] args) {

        int samples = 1;

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        ArrayList<Task> tasks = new ArrayList<>();


        for (Character c : alphabet) {
            for(int i = 0;i<samples;i++){
                tasks.add(new Task(c,i));
            }
        }


        System.out.println(pickTask(tasks));
        System.out.println(pickTask(tasks));
        System.out.println(pickTask(tasks));
        System.out.println(pickTask(tasks));

        System.out.println(tasks.size());

    }


    public static Task pickTask(ArrayList<Task> ar){
        Random rn = new Random();
        int random = rn.nextInt(ar.size() - 0 + 1) + 0;
        Task picked =ar.get(random);
        ar.remove(random);
        return picked;
    }


}
