import Model.NeuralNet;
import Model.LUT;
import Interface.LUTInterface;
import Interface.NeuralNetInterface;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;


public class LUTNeuralNetRunner {
    public static final double LOSS = 0.05;


    public static void main(String[] args) throws PythonExecutionException, IOException {
        NeuralNet nn = new NeuralNet(5, //numInput
                10, //numHidden
                0.01, //rho, learning rate
                0.9, //alpha, momentum term
                true //false for binary, true for bipolar
        );

        //prompt the users to input number of trails
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the number of trials you want to run: ");
        int trials;
        try {
            trials = reader.nextInt();
        } catch (Exception e) {
            System.out.println("Please input one integer to indicate the number of trials!");
            return;
        }
        reader.close();

        //load LUT
        LUT robotLUT = new LUT(5, 5, 5, 5, 5);
        robotLUT.load("outputs/robotRunnerLUT.data/robotRunnerLUT_offpolicy.txt");
        robotLUT.normalize();

        // train for each trial
        int epochSum = 0;
        for (int i = 0; i < trials; i++) {
            System.out.println("-------------Trial " + (i + 1) + "-------------");
            double totalLoss;
            double output;
            List<Integer> epochs = new ArrayList<>();
            List<Double> losses = new ArrayList<>();
            int epoch = 0;
            // train for each epoch
            nn.initializeWeights();
            do {
                // train for each data
                totalLoss = 0;
                for (int a = 0; a < 5; a++) {
                    for (int b = 0; b < 5; b++) {
                        for (int c = 0; c < 5; c++) {
                            for (int d = 0; d < 5; d++) {
                                for (int e = 0; e < 5; e++) {
                                    double[] X = normalizeX(a,b,c,d,e);
                                    double singleLoss = nn.train(X, robotLUT.outputFor(X));
                                    totalLoss += Math.pow(singleLoss, 2);
                                }
                            }
                        }
                    }
                }
                epochs.add(epoch);
                losses.add(Math.pow(totalLoss/3125, 0.5));
                epoch++;
                System.out.println("Error at epoch " + epoch + " = " + Math.pow(totalLoss/3125, 0.5));
//            } while (totalError > LOSS);
            } while (epoch <= 1000);
            System.out.println("Target error reached at epochs " + epoch + ". \n");
            epochSum += epoch;

            BufferedWriter outputWriter = null;
            outputWriter = new BufferedWriter(new FileWriter("outputs/nnLUT.data/nn_0.01_0.9.txt"));
            outputWriter.write("pho");
            outputWriter.newLine();
            outputWriter.write("0.01");
            outputWriter.newLine();

            for(int j = 0; j < epochs.size(); j++){
                outputWriter.write(epochs.get(j)+":"+losses.get(j));
                outputWriter.newLine();
            }
            outputWriter.flush();
            outputWriter.close();

            System.out.println("=================================================================");
            System.out.println("Training finished!");
        }

        System.out.println("Average convergence epochs number = " + epochSum / trials);

    }
    private static double[] normalizeX(int energy1, int dist1, int energy2, int dist2, int action){
        Map<Integer, Double> normalizedE = new HashMap<Integer, Double>(){{
            put(0, 0.0);
            put(1, 0.2);
            put(2, 0.4);
            put(3, 0.6);
            put(4, 1.0);
        }};

        Map<Integer, Double> normalizedD = new HashMap<Integer, Double>(){{
            put(0, 0.05);
            put(1, 0.25);
            put(2, 0.5);
            put(3, 0.75);
            put(4, 1.0);
        }};

        Map<Integer, Double> normalizedA = new HashMap<Integer, Double>(){{
            put(0, 0.0);
            put(1, 0.25);
            put(2, 0.5);
            put(3, 0.75);
            put(4, 1.0);
        }};

        return new double[]{
                normalizedE.get(energy1),
                normalizedE.get(dist1),
                normalizedE.get(energy2),
                normalizedE.get(dist2),
                normalizedE.get(action)
        };
    }
}