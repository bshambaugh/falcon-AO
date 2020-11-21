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
package iws.falcon.matcher.string;

import iws.falcon.matcher.AbstractMatcher;
import iws.falcon.matrix.NamedMatrix;
import iws.falcon.model.Node;
import iws.falcon.model.NodeList;
import iws.falcon.model.RBGModel;
import iws.falcon.output.AlignmentSelector;
import iws.falcon.output.AlignmentSet;
import java.util.ArrayList;

/**
 * @author Wei Hu
 */
public class StringMatcher implements AbstractMatcher
{
    private RBGModel modelA = null,  modelB = null;
    private AlignmentSet alignmentSet = null;
    private AlignmentSet classAlignmentSet = null;
    private AlignmentSet propertyAlignmentSet = null;
    private AlignmentSet instanceAlignmentSet = null;
    private NamedMatrix classMatrix = null;
    private NamedMatrix propertyMatrix = null;
    private NamedMatrix instanceMatrix = null;

    public StringMatcher(RBGModel modelA, RBGModel modelB)
    {
        this.modelA = modelA;
        this.modelB = modelB;
    }

    private StringBuffer normalize(String s)
    {
        StringBuffer buffer = new StringBuffer();
        int currentState = 0;
        for (int j = 0; j < s.length(); j++) {
            char nextChar = s.charAt(j);
            switch (currentState) {
                case 0:
                    if (Character.isUpperCase(nextChar)) {
                        buffer.append(Character.toLowerCase(nextChar));
                        currentState = 1;
                    } else if (Character.isLowerCase(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 1;
                    } else if (Character.isDigit(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 2;
                    } else {
                        currentState = 0;
                    }
                    break;
                case 1:
                    if (Character.isUpperCase(nextChar)) {
                        buffer.append(" ");
                        buffer.append(Character.toLowerCase(nextChar));
                        currentState = 1;
                    } else if (Character.isLowerCase(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 1;
                    } else if (Character.isDigit(nextChar)) {
                        buffer.append(" ");
                        buffer.append(nextChar);
                        currentState = 2;
                    } else {
                        buffer.append(" ");
                        currentState = 0;
                    }
                    break;
                case 2:
                    if (Character.isUpperCase(nextChar)) {
                        buffer.append(" ");
                        buffer.append(Character.toLowerCase(nextChar));
                        currentState = 1;
                    } else if (Character.isLowerCase(nextChar)) {
                        buffer.append(" ");
                        buffer.append(nextChar);
                        currentState = 1;
                    } else if (Character.isDigit(nextChar)) {
                        buffer.append(nextChar);
                        currentState = 2;
                    } else {
                        buffer.append(" ");
                        currentState = 0;
                    }
                    break;
            }
        }
        return buffer;
    }

    public NamedMatrix calcSimilarity(NodeList left, NodeList right)
    {
        int numRows = left.size(), numColumns = right.size();
        NamedMatrix matrix = new NamedMatrix(numRows, numColumns);
        matrix.setRowList(left.getList());
        matrix.setColumnList(right.getList());
        for (int i = 0; i < numRows; i++) {
            Node node1 = left.get(i);
            String n1 = new String(normalize(node1.getLocalName()));
            ArrayList l1 = node1.getLabel();
            ArrayList c1 = node1.getComment();
            for (int j = 0; j < numColumns; j++) {
                Node node2 = right.get(j);
                String n2 = new String(normalize(node2.getLocalName()));
                ArrayList l2 = node2.getLabel();
                ArrayList c2 = node2.getComment();
                double s1 = 0, s2 = 0, s3 = 0, similarity = 0;
                if (Parameters.method == 0) {
                    if (n1 != null && n2 != null && Parameters.localnameWeight > 0) {
                        s1 = Parameters.localnameWeight * ISub.getSimilarity(n1, n2);
                    }
                    if (l1 != null && l2 != null && Parameters.labelWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < l1.size(); m++) {
                            String t1 = new String(
                                    normalize((String) l1.get(m)));
                            for (int n = 0; n < l2.size(); n++) {
                                String t2 = new String(normalize((String) l2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s2 = Parameters.labelWeight * max;
                    }
                    if (c1 != null && c2 != null && Parameters.commentWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < c1.size(); m++) {
                            String t1 = new String(
                                    normalize((String) c1.get(m)));
                            for (int n = 0; n < c2.size(); n++) {
                                String t2 = new String(normalize((String) c2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s3 = Parameters.commentWeight * max;
                    }
                    if (s1 > similarity) {
                        similarity = s1;
                    }
                    if (s2 > similarity) {
                        similarity = s2;
                    }
                    if (s3 > similarity) {
                        similarity = s3;
                    }
                } else if (Parameters.method == 1) {
                    if (n1 != null && n2 != null && Parameters.localnameWeight > 0) {
                        s1 = Parameters.localnameWeight * EditDistance.getSimilarity(n1, n2);
                    }
                    if (l1 != null && l2 != null && Parameters.labelWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < l1.size(); m++) {
                            String t1 = new String(
                                    normalize((String) l1.get(m)));
                            for (int n = 0; n < l2.size(); n++) {
                                String t2 = new String(normalize((String) l2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s2 = Parameters.labelWeight * max;
                    }
                    if (c1 != null && c2 != null && Parameters.commentWeight > 0) {
                        double max = 0;
                        for (int m = 0; m < c1.size(); m++) {
                            String t1 = new String(
                                    normalize((String) c1.get(m)));
                            for (int n = 0; n < c2.size(); n++) {
                                String t2 = new String(normalize((String) c2.get(n)));
                                double temp = ISub.getSimilarity(t1, t2);
                                if (temp > max) {
                                    max = temp;
                                }
                            }
                        }
                        s3 = Parameters.commentWeight * max;
                    }
                    if (s1 > similarity) {
                        similarity = s1;
                    }
                    if (s2 > similarity) {
                        similarity = s2;
                    }
                    if (s3 > similarity) {
                        similarity = s3;
                    }
                }
                if (similarity != 0) {
                    matrix.set(i, j, similarity);
                }
            }
        }
        return matrix;
    }

    public void match()
    {
        alignmentSet = new AlignmentSet();
        classAlignmentSet = AlignmentSelector.select(matchClasses(),
                Parameters.threshold);
        for (int i = 0, n = classAlignmentSet.size(); i < n; i++) {
            alignmentSet.addAlignment(classAlignmentSet.getAlignment(i));
        }
        propertyAlignmentSet = AlignmentSelector.select(matchProperties(),
                Parameters.threshold);
        for (int i = 0, n = propertyAlignmentSet.size(); i < n; i++) {
            alignmentSet.addAlignment(propertyAlignmentSet.getAlignment(i));
        }
        if (Parameters.inclInstMatch) {
            instanceAlignmentSet = AlignmentSelector.select(matchInstances(),
                    Parameters.threshold);
            for (int i = 0, n = instanceAlignmentSet.size(); i < n; i++) {
                alignmentSet.addAlignment(instanceAlignmentSet.getAlignment(i));
            }
        }
    }

    public AlignmentSet getAlignmentSet()
    {
        return alignmentSet;
    }

    public NamedMatrix matchClasses()
    {
        NodeList nodeListA = modelA.getNamedClassNodes();
        NodeList nodeListB = modelB.getNamedClassNodes();
        classMatrix = calcSimilarity(nodeListA, nodeListB);
        return classMatrix;
    }

    public NamedMatrix matchProperties()
    {
        NodeList nodeListA = modelA.getPropertyNodes();
        NodeList nodeListB = modelB.getPropertyNodes();
        propertyMatrix = calcSimilarity(nodeListA, nodeListB);
        return propertyMatrix;
    }

    public NamedMatrix matchInstances()
    {
        NodeList nodeListA = modelA.getNamedInstanceNodes();
        NodeList nodeListB = modelB.getNamedInstanceNodes();
        instanceMatrix = calcSimilarity(nodeListA, nodeListB);
        return instanceMatrix;
    }

    public AlignmentSet getClassAlignmentSet()
    {
        return classAlignmentSet;
    }

    public AlignmentSet getPropertyAlignmentSet()
    {
        return propertyAlignmentSet;
    }

    public AlignmentSet getInstanceAlignmentSet()
    {
        return instanceAlignmentSet;
    }

    public NamedMatrix getClassMatrix()
    {
        return classMatrix;
    }

    public NamedMatrix getPropertyMatrix()
    {
        return propertyMatrix;
    }

    public NamedMatrix getInstanceMatrix()
    {
        return instanceMatrix;
    }
}
