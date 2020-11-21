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

import com.hp.hpl.jena.ontology.OntModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Wei Hu
 */
public class MainFrame
{
    private JFrame frame = null;
    public static OntModel model1 = null;
    public static OntModel model2 = null;
    boolean finished = false;
    
    public static final JTabbedPane tabbedPane = new JTabbedPane();

    public MainFrame()
    {
        File tempDir = new File("./temp/");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        File tempResult = new File("./temp/tempResult.rdf");
        if (tempResult.exists()) {
            tempResult.delete();
        }
        try {
            tempResult.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    public void init()
    {
        MatchPanel matchPanel = new MatchPanel();
        // DisplayPanel displayPanel = new DisplayPanel();
        EvalPanel evalPanel = new EvalPanel();
        
        tabbedPane.addTab("Matching", null, matchPanel, null);
        // tabbedPane.addTab("Display", null, displayPanel, null);
        tabbedPane.addTab("Evaluation", null, evalPanel, null);
        tabbedPane.setSize(new Dimension(600, 500));
        tabbedPane.setEnabledAt(1, false);

        frame = new JFrame();
        frame.setSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.getContentPane().setBounds(0, 0, 800, 600);
        frame.setTitle("Falcon-AO 2008 -- http://iws.seu.edu.cn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        final JMenu helpMenu = new JMenu();
        menuBar.add(helpMenu);
        helpMenu.setText("Help");

        final JMenuItem aboutMenu = new JMenuItem();
        aboutMenu.setText("About");
        aboutMenu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String s = "Falcon-AO 2008 \r\n";
                s += "All rights reserved by Institute of Web Science,\r\n";
                s += "Southeast University, PR China.\r\n";
                s += "Chief developer: Wei Hu (whu@seu.edu.cn)";
                JOptionPane.showMessageDialog(null, s, "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenu);

        final JPanel mainResult = new JPanel();
        mainResult.setLayout(new BorderLayout());
        frame.getContentPane().add(mainResult);
        mainResult.add(tabbedPane);
    }

    public static void main(String args[])
    {
        MainFrame window = new MainFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = window.frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        int height = (screenSize.height - frameSize.height) / 2;
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        int width = (screenSize.width - frameSize.width) / 2;
        window.frame.setLocation(width, height);
        window.frame.setVisible(true);
    }
}
