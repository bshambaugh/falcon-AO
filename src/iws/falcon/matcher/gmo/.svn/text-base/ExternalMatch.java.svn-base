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
package iws.falcon.matcher.gmo;

import iws.falcon.model.Constants;
import iws.falcon.model.Node;
import iws.falcon.model.NodeCategory;
import iws.falcon.model.RBGModel;
import iws.falcon.output.Alignment;
import iws.falcon.output.AlignmentSet;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class ExternalMatch
{
    private AlignmentSet classSim = new AlignmentSet();
    private AlignmentSet propertySim = new AlignmentSet();
    private AlignmentSet instanceSim = new AlignmentSet();
    private RBGModel rbgModelA = null;
    private RBGModel rbgModelB = null;

    public ExternalMatch(RBGModel modelA, RBGModel modelB)
    {
        rbgModelA = modelA;
        rbgModelB = modelB;
    }

    public RBGModel getModelA()
    {
        return rbgModelA;
    }

    public RBGModel getModelB()
    {
        return rbgModelB;
    }

    public double getSimilarity(Node left, Node right)
    {
        double sim = 0;
        if (left.isLiteral() && right.isLiteral()) {
            sim = DataLiteralSim.getDataLiteralSim(left, right);
        } else if (NodeCategory.getCategory(left) == Constants.ONTOLOGY_INSTANCE 
                && NodeCategory.getCategory(right) == Constants.ONTOLOGY_INSTANCE) {
            if (instanceSim != null) {
                if (left.isAnon() || right.isAnon()) {
                    sim = 0;
                } else {
                    sim = instanceSim.getSimilarity(left, right);
                    if (sim <= 0) {
                        sim = instanceSim.getSimilarity(right, left);
                    }
                }
            }
        } else if (left.getNodeLevel() == Node.LANGUAGE_LEVEL 
                && right.getNodeLevel() == Node.LANGUAGE_LEVEL 
                && left.getCategory() == right.getCategory()) {
            if (Constants.XSD_NS.equals(left.getNameSpace()) 
                    && Constants.XSD_NS.equals(right.getNameSpace())) {
                sim = DatatypeSim.getSimilarity(left.toString(), right.toString());
            } else if (Constants.XSD_NS.equals(left.getNameSpace()) 
                    || Constants.XSD_NS.equals(right.getNameSpace())) {
                sim = 0;
            } else {
                sim = BuiltInVocSim.getSimilarity(left.toString(), right.toString());

            }
        } else if (left.getNodeLevel() == Node.EXTERNAL 
                && right.getNodeLevel() == Node.EXTERNAL) {
            if (left.getCategory() == Node.CLASS 
                    && right.getCategory() == Node.CLASS) {
                if (classSim != null) {
                    if (left.isAnon() || right.isAnon()) {
                        sim = 0;
                    } else {
                        sim = classSim.getSimilarity(left, right);
                        if (sim <= 0) {
                            sim = classSim.getSimilarity(right, left);
                        }
                    }
                }
            } else if (left.getCategory() >= Node.OBJECTPROPERTY 
                    && left.getCategory() <= Node.DATATYPEPROPERTY 
                    && right.getCategory() >= Node.OBJECTPROPERTY 
                    && right.getCategory() <= Node.DATATYPEPROPERTY) {
                if (propertySim != null) {
                    sim = propertySim.getSimilarity(left, right);
                    if (sim <= 0) {
                        sim = propertySim.getSimilarity(right, left);
                    }
                }
            }
        } else if (left.toString().equals(right.toString())) {
            sim = 1;
        }
        if (sim < 0) {
            sim = 0;
        }
        return sim;
    }

    public void setInstanceSim(AlignmentSet as)
    {
        if (as == null || as.size() == 0) {
            return;
        }
        for (int i = 0, n = as.size(); i < n; i++) {
            Alignment alignment = as.getAlignment(i);
            Node nodeA = null;
            Node nodeB = null;
            nodeA = rbgModelA.getNode(alignment.getEntity1().toString());
            if (nodeA == null) {
                nodeA = rbgModelA.getNode(alignment.getEntity2().toString());
                nodeB = rbgModelB.getNode(alignment.getEntity1().toString());
            } else {
                nodeB = rbgModelB.getNode(alignment.getEntity2().toString());
            }
            instanceSim.addAlignment(new Alignment(
                    nodeA, nodeB, alignment.getSimilarity()));
        }
    }

    public AlignmentSet getInstanceSim()
    {
        return instanceSim;
    }

    public void setClassSim(AlignmentSet as)
    {
        if (as == null || as.size() == 0) {
            return;
        }
        for (int i = 0, n = as.size(); i < n; i++) {
            Alignment alignment = as.getAlignment(i);
            Node nodeA = null;
            Node nodeB = null;
            nodeA = rbgModelA.getNode(alignment.getEntity1().toString());
            if (nodeA == null) {
                nodeA = rbgModelA.getNode(alignment.getEntity2().toString());
                nodeB = rbgModelB.getNode(alignment.getEntity1().toString());
            } else {
                nodeB = rbgModelB.getNode(alignment.getEntity2().toString());
            }
            nodeA.setNodeLevel(Node.EXTERNAL);
            nodeB.setNodeLevel(Node.EXTERNAL);
            classSim.addAlignment(new Alignment(
                    nodeA, nodeB, alignment.getSimilarity()));
        }
    }

    public AlignmentSet getClassSim()
    {
        return classSim;
    }

    public void setPropertySim(AlignmentSet as)
    {
        if (as == null || as.size() == 0) {
            return;
        }
        for (int i = 0, n = as.size(); i < n; i++) {
            Alignment alignment = as.getAlignment(i);
            Node nodeA = null;
            Node nodeB = null;
            nodeA = rbgModelA.getNode(alignment.getEntity1().toString());
            if (nodeA == null) {
                nodeA = rbgModelA.getNode(alignment.getEntity2().toString());
                nodeB = rbgModelB.getNode(alignment.getEntity1().toString());
            } else {
                nodeB = rbgModelB.getNode(alignment.getEntity2().toString());
            }
            nodeA.setNodeLevel(Node.EXTERNAL);
            nodeB.setNodeLevel(Node.EXTERNAL);
            propertySim.addAlignment(new Alignment(
                    nodeA, nodeB, alignment.getSimilarity()));
        }
    }

    public AlignmentSet getPropertySim()
    {
        return propertySim;
    }
}
