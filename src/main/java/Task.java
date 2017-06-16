import java.awt.image.BufferedImage;

/**
 * Created by Petr on 12.06.2017.
 */
public class Task {

    /**
     * Constructor with parameters letter ("a"), number defining the order of the created image.
     * @param letter
     * @param number
     * @param data
     */
    public Task(Character letter, Integer number, BufferedImage data) {
        this.letter = letter;
        this.number = number;
        this.data = data;
    }

    public Character letter;

    public Integer number;

    public BufferedImage data;


    @Override
    public String toString() {
        return "Task{" +
                "letter=" + letter +
                "number=" + number+
                ", data=" + data +
                '}';
    }
}
