package Tools;

public class Matrix {
    public double[][] m;
    private int rows;
    private int cols;

    //if not specify range [min, max], the default initialization is all zeros
    public Matrix(int rows, int cols) {
        m = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = 0;
            }
        }
    }

    public Matrix(int rows, int cols, double min, double max) {
        m = new double[rows][cols];
        double range = max - min;
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                m[i][j] = Math.random() * range + min;
            }
        }
    }

    private Matrix() {

    }

    /**
     * Return a bipolar sigmoid of the input X
     *
     * @param x The input
     * @return f(x) = 2 / (1+e(-x)) - 1
     */
    public double bipolarSigmoid(double x) {
        return 2 / (1 + Math.exp(-1 * x)) - 1;
    }

    /**
     * Return a binary sigmoid of the input X
     *
     * @param x The input
     * @return f(x) =  1 / (1+e(-x))
     */
    public double binarySigmoid(double x) {
        return 1 / (1 + Math.exp(-1 * x));
    }

    public void bipolarSigmoid() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.m[i][j] = bipolarSigmoid(this.m[i][j]);
            }
        }
    }

    public void binarySigmoid() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.m[i][j] = binarySigmoid(this.m[i][j]);
            }
        }
    }


    public void add(double c) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.m[i][j] += c;
            }
        }
    }

    public void add(Matrix mtx) {
        if (cols != mtx.getCols() || rows != mtx.getRows()) {
            System.out.println("Shape Mismatch!");
            return;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.m[i][j] += mtx.m[i][j];
            }
        }
    }

    public static Matrix subtract(Matrix a, Matrix b) {
        Matrix res = new Matrix(a.getRows(), a.getCols());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                res.m[i][j] = a.m[i][j] - b.m[i][j];
            }
        }
        return res;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix res = new Matrix(a.getRows(), b.getCols());
        for (int i = 0; i < res.getRows(); i++) {
            for (int j = 0; j < res.getCols(); j++) {
                double ele = 0;
                for (int k = 0; k < a.getCols(); k++) {
                    ele += a.m[i][k] * b.m[k][j];
                }
                res.m[i][j] = ele;
            }
        }
        return res;
    }

    public static Matrix parseArray(double[] array) {
        Matrix res = new Matrix(1, array.length);
        for (int i = 0; i < res.getCols(); i++) {
            res.m[0][i] = array[i];
        }
        return res;
    }

    public static Matrix initializeWith2dArray(double[][] dArray) {
        Matrix res = new Matrix(dArray.length, dArray[0].length);
        for (int i = 0; i < res.getRows(); i++) {
            for (int j = 0; j < res.getCols(); j++) {
                res.m[i][j] = dArray[i][j];
            }

        }
        return res;
    }

    public static double[] toArray(Matrix mtx) {
        double[] res = new double[mtx.getCols()];
        for (int i = 0; i < mtx.getCols(); i++) {
            res[i] = mtx.m[0][i];
        }
        return res;
    }

    public static void print(Matrix p) {
        for (int i = 0; i < p.getRows(); i++) {
            for (int j = 0; j < p.getCols(); j++) {
                System.out.print(p.m[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

}

