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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import iws.falcon.ext.sentence.RDFSentence;
import iws.falcon.ext.sentence.RDFSentenceGraph;
import iws.falcon.ext.sentence.filter.OntologyHeaderFilter;
import iws.falcon.ext.sentence.filter.PureSchemaFilter;
import iws.falcon.model.Node;
import iws.falcon.model.NodeList;
import iws.falcon.model.RBGModel;

/**
 * @author Wei Hu
 */
public class Partitioner
{
    private RBGModel rbgModel = null;
    private String ontName = null;
    private int maxSize = Integer.MAX_VALUE;
    private String tempDir = null;
    private NodeList entities = null;
    private HashMap links = null;
    private HashMap clusters = null;
    private ArrayList models = null;

    public Partitioner(RBGModel model, String name, int ms, String td)
    {
        rbgModel = model;
        ontName = name;
        maxSize = ms; 
        tempDir = td; 
    }

    public NodeList getEntities()
    {
        return entities;
    }

    public HashMap getLinks()
    {
        return links;
    }

    public HashMap getClusters()
    {
        return clusters;
    }

    public ArrayList getOntModels()
    {
        return models;
    }

    public void partition()
    {
        step1_parse();
        System.out.println("[Partitioning][step=1][" + entities.size() 
                + " entities have been generated]");

        step2_link();
        System.out.println("[Partitioning][step=2][" + links.size() 
                + " links have been generated]");

        step3_partition();
        System.out.println("[Partitioning][step=3][" + clusters.size()
                + " clusters have been generated]");

        step4_generate();
        System.out.println("[Partitioning][step=4][" + models.size() 
                + " blocks have been generated]");
    }

    private void step1_parse()
    {
        entities = new NodeList(1000);
        for (Iterator i = rbgModel.listNamedClassNodes(); i.hasNext();) {
            entities.add((Node) i.next());
        }
        for (Iterator i = rbgModel.listPropertyNodes(); i.hasNext();) {
            entities.add((Node) i.next());
        }
    }

    private void step2_link()
    {
        LinkGenerator lg = new LinkGenerator();
        lg.initEntities(entities);
        lg.generateStructuralLinks1(); // hierarchical distance
        lg.generateStructuralLinks2(); // overlapped domain
        lg.combine();
        links = lg.getLinks();
    }

    private void step3_partition()
    {
        ClusterGenerator op = new ClusterGenerator();
        op.initClusters(entities, links);
        op.executePartitioning(maxSize);
        clusters = op.getClusters();
    }

    private void step4_generate()
    {
        ArrayList list = new ArrayList();
        for (Iterator i = clusters.values().iterator(); i.hasNext();) {
            list.add((Cluster) i.next());
        }
        HashMap uriToClusterID = new HashMap();
        for (int i = 0, n = list.size(); i < n; i++) {
            Cluster cluster = (Cluster) list.get(i);
            int clusterID = cluster.getClusterID();
            for (Iterator iter = cluster.listElements(); iter.hasNext();) {
                String uri = ((Node) iter.next()).toString();
                uriToClusterID.put(uri, clusterID);
            }
        }
        RDFSentenceGraph sg = new RDFSentenceGraph(rbgModel.getOntModel());
        sg.build();
        sg.filter(new OntologyHeaderFilter(sg.getOntologyURIs()));
        sg.filter(new PureSchemaFilter());
        models = new ArrayList(list.size());
        HashMap clusterIDToOntModelID = new HashMap();
        for (int i = 0, n = list.size(); i < n; i++) {
            models.add(ModelFactory.createOntologyModel());
            int cid = ((Cluster) list.get(i)).getClusterID();
            clusterIDToOntModelID.put(cid, i);
        }
        for (int i = 0, n = sg.getRDFSentences().size(); i < n; i++) {
            RDFSentence sentence = sg.getRDFSentence(i);
            // sentence��subject����block
            ArrayList uris = sentence.getSubjectDomainVocabularyURIs();
            HashMap uniqueURIs = new HashMap();
            for (int j = 0, m = uris.size(); j < m; j++) {
                Integer clusterID = (Integer) uriToClusterID.get(uris.get(j));
                if (clusterID != null) {
                    uniqueURIs.put(clusterID, null);
                }
            }
            if (uniqueURIs.size() == 1) {
                Integer cid = (Integer) uniqueURIs.keySet().iterator().next();
                Integer mid = (Integer) clusterIDToOntModelID.get(cid);
                OntModel block = (OntModel) models.get(mid.intValue());
                ArrayList statements = sentence.getStatements();
                for (int j = 0, m = statements.size(); j < m; j++) {
                    block.add((Statement) statements.get(j));
                }
            }
        }
        for (int i = 0, n = models.size(); i < n; i++) {
            int cid = ((Cluster) list.get(i)).getClusterID();
            String filepath = tempDir + ontName + "_block_" + cid + ".rdf";
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
            } 
            OntModel block = (OntModel) models.get(i);
            try {
                FileOutputStream fos = new FileOutputStream(filepath);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                block.write(bos, "RDF/XML-ABBREV");
                bos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            block.close();
        }
    }
}
