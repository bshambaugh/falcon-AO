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

import iws.falcon.model.Node;
import iws.falcon.model.NodeList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Wei Hu
 */
public class ClusterGenerator
{
    private HashMap clusters = null;

    public HashMap getClusters()
    {
        return clusters;
    }

    public void initClusters(NodeList entities, HashMap links)
    {
        clusters = new HashMap(entities.size());
        HashMap uriToClusterID = new HashMap();
        for (int cid = 0, n = entities.size(); cid < n; cid++) {
            Node entity = (Node) entities.get(cid);
            Cluster cluster = new Cluster(cid);
            cluster.setCohesion(entity.getHierarchy());
            cluster.putElement(entity.toString(), entity);
            clusters.put(cid, cluster);
            uriToClusterID.put(entity.toString(), cid);
        }
        for (Iterator i = links.values().iterator(); i.hasNext();) {
            Link link = (Link) i.next();
            String uri1 = link.getURI1(), uri2 = link.getURI2();
            double similarity = link.getSimilarity();
            int cid1 = ((Integer) uriToClusterID.get(uri1)).intValue();
            int cid2 = ((Integer) uriToClusterID.get(uri2)).intValue();
            Cluster cluster1 = (Cluster) clusters.get(cid1);
            Cluster cluster2 = (Cluster) clusters.get(cid2);
            cluster1.putCoupling(cid2, similarity);
            cluster2.putCoupling(cid1, similarity);
        }
    }

    private double goodness(double value, int size1, int size2)
    {
        if (size1 * size2 == 1) {
            return value;
        }
        return value / Math.log(size1 * size2);
    }

    private int getBestCluster()
    {
        int cid = -1;
        double maxGoodness = 0;
        for (Iterator i = clusters.values().iterator(); i.hasNext();) {
            Cluster cluster = (Cluster) i.next();
            int elementSize = cluster.getElements().size();
            double cohesion = cluster.getCohesion();
            double goodness = goodness(cohesion, elementSize, elementSize);
            if (maxGoodness < goodness) {
                maxGoodness = goodness;
                cid = cluster.getClusterID();
            }
        }
        return cid;
    }

    private int getRelatedCluster(int cid1)
    {
        int cid2 = -1;
        double maxGoodness = 0;
        Cluster cluster = (Cluster) clusters.get(cid1);
        int elementsSize1 = cluster.getElements().size();
        HashMap temp = cluster.getCouplings();
        for (Iterator i = temp.keySet().iterator(); i.hasNext();) {
            Integer tempCid = (Integer) i.next();
            Cluster tempCluster = (Cluster) clusters.get(tempCid);
            int elementsSize2 = tempCluster.getElements().size();
            double coupling = ((Double) temp.get(tempCid)).doubleValue();
            double goodness = goodness(coupling, elementsSize1, elementsSize2);
            if (maxGoodness < goodness) {
                maxGoodness = goodness;
                cid2 = tempCid.intValue();
            }
        }
        return cid2;
    }

    public void executePartitioning(int maxSize)
    {
        HashMap removals = new HashMap();
        exit:
        while (clusters.size() > 0) {
            int cid1 = getBestCluster();
            if (cid1 == -1) {
                break exit;
            }
            int cid2 = getRelatedCluster(cid1);
            while (cid2 == -1) {
                Cluster cluster = (Cluster) clusters.get(cid1);
                removals.put(cid1, cluster);
                clusters.remove(cid1);
                for (Iterator i = clusters.values().iterator(); i.hasNext();) {
                    Cluster tempCluster = (Cluster) i.next();
                    tempCluster.getCouplings().remove(cid1);
                }
                cid1 = getBestCluster();
                if (cid1 == -1) {
                    break exit;
                }
                cid2 = getRelatedCluster(cid1);
            }
            Cluster cluster1 = (Cluster) clusters.get(cid1);
            Cluster cluster2 = (Cluster) clusters.get(cid2);
            for (Iterator i = cluster2.listElements(); i.hasNext();) {
                Node entity = (Node) i.next();
                cluster1.putElement(entity.toString(), entity);
            }
            for (Iterator i = cluster2.getCouplings().keySet().iterator(); i.hasNext();) {
                Integer cid = (Integer) i.next();
                if (cid.intValue() != cluster1.getClusterID()) {
                    Double tempCoupling = (Double) cluster2.getCouplings().get(cid);
                    if (cluster1.getCouplings().containsKey(cid)) {
                        double oc = ((Double) cluster1.getCouplings().get(cid)).doubleValue();
                        double nc = oc + tempCoupling.doubleValue();
                        cluster1.getCouplings().put(cid, new Double(nc));
                    } else {
                        cluster1.getCouplings().put(cid, tempCoupling);
                    }
                }
            }
            double coupling = ((Double) cluster1.getCouplings().get(cid2)).doubleValue();
            double cohesion = cluster1.getCohesion() + cluster2.getCohesion() + coupling;
            cluster1.setCohesion(cohesion);
            cluster1.getCouplings().remove(cid2);
            clusters.remove(cid2);
            for (Iterator i = clusters.values().iterator(); i.hasNext();) {
                Cluster cluster = (Cluster) i.next();
                if (cluster.getClusterID() != cid1 && cluster.getClusterID() != cid2) {
                    HashMap tempTable = cluster.getCouplings();
                    double coupling1 = 0, coupling2 = 0;
                    Double temp1 = (Double) tempTable.get(cid1);
                    if (temp1 != null) {
                        coupling1 = temp1.doubleValue();
                    }
                    Double temp2 = (Double) tempTable.get(cid2);
                    if (temp2 != null) {
                        coupling2 = temp2.doubleValue();
                        tempTable.remove(cid2);
                    }
                    if (coupling1 + coupling2 != 0) {
                        tempTable.put(cid1, coupling1 + coupling2);
                    }
                }
            }
            if (cluster1.getElements().size() > maxSize / 2) {
                Cluster cluster = (Cluster) clusters.get(cid1);
                removals.put(cid1, cluster);
                clusters.remove(cid1);
                for (Iterator i = clusters.values().iterator(); i.hasNext();) {
                    Cluster tempCluster = (Cluster) i.next();
                    tempCluster.getCouplings().remove(cid1);
                }
            }
        }
        if (removals.size() > 0) {
            for (Iterator i = removals.values().iterator(); i.hasNext();) {
                Cluster cluster = (Cluster) i.next();
                clusters.put(cluster.getClusterID(), cluster);
            }
        }
        for (Iterator i = clusters.values().iterator(); i.hasNext();) {
            Cluster cluster = (Cluster) i.next();
            int size = cluster.getElements().size();
            if (size == 1) {
                i.remove();
            }
        }
    }
}
