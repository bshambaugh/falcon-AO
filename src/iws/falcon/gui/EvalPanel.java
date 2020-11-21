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
package iws.falcon.gui;

import iws.falcon.output.AlignmentReader2;
import iws.falcon.output.AlignmentSet;
import iws.falcon.output.Evaluator;
import iws.falcon.output.ResultData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 * @author Wei Hu
 */
public class EvalPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JComboBox comboBox = null;
    private JTextField textFieldFmeasure = null;
    private JTextField textFieldPrecision = null;
    private JTextField textFieldRecall = null;
    private JTextField textFieldCorrect = null;
    private JTextField textFieldExist = null;
    private JTextField textFieldFound = null;
    private JTextField textFieldOnto1 = null;
    private JTextField textFieldOnto2 = null;
    private AlignmentSet correctAlignments = null;
    private AlignmentSet errorAlignments = null;
    private AlignmentSet lostAlignments = null;

    public EvalPanel()
    {
        super();
        setPreferredSize(new Dimension(640, 480));
        setLayout(new BorderLayout());

        final TablePanel alignTable = new TablePanel(correctAlignments);
        alignTable.setPreferredSize(new Dimension(600, 200));

        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        final JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        inputPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        final JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(300, 60));
        panel.setPreferredSize(new Dimension(300, 60));
        final GridLayout gridLayout = new GridLayout();
        gridLayout.setRows(2);
        panel.setLayout(gridLayout);
        inputPanel.add(panel);

        final JPanel panel_1 = new JPanel();
        panel_1.setPreferredSize(new Dimension(0, 30));
        panel.add(panel_1);

        final JLabel label_1 = new JLabel();
        panel_1.add(label_1);
        label_1.setText("  Falcon's alignments: ");

        textFieldOnto1 = new JTextField();
        textFieldOnto1.setText("./temp/tempResult.rdf");
        textFieldOnto1.setPreferredSize(new Dimension(450, 22));
        panel_1.add(textFieldOnto1);

        final JButton browseButton1 = new JButton();
        browseButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setName("Open");
                fileChooser.setToolTipText("Please input an alignment file");
                int rVal = fileChooser.showOpenDialog(mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    textFieldOnto1.setText(fileChooser.getSelectedFile().toString());
                }
            }
        });
        browseButton1.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        panel_1.add(browseButton1);
        browseButton1.setText("  Browse  ");

        final JPanel panel_2 = new JPanel();
        panel.add(panel_2);

        final JLabel label_2 = new JLabel();
        panel_2.add(label_2);
        label_2.setText("Reference alignments: ");

        textFieldOnto2 = new JTextField();
        textFieldOnto2.setPreferredSize(new Dimension(450, 22));
        panel_2.add(textFieldOnto2);

        final JButton browseButton2 = new JButton();
        browseButton2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setName("Open");
                fileChooser.setToolTipText("Please input an alignment file");
                int rVal = fileChooser.showOpenDialog(mainPanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    textFieldOnto2.setText(fileChooser.getSelectedFile().toString());
                }
            }
        });
        browseButton2.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        panel_2.add(browseButton2);
        browseButton2.setText("  Browse  ");

        final JPanel panel_3 = new JPanel();
        final GridLayout gridLayout_1 = new GridLayout(1, 1);
        panel_3.setLayout(gridLayout_1);
        panel_3.setPreferredSize(new Dimension(100, 0));
        inputPanel.add(panel_3, BorderLayout.EAST);

        final JButton evaluateButton = new JButton();
        evaluateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s1 = textFieldOnto1.getText().trim();
                String s2 = textFieldOnto2.getText().trim();
                if (s1.length() > 0 && s2.length() > 0) {
                    Evaluator evaluater = new Evaluator();
                    AlignmentReader2 reader1 = new AlignmentReader2(
                            MainFrame.model1, MainFrame.model2, s1);
                    AlignmentReader2 reader2 = new AlignmentReader2(
                            MainFrame.model1, MainFrame.model2, s2);
                    // System.out.println(s1 + "\n" + s2);
                    ResultData result = evaluater.compare(reader1.read(),
                            reader2.read());
                    textFieldFound.setText(new Integer(result.getFound()).toString());
                    textFieldExist.setText(new Integer(result.getExist()).toString());
                    textFieldCorrect.setText(new Integer(result.getCorrect()).toString());
                    textFieldPrecision.setText(new Float(result.getPrecision()).toString());
                    textFieldRecall.setText(new Float(result.getRecall()).toString());
                    textFieldFmeasure.setText(new Float(result.getFmeasure()).toString());
                    correctAlignments = result.getCorrectAlignments();
                    errorAlignments = result.getErrorAlignments();
                    lostAlignments = result.getLostAlignments();
                    comboBox.setSelectedIndex(0);
                    alignTable.setAlignment(correctAlignments);
                    alignTable.repaintTable();
                    evaluateButton.setEnabled(false);
                }
            }
        });
        panel_3.add(evaluateButton);
        evaluateButton.setText("Evaluate!");

        final JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        mainPanel.add(resultPanel);

        final JPanel prfPanel = new JPanel();
        prfPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        final GridLayout gridLayout_2 = new GridLayout(2, 3);
        prfPanel.setLayout(gridLayout_2);
        resultPanel.add(prfPanel, BorderLayout.NORTH);

        final JPanel foundPanel = new JPanel();
        prfPanel.add(foundPanel);

        final JLabel label = new JLabel();
        foundPanel.add(label);
        label.setText("    Found: ");

        textFieldFound = new JTextField();
        textFieldFound.setEditable(false);
        textFieldFound.setPreferredSize(new Dimension(120, 22));
        foundPanel.add(textFieldFound);
        textFieldFound.setText("NaN");

        final JPanel existPanel = new JPanel();
        prfPanel.add(existPanel);

        final JLabel label_3 = new JLabel();
        existPanel.add(label_3);
        label_3.setText("Existing: ");

        textFieldExist = new JTextField();
        textFieldExist.setEditable(false);
        textFieldExist.setPreferredSize(new Dimension(120, 22));
        existPanel.add(textFieldExist);
        textFieldExist.setText("NaN");

        final JPanel correctPanel = new JPanel();
        prfPanel.add(correctPanel);

        final JLabel label_4 = new JLabel();
        correctPanel.add(label_4);
        label_4.setText("     Correct: ");

        textFieldCorrect = new JTextField();
        textFieldCorrect.setEditable(false);
        textFieldCorrect.setPreferredSize(new Dimension(120, 22));
        correctPanel.add(textFieldCorrect);
        textFieldCorrect.setText("NaN");

        final JPanel precisionPanel = new JPanel();
        prfPanel.add(precisionPanel);

        final JLabel label_5 = new JLabel();
        precisionPanel.add(label_5);
        label_5.setText("Precision: ");

        textFieldPrecision = new JTextField();
        textFieldPrecision.setEditable(false);
        textFieldPrecision.setPreferredSize(new Dimension(120, 22));
        textFieldPrecision.setMaximumSize(new Dimension(120, 22));
        precisionPanel.add(textFieldPrecision);
        textFieldPrecision.setText("NaN");

        final JPanel recallPanel = new JPanel();
        prfPanel.add(recallPanel);

        final JLabel label_6 = new JLabel();
        recallPanel.add(label_6);
        label_6.setText("   Recall: ");

        textFieldRecall = new JTextField();
        textFieldRecall.setEditable(false);
        textFieldRecall.setPreferredSize(new Dimension(120, 22));
        recallPanel.add(textFieldRecall);
        textFieldRecall.setText("NaN");

        final JPanel fmeasurePanel = new JPanel();
        prfPanel.add(fmeasurePanel);

        final JLabel label_7 = new JLabel();
        fmeasurePanel.add(label_7);
        label_7.setText("F-Measure: ");

        textFieldFmeasure = new JTextField();
        textFieldFmeasure.setEditable(false);
        textFieldFmeasure.setPreferredSize(new Dimension(120, 22));
        fmeasurePanel.add(textFieldFmeasure);
        textFieldFmeasure.setText("NaN");

        final JPanel detailPanel = new JPanel();
        detailPanel.setBorder(new LineBorder(new Color(96, 13, 48), 2, false));
        detailPanel.setLayout(new BorderLayout());
        resultPanel.add(detailPanel);

        final JPanel titlePanel = new JPanel();
        detailPanel.add(titlePanel, BorderLayout.NORTH);

        comboBox = new JComboBox();
        comboBox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                int index = comboBox.getSelectedIndex();
                if (index == 0) {
                    alignTable.setAlignment(correctAlignments);
                } else if (index == 1) {
                    alignTable.setAlignment(errorAlignments);
                } else if (index == 2) {
                    alignTable.setAlignment(lostAlignments);
                }
                alignTable.repaintTable();
            }
        });

        comboBox.setPreferredSize(new Dimension(200, 22));
        comboBox.setModel(new DefaultComboBoxModel(new String[]{
                    " Correct alignments ", " Error alignments ",
                    " Lost alignments "
                }));
        titlePanel.add(comboBox);

        final JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        detailPanel.add(tablePanel);

        tablePanel.add(alignTable, BorderLayout.CENTER);
    }
}
