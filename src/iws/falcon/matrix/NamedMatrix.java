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

import java.util.ArrayList;

/**
 * @author Wei Hu
 */
public class NamedMatrix extends BasicMatrix
{
    private ArrayList rowList = null,  columnList = null;

    public NamedMatrix(int numRows, int numColumns)
    {
        super(numRows, numColumns);
    }

    public NamedMatrix(ArrayList rlist, ArrayList clist)
    {
        super(rlist.size(), clist.size());
        rowList = rlist;
        columnList = clist;
    }

    public NamedMatrix(NamedMatrix matrix)
    {
        super(matrix.numRows, matrix.numColumns);
        rowList = matrix.getRowList();
        columnList = matrix.getColList();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                set(i, j, matrix.get(i, j));
            }
        }
    }

    public void setRowList(ArrayList rlist)
    {
        rowList = rlist;
    }

    public void setColumnList(ArrayList clist)
    {
        columnList = clist;
    }

    public ArrayList getRowList()
    {
        return rowList;
    }

    public ArrayList getColList()
    {
        return columnList;
    }

    public double get(Object row, Object col)
    {
        int i = -1;
        for (int k = 0, n = numRows; k < n; k++) {
            if (rowList.get(k).toString().equals(row.toString())) {
                i = k;
            }
        }
        int j = -1;
        for (int k = 0, n = numColumns; k < n; k++) {
            if (columnList.get(k).toString().equals(col.toString())) {
                j = k;
            }
        }
        if (i < 0 || j < 0) {
            System.err.println("getError: Cannot find such row or column.");
            return -1;
        } else {
            return get(i, j);
        }
    }

    public void set(Object row, Object col, double value)
    {
        int i = -1;
        for (int k = 0, n = numRows; k < n; k++) {
            if (rowList.get(k).toString().equals(row.toString())) {
                i = k;
            }
        }
        int j = -1;
        for (int k = 0, n = numColumns; k < n; k++) {
            if (columnList.get(k).toString().equals(col.toString())) {
                j = k;
            }
        }
        if (i < 0 || j < 0) {
            System.err.println("setError: Cannot find such row or column.");
            return;
        } else {
            set(i, j, value);
        }
    }

    public Object getRow(int row)
    {
        if (row < 0 || row >= numRows) {
            System.err.println("getRowError: Index is out of bound.");
            return null;
        } else {
            return rowList.get(row);
        }
    }

    public Object getColumn(int col)
    {
        if (col < 0 || col >= numColumns) {
            System.err.println("getColumnError: Index is out of bound.");
            return null;
        } else {
            return columnList.get(col);
        }
    }

    public int getRowIndex(Object row)
    {
        int index = -1;
        for (int k = 0, n = numRows; k < n; k++) {
            if (rowList.get(k).toString().equals(row.toString())) {
                index = k;
            }
        }
        return index;
    }

    public int getColumnIndex(Object col)
    {
        int index = -1;
        for (int k = 0, n = numColumns; k < n; k++) {
            if (columnList.get(k).toString().equals(col.toString())) {
                index = k;
            }
        }
        return index;
    }

    public void replaceRow(Object oldRow, Object newRow)
    {
        int i = getRowIndex(oldRow);
        int j = getRowIndex(newRow);
        if (i == -1 || j == -1) {
            return;
        }
        for (int k = 0; k < numColumns; k++) {
            set(i, k, get(j, k));
        }
    }

    public void replaceCol(Object oldCol, Object newCol)
    {
        int i = getColumnIndex(oldCol);
        int j = getColumnIndex(newCol);
        if (i == -1 || j == -1) {
            return;
        }
        for (int k = 0; k < numRows; k++) {
            set(k, i, get(k, j));
        }
    }

    public void show()
    {
        for (int i = 0; i < numRows; i++) {
            System.out.println("rowList[" + i + "]:" + rowList.get(i));
        }
        for (int j = 0; j < numColumns; j++) {
            System.out.println("colList[" + j + "]:" + columnList.get(j));
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }
}
