package Model;

import Interface.LUTInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import robocode.RobocodeFileOutputStream;

import java.io.*;

public class LUT implements LUTInterface {
    private double[][][][][] lut;
    private int[][][][][] visits;
    private int numDim1Levels;
    private int numDim2Levels;
    private int numDim3Levels;
    private int numDim4Levels;
    private int numDim5Levels;

    public LUT(
            int numDim1Levels,
            int numDim2Levels,
            int numDim3Levels,
            int numDim4Levels,
            int numDim5Levels) {
        this.numDim1Levels = numDim1Levels;
        this.numDim2Levels = numDim2Levels;
        this.numDim3Levels = numDim3Levels;
        this.numDim4Levels = numDim4Levels;
        this.numDim5Levels = numDim5Levels;

        lut = new double[numDim1Levels][numDim2Levels][numDim3Levels][numDim4Levels][numDim5Levels];
        visits = new int[numDim1Levels][numDim2Levels][numDim3Levels][numDim4Levels][numDim5Levels];
        this.initialiseLUT();
    }

    // initialize lut
    @Override
    public void initialiseLUT() {
        for (int a = 0; a < numDim1Levels; a++) {
            for (int b = 0; b < numDim2Levels; b++) {
                for (int c = 0; c < numDim3Levels; c++) {
                    for (int d = 0; d < numDim4Levels; d++) {
                        for (int e = 0; e < numDim5Levels; e++) {
                            lut[a][b][c][d][e] = Math.random();
                            visits[a][b][c][d][e] = 0;
                        }
                    }
                }
            }
        }
    }

    @Override
    public int indexFor(double[] X) {
        return 0;
    }

