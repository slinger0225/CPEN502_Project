import NeuralNet.NeuralNet;

import java.util.Scanner;


public class Main {
    public static final double LOSS = 0.05;

    public static void main(String[] args) {
        //        Bipolar training set
//        double[][] xorTraining = {{-1, -1}, {-1, +1}, {+1, -1}, {+1, +1}};
//        double[] xorTarget = {-1, +1, +1, -1};
        //        Binary training set
        double[][] xorTraining = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        double[] xorTarget = {0, 1, 1, 0};
        NeuralNet nn = new NeuralNet(2, //numInput
                4, //numHidden
                0.2, //rho, learning rate
                0.0, //alpha, momentum term
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
            int epoch = 0;
            // train for each epoch
            nn.initializeWeights();
            do {
                // train for each data
                for (int j = 0; j < xorTraining.length; j++) {
                    nn.train(xorTraining[j], xorTarget[j]);
                }
                //Calculate total error
                totalError = 0;
                for (int j = 0; j < xorTraining.length; j++) {
                    output = nn.outputFor(xorTraining[j]);
                    totalError += 0.5 * Math.pow((xorTarget[j] - output), 2);
                }
                epoch++;
                System.out.println("Error at epoch " + epoch + " = " + totalError);
            } while (totalError > LOSS);
            System.out.println("Target error reached at epochs " + epoch + ". \n");
            epochSum += epoch;
        }

        System.out.println("Average convergence epochs number = " + epochSum / trials);

    }
}