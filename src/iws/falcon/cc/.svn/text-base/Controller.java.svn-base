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

import iws.falcon.matcher.AbstractMatcher;
import iws.falcon.matcher.gmo.ExternalMatch;
import iws.falcon.matcher.gmo.GmoMatcher;
import iws.falcon.matcher.pbm.BlockMapping;
import iws.falcon.matcher.pbm.PbmMatcher;
import iws.falcon.matcher.post.Patcher;
import iws.falcon.matcher.string.StringMatcher;
import iws.falcon.matcher.vdoc.VDocMatcher;
import iws.falcon.matrix.NamedMatrix;
import iws.falcon.model.Constants;
import iws.falcon.model.Node;
import iws.falcon.model.NodeCategory;
import iws.falcon.model.RBGModel;
import iws.falcon.model.RBGModelFactory;
import iws.falcon.output.Alignment;
import iws.falcon.output.AlignmentReader2;
import iws.falcon.output.AlignmentSelector;
import iws.falcon.output.AlignmentSet;
import iws.falcon.output.AlignmentWriter2;
import iws.falcon.output.Evaluator;
import iws.falcon.output.ResultData;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author Wei Hu
 */
public class Controller
{
    private String filepath1 = null,  filepath2 = null;
    
    private int stringComp1 = -1;
    private int vdocComp1 = -1,  vdocComp2 = -1;
    private int gmoComp1 = -1,  gmoComp2 = -1;
    
    private int namedClassNumA = 0,  namedClassNumB = 0;
    private int namedPropertyNumA = 0,  namedPropertyNumB = 0;
    private int namedInstanceNumA = 0,  namedInstanceNumB = 0;
    
    private OntModel model1 = null, model2 = null;

    public Controller()
    {
        filepath1 = Parameters.onto1;
        filepath2 = Parameters.onto2;
    }

    public Controller(String fp1, String fp2)
    {
        filepath1 = fp1;
        filepath2 = fp2;
    }
    
    public OntModel getOntModel1()
    {
        return model1;
    }
    
    public OntModel getOntModel2()
    {
        return model2;
    }

    public AlignmentSet run()
    {
        OntDocumentManager mgr = new OntDocumentManager();
        mgr.setProcessImports(false);
        OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
        spec.setDocumentManager(mgr);

        model1 = ModelFactory.createOntologyModel(spec, null);
        model1.read(filepath1);
        model2 = ModelFactory.createOntologyModel(spec, null);
        model2.read(filepath2);

        int size1 = 0, size2 = 0;
        for (StmtIterator i = model1.listStatements(); i.hasNext(); i.next()) {
            size1++;
        }
        for (StmtIterator i = model2.listStatements(); i.hasNext(); i.next()) {
            size2++;
        }

        if (size1 >= Parameters.largeOnto || size2 >= Parameters.largeOnto) {
            RBGModel rbgm1 = RBGModelFactory.createModel("PBM_MODEL");
            rbgm1.setOntModel(model1);
            RBGModel rbgm2 = RBGModelFactory.createModel("PBM_MODEL");
            rbgm2.setOntModel(model2);

            String wd = "./temp/";
            File file = new File(wd);
            if (file.exists() == false) {
                file.mkdir();
            }
            String fn1 = new File(filepath1).getName();
            String fn2 = new File(filepath2).getName();

            PbmMatcher pbm = new PbmMatcher(rbgm1, rbgm2, fn1, fn2, wd);
            pbm.match();
            AlignmentSet anchors = pbm.getAnchors();
            ArrayList blockMappings = pbm.getBlockMappings();

            AlignmentSet alignSet = new AlignmentSet();
            for (int i = 0; i < blockMappings.size(); i++) {
                BlockMapping bm = (BlockMapping) blockMappings.get(i);
                String f1 = bm.getClusterName1();
                String f2 = bm.getClusterName2();
                OntModel m1 = ModelFactory.createOntologyModel(spec, null);
                OntModel m2 = ModelFactory.createOntologyModel(spec, null);
                m1.read("File:" + f1);
                m2.read("File:" + f2);
                AlignmentSet tempSet = run(m1, m2);
                for (int j = 0; j < tempSet.size(); j++) {
                    alignSet.addAlignment(tempSet.getAlignment(j));
                }
            }
            for (int i = 0, n = anchors.size(); i < n; i++) {
                Alignment anchor = (Alignment) anchors.getAlignment(i);
                boolean flag = true;
                for (int k = 0; k < alignSet.size(); k++) {
                    Alignment temp = alignSet.getAlignment(k);
                    if (anchor.equals(temp)) {
                        flag = false;
                        if (temp.getSimilarity() < anchor.getSimilarity()) {
                            temp.setSimilarity(anchor.getSimilarity());
                        }
                    }
                }
                if (flag == true) {
                    alignSet.addAlignment(anchor);
                }
            }
            Patcher patcher = new Patcher(alignSet);
            patcher.match();
            return alignSet;
        } else {
            return run(model1, model2);
        }
    }

