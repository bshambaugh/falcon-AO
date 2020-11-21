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
import iws.falcon.output.AlignmentSet;
import java.util.Iterator;

/**
 * @author Wei Hu
 */
public class LingComp
{
    private RBGModel modelA = null,  modelB = null;
    private int counts[] = new int[6];

    public LingComp(RBGModel modelA, RBGModel modelB)
    {
        this.modelA = modelA;
        this.modelB = modelB;
        countNodesByCategory();
    }

    private void countNodesByCategory()
    {
        for (Iterator iter = modelA.listNodes(); iter.hasNext();) {
            Node node = (Node) iter.next();
            int category = NodeCategory.getCategoryWithoutExternal(node);
            if (category == Constants.ONTOLOGY_CLASS) {
                if (!node.isAnon()) {
                    counts[0]++;
                }
            } else if (category == Constants.ONTOLOGY_PROPERTY) {
                if (!node.isAnon()) {
                    counts[1]++;
                }
            } else if (category == Constants.ONTOLOGY_INSTANCE) {
                if (!node.isAnon()) {
                    counts[2]++;
                }
            }
        }
        for (Iterator iter = modelB.listNodes(); iter.hasNext();) {
            Node node = (Node) iter.next();
            int category = NodeCategory.getCategoryWithoutExternal(node);
            if (category == Constants.ONTOLOGY_CLASS) {
                if (!node.isAnon()) {
                    counts[3]++;
                }
            } else if (category == Constants.ONTOLOGY_PROPERTY) {
                if (!node.isAnon()) {
                    counts[4]++;
                }
            } else if (category == Constants.ONTOLOGY_INSTANCE) {
                if (!node.isAnon()) {
                    counts[5]++;
                }
            }
        }
    }

    private int[] countSizeWithoutInstances()
    {
        int sizes[] = new int[2];
        if (counts[0] != 0 && counts[3] != 0) {
            sizes[0] += counts[0];
            sizes[1] += counts[3];
        }
        if (counts[1] != 0 && counts[4] != 0) {
            sizes[0] += counts[1];
            sizes[1] += counts[4];
        }
        return sizes;
    }

    private int[] countSizeWithInstances()
    {
        int sizes[] = new int[2];
        if (counts[0] != 0 && counts[3] != 0) {
            sizes[0] += counts[0];
            sizes[1] += counts[3];
        }
        if (counts[1] != 0 && counts[4] != 0) {
            sizes[0] += counts[1];
            sizes[1] += counts[4];
        }
        if (counts[2] != 0 && counts[5] != 0) {
            sizes[0] += counts[2];
            sizes[1] += counts[5];
        }
        return sizes;
    }

    public int estimate1(AlignmentSet as)
    {
        int sizes[] = countSizeWithoutInstances();
        int temp = sizes[0] < sizes[1] ? sizes[0] : sizes[1];
        int elemSetSize = as.size(Parameters.lingHighSim);
        double value = elemSetSize / (double) (temp);
        if (value > Parameters.lingHighValue) {
            return Parameters.highComp;
        } else if (value > Parameters.lingLowValue) {
            return Parameters.mediumComp;
        } else {
            return Parameters.lowComp;
        }
    }

    public int estimate2(AlignmentSet as)
    {
        int sizes[] = countSizeWithInstances();
        int temp = sizes[0] < sizes[1] ? sizes[0] : sizes[1];
        int elemSetSize = as.size(Parameters.lingHighSim);
        double value = elemSetSize / (double) temp;
        if (value > Parameters.lingHighValue) {
            return Parameters.highComp;
        } else if (value > Parameters.lingLowValue) {
            return Parameters.mediumComp;
        } else {
            return Parameters.lowComp;
        }
    }
}
