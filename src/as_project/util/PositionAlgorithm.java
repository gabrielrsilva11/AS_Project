package as_project.util;

import java.util.Random;
import static as_project.util.Constants.*;

/**
* Position Algorithm - Allows to fetch random numbers corresponding to a vertical position and horizontal positions
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class PositionAlgorithm {
    /**
     * Gets a random integer with max of ROWS
     * @return random integer between 0 and ROWS
     */
    public static int getVerticalPosition() {
        return new Random().nextInt(ROWS);
    }
    /**
     * Returns a random integer corresponding how many steps a farmer should take
     * @param numSteps max number of steps each farmer is allowed
     * @return random integer whose value depends on numSteps (either 1 or 2)
     */
    public static int getHorizontalPosition(int numSteps) {
        return new Random().nextInt(numSteps) + 1;
    }
}