    private AlignmentSet run(OntModel model1, OntModel model2)
    {
        RBGModel rbgmVDoc1 = RBGModelFactory.createModel("VDOC_MODEL");
        rbgmVDoc1.setOntModel(model1);
        RBGModel rbgmVDoc2 = RBGModelFactory.createModel("VDOC_MODEL");
        rbgmVDoc2.setOntModel(model2);
        VDocMatcher vdoc = new VDocMatcher(rbgmVDoc1, rbgmVDoc2);
        vdoc.match();
        AlignmentSet vdocAS = vdoc.getAlignmentSet();
        AlignmentSet classVDocAS = vdoc.getClassAlignmentSet();
        AlignmentSet propertyVDocAS = vdoc.getPropertyAlignmentSet();
        AlignmentSet schemaVDocAS = combine(classVDocAS, propertyVDocAS);
        LingComp comp1 = new LingComp(rbgmVDoc1, rbgmVDoc2);
        vdocComp1 = comp1.estimate1(schemaVDocAS);
        vdocComp2 = comp1.estimate2(vdocAS);

        RBGModel rbgmString1 = RBGModelFactory.createModel("STRING_MODEL");
        rbgmString1.setOntModel(model1);
        RBGModel rbgmString2 = RBGModelFactory.createModel("STRING_MODEL");
        rbgmString2.setOntModel(model2);
        StringMatcher string = new StringMatcher(rbgmString1, rbgmString2);
        string.match();
        AlignmentSet classStringAS = string.getClassAlignmentSet();
        AlignmentSet propertyStringAS = string.getPropertyAlignmentSet();
        AlignmentSet schemaStringAS = combine(classStringAS, propertyStringAS);
        LingComp comp2 = new LingComp(rbgmString1, rbgmString2);
        stringComp1 = comp2.estimate1(schemaStringAS);

        RBGModel rbgmGmo1 = RBGModelFactory.createModel("GMO_MODEL");
        rbgmGmo1.setOntModel(model1);
        RBGModel rbgmGmo2 = RBGModelFactory.createModel("GMO_MODEL");
        rbgmGmo2.setOntModel(model2);
        StruComp comp3 = new StruComp(rbgmGmo1, rbgmGmo2);
        gmoComp1 = comp3.estimate1();
        countNodesByCategory(rbgmGmo1, rbgmGmo2);
        if (gmoComp1 == Parameters.highComp) {
            if (stringComp1 == Parameters.highComp || vdocComp1 == Parameters.highComp) {
                if (stringComp1 == vdocComp1) {
                    boolean flag1 = isAllFound(vdoc);
                    boolean flag2 = isAllFound(string);
                    if (flag1 == true && flag2 == true) {
                        NamedMatrix nmcVDoc = vdoc.getClassMatrix();
                        NamedMatrix nmcString = string.getClassMatrix();
                        NamedMatrix nmc = combine(nmcVDoc, nmcString);
                        AlignmentSet escCombine = AlignmentSelector.select(nmc,
                                Parameters.lingLowSim);
                        NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
                        NamedMatrix nmpString = string.getPropertyMatrix();
                        NamedMatrix nmp = combine(nmpVDoc, nmpString);
                        AlignmentSet espCombine = AlignmentSelector.select(nmp,
                                Parameters.lingLowSim);
                        return combine(escCombine, espCombine);
                    } else if (flag1 == true) {
                        return schemaStringAS.cut(Parameters.lingLowSim);
                    } else if (flag2 == true) {
                        return schemaVDocAS.cut(Parameters.lingLowSim);
                    } else {
                        GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
                        ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
                        NamedMatrix nmcVDoc = vdoc.getClassMatrix();
                        NamedMatrix nmcString = string.getClassMatrix();
                        NamedMatrix nmc = combine(nmcVDoc, nmcString);
                        AlignmentSet escCombine = AlignmentSelector.select(nmc,
                                Parameters.lingLowSim);
                        NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
                        NamedMatrix nmpString = string.getPropertyMatrix();
                        NamedMatrix nmp = combine(nmpVDoc, nmpString);
                        AlignmentSet espCombine = AlignmentSelector.select(nmp,
                                Parameters.lingLowSim);
                        NamedMatrix nmiVDoc = vdoc.getInstanceMatrix();
                        NamedMatrix nmiString = string.getInstanceMatrix();
                        NamedMatrix nmi = combine(nmiVDoc, nmiString);
                        AlignmentSet esiCombine = AlignmentSelector.select(nmi,
                                Parameters.lingLowSim);
                        ext.setClassSim(escCombine);
                        ext.setPropertySim(espCombine);
                        ext.setInstanceSim(esiCombine);
                        gmo.setExternalMatch(ext);
                        gmo.match();
                        AlignmentSet escGmo = gmo.getClassAlignmentSet();
                        AlignmentSet espGmo = gmo.getPropertyAlignmentSet();
                        AlignmentSet esGmo = combine(escGmo, espGmo);
                        return combine(combine(escCombine, espCombine), esGmo);
                    }
                } else if (stringComp1 > vdocComp1) {
                    boolean flag = isAllFound(string);
                    if (flag == true) {
                        return schemaStringAS.cut(Parameters.lingLowSim);
                    } else {
                        GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
                        ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
                        AlignmentSet escString = classStringAS.cut(Parameters.lingLowSim);
                        AlignmentSet espString = propertyStringAS.cut(Parameters.lingLowSim);
                        AlignmentSet esiString = string.getInstanceAlignmentSet().cut(Parameters.lingLowSim);
                        ext.setClassSim(escString);
                        ext.setPropertySim(espString);
                        ext.setInstanceSim(esiString);
                        gmo.setExternalMatch(ext);
                        gmo.match();
                        AlignmentSet escGmo = gmo.getClassAlignmentSet();
                        AlignmentSet espGmo = gmo.getPropertyAlignmentSet();
                        AlignmentSet esGmo = combine(escGmo, espGmo);
                        return combine(combine(escString, espString), esGmo);
                    }
                } else {
                    boolean flag = isAllFound(vdoc);
                    if (flag == true) {
                        return schemaVDocAS.cut(Parameters.lingLowSim);
                    } else {
                        GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
                        ExternalMatch ext = new ExternalMatch(rbgmGmo2, rbgmGmo1);
                        AlignmentSet escVDoc = classVDocAS.cut(Parameters.lingLowSim);
                        AlignmentSet espVDoc = propertyVDocAS.cut(Parameters.lingLowSim);
                        AlignmentSet esiVDoc = vdoc.getInstanceAlignmentSet().cut(Parameters.lingLowSim);
                        ext.setClassSim(escVDoc);
                        ext.setPropertySim(espVDoc);
                        ext.setInstanceSim(esiVDoc);
                        gmo.setExternalMatch(ext);
                        gmo.match();
                        AlignmentSet escGmo = gmo.getClassAlignmentSet();
                        AlignmentSet espGmo = gmo.getPropertyAlignmentSet();
                        AlignmentSet esGmo = combine(escGmo, espGmo);
                        return combine(combine(escVDoc, espVDoc), esGmo);
                    }
                }
            } else if (stringComp1 == Parameters.mediumComp || vdocComp1 == Parameters.mediumComp) {
                if (stringComp1 == vdocComp1) {
                    NamedMatrix nmcVDoc = vdoc.getClassMatrix();
                    NamedMatrix nmcString = string.getClassMatrix();
                    NamedMatrix nmc = combine(nmcVDoc, nmcString);
                    AlignmentSet escCombine = AlignmentSelector.select(nmc,
                            Parameters.lingHighSim);
                    NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
                    NamedMatrix nmpString = string.getPropertyMatrix();
                    NamedMatrix nmp = combine(nmpVDoc, nmpString);
                    AlignmentSet espCombine = AlignmentSelector.select(nmp,
                            Parameters.lingHighSim);
                    AlignmentSet esCombine = combine(escCombine, espCombine);
                    GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
                    gmo.match();
                    AlignmentSet escGmo = gmo.getClassAlignmentSet();
                    AlignmentSet espGmo = gmo.getPropertyAlignmentSet();
                    AlignmentSet esGmo = combine(escGmo, espGmo);
                    gmoComp2 = comp3.estimate2(esCombine, esGmo);
                    if (gmoComp2 == Parameters.highComp) {
                        return esGmo;
                    } else {
                        return esCombine;
                    }
                } else if (stringComp1 > vdocComp1) {
                    AlignmentSet esString = schemaStringAS.cut(Parameters.lingHighSim);
                    GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
                    gmo.match();
                    AlignmentSet escGmo = gmo.getClassAlignmentSet();
                    AlignmentSet espGmo = gmo.getPropertyAlignmentSet();
                    AlignmentSet esGmo = combine(escGmo, espGmo);
                    gmoComp2 = comp3.estimate2(esString, esGmo);
                    if (gmoComp2 == Parameters.highComp) {
                        return esGmo;
                    } else {
                        return esString;
                    }
                } else {
                    AlignmentSet esVDoc = schemaVDocAS.cut(Parameters.lingHighSim);
                    GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
                    gmo.match();
                    AlignmentSet escGmo = gmo.getClassAlignmentSet();
                    AlignmentSet espGmo = gmo.getPropertyAlignmentSet();
                    AlignmentSet esGmo = combine(escGmo, espGmo);
                    gmoComp2 = comp3.estimate2(esVDoc, esGmo);
                    if (gmoComp2 == Parameters.highComp) {
                        return esGmo;
                    } else {
                        return esVDoc;
                    }
                }
            } else if (vdocComp2 > Parameters.lowComp) {
                AlignmentSet esVDoc = schemaVDocAS.cut(Parameters.lingLowSim);
                GmoMatcher gmo = new GmoMatcher(rbgmGmo2, rbgmGmo1);
                gmo.match();
                AlignmentSet escGmo = gmo.getClassAlignmentSet();
                AlignmentSet espGmo = gmo.getPropertyAlignmentSet();
                AlignmentSet esGmo = combine(escGmo, espGmo);
                gmoComp2 = comp3.estimate2(esVDoc, esGmo);
                if (gmoComp2 == Parameters.highComp) {
                    return esGmo;
                } else {
                    return esVDoc;
                }
            } else {
                System.out.println("The two ontologies are unmatchable!");
                return new AlignmentSet();
            }
        } else {
            if (stringComp1 == Parameters.lowComp && vdocComp1 == Parameters.lowComp) {
                if (vdocComp2 > Parameters.lowComp) {
                    return schemaVDocAS.cut(Parameters.lingLowSim);
                } else {
                    System.out.println("The two ontologies are unmatchable!");
                    return new AlignmentSet();
                }
            } else {
                if (stringComp1 == vdocComp1) {
                    NamedMatrix nmcVDoc = vdoc.getClassMatrix();
                    NamedMatrix nmcString = string.getClassMatrix();
                    NamedMatrix nmc = combine(nmcVDoc, nmcString);
                    AlignmentSet escCombine = AlignmentSelector.select(nmc,
                            Parameters.lingHighSim);
                    NamedMatrix nmpVDoc = vdoc.getPropertyMatrix();
                    NamedMatrix nmpString = string.getPropertyMatrix();
                    NamedMatrix nmp = combine(nmpVDoc, nmpString);
                    AlignmentSet espCombine = AlignmentSelector.select(nmp,
                            Parameters.lingHighSim);
                    return combine(escCombine, espCombine);
                } else if (stringComp1 > vdocComp1) {
                    return schemaStringAS.cut(Parameters.lingHighSim);
                } else {
                    return schemaVDocAS.cut(Parameters.lingHighSim);
                }
            }
        }
    }

