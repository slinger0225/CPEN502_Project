package NeuralNet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Tools.Matrix;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class NeuralNetTest {
    @Test
    @DisplayName("Output for")
    void outputFor() {
        NeuralNet nn = new NeuralNet(2, //numInput
                2, //numHidden
                0.2, //rho, learning rate
                0.0, //alpha, momentum term
                false //false for binary, true for bipolar
        );
        nn.zeroWeights();
        //Specify weights
        double[][] weightsIHArray = {{0, 0}, {0, 0}};
        Matrix weightsIH = Matrix.initializeWith2dArray(weightsIHArray);
        nn.setWeightIH(weightsIH);

        double[][] weightsHOArray = {{0}, {0}};
        Matrix weightsHO = Matrix.initializeWith2dArray(weightsHOArray);
        nn.setWeightHO(weightsHO);

        //input data
        double[] input = {1, 1};
        assertEquals(0.5, nn.outputFor(input));
    }

    @Test
    @DisplayName("Save")
    void save() {
        NeuralNet nn = new NeuralNet(2, //numInput
                4, //numHidden
                0.2, //rho, learning rate
                0.0, //alpha, momentum term
                false //false for binary, true for bipolar
        );
        nn.initializeWeights();
        File file = new File("outputs/neuralnet-test.json");
        boolean result;
        try {
            nn.save(file);
            result = true;
        } catch (IOException e) {
            result = false;
        }
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Load")
    void load() {
        NeuralNet nn = new NeuralNet(2, //numInput
                4, //numHidden
                0.2, //rho, learning rate
                0.0, //alpha, momentum term
                false //false for binary, true for bipolar
        );
        nn.initializeWeights();
        boolean result;
        NeuralNet loadNN = null;
        try {
            loadNN = nn.load("outputs/neuralnet-test.json");
            result = true;
        } catch (IOException e) {
            result = false;
        }

        assertEquals(true, result);
        assertEquals(loadNN.getWeightIH(), nn.getWeightIH());
        assertEquals(loadNN.getWeightHO(), nn.getWeightHO());
    }

    @Test
    @DisplayName("Load with exception-NoFileException")
    void loadNoFileException() {
        NeuralNet nn = new NeuralNet(2, //numInput
                4, //numHidden
                0.2, //rho, learning rate
                0.0, //alpha, momentum term
                false //false for binary, true for bipolar
        );
        nn.initializeWeights();
        boolean result;
        try {
            nn.load("outputs/random.json");
            result = true;
        } catch (IOException e) {
            result = false;
            assertTrue(e.getMessage().contains("No such file or directory"));
        }
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Load with exception-StructureNotMatch")
    void loadStructureNotMatchException() {
        NeuralNet nn = new NeuralNet(2, //numInput
                6, //numHidden
                0.2, //rho, learning rate
                0.0, //alpha, momentum term
                false //false for binary, true for bipolar
        );
        nn.initializeWeights();
        boolean result;
        try {
            nn.load("outputs/neuralnet-test.json");
            result = true;
        } catch (IOException e) {
            result = false;
            assertTrue(e.getMessage().contains("Neural net structure does not match")); // nn.numHidden = 6, not consistent with loadNN.numHidden = 4
        }
        assertEquals(false, result);
    }
}