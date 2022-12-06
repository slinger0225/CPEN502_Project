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


public class LUTNeuralNetRunner {
    public static final double LOSS = 0.05;


    public static void main(String[] args) throws PythonExecutionException, IOException {
        NeuralNet nn = new NeuralNet(5, //numInput
                20, //numHidden
                0.2, //rho, learning rate
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
                for (int a = 0; a < 5; a++) {
                    for (int b = 0; b < 5; b++) {
                        for (int c = 0; c < 5; c++) {
                            for (int d = 0; d < 5; d++) {
                                for (int e = 0; e < 5; e++) {
                                    double[] X = new double[]{a, b, c, d, e};
                                    double error = nn.train(X, NeuralNetInterface.sigmoid(robotLUT.outputFor(X)));
                                    totalError += 0.5 * Math.pow(error, 2);
                                }
                            }
                        }
                    }
                }
                epochs.add(epoch);
                errors.add(totalError);
                epoch++;
                System.out.println("Error at epoch " + epoch + " = " + totalError);
//            } while (totalError > LOSS);
            } while (epoch <= 100000);
            System.out.println("Target error reached at epochs " + epoch + ". \n");
            epochSum += epoch;
            if (trials == 1) {
                Plot plt = Plot.create();
                plt.plot().add(epochs, errors);
                plt.xlabel("Number of epochs");
                plt.ylabel("Error");
                plt.title("Total Error");
                plt.show();
            }
        }

        System.out.println("Average convergence epochs number = " + epochSum / trials);

    }
}