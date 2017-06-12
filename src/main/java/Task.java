/**
 * Created by Petr on 12.06.2017.
 */
public class Task {

    public Task(Character letter, int number) {
        this.letter = letter;
        this.number = number;
    }

    public Character letter;

    public int number;


    @Override
    public String toString() {
        return "Task{" +
                "letter=" + letter +
                ", number=" + number +
                '}';
    }
}
