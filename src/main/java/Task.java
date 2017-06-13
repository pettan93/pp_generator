/**
 * Created by Petr on 12.06.2017.
 */
public class Task {

    public Task(Character letter, Object data) {
        this.letter = letter;
        this.data = data;
    }

    public Character letter;

    public Object data;


    @Override
    public String toString() {
        return "Task{" +
                "letter=" + letter +
                ", data=" + data +
                '}';
    }
}