    private void countNodesByCategory(RBGModel modelA, RBGModel modelB)
    {
        for (Iterator iter = modelA.listNodes(); iter.hasNext();) {
            Node node = (Node) iter.next();
            int category = NodeCategory.getCategoryWithoutExternal(node);
            if (category == Constants.ONTOLOGY_CLASS) {
                if (!node.isAnon()) {
                    namedClassNumA++;
                }
            } else if (category == Constants.ONTOLOGY_PROPERTY) {
                if (!node.isAnon()) {
                    namedPropertyNumA++;
                }
            } else if (category == Constants.ONTOLOGY_INSTANCE) {
                if (!node.isAnon()) {
                    namedInstanceNumA++;
                }
            }
        }
        for (Iterator iter = modelB.listNodes(); iter.hasNext();) {
            Node node = (Node) iter.next();
            int category = NodeCategory.getCategoryWithoutExternal(node);
            if (category == Constants.ONTOLOGY_CLASS) {
                if (!node.isAnon()) {
                    namedClassNumB++;
                }
            } else if (category == Constants.ONTOLOGY_PROPERTY) {
                if (!node.isAnon()) {
                    namedPropertyNumB++;
                }
            } else if (category == Constants.ONTOLOGY_INSTANCE) {
                if (!node.isAnon()) {
                    namedInstanceNumB++;
                }
            }
        }
    }

