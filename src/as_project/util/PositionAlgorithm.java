/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.util;

import java.util.Random;
import static as_project.util.Constants.*;

/**
 *
 *
 */
public class PositionAlgorithm {
    
    public static int getVerticalPosition() {
        return new Random().nextInt(ROWS);
    }
    
    public static int getHorizontalPosition(int numSteps, int previousPosition) {
        return new Random().nextInt(numSteps + 1) + 1 + previousPosition;
    }
}
