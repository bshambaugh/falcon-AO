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
import iws.falcon.model.impl.NodeImpl;
import java.io.File;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Wei Hu
 */
public class AlignmentReader2
{
    private OntModel model1 = null;
    private OntModel model2 = null;
    private String alignpath = null;

    public AlignmentReader2(String op1, String op2, String ap)
    {
        OntDocumentManager mgr = new OntDocumentManager();
        mgr.setProcessImports(false);
        OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
        spec.setDocumentManager(mgr);
        model1 = ModelFactory.createOntologyModel(spec, null);
        model2 = ModelFactory.createOntologyModel(spec, null);
        model1.read(op1);
        model2.read(op2);
        alignpath = ap;
    }

    public AlignmentReader2(OntModel m1, OntModel m2, String ap)
    {
        model1 = m1;
        model2 = m2;
        alignpath = ap;
    }

    public AlignmentSet read()
    {
        try {
            File file = new File(alignpath);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            Element align = root.element("Alignment");
            Iterator map = align.elementIterator("map");
            if (!map.hasNext()) {
                return null;
            }
            AlignmentSet alignmentSet = new AlignmentSet();
            while (map.hasNext()) {
                Element cell = ((Element) map.next()).element("Cell");
                if (cell == null) {
                    continue;
                }
                String s1 = cell.element("entity1").attributeValue("resource");
                String s2 = cell.element("entity2").attributeValue("resource");
                Resource e1 = model1.getResource(s1);
                Resource e2 = model2.getResource(s2);
                if (e1 == null || e2 == null) {
                    System.err.println("readError: Cannot find such entity.");
                    continue;
                }
                Node n1 = new NodeImpl(e1), n2 = new NodeImpl(e2);
                double sim = Double.parseDouble(cell.elementText("measure"));
                String rel = cell.elementText("relation");
                Alignment alignment = new Alignment(n1, n2, sim, rel);
                alignmentSet.addAlignment(alignment);
            }
            return alignmentSet;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