    private boolean isAllFound(AbstractMatcher matcher)
    {
        if (Parameters.inclInstMatch == true) {
            int sizeA = namedClassNumA + namedPropertyNumA + namedInstanceNumA;
            int sizeB = namedClassNumB + namedPropertyNumB + namedInstanceNumB;
            int size = matcher.getAlignmentSet().size(Parameters.lingLowSim);
            if (size == sizeA || size == sizeB) {
                return true;
            } else {
                return false;
            }
        } else {
            int sizeA = namedClassNumA + namedPropertyNumA;
            int sizeB = namedClassNumB + namedPropertyNumB;
            int size = matcher.getClassAlignmentSet().size(Parameters.lingLowSim);
            size += matcher.getPropertyAlignmentSet().size(Parameters.lingLowSim);
            if (size == sizeA || size == sizeB) {
                return true;
            } else {
                return false;
            }
        }
    }

    private AlignmentSet combine(AlignmentSet as1, AlignmentSet as2)
    {
        AlignmentSet alignSet = new AlignmentSet();
        if (as1 != null) {
            for (int i = 0, n = as1.size(); i < n; i++) {
                alignSet.addAlignment(as1.getAlignment(i));
            }
        }
        if (as2 != null) {
            for (int i = 0, n = as2.size(); i < n; i++) {
                alignSet.addAlignment(as2.getAlignment(i));
            }
        }
        return alignSet;
    }

