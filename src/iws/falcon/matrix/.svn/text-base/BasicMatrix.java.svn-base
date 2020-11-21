/**
 * Copyright 2008 Institute of Web Science, Southeast University, PR China
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iws.falcon.matrix;

import no.uib.cipr.matrix.DenseMatrix;

/**
 * @author Wei Hu
 */
public class BasicMatrix extends DenseMatrix
{
    public BasicMatrix(int numRows, int numColumns)
    {
        super(numRows, numColumns);
    }

    public BasicMatrix(double matrix[][])
    {
        super(matrix);
    }

    public BasicMatrix(BasicMatrix matrix)
    {
        super(matrix);
    }

    public double[][] getMatrix()
    {
        double[][] value = new double[numRows][numColumns];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                value[i][j] = get(i, j);
            }
        }
        return value;
    }

    public void setMatrix(BasicMatrix matrix)
    {
        checkSize(matrix);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                set(i, j, matrix.get(i, j));
            }
        }
    }

    public void setMatrix(double matrix[][])
    {
        if (numRows != matrix.length || numColumns != matrix[0].length) {
            System.err.println("setMatrixError: Row or column size is inconsistent.");
            return;
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                set(i, j, matrix[i][j]);
            }
        }
    }

    public BasicMatrix cloneMatrix()
    {
        BasicMatrix matrix = new BasicMatrix(numRows, numColumns);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                matrix.set(i, j, get(i, j));
            }
        }
        return matrix;
    }

    public double sumEntriesInRow(int row)
    {
        double sum = 0;
        for (int j = 0; j < numColumns; j++) {
            sum += get(row, j);
        }
        return sum;
    }

    public double sumEntriesInColumn(int col)
    {
        double sum = 0;
        for (int i = 0; i < numRows; i++) {
            sum += get(i, col);
        }
        return sum;
    }

    public int countNonZeroEntriesInRow(int row)
    {
        int times = 0;
        for (int j = 0; j < numColumns; j++) {
            if (get(row, j) > 0) {
                times++;
            }
        }
        return times;
    }

    public int countNonZeroEntriesInColumn(int col)
    {
        int times = 0;
        for (int i = 0; i < numRows; i++) {
            if (get(i, col) > 0) {
                times++;
            }
        }
        return times;
    }

    public int countZeroEntriesInRow(int row)
    {
        int times = 0;
        for (int j = 0; j < numColumns; j++) {
            if (get(row, j) == 0) {
                times++;
            }
        }
        return times;
    }

    public int countZeroEntriesInColumn(int col)
    {
        int times = 0;
        for (int i = 0; i < numRows; i++) {
            if (get(i, col) == 0) {
                times++;
            }
        }
        return times;
    }

    public BasicMatrix copyAdd(BasicMatrix matrix)
    {
        checkSize(matrix);
        BasicMatrix value = new BasicMatrix(numRows, numColumns);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                double data = get(i, j) + matrix.get(i, j);
                if (data != 0) {
                    value.set(i, j, data);
                }
            }
        }
        return value;
    }

    public BasicMatrix multi(BasicMatrix matrix)
    {
        BasicMatrix value = new BasicMatrix(numRows, matrix.numColumns);
        mult(matrix, value);
        return value;
    }

    public BasicMatrix times(double data)
    {
        BasicMatrix value = new BasicMatrix(numRows, numColumns);
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                double temp = data * get(i, j);
                if (temp != 0) {
                    value.set(i, j, temp);
                }
            }
        }
        return value;
    }

    public BasicMatrix trans()
    {
        BasicMatrix value = new BasicMatrix(numColumns, numRows);
        transpose(value);
        return value;
    }

    public BasicMatrix cosine()
    {
        double temp[] = new double[numColumns];
        for (int j = 0; j < numColumns; j++) {
            temp[j] = sumEntriesSquareInColumn(j);
        }
        BasicMatrix matrix = new BasicMatrix(numColumns, numColumns);
        for (int m = 0; m < numColumns; m++) {
            for (int n = m + 1; n < numColumns; n++) {
                double value = 0;
                for (int k = 0; k < numRows; k++) {
                    value += get(k, m) * get(k, n);
                }
                if (value != 0) {
                    matrix.set(m, n, value / Math.sqrt(temp[m] * temp[n]));
                }
            }
        }
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j <= i; j++) {
                if (i == j) {
                    matrix.set(i, j, 1);
                } else {
                    matrix.set(i, j, matrix.get(j, i));
                }
            }
        }
        return matrix;
    }

    public double sumEntriesSquareInRow(int row)
    {
        double value = 0;
        for (int j = 0; j < numColumns; j++) {
            double temp = get(row, j);
            if (temp != 0) {
                value += temp * temp;
            }
        }
        return value;
    }

    public double sumEntriesSquareInColumn(int col)
    {
        double value = 0;
        for (int i = 0; i < numRows; i++) {
            double temp = get(i, col);
            if (temp != 0) {
                value += temp * temp;
            }
        }
        return value;
    }

    public double sumEntriesSquare()
    {
        double value = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                double temp = get(i, j);
                if (temp != 0) {
                    value += temp * temp;
                }
            }
        }
        return value;
    }

    @Override
    public double max()
    {
        double max = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                double value = get(i, j);
                if (value > max) {
                    max = value;
                }
            }
        }
        return max;
    }

    public double maxEntriesInRow(int row)
    {
        double max = 0;
        for (int j = 0; j < numColumns; j++) {
            double value = get(row, j);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public double maxEntriesInColumn(int col)
    {
        double max = 0;
        for (int i = 0; i < numRows; i++) {
            double value = get(i, col);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public int indexOfMaxEntriesInRow(int row)
    {
        int col = -1;
        double max = 0;
        for (int j = 0; j < numColumns; j++) {
            double value = get(row, j);
            if (value > max) {
                max = value;
                col = j;
            }
        }
        return col;
    }

    public double indexOfMaxEntriesInColumn(int col)
    {
        int row = -1;
        double max = 0;
        for (int i = 0; i < numRows; i++) {
            double value = get(i, col);
            if (value > max) {
                max = value;
                row = i;
            }
        }
        return row;
    }

    public BasicMatrix norm()
    {
        double max = max();
        if (max == 0) {
            return this;
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                double temp = get(i, j) / max;
                set(i, j, temp);
            }
        }
        return this;
    }

    public double distance(BasicMatrix matrix)
    {
        checkSize(matrix);
        double k1 = Math.sqrt(sumEntriesSquare());
        double k2 = Math.sqrt(matrix.sumEntriesSquare());
        double value = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                double temp = get(i, j) / k1 - matrix.get(i, j) / k2;
                if (temp != 0) {
                    value += temp * temp;
                }
            }
        }
        return Math.sqrt(value);
    }

    public BasicMatrix setZero(double threshold)
    {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                double value = get(i, j);
                if (value <= threshold) {
                    set(i, j, 0);
                }
            }
        }
        return this;
    }

    public void show()
    {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
