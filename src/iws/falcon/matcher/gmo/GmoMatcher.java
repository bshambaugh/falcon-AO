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
package iws.falcon.matcher.gmo;

import iws.falcon.matcher.AbstractMatcher;
import iws.falcon.matrix.BasicMatrix;
import iws.falcon.matrix.NamedMatrix;
import iws.falcon.model.Constants;
import iws.falcon.model.Node;
import iws.falcon.model.NodeCategory;
import iws.falcon.model.Quadruple;
import iws.falcon.model.RBGModel;
import iws.falcon.output.AlignmentSelector;
import iws.falcon.output.AlignmentSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class GmoMatcher implements AbstractMatcher
{
    private RBGModel rbgModelA = null;
    private RBGModel rbgModelB = null;
    private ExternalMatch external = null;
    private NamedMatrix Pk = null,  Ck = null,  Ik = null;
    private NamedMatrix EPba = null,  ECba = null,  EIba = null;
    private NamedMatrix Sk = null;
    private NamedMatrix Aeps = null,  Aecs = null,  Aeis = null;
    private NamedMatrix Aps = null,  Acs = null,  Ais = null;
    private NamedMatrix Aep = null,  Aec = null,  Aei = null;
    private NamedMatrix Apop = null,  Acop = null,  Aiop = null;
    private NamedMatrix Beps = null,  Becs = null,  Beis = null;
    private NamedMatrix Bps = null,  Bcs = null,  Bis = null;
    private NamedMatrix Bep = null,  Bec = null,  Bei = null;
    private NamedMatrix Bpop = null,  Bcop = null,  Biop = null;
    private AlignmentSet alignmentSet = null;
    private AlignmentSet classAlignmentSet = null;
    private AlignmentSet propertyAlignmentSet = null;
    private AlignmentSet instanceAlignmentSet = null;

    public GmoMatcher(RBGModel modelA, RBGModel modelB)
    {
        rbgModelA = modelA;
        rbgModelB = modelB;
    }

    public void setExternalMatch(ExternalMatch ext)
    {
        external = ext;
    }

    private void initMatrix()
    {
        ArrayList nodesAep = new ArrayList();
        ArrayList nodesAec = new ArrayList();
        ArrayList nodesAei = new ArrayList();

        ArrayList nodesAop = new ArrayList();
        ArrayList nodesAoc = new ArrayList();
        ArrayList nodesAoi = new ArrayList();
        ArrayList nodesAstmt = new ArrayList();

        ArrayList nodesBep = new ArrayList();
        ArrayList nodesBec = new ArrayList();
        ArrayList nodesBei = new ArrayList();

        ArrayList nodesBop = new ArrayList();
        ArrayList nodesBoc = new ArrayList();
        ArrayList nodesBoi = new ArrayList();
        ArrayList nodesBstmt = new ArrayList();

        Iterator nodesA = rbgModelA.listNodes();
        while (nodesA.hasNext()) {
            Node node = (Node) nodesA.next();
            switch (NodeCategory.getCategory(node)) {
                case Constants.STMT:
                    if ((node.getSubject().getNodeLevel() == Node.EXTERNAL 
                            || (node.getSubject().getCategory() == Node.INSTANCE) 
                            || (node.getSubject().getNodeLevel() == Node.LANGUAGE_LEVEL)) 
                                    && (node.getPredicate().getNodeLevel() == Node.EXTERNAL
                            || node.getPredicate().getNodeLevel() == Node.LANGUAGE_LEVEL)
                                    && (node.getObject().getNodeLevel() == Node.EXTERNAL 
                            || node.getObject().getNodeLevel() == Node.LANGUAGE_LEVEL)) {
                        break;
                    } else {
                        nodesAstmt.add(node);
                    }
                    break;
                case Constants.EXTERNAL_PROPERTY:
                    nodesAep.add(node);
                    break;
                case Constants.EXTERNAL_CLASS:
                    nodesAec.add(node);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    nodesAei.add(node);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    nodesAop.add(node);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    nodesAoc.add(node);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        nodesAei.add(node);
                    } else {
                        nodesAoi.add(node);
                    }
                    break;
                default:
                    break;
            }
        }
        Iterator nodesB = rbgModelB.listNodes();
        while (nodesB.hasNext()) {
            Node node = (Node) nodesB.next();
            switch (NodeCategory.getCategory(node)) {
                case Constants.STMT:
                    if ((node.getSubject().getNodeLevel() == Node.EXTERNAL 
                            || (node.getSubject().getCategory() == Node.INSTANCE)
                            || (node.getSubject().getNodeLevel() == Node.LANGUAGE_LEVEL)) 
                                    && ((node.getPredicate().getNodeLevel() == Node.EXTERNAL 
                            || node.getPredicate().getNodeLevel() == Node.LANGUAGE_LEVEL)) 
                                    && (node.getObject().getNodeLevel() == Node.EXTERNAL 
                            || node.getObject().getNodeLevel() == Node.LANGUAGE_LEVEL)) {
                        break;
                    } else {
                        nodesBstmt.add(node);
                    }
                    break;
                case Constants.EXTERNAL_PROPERTY:
                    nodesBep.add(node);
                    break;
                case Constants.EXTERNAL_CLASS:
                    nodesBec.add(node);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    nodesBei.add(node);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    nodesBop.add(node);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    nodesBoc.add(node);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        nodesBei.add(node);
                    } else {
                        nodesBoi.add(node);
                    }
                    break;
                default:
                    break;
            }
        }

        Aeps = new NamedMatrix(nodesAep, nodesAstmt);
        Aecs = new NamedMatrix(nodesAec, nodesAstmt);
        Aeis = new NamedMatrix(nodesAei, nodesAstmt);

        Aep = new NamedMatrix(nodesAstmt, nodesAep);
        Aec = new NamedMatrix(nodesAstmt, nodesAec);
        Aei = new NamedMatrix(nodesAstmt, nodesAei);

        Aps = new NamedMatrix(nodesAop, nodesAstmt);
        Acs = new NamedMatrix(nodesAoc, nodesAstmt);
        Ais = new NamedMatrix(nodesAoi, nodesAstmt);

        Apop = new NamedMatrix(nodesAstmt, nodesAop);
        Acop = new NamedMatrix(nodesAstmt, nodesAoc);
        Aiop = new NamedMatrix(nodesAstmt, nodesAoi);

        Beps = new NamedMatrix(nodesBep, nodesBstmt);
        Becs = new NamedMatrix(nodesBec, nodesBstmt);
        Beis = new NamedMatrix(nodesBei, nodesBstmt);

        Bep = new NamedMatrix(nodesBstmt, nodesBep);
        Bec = new NamedMatrix(nodesBstmt, nodesBec);
        Bei = new NamedMatrix(nodesBstmt, nodesBei);

        Bps = new NamedMatrix(nodesBop, nodesBstmt);
        Bcs = new NamedMatrix(nodesBoc, nodesBstmt);
        Bis = new NamedMatrix(nodesBoi, nodesBstmt);

        Bpop = new NamedMatrix(nodesBstmt, nodesBop);
        Bcop = new NamedMatrix(nodesBstmt, nodesBoc);
        Biop = new NamedMatrix(nodesBstmt, nodesBoi);

        initAdjacencyMatrix();

        EPba = new NamedMatrix(nodesBep, nodesAep);
        ECba = new NamedMatrix(nodesBec, nodesAec);
        EIba = new NamedMatrix(nodesBei, nodesAei);

        initExternalSim();

        Pk = new NamedMatrix(nodesBop, nodesAop);
        Ck = new NamedMatrix(nodesBoc, nodesAoc);
        Ik = new NamedMatrix(nodesBoi, nodesAoi);

        for (int i = 0; i < nodesBop.size(); i++) {
            for (int j = 0; j < nodesAop.size(); j++) {
                Pk.set(i, j, 1);
            }
        }
        for (int i = 0; i < nodesBoc.size(); i++) {
            for (int j = 0; j < nodesAoc.size(); j++) {
                Ck.set(i, j, 1);
            }
        }
        for (int i = 0; i < nodesBoi.size(); i++) {
            for (int j = 0; j < nodesAoi.size(); j++) {
                Ik.set(i, j, 1);
            }
        }
        Sk = new NamedMatrix(nodesBstmt, nodesAstmt);

        for (int i = 0; i < nodesBstmt.size(); i++) {
            for (int j = 0; j < nodesAstmt.size(); j++) {
                Sk.set(i, j, 1);
            }
        }
    }

    private void initAdjacencyMatrix()
    {
        Iterator quadruplesA = rbgModelA.listQuadruples();
        while (quadruplesA.hasNext()) {
            Quadruple quadruple = (Quadruple) quadruplesA.next();
            Node subject = quadruple.getSubject();
            Node predicate = quadruple.getPredicate();
            Node object = quadruple.getObject();
            Node stmt = quadruple.getStatement();
            if ((subject.getNodeLevel() == Node.EXTERNAL 
                    || (subject.getCategory() == Node.INSTANCE)
                    || (subject.getNodeLevel() == Node.LANGUAGE_LEVEL)) 
                            && ((predicate.getNodeLevel() == Node.EXTERNAL
                    || predicate.getNodeLevel() == Node.LANGUAGE_LEVEL))
                            && (object.getNodeLevel() == Node.EXTERNAL 
                    || object.getNodeLevel() == Node.LANGUAGE_LEVEL)) {
                continue;
            }
            switch (NodeCategory.getCategory(subject)) {
                case Constants.EXTERNAL_PROPERTY:
                    Aeps.set(subject, stmt, 1);
                    break;
                case Constants.EXTERNAL_CLASS:
                    Aecs.set(subject, stmt, 1);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    Aeis.set(subject, stmt, 1);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    Aps.set(subject, stmt, 1);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    Acs.set(subject, stmt, 1);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        Aeis.set(subject, stmt, 1);
                    } else {
                        Ais.set(subject, stmt, 1);
                    }
                    break;
                default:
                    break;
            }
            switch (NodeCategory.getCategory(predicate)) {
                case Constants.EXTERNAL_PROPERTY:
                    Aep.set(stmt, predicate, 1);
                    break;
                case Constants.EXTERNAL_CLASS:
                    Aec.set(stmt, predicate, 1);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    Aei.set(stmt, predicate, 1);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    Apop.set(stmt, predicate, 1);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    Acop.set(stmt, predicate, 1);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        Aei.set(stmt, predicate, 1);
                    } else {
                        Aiop.set(stmt, predicate, 1);
                    }
                    break;
                default:
                    break;
            }
            switch (NodeCategory.getCategory(object)) {
                case Constants.EXTERNAL_PROPERTY:
                    Aep.set(stmt, object, 1);
                    break;
                case Constants.EXTERNAL_CLASS:
                    Aec.set(stmt, object, 1);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    Aei.set(stmt, object, 1);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    Apop.set(stmt, object, 1);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    Acop.set(stmt, object, 1);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        Aei.set(stmt, object, 1);
                    } else {
                        Aiop.set(stmt, object, 1);
                    }
                    break;
                default:
                    break;
            }
        }

        Iterator quadruplesB = rbgModelB.listQuadruples();
        while (quadruplesB.hasNext()) {
            Quadruple quadruple = (Quadruple) quadruplesB.next();
            Node subject = quadruple.getSubject();
            Node predicate = quadruple.getPredicate();
            Node object = quadruple.getObject();
            Node stmt = quadruple.getStatement();
            if ((subject.getNodeLevel() == Node.EXTERNAL
                    || (subject.getCategory() == Node.INSTANCE) 
                    || (subject.getNodeLevel() == Node.LANGUAGE_LEVEL)) 
                            && ((predicate.getNodeLevel() == Node.EXTERNAL 
                    || predicate.getNodeLevel() == Node.LANGUAGE_LEVEL)) 
                            && (object.getNodeLevel() == Node.EXTERNAL 
                    || object.getNodeLevel() == Node.LANGUAGE_LEVEL)) {
                continue;
            }
            switch (NodeCategory.getCategory(subject)) {
                case Constants.EXTERNAL_PROPERTY:
                    Beps.set(subject, stmt, 1);
                    break;
                case Constants.EXTERNAL_CLASS:
                    Becs.set(subject, stmt, 1);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    Beis.set(subject, stmt, 1);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    Bps.set(subject, stmt, 1);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    Bcs.set(subject, stmt, 1);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        Beis.set(subject, stmt, 1);
                    } else {
                        Bis.set(subject, stmt, 1);
                    }
                    break;
                default:
                    break;
            }
            switch (NodeCategory.getCategory(predicate)) {
                case Constants.EXTERNAL_PROPERTY:
                    Bep.set(stmt, predicate, 1);
                    break;
                case Constants.EXTERNAL_CLASS:
                    Bec.set(stmt, predicate, 1);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    Bei.set(stmt, predicate, 1);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    Bpop.set(stmt, predicate, 1);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    Bcop.set(stmt, predicate, 1);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        Bei.set(stmt, predicate, 1);
                    } else {
                        Biop.set(stmt, predicate, 1);
                    }
                    break;
                default:
                    break;
            }
            switch (NodeCategory.getCategory(object)) {
                case Constants.EXTERNAL_PROPERTY:
                    Bep.set(stmt, object, 1);
                    break;
                case Constants.EXTERNAL_CLASS:
                    Bec.set(stmt, object, 1);
                    break;
                case Constants.EXTERNAL_INSTANCE:
                    Bei.set(stmt, object, 1);
                    break;
                case Constants.ONTOLOGY_PROPERTY:
                    Bpop.set(stmt, object, 1);
                    break;
                case Constants.ONTOLOGY_CLASS:
                    Bcop.set(stmt, object, 1);
                    break;
                case Constants.ONTOLOGY_INSTANCE:
                    if (Parameters.inclInstMatch == false) {
                        Bei.set(stmt, object, 1);
                    } else {
                        Biop.set(stmt, object, 1);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void initExternalSim()
    {
        ArrayList nodesBep = EPba.getRowList();
        ArrayList nodesAep = EPba.getColList();
        ArrayList nodesBec = ECba.getRowList();
        ArrayList nodesAec = ECba.getColList();
        ArrayList nodesBei = EIba.getRowList();
        ArrayList nodesAei = EIba.getColList();

        if (external == null) {
            external = new ExternalMatch(rbgModelB, rbgModelA);
        }
        for (int i = 0; i < nodesBep.size(); i++) {
            for (int j = 0; j < nodesAep.size(); j++) {
                double sim = external.getSimilarity((Node) nodesBep.get(i),
                        (Node) nodesAep.get(j));
                if (sim != 0) {
                    EPba.set(i, j, sim);
                }
            }
        }
        for (int i = 0; i < nodesBec.size(); i++) {
            for (int j = 0; j < nodesAec.size(); j++) {
                double sim = external.getSimilarity((Node) nodesBec.get(i),
                        (Node) nodesAec.get(j));
                if (sim != 0) {
                    ECba.set(i, j, sim);
                }
            }
        }
        for (int i = 0; i < nodesBei.size(); i++) {
            for (int j = 0; j < nodesAei.size(); j++) {
                double sim = external.getSimilarity((Node) nodesBei.get(i),
                        (Node) nodesAei.get(j));
                if (sim != 0) {
                    EIba.set(i, j, sim);
                }
            }
        }
    }

    public void match()
    {
        initMatrix();

        double square = 0;
        double norm = 1;

        for (int i = 0; i < Parameters.iterTimes; i++) {
            BasicMatrix tempPk = new BasicMatrix(Pk);
            BasicMatrix tempCk = new BasicMatrix(Ck);
            BasicMatrix tempIk = new BasicMatrix(Ik);
            BasicMatrix tempSk = new BasicMatrix(Sk);

            Pk.setMatrix(Bps.multi(Sk).multi(Aps.trans()).copyAdd(
                    Bpop.trans().multi(Sk).multi(Apop)));
            Ck.setMatrix(Bcs.multi(Sk).multi(Acs.trans()).copyAdd(
                    Bcop.trans().multi(Sk).multi(Acop)));
            Ik.setMatrix(Bis.multi(Sk).multi(Ais.trans()).copyAdd(
                    Biop.trans().multi(Sk).multi(Aiop)));

            /*
             * Sk = block11 + block12 + block13 + block21 + block22 + block23 +
             * block31 + block32 + block33 + block41 + block42 + block43
             */
            BasicMatrix block11 = Beps.trans().multi(EPba).multi(Aeps);
            BasicMatrix block12 = Becs.trans().multi(ECba).multi(Aecs);
            BasicMatrix block13 = Beis.trans().multi(EIba).multi(Aeis);
            BasicMatrix block21 = Bep.multi(EPba).multi(Aep.trans());
            BasicMatrix block22 = Bec.multi(ECba).multi(Aec.trans());
            BasicMatrix block23 = Bei.multi(EIba).multi(Aei.trans());
            BasicMatrix block31 = Bpop.multi(tempPk).multi(Apop.trans());
            BasicMatrix block32 = Bcop.multi(tempCk).multi(Acop.trans());
            BasicMatrix block33 = Biop.multi(tempIk).multi(Aiop.trans());
            BasicMatrix block41 = Bps.trans().multi(tempPk).multi(Aps);
            BasicMatrix block42 = Bcs.trans().multi(tempCk).multi(Acs);
            BasicMatrix block43 = Bis.trans().multi(tempIk).multi(Ais);
            Sk.setMatrix(block11.copyAdd(block12).copyAdd(block13).copyAdd(
                    block21).copyAdd(block22).copyAdd(block23).copyAdd(
                    block31).copyAdd(block32).copyAdd(block33).copyAdd(
                    block41).copyAdd(block42).copyAdd(block43));

            square = Pk.sumEntriesSquare() + Ck.sumEntriesSquare() 
                    + Ik.sumEntriesSquare() + Sk.sumEntriesSquare();
            norm = 1 / Math.sqrt(square);

            Pk.setMatrix(Pk.times(norm));
            Ck.setMatrix(Ck.times(norm));
            Ik.setMatrix(Ik.times(norm));
            Sk.setMatrix(Sk.times(norm));

            if ((i % 2) == 1 && Sk.distance(tempSk) < Parameters.convergence) {
                break;
            }
        }

        Pk.norm();
        Ck.norm();
        Ik.norm();
        Sk.norm();

        alignmentSet = new AlignmentSet();

        int rows = 0, columns = 0;
        ArrayList rowList = Ck.getRowList(), colList = Ck.getColList();
        ArrayList newRowList = new ArrayList();
        ArrayList newColList = new ArrayList();
        for (int i = 0; i < rowList.size(); i++) {
            Node node = (Node) rowList.get(i);
            if (!node.isAnon()) {
                rows++;
                newRowList.add(node);
            }
        }
        for (int j = 0; j < colList.size(); j++) {
            Node node = (Node) colList.get(j);
            if (!node.isAnon()) {
                columns++;
                newColList.add(node);
            }
        }
        NamedMatrix namedClassMatrix = new NamedMatrix(newRowList, newColList);
        for (int i = 0; i < rows; i++) {
            int r = Ck.getRowIndex(newRowList.get(i));
            for (int j = 0; j < columns; j++) {
                int c = Ck.getColumnIndex(newColList.get(j));
                namedClassMatrix.set(i, j, Ck.get(r, c));
            }
        }
        classAlignmentSet = AlignmentSelector.select(namedClassMatrix,
                Parameters.threshold);
        for (int i = 0, n = classAlignmentSet.size(); i < n; i++) {
            alignmentSet.addAlignment(classAlignmentSet.getAlignment(i));
        }

        propertyAlignmentSet = AlignmentSelector.select(Pk,
                Parameters.threshold);
        for (int i = 0, n = propertyAlignmentSet.size(); i < n; i++) {
            alignmentSet.addAlignment(propertyAlignmentSet.getAlignment(i));
        }

        if (Parameters.inclInstMatch) {
            rows = 0;
            columns = 0;
            rowList = Ik.getRowList();
            colList = Ik.getColList();
            newRowList = new ArrayList();
            newColList = new ArrayList();
            for (int i = 0; i < rowList.size(); i++) {
                Node node = (Node) rowList.get(i);
                if (!node.isAnon()) {
                    rows++;
                    newRowList.add(node);
                }
            }
            for (int j = 0; j < colList.size(); j++) {
                Node node = (Node) colList.get(j);
                if (!node.isAnon()) {
                    columns++;
                    newColList.add(node);
                }
            }
            NamedMatrix namedInstanceMatrix = new NamedMatrix(newRowList,
                    newColList);
            for (int i = 0; i < rows; i++) {
                int r = Ik.getRowIndex(newRowList.get(i));
                for (int j = 0; j < columns; j++) {
                    int c = Ik.getColumnIndex(newColList.get(j));
                    namedInstanceMatrix.set(i, j, Ck.get(r, c));
                }
            }
            instanceAlignmentSet = AlignmentSelector.select(
                    namedInstanceMatrix, Parameters.threshold);
            for (int i = 0, n = instanceAlignmentSet.size(); i < n; i++) {
                alignmentSet.addAlignment(instanceAlignmentSet.getAlignment(i));
            }
        }
    }

    public AlignmentSet getAlignmentSet()
    {
        return alignmentSet;
    }

    public AlignmentSet getClassAlignmentSet()
    {
        return classAlignmentSet;
    }

    public AlignmentSet getPropertyAlignmentSet()
    {
        return propertyAlignmentSet;
    }

    public AlignmentSet getInstanceAlignmentSet()
    {
        return instanceAlignmentSet;
    }
}