    private NamedMatrix combine(NamedMatrix matrix1, NamedMatrix matrix2)
    {
        if (matrix1.numRows() != matrix2.numRows()) {
            System.err.println("combineError: Rows are not equal.");
            return null;
        } else if (matrix1.numColumns() != matrix2.numColumns()) {
            System.err.println("combineError: Columns are not equal.");
            return null;
        } else {
            ArrayList rowList = matrix1.getRowList();
            ArrayList colList = matrix1.getColList();
            NamedMatrix matrix = new NamedMatrix(matrix1);
            for (int i = 0, m = rowList.size(); i < m; i++) {
                for (int j = 0, n = colList.size(); j < n; j++) {
                    double temp = matrix2.get(rowList.get(i), colList.get(j));
                    double value = Parameters.combWeight * matrix.get(i, j);
                    value += (1 - Parameters.combWeight) * temp;
                    matrix.set(i, j, value);
                }
            }
            return matrix;
        }
    }

    public ResultData evaluate(AlignmentSet as, String fp1, String fp2, String fp4)
    {
        AlignmentSet refSet = (new AlignmentReader2(fp1, fp2, fp4)).read();
        Evaluator evaluator = new Evaluator();
        return evaluator.compare(as, refSet);
    }

    public ResultData evaluate(AlignmentSet as)
    {
        String fp4 = Parameters.reference;
        AlignmentReader2 ar2 = new AlignmentReader2(model1, model2, fp4);
        AlignmentSet rs = ar2.read();
        Evaluator evaluator = new Evaluator();
        return evaluator.compare(as, rs);
    }

    public void writeToFile(AlignmentSet as, String fp3)
    {
        AlignmentWriter2 writer = new AlignmentWriter2(as, fp3);
        File file1 = new File(filepath1);
        File file2 = new File(filepath2);
        writer.write(file1.getName(), file2.getName(),
                file1.getAbsolutePath(), file2.getAbsolutePath());
    }

    public void writeToFile(AlignmentSet as)
    {
        AlignmentWriter2 writer = new AlignmentWriter2(as, Parameters.output);
        File file1 = new File(filepath1);
        File file2 = new File(filepath2);
        writer.write(file1.getName(), file2.getName(),
                file1.getAbsolutePath(), file2.getAbsolutePath());
    }

    public static void main(String args[])
    {
        System.out.println(Calendar.getInstance().getTime().toString() + "\n");

        Configuration config = new Configuration();
        config.init();
        
        Controller controller = new Controller();
        AlignmentSet alignments = controller.run();
        controller.writeToFile(alignments);
	if(Parameters.reference != null) {
            controller.evaluate(alignments);
        }

        System.out.println("\n" + Calendar.getInstance().getTime().toString());
    }
}
