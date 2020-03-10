/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.util;

import as_project.models.FarmerStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author manuelcura
 */
public class SelectionAlgorithm {
    
    private int numberOfFarmers;
    private int numberOfSteps;
    private int pathBoxColumn;
    private int boxRow;
    private Random random;
    private Map<String, int[]> positions;
    private int[] position;
    
    public SelectionAlgorithm(int numberOfFarmers, int numberOfSteps) {
        this.numberOfFarmers = numberOfFarmers;
        this.numberOfSteps = numberOfSteps;
        this.boxRow = 5;
        this.pathBoxColumn = 10;
        this.positions = new HashMap();
        random = new Random();
    }
    
    public SelectionAlgorithm(int numberOfFarmers, int numberOfSteps, int boxRow, int pathBoxColumn) {
        this.numberOfFarmers = numberOfFarmers;
        this.numberOfSteps = numberOfSteps;
        this.boxRow = boxRow;
        this.pathBoxColumn = pathBoxColumn;
        this.positions = new HashMap();
        random = new Random();
    }
    
    public int[] getNextPosition(String farmerId, FarmerStatus status) {
        switch(status){
            case AT_STORE_HOUSE:
            case AT_STANDING_AREA:
            case AT_GRANARY:
                positions.put(farmerId, generateNextPosition(false, 0));
                break;
            case AT_PATH:
                positions.put(farmerId, generateNextPosition(true, positions.get(farmerId)[1]));
                break;
        }
        return positions.get(farmerId);
    }
    
    public int[] getPosition(String farmerId) {
        return positions.get(farmerId);
    }
    
    public int generateRandomValue(int maxValue) {
        return random.nextInt(maxValue);
    }
    
    public int generateRandomPathValue(int minValue) {
        return random.nextInt(numberOfSteps + 1) + 1 + minValue;
    }

    public int[] generateNextPosition(boolean isPath, int lastPosition) {
        position = new int[2];
        position[0] = generateRandomValue(boxRow);
        if(isPath) {
            position[1] = generateRandomPathValue(lastPosition);
        } else {
            position[1] = 0;
        }
        while(positions.containsValue(position)) {
            if(isPath) {
                position[1] = generateRandomValue(pathBoxColumn);
            } else {
                position[1] = 0;      
            }
        }
        return position;
    }
    
}
