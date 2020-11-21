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

import iws.falcon.model.Node;
import java.util.ArrayList;

/**
 * @author Wei Hu
 */
public class AlignmentSet
{
    private ArrayList collection = null;

    public AlignmentSet()
    {
        collection = new ArrayList();
    }

    public void addAlignment(Alignment alignment)
    {
        collection.add(alignment);
    }

    public Alignment getAlignment(int index)
    {
        if (index >= 0 && index < size()) {
            return (Alignment) collection.get(index);
        } else {
            System.err.println("getAlignmentError: Index is out of bound.");
            return null;
        }
    }

    public double getSimilarity(Node left, Node right)
    {
        Alignment align = contains(left, right);
        if (align == null) {
            return 0;
        } else {
            return align.getSimilarity();
        }
    }

    public void setSimilarity(Node left, Node right, double sim)
    {
        Alignment align = contains(left, right);
        if (align == null) {
            System.err.println("setSimilarityError: Cannot find such alignment.");
        } else {
            align.setSimilarity(sim);
        }
    }

    public boolean removeAlignment(int index)
    {
        if (index >= 0 && index < size()) {
            collection.remove(index);
            return true;
        } else {
            System.err.println("removeAlignmentError: Index is out of bound.");
            return false;
        }
    }

    public boolean removeAlignment(Node left, Node right)
    {
        for (int i = 0, n = size(); i < n; i++) {
            Alignment align = (Alignment) collection.get(i);
            if (align.getEntity1().equals(left) && align.getEntity2().equals(right)) {
                collection.remove(i);
                return true;
            }
        }
        System.err.println("removeAlignmentError: Cannot find such alignment.");
        return false;
    }

    public Alignment contains(Node left, Node right)
    {
        for (int i = 0, n = size(); i < n; i++) {
            Alignment align = (Alignment) collection.get(i);
            if (align.getEntity1().equals(left) && align.getEntity2().equals(right)) {
                return align;
            }
        }
        return null;
    }

    public AlignmentSet cut(double threshold)
    {
        for (int i = 0; i < size(); i++) {
            Alignment align = (Alignment) collection.get(i);
            if (align.getSimilarity() <= threshold) {
                removeAlignment(i);
                i--;
            }
        }
        return this;
    }

    public int size()
    {
        return collection.size();
    }

    public int size(double threshold)
    {
        int count = 0;
        for (int i = 0, n = size(); i < n; i++) {
            Alignment align = (Alignment) collection.get(i);
            if (align.getSimilarity() > threshold) {
                count++;
            }
        }
        return count;
    }

    public void show()
    {
        for (int i = 0, n = size(); i < n; i++) {
            Alignment align = (Alignment) collection.get(i);
            System.out.println("entity1=" + align.getEntity1().toString());
            System.out.println("entity2=" + align.getEntity2().toString());
            System.out.println("similarity=" + align.getSimilarity());
            System.out.println("relation=" + align.getRelation() + "\n");
        }
    }
}
