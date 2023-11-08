import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestCells {
    public static void main(String[] args) {
        Cells cells = new Cells(5, 5,
                new Point(1,1),
                new Point(1,4),
                new Point(5,3),
                new Point(2,5),
                new Point(4,4));

        System.out.println(cells.toString());
    }

}
