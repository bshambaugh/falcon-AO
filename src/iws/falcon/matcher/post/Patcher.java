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
package iws.falcon.matcher.post;

import iws.falcon.matcher.AbstractMatcher;
import iws.falcon.output.Alignment;
import iws.falcon.output.AlignmentSet;
import java.util.HashMap;

/**
 * @author Wei Hu
 */
public class Patcher implements AbstractMatcher
{
    private AlignmentSet alignSet = null;

    public Patcher(AlignmentSet as)
    {
        alignSet = as;
    }

    // �������
    public void match()
    {
        HashMap am = new HashMap(alignSet.size());
        for (int i = 0; i < alignSet.size(); i++) {
            Alignment align = alignSet.getAlignment(i);
            String s1 = align.getEntity1().toString();
            Alignment temp = (Alignment) am.get(s1);
            if (temp == null) {
                am.put(s1, align);
            } else {
                double sim1 = temp.getSimilarity(), sim2 = align.getSimilarity();
                if (sim1 < sim2) {
                    temp.setEntity2(align.getEntity2());
                    temp.setSimilarity(sim2);
                    temp.setRelation(align.getRelation());
                }
                if (sim2 != 1.0) {
                    alignSet.removeAlignment(i);
                    i--;
                }
            }
        }
        am.clear();
        for (int i = 0; i < alignSet.size(); i++) {
            Alignment align = alignSet.getAlignment(i);
            String s2 = align.getEntity2().toString();
            Alignment temp = (Alignment) am.get(s2);
            if (temp == null) {
                am.put(s2, align);
            } else {
                double sim1 = temp.getSimilarity(), sim2 = align.getSimilarity();
                if (sim1 < sim2) {
                    temp.setEntity1(align.getEntity1());
                    temp.setSimilarity(sim2);
                    temp.setRelation(align.getRelation());
                }
                if (sim2 != 1.0) {
                    alignSet.removeAlignment(i);
                    i--;
                }
            }
        }
        am.clear();
        for (int i = 0; i < alignSet.size(); i++) {
            Alignment align = alignSet.getAlignment(i);
            for (int j = i + 1; j < alignSet.size(); j++) {
                Alignment temp = alignSet.getAlignment(j);
                if (align.equals(temp)) {
                    alignSet.removeAlignment(i);
                    i--;
                    j--;
                }
            }
        }
    }

    public AlignmentSet getAlignmentSet()
    {
        return null;
    }

    public AlignmentSet getClassAlignmentSet()
    {
        return null;
    }

    public AlignmentSet getPropertyAlignmentSet()
    {
        return null;
    }

    public AlignmentSet getInstanceAlignmentSet()
    {
        return null;
    }
}
