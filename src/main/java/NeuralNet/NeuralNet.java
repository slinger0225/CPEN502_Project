package NeuralNet;

import Interface.NeuralNetInterface;
import Tools.Matrix;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NeuralNet implements NeuralNetInterface {
    private final double bias = 1.0; // The input for each neurons bias weight
    private final int argNumOutputs = 1;
    private int argNumInputs;
    private int argNumHidden;
    private double argLearningRate;
    private double argMomentumTerm;
    private double argA;
    private double argB;
    private boolean bipolar;
    private Matrix weightIH;
    private Matrix weightHO;

    /**
     * Constructor. (Cannot be declared in an interface, but your implementation will need one)
     *
     * @param argNumInputs    The number of inputs in your input vector
     * @param argNumHidden    The number of hidden neurons in your hidden layer. Only a single hidden layer is supported
     * @param argLearningRate The learning rate coefficient
     * @param argMomentumTerm The momentum coefficient
     *                        <p>
     *                        public abstract NeuralNet (
     *                        int argNumInputs,
     *                        int argNumHidden,
     *                        double argLearningRate,
     *                        double argMomentumTerm,
     *                        double argA,
     *                        double argB );
     */
    public NeuralNet(int argNumInputs, int argNumHidden, double argLearningRate, double argMomentumTerm, boolean bipolar) {
        this.argNumInputs = argNumInputs;
        this.argNumHidden = argNumHidden;
        this.argLearningRate = argLearningRate;
        this.argMomentumTerm = argMomentumTerm;
        this.bipolar = bipolar;
    }

    private NeuralNet() {
    }

    /**
     * Initialize the weights to random values.
     * For say 2 inputs, the input vector is [0] & [1]. We add [2] for the bias.
     * Like wise for hidden units. For say 2 hidden units which are stored in an array.
     * [0] & [1] are the hidden & [2] the bias.
     * We also initialise the last weight change arrays. This is to implement the alpha term.
     */
    @Override
    public void initializeWeights() {
        this.weightIH = new Matrix(argNumInputs, argNumHidden, -0.5, 0.5);
        this.weightHO = new Matrix(argNumHidden, argNumOutputs, -0.5, 0.5);
    }

    /**
     * Initialize the weights to 0.
     */
    @Override
    public void zeroWeights() {
        this.weightIH = new Matrix(argNumInputs, argNumHidden);
        this.weightHO = new Matrix(argNumHidden, argNumOutputs);
    }

    /**
     * @param X The input vector. An array of doubles.
     * @return The value returned by th LUT or NN for this input vector
     */
    @Override
    public double outputFor(double[] X) {
        // Input to hidden
        Matrix dataI = Matrix.parseArray(X);
        Matrix dataH = Matrix.multiply(dataI, weightIH);

        // Activation function input -> hidden
        if (this.bipolar) {
            dataH.bipolarSigmoid();
        } else {
            dataH.binarySigmoid();
        }

        // Hidden to output
        Matrix dataO = Matrix.multiply(dataH, weightHO);
        // Activation function hidden -> output
        if (this.bipolar) {
            dataO.bipolarSigmoid();
        } else {
            dataO.binarySigmoid();
        }
        double output = Matrix.toArray(dataO)[0];

        return output;
    }

    /**
     * This method will tell the NN or the LUT the output
     * value that should be mapped to the given input vector. I.e.
     * the desired correct output value for an input.
     *
     * @param X        The input vector
     * @param argValue The new value to learn
     * @return The error in the output for that input vector
     */
    @Override
    public double train(double[] X, double argValue) {
        double error = 0;
        return error;
    }

    /**
     * A method to write either a LUT or weights of an neural net to a file.
     *
     * @param argFile of type File.
     */
    @Override
    public void save(File argFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Serialize Java object info JSON file.
        mapper.writeValue(argFile, this);
    }

    /**
     * Loads the LUT or neural net weights from file. The load must of course
     * have knowledge of how the data was written out by the save method.
     * You should raise an error in the case that an attempt is being
     * made to load data into an LUT or neural net whose structure does not match
     * the data in the file. (e.g. wrong number of hidden neurons).
     *
     * @throws IOException
     */
    @Override
    public NeuralNet load(String argFileName) throws IOException {
        File file = new File(argFileName);
        ObjectMapper mapper = new ObjectMapper();
        // Deserialize JSON file into Java object.
        NeuralNet loadNN = mapper.readValue(file, NeuralNet.class);
        if (loadNN.getArgNumHidden() != this.argNumHidden || loadNN.getArgNumInputs() != this.argNumInputs || loadNN.getArgLearningRate() != this.argLearningRate || loadNN.getArgMomentumTerm() != this.argMomentumTerm) {
            throw new IOException("Neural net structure does not match");
        }
        this.weightIH = loadNN.getWeightIH();
        this.weightHO = loadNN.getWeightHO();
        return loadNN;
    }

    public int getArgNumInputs() {
        return argNumInputs;
    }

    public void setArgNumInputs(int argNumInputs) {
        this.argNumInputs = argNumInputs;
    }

    public int getArgNumHidden() {
        return argNumHidden;
    }

    public void setArgNumHidden(int argNumHidden) {
        this.argNumHidden = argNumHidden;
    }

    public double getArgLearningRate() {
        return argLearningRate;
    }

    public void setArgLearningRate(double argLearningRate) {
        this.argLearningRate = argLearningRate;
    }

    public double getArgMomentumTerm() {
        return argMomentumTerm;
    }

    public void setArgMomentumTerm(double argMomentumTerm) {
        this.argMomentumTerm = argMomentumTerm;
    }

    public boolean isBipolar() {
        return bipolar;
    }

    public void setBipolar(boolean bipolar) {
        this.bipolar = bipolar;
    }

    public Matrix getWeightIH() {
        return weightIH;
    }

    public Matrix getWeightHO() {
        return weightHO;
    }

    public void setWeightIH(Matrix weights) {
        this.weightIH = weights;
    }

    public Matrix setWeightHO(Matrix weights) {
        return weightHO = weights;
    }

}