    public int visits(double[] x) throws ArrayIndexOutOfBoundsException {
        if (x.length != 5) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            int a = (int) x[0];
            int b = (int) x[1];
            int c = (int) x[2];
            int d = (int) x[3];
            int e = (int) x[4];
            return visits[a][b][c][d][e];
        }
    }

    public void print() {
        for (int a = 0; a < numDim1Levels; a++) {
            for (int b = 0; b < numDim2Levels; b++) {
                for (int c = 0; c < numDim3Levels; c++) {
                    for (int d = 0; d < numDim4Levels; d++) {
                        for (int e = 0; e < numDim5Levels; e++) {
                            System.out.printf("+++ {%d, %d, %d, %d, %d} = %2.3f visits: %d\n",
                                    a, b, c, d, e,
                                    lut[a][b][c][d][e],
                                    visits[a][b][c][d][e]
                            );
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        String output = "";
        for (int a = 0; a < numDim1Levels; a++) {
            for (int b = 0; b < numDim2Levels; b++) {
                for (int c = 0; c < numDim3Levels; c++) {
                    for (int d = 0; d < numDim4Levels; d++) {
                        for (int e = 0; e < numDim5Levels; e++) {
                            output += String.format("+++ {%d, %d, %d, %d, %d} = %2.3f visits: %d\n",
                                    a, b, c, d, e,
                                    lut[a][b][c][d][e],
                                    visits[a][b][c][d][e]
                            );
                        }
                    }
                }
            }
        }
        return output;
    }


    @Override
    public double outputFor(double[] x) throws ArrayIndexOutOfBoundsException {
        if (x.length != 5)
            throw new ArrayIndexOutOfBoundsException();
        else {
            int a = (int) x[0];
            int b = (int) x[1];
            int c = (int) x[2];
            int d = (int) x[3];
            int e = (int) x[4];
            return lut[a][b][c][d][e];
        }
    }

    @Override
    public double train(double[] x, double target) throws ArrayIndexOutOfBoundsException {
        if (x.length != 5)
            throw new ArrayIndexOutOfBoundsException();
        else {
            int a = (int) x[0];
            int b = (int) x[1];
            int c = (int) x[2];
            int d = (int) x[3];
            int e = (int) x[4];
            lut[a][b][c][d][e] = target;
            visits[a][b][c][d][e]++;
        }
        return 1;
    }

    // This version saves the LUT in a format useful for training an NN
    @Override
    public void save(File fileName) {
        System.out.println("start saving");

        PrintStream saveFile = null;

        try {
            saveFile = new PrintStream(new RobocodeFileOutputStream(fileName));
        } catch (IOException e) {
            System.out.println("*** Could not create output stream for NN save file.");
        }

        // First line is the number of rows of data
        assert saveFile != null;
        saveFile.println(numDim1Levels * numDim2Levels * numDim3Levels * numDim4Levels * numDim5Levels);

        // Second line is the number of dimensions per row
        saveFile.println(5);

        System.out.println("start writing");

        for (int a = 0; a < numDim1Levels; a++) {
            for (int b = 0; b < numDim2Levels; b++) {
                for (int c = 0; c < numDim3Levels; c++) {
                    for (int d = 0; d < numDim4Levels; d++) {
                        for (int e = 0; e < numDim5Levels; e++) {
                            // e, d, e2, d2, a, q, visits
                            String row = String.format("%d,%d,%d,%d,%d,%2.5f,%d",
                                    a, b, c, d, e,
                                    lut[a][b][c][d][e],
                                    visits[a][b][c][d][e]
                            );
                            saveFile.println(row);
                        }
                    }
                }
            }
        }
        saveFile.close();
        System.out.println("finish saving");
    }

    @Override
    public void load(String fileName) throws IOException {

        FileInputStream inputFile = new FileInputStream(fileName);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputFile));
        int numExpectedRows = numDim1Levels * numDim2Levels * numDim3Levels * numDim4Levels * numDim5Levels;

        // Check the number of rows is compatible
        int numRows = Integer.valueOf(inputReader.readLine());
        // Check the number of dimensions is compatible
        int numDimensions = Integer.valueOf(inputReader.readLine());

        if (numRows != numExpectedRows || numDimensions != 5) {
            System.out.printf(
                    "*** rows/dimensions expected is %s/%s but %s/%s encountered\n",
                    numExpectedRows, 5, numRows, numDimensions
            );
            inputReader.close();
            throw new IOException();
        }

        for (int a = 0; a < numDim1Levels; a++) {
            for (int b = 0; b < numDim2Levels; b++) {
                for (int c = 0; c < numDim3Levels; c++) {
                    for (int d = 0; d < numDim4Levels; d++) {
                        for (int e = 0; e < numDim5Levels; e++) {

                            // Read line formatted like this: <e,d,e2,d2,a,q,visits\n>
                            String line = inputReader.readLine();
                            String tokens[] = line.split(",");
                            int dim1 = Integer.parseInt(tokens[0]);
                            int dim2 = Integer.parseInt(tokens[1]);
                            int dim3 = Integer.parseInt(tokens[2]);
                            int dim4 = Integer.parseInt(tokens[3]);
                            int dim5 = Integer.parseInt(tokens[4]); // actions
                            double q = Double.parseDouble(tokens[5]);
                            int v = Integer.parseInt(tokens[6]);
                            lut[a][b][c][d][e] = q;
                            visits[a][b][c][d][e] = v;
                        }
                    }
                }
            }
        }
        inputReader.close();
    }

    public double[][][][][] getLut() {
        return lut;
    }

    public void setLut(double[][][][][] lut) {
        this.lut = lut;
    }

    public int[][][][][] getVisits() {
        return visits;
    }

    public void setVisits(int[][][][][] visits) {
        this.visits = visits;
    }

    public int getNumDim1Levels() {
        return numDim1Levels;
    }

    public void setNumDim1Levels(int numDim1Levels) {
        this.numDim1Levels = numDim1Levels;
    }

    public int getNumDim2Levels() {
        return numDim2Levels;
    }

    public void setNumDim2Levels(int numDim2Levels) {
        this.numDim2Levels = numDim2Levels;
    }

    public int getNumDim3Levels() {
        return numDim3Levels;
    }

    public void setNumDim3Levels(int numDim3Levels) {
        this.numDim3Levels = numDim3Levels;
    }

    public int getNumDim4Levels() {
        return numDim4Levels;
    }

    public void setNumDim4Levels(int numDim4Levels) {
        this.numDim4Levels = numDim4Levels;
    }

    public int getNumDim5Levels() {
        return numDim5Levels;
    }

    public void setNumDim5Levels(int numDim5Levels) {
        this.numDim5Levels = numDim5Levels;
    }


}
