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
package iws.falcon.matcher.pbm;

import iws.falcon.matcher.string.StringMatcher;
import iws.falcon.model.Node;
import iws.falcon.model.RBGModel;
import iws.falcon.output.Alignment;
import iws.falcon.output.AlignmentSet;
import iws.falcon.output.AlignmentWriter2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Wei Hu
 */
public class BlockMatcher
{
    private double matrix[][] = null;
    private AlignmentSet anchors = null;
    private ArrayList blockMappings = null;
    private RBGModel rbgModelA = null,  rbgModelB = null;
    private String name1 = null,  name2 = null;
    private HashMap clusters1 = null,  clusters2 = null;
    private String tempDir = null;

    public AlignmentSet getAnchors()
    {
        return anchors;
    }

    public ArrayList getBlockMappings()
    {
        return blockMappings;
    }

    public BlockMatcher(RBGModel modelA, RBGModel modelB, String n1, String n2,
            HashMap c1, HashMap c2, String dir)
    {
        rbgModelA = modelA;
        rbgModelB = modelB;
        name1 = n1;
        name2 = n2;
        clusters1 = c1;
        clusters2 = c2;
        tempDir = dir;
    }

    public void blockMatch()
    {
        step1_anchor();
        System.out.println("[Block matching][step=1][" + anchors.size() 
                + " anchors have been generated]");

        step2_match();
        System.out.println("[Block matching][step=2][" + blockMappings.size() 
                + " block mappings have been generated]");
    }

    public void step1_anchor()
    {
        anchors = new AlignmentSet();
        StringMatcher matcher = new StringMatcher(rbgModelA, rbgModelB);
        matcher.match();
        AlignmentSet cas = matcher.getClassAlignmentSet();
        AlignmentSet pas = matcher.getPropertyAlignmentSet();
        for (int i = 0, n = cas.size(); i < n; i++) {
            anchors.addAlignment(cas.getAlignment(i));
        }
        for (int i = 0, n = pas.size(); i < n; i++) {
            anchors.addAlignment(pas.getAlignment(i));
        }
        String filepath = tempDir + "anchors.rdf";
        AlignmentWriter2 aw2 = new AlignmentWriter2(anchors, filepath);
        aw2.write(name1, name2, name1, name2);
    }

    public void step2_match()
    {
        HashMap temp = new HashMap(anchors.size());
        for (int i = 0, n = anchors.size(); i < n; i++) {
            Alignment anchor = (Alignment) anchors.getAlignment(i);
            String uri1 = anchor.getEntity1().toString();
            String uri2 = anchor.getEntity2().toString();
            String key = uri1 + ":" + uri2;
            temp.put(key, anchor);
        }
        int size1 = clusters1.size();
        ArrayList temp1 = new ArrayList(size1);
        for (Iterator i = clusters1.values().iterator(); i.hasNext();) {
            Cluster cluster = (Cluster) i.next();
            temp1.add(cluster);
        }
        int size2 = clusters2.size();
        ArrayList temp2 = new ArrayList(size2);
        for (Iterator i = clusters2.values().iterator(); i.hasNext();) {
            Cluster cluster = (Cluster) i.next();
            temp2.add(cluster);
        }
        int anchorInClusters1[] = new int[size1];
        for (int i = 0; i < size1; i++) {
            Cluster cluster1 = (Cluster) temp1.get(i);
            HashMap elements1 = cluster1.getElements();
            for (Iterator i1 = elements1.values().iterator(); i1.hasNext();) {
                String uri1 = ((Node) i1.next()).toString();
                for (int j = 0; j < anchors.size(); j++) {
                    Alignment anchor = (Alignment) anchors.getAlignment(j);
                    if (anchor.getEntity1().toString().equals(uri1)) {
                        anchorInClusters1[i]++;
                    }
                }
            }
        }
        int anchorInClusters2[] = new int[size2];
        for (int i = 0; i < size2; i++) {
            Cluster cluster2 = (Cluster) temp2.get(i);
            HashMap elements2 = cluster2.getElements();
            for (Iterator i2 = elements2.values().iterator(); i2.hasNext();) {
                String uri2 = ((Node) i2.next()).toString();
                for (int j = 0; j < anchors.size(); j++) {
                    Alignment anchor = (Alignment) anchors.getAlignment(j);
                    if (anchor.getEntity2().toString().equals(uri2)) {
                        anchorInClusters2[i]++;
                    }
                }
            }
        }
        matrix = new double[size1][size2];
        for (int i = 0; i < size1; i++) {
            Cluster cluster1 = (Cluster) temp1.get(i);
            HashMap elements1 = cluster1.getElements();
            for (int j = 0; j < size2; j++) {
                Cluster cluster2 = (Cluster) temp2.get(j);
                HashMap elements2 = cluster2.getElements();
                for (Iterator i1 = elements1.values().iterator(); i1.hasNext();) {
                    String uri1 = ((Node) i1.next()).toString();
                    for (Iterator i2 = elements2.values().iterator(); i2.hasNext();) {
                        String uri2 = ((Node) i2.next()).toString();
                        String key = uri1 + ":" + uri2;
                        Alignment anchor = (Alignment) temp.get(key);
                        if (anchor != null) {
                            matrix[i][j] += anchor.getSimilarity();
                        }
                    }
                }
                matrix[i][j] = matrix[i][j] / (anchorInClusters1[i] + anchorInClusters2[j]);
            }
        }
        blockMappings = new ArrayList();
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                double sim = matrix[i][j];
                if (sim > Parameters.threshold) {
                    int cid1 = ((Cluster) temp1.get(i)).getClusterID();
                    int cid2 = ((Cluster) temp2.get(j)).getClusterID();
                    String s1 = tempDir + name1 + "_block_" + cid1 + ".rdf";
                    String s2 = tempDir + name2 + "_block_" + cid2 + ".rdf";
                    BlockMapping bm = new BlockMapping(s1, s2, sim);
                    blockMappings.add(bm);
                }
            }
        }
        String fp = tempDir + "bm.rdf";
        BlockMappingWriter2 bmw = new BlockMappingWriter2(blockMappings, fp);
        bmw.write(name1, name2, name1, name2);
    }
}
