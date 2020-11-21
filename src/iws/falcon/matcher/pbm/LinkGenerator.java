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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Wei Hu
 */
public class LinkGenerator
{
    private NodeList entities = null;
    private ArrayList links1 = null;
    private ArrayList links2 = null;
    private ArrayList overall = null;

    public LinkGenerator()
    {
        links1 = new ArrayList();
        links2 = new ArrayList();
        overall = new ArrayList();
    }

    public void initEntities(NodeList nl)
    {
        entities = nl;
    }

    public ArrayList getOverall()
    {
        return overall;
    }

    public HashMap getLinks()
    {
        HashMap links = new HashMap(overall.size());
        for (int i = 0, n = overall.size(); i < n; i++) {
            Link link = (Link) overall.get(i);
            String key = link.getURI1() + ":" + link.getURI2();
            links.put(key, link);
        }
        return links;
    }

    public void generateStructuralLinks1()
    {
        double maxSim = Parameters.structCompCutoff;
        for (int i = 0, n = entities.size(); i < n; i++) {
            Node entity1 = (Node) entities.get(i);
            String uri1 = entity1.toString();
            int hier1 = entity1.getHierarchy();
            NodeList parents1 = entity1.getNamedSupers();
            for (int j = i + 1; j < n; j++) {
                Node entity2 = (Node) entities.get(j);
                String uri2 = entity2.toString();
                int hier2 = entity2.getHierarchy();
                NodeList parents2 = entity2.getNamedSupers();
                if (parents1 != null && parents1.contains(entity2)
                        || parents2 != null && parents2.contains(entity1)) {
                    if (hier1 < hier2) {
                        double sim = (2.0 * hier1) / (hier1 + hier2);
                        if (sim > Parameters.structCompCutoff) {
                            Link link = new Link(uri1, uri2, sim);
                            links1.add(link);
                            maxSim = sim > maxSim ? sim : maxSim;
                        }
                    } else {
                        double sim = (2.0 * hier2) / (hier1 + hier2);
                        if (sim > Parameters.structCompCutoff) {
                            Link link = new Link(uri1, uri2, sim);
                            links1.add(link);
                            maxSim = sim > maxSim ? sim : maxSim;
                        }
                    }
                } else if (parents1 != null && parents2 != null) {
                    int hier = 0;
                    for (int k = 0, m = parents1.size(); k < m; k++) {
                        Node entity = (Node) parents1.get(k);
                        int temp = entity.getHierarchy();
                        if (parents2.contains(entity) 
                                && temp > hier && temp < hier1 && temp < hier2) {
                            hier = temp;
                        }
                    }
                    if (hier > 0) {
                        double sim = (2.0 * hier) / (hier1 + hier2);
                        if (sim > Parameters.structCompCutoff) {
                            Link link = new Link(uri1, uri2, sim);
                            links1.add(link);
                            maxSim = sim > maxSim ? sim : maxSim;
                        }
                    }
                }
            }
        }
        if (maxSim > Parameters.structCompCutoff) {
            for (int i = 0, n = links1.size(); i < n; i++) {
                Link link = (Link) links1.get(i);
                link.setSimilarity(link.getSimilarity() / maxSim);
            }
        }
    }

    public void generateStructuralLinks2()
    {
        double maxSim = Parameters.structCompCutoff;
        for (int i = 0, n = entities.size(); i < n; i++) {
            Node entity1 = (Node) entities.get(i);
            String uri1 = entity1.toString();
            NodeList domains1 = entity1.getNamedDomains();
            if (domains1 == null) {
                continue;
            }
            int size1 = domains1.size();
            for (int j = i + 1; j < n; j++) {
                Node entity2 = (Node) entities.get(j);
                String uri2 = entity2.toString();
                NodeList domains2 = entity2.getNamedDomains();
                if (domains2 == null) {
                    continue;
                }
                int size2 = domains2.size();
                if (domains1.size() > 0 && domains2.size() > 0) {
                    int count = 0;
                    for (int k = 0; k < size1; k++) {
                        Node entity = (Node) domains1.get(k);
                        if (domains2.contains(entity)) {
                            count++;
                        }
                    }
                    if (count > 0) {
                        double sim = 2.0 * count / (size1 + size2);
                        if (sim > Parameters.structCompCutoff) {
                            Link link = new Link(uri1, uri2, sim);
                            links2.add(link);
                            maxSim = sim > maxSim ? sim : maxSim;
                        }
                    }
                }
            }
        }
        if (maxSim > Parameters.structCompCutoff) {
            for (int i = 0, n = links2.size(); i < n; i++) {
                Link link = (Link) links2.get(i);
                link.setSimilarity(link.getSimilarity() / maxSim);
            }
        }
    }

    public void combine()
    {
        double para1 = Parameters.hierWeight;
        double para2 = 1 - Parameters.hierWeight;
        for (int i = 0; i < links1.size(); i++) {
            Link link1 = (Link) links1.get(i);
            for (int j = 0; j < links2.size(); j++) {
                Link link2 = (Link) links2.get(j);
                if ((link1.getURI1().equals(link2.getURI1()) 
                                && link1.getURI2().equals(link2.getURI2())) 
                        || (link1.getURI1().equals(link2.getURI2()) 
                                && link1.getURI2().equals(link2.getURI1()))) {
                    double sim = para1 * link1.getSimilarity() + para2 * link2.getSimilarity();
                    Link link = new Link(link1.getURI1(), link2.getURI2(), sim);
                    overall.add(link);
                    links1.remove(i);
                    i--;
                    links2.remove(j);
                    j--;
                }
            }
        }
        for (int i = 0, n = links1.size(); i < n; i++) {
            overall.add(links1.get(i));
        }
        for (int j = 0, n = links2.size(); j < n; j++) {
            overall.add(links2.get(j));
        }
    }
}
