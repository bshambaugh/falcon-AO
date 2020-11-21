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
package iws.falcon.output;

import iws.falcon.matrix.NamedMatrix;
import iws.falcon.model.Node;

/**
 * @author Wei Hu
 */
public class AlignmentSelector
{
    public static AlignmentSet select(NamedMatrix temp, double threshold)
    {
        AlignmentSet as = new AlignmentSet();
        NamedMatrix matrix = new NamedMatrix(temp);
        int numRows = matrix.numRows(), numColumns = matrix.numColumns();
        if (numRows <= numColumns) {
            for (int i = 0; i < numRows; i++) {
                double max = 0;
                int row = -1, col = -1;
                for (int m = 0; m < numRows; m++) {
                    for (int n = 0; n < numColumns; n++) {
                        double value = matrix.get(m, n);
                        if (value > max) {
                            max = value;
                            row = m;
                            col = n;
                        }
                    }
                }
                if (max > threshold && row != -1 && col != -1) {
                    Node node1 = (Node) matrix.getRow(row);
                    Node node2 = (Node) matrix.getColumn(col);
                    as.addAlignment(new Alignment(node1, node2, max));
                    for (int n = 0; n < numColumns; n++) {
                        matrix.set(row, n, 0);
                    }
                    for (int m = 0; m < numRows; m++) {
                        matrix.set(m, col, 0);
                    }
                } else {
                    break;
                }
            }
        } else {
            for (int j = 0; j < numColumns; j++) {
                double max = 0;
                int row = -1, col = -1;
                for (int m = 0; m < numColumns; m++) {
                    for (int n = 0; n < numRows; n++) {
                        double value = matrix.get(n, m);
                        if (value > max) {
                            max = value;
                            row = n;
                            col = m;
                        }
                    }
                }
                if (max > threshold && row != -1 && col != -1) {
                    Node node1 = (Node) matrix.getRow(row);
                    Node node2 = (Node) matrix.getColumn(col);
                    as.addAlignment(new Alignment(node1, node2, max));
                    for (int m = 0; m < numColumns; m++) {
                        matrix.set(row, m, 0);
                    }
                    for (int n = 0; n < numRows; n++) {
                        matrix.set(n, col, 0);
                    }
                } else {
                    break;
                }
            }
        }
        return as;
    }
}
