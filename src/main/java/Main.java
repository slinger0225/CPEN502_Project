import NeuralNet.NeuralNet;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static final double LOSS = 0.05;


    public static void main(String[] args) throws PythonExecutionException, IOException {
        //        Bipolar training set
//        double[][] xorTraining = {{-1, -1}, {-1, +1}, {+1, -1}, {+1, +1}};
//        double[] xorTarget = {-1, +1, +1, -1};
        //        Binary training set
        double[][] xorTraining = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[] xorTarget = {0, 1, 1, 0};
        NeuralNet nn = new NeuralNet(2, //numInput
                4, //numHidden
                0.2, //rho, learning rate
                0.9, //alpha, momentum term
                false //false for binary, true for bipolar
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


        // train for each trial
        int epochSum = 0;
        for (int i = 0; i < trials; i++) {
            System.out.println("-------------Trial " + (i + 1) + "-------------");
            double totalError = 1;
            double output;
            List<Integer> epochs = new ArrayList<>();
            List<Double> errors = new ArrayList<>();
            int epoch = 0;
            // train for each epoch
            nn.initializeWeights();
            do {
                // train for each data
                totalError = 0;
                for (int j = 0; j < xorTraining.length; j++) {
                    double e=nn.train(xorTraining[j], xorTarget[j]);
                    totalError += 0.5 * Math.pow(e, 2);
                }
                //Calculate total error
//                totalError = 0;
//                for (int j = 0; j < xorTraining.length; j++) {
//                    output = nn.outputFor(xorTraining[j]);
//                    totalError += 0.5 * Math.pow((xorTarget[j] - output), 2);
//                }
                epochs.add(epoch);
                errors.add(totalError);
                epoch++;
                System.out.println("Error at epoch " + epoch + " = " + totalError);
            } while (totalError > LOSS);
//            } while (epoch <= 300);
            System.out.println("Target error reached at epochs " + epoch + ". \n");
            epochSum += epoch;
            if (trials == 1) {
                Plot plt = Plot.create();
                plt.plot().add(epochs, errors);
                plt.legend().loc("upper right");
                plt.title("Total Error");
                plt.show();
            }
        }

        System.out.println("Average convergence epochs number = " + epochSum / trials);

    }
}