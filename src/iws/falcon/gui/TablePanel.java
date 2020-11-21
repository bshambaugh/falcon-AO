/*
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
package iws.falcon.gui;

import iws.falcon.output.Alignment;
import iws.falcon.output.AlignmentSet;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @author Wei Hu
 */
public class TablePanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JTable alignTable = null;
    private AlignmentSet alignmentSet = null;
    private final int tableWidth = 900;
    private final String col0 = "ID";
    private final String col1 = "Ontology1";
    private final String col2 = "Ontology2";
    private final String col3 = "Similarity";
    private final String col4 = "Relation";
    private TableModel dataModel = null;

    public TablePanel()
    {
        super();
        init();
    }

    public TablePanel(AlignmentSet alignments)
    {
        super();
        alignmentSet = alignments;
        init();
    }

    public void init()
    {
        final JPanel tablePanel = new JPanel();
        setLayout(new BorderLayout());
        tablePanel.setLayout(new BorderLayout());
        add(tablePanel);

        dataModel = new AbstractTableModel()
        {

            private static final long serialVersionUID = 1L;

            public int getColumnCount()
            {
                return 5;
            }

            public int getRowCount()
            {
                if (alignmentSet == null) {
                    return 0;
                } else {
                    return alignmentSet.size();
                }
            }

            public Object getValueAt(int row, int col)
            {
                if (alignmentSet != null) {
                    if (col == 0) {
                        return new Integer(row);
                    } else if (col == 1) {
                        Alignment align = alignmentSet.getAlignment(row);
                        return align.filter(align.getEntity1().toString());
                    } else if (col == 2) {
                        Alignment align = alignmentSet.getAlignment(row);
                        return align.filter(align.getEntity2().toString());
                    } else if (col == 3) {
                        Alignment align = alignmentSet.getAlignment(row);
                        return new Double(align.getSimilarity());
                    } else {
                        Alignment align = alignmentSet.getAlignment(row);
                        return align.getRelation();
                    }
                } else {
                    return "";
                }
            }

            @Override
            public String getColumnName(int index)
            {
                if (index == 0) {
                    return col0;
                } else if (index == 1) {
                    return col1;
                } else if (index == 2) {
                    return col2;
                } else if (index == 3) {
                    return col3;
                } else if (index == 4) {
                    return col4;
                } else {
                    return "";
                }
            }
        };

        alignTable = new JTable(dataModel);
        alignTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        alignTable.setBounds(1, 1, 800, 600);

        setTableColumnWidth();

        final JScrollPane scrollPane = new JScrollPane(alignTable);
        tablePanel.add(scrollPane);
    }

    public void setAlignment(AlignmentSet alignments)
    {
        alignmentSet = alignments;
        alignTable.tableChanged(null);

        setTableColumnWidth();
    }

    private void setTableColumnWidth()
    {
        TableColumn index = alignTable.getColumn(col0);
        TableColumn onto1 = alignTable.getColumn(col1);
        TableColumn onto2 = alignTable.getColumn(col2);
        TableColumn sim = alignTable.getColumn(col3);
        TableColumn rel = alignTable.getColumn(col4);

        index.setPreferredWidth(tableWidth / 20);
        onto1.setPreferredWidth(tableWidth / 3);
        onto2.setPreferredWidth(tableWidth / 3);
        sim.setPreferredWidth(tableWidth / 10);
        rel.setPreferredWidth(tableWidth / 10);
    }

    public void repaintTable()
    {
        alignTable.repaint();
    }
}
