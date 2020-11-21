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
package iws.falcon.cc;

import iws.falcon.model.Constants;
import iws.falcon.model.Node;
import iws.falcon.model.NodeCategory;
import iws.falcon.model.RBGModel;
import iws.falcon.output.Alignment;
import iws.falcon.output.AlignmentSet;
import java.util.Iterator;

/**
 * @author Wei Hu
 */
public class StruComp
{
    private RBGModel modelA = null,  modelB = null;

    public StruComp(RBGModel modelA, RBGModel modelB)
    {
        this.modelA = modelA;
        this.modelB = modelB;
    }

    public int estimate1()
    {
        double percentA = percent(modelA);
        double percentB = percent(modelB);
        double pab = percentA / percentB, pba = percentB / percentA;
        if (pab >= Parameters.structPercent || pba >= Parameters.structPercent) {
            return Parameters.lowComp;
        } else {
            int countA[] = count(modelA);
            int countB[] = count(modelB);
            double temp1 = 0, temp2 = 0, temp3 = 0;
            int size = countA.length;
            for (int i = 0; i < size; i++) {
                temp1 += countA[i] * countB[i];
                temp2 += countA[i] * countA[i];
                temp3 += countB[i] * countB[i];
            }
            if (temp2 == 0 || temp3 == 0) {
                return Parameters.lowComp;
            } else {
                double value = temp1 / Math.sqrt(temp2 * temp3);
                if (value >= Parameters.structHighValue) {
                    return Parameters.highComp;
                } else if (value >= Parameters.structLowValue) {
                    return Parameters.mediumComp;
                } else {
                    return Parameters.lowComp;
                }
            }
        }
    }

    public int estimate2(AlignmentSet as1, AlignmentSet as2)
    {
        if (as1.size() == 0) {
            return Parameters.lowComp;
        }
        int count = 0;
        for (int i = 0, m = as1.size(); i < m; i++) {
            Alignment align1 = as1.getAlignment(i);
            for (int j = 0, n = as2.size(); j < n; j++) {
                Alignment align2 = as2.getAlignment(j);
                if (align1.equals(align2)) {
                    count++;
                }
            }
        }
        double value = count / (double) as1.size();
        if (value > Parameters.structHighRate) {
            return Parameters.highComp;
        } else if (value > Parameters.structLowRate) {
            return Parameters.mediumComp;
        } else {
            return Parameters.lowComp;
        }
    }

    private double percent(RBGModel model)
    {
        int stmtNum = 0, namedClassNum = 0, propertyNum = 0, namedInstanceNum = 0;
        for (Iterator iter = model.listNodes(); iter.hasNext();) {
            Node node = (Node) iter.next();
            int category = NodeCategory.getCategoryWithoutExternal(node);
            if (category == Constants.ONTOLOGY_CLASS) {
                if (!node.isAnon()) {
                    namedClassNum++;
                }
            } else if (category == Constants.ONTOLOGY_PROPERTY) {
                if (!node.isAnon()) {
                    propertyNum++;
                }
            } else if (category == Constants.ONTOLOGY_INSTANCE) {
                if (!node.isAnon()) {
                    namedInstanceNum++;
                }
            } else if (node.getNodeType() == Node.STATEMENT) {
                stmtNum++;
            }
        }
        return stmtNum / (double) (namedClassNum + propertyNum + namedInstanceNum);
    }

    private int[] count(RBGModel model)
    {
        int count[] = new int[9];
        int subClassOfNum = 0, domainNum = 0, rangeNum = 0, onPropertyNum = 0;
        int equivalentClassNum = 0, disjointWithNum = 0, 
                intersectionOfNum = 0, unionOfNum = 0, complementOfNum = 0;
        for (Iterator iter = model.listStmtNodes(); iter.hasNext();) {
            Node predicate = ((Node) iter.next()).getPredicate();
            String temp = predicate.toString();
            if (temp.equals(Constants.RDFS_NS + "subClassOf")) {
                subClassOfNum++;
            } else if (temp.equals(Constants.RDFS_NS + "domain")) {
                domainNum++;
            } else if (temp.equals(Constants.RDFS_NS + "range")) {
                rangeNum++;
            } else if (temp.equals(Constants.OWL_NS + "onProperty")) {
                onPropertyNum++;
            } else if (temp.equals(Constants.OWL_NS + "equivalentClass")) {
                equivalentClassNum++;
            } else if (temp.equals(Constants.OWL_NS + "disjointWith")) {
                disjointWithNum++;
            } else if (temp.equals(Constants.OWL_NS + "intersectionOf")) {
                intersectionOfNum++;
            } else if (temp.equals(Constants.OWL_NS + "unionOf")) {
                unionOfNum++;
            } else if (temp.equals(Constants.OWL_NS + "complementOf")) {
                complementOfNum++;
            }
        }
        count[0] = subClassOfNum;
        count[1] = domainNum;
        count[2] = rangeNum;
        count[3] = onPropertyNum;
        count[4] = equivalentClassNum;
        count[5] = disjointWithNum;
        count[6] = intersectionOfNum;
        count[7] = unionOfNum;
        count[8] = complementOfNum;
        return count;
    }
}
