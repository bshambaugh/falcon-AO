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
package iws.falcon.ext.sentence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import iws.falcon.ext.sentence.filter.RDFSentenceFilter;
import iws.falcon.ext.sentence.recognition.IndividualRecognition;
import iws.falcon.model.Constants;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

/**
 * @author Gong Cheng
 */
public class RDFSentenceGraph
{
    /* Whether process imports */
    public static boolean processImports = false;

    /* All the RDF sentences */
    private ArrayList RDFSentences = null;

    /* All the vocabularies */
    private ArrayList vocabularies = null;

    /* Hash the URIs to vocabularies */
    private HashMap URIToVocabulary = null;

    /* The Jena OntModel */
    private OntModel ontModel = null;

    /* The ConceptRecognition */
    private IndividualRecognition conceptRecog = null;

    /* All the URIs of ontologies */
    private ArrayList ontologyURIs = null;

    /* Internal use only */
    private HashMap sIDToOIDs = null;
    private HashMap oIDToIDs = null;
    private HashMap sID_oIDToStmts = null;

    public RDFSentenceGraph(String URL)
    {
        OntDocumentManager ontDocumentManager = new OntDocumentManager();
        ontDocumentManager.setProcessImports(RDFSentenceGraph.processImports);
        OntModelSpec ontModelSpec = new OntModelSpec(OntModelSpec.RDFS_MEM);
        ontModelSpec.setDocumentManager(ontDocumentManager);
        ontModel = ModelFactory.createOntologyModel(ontModelSpec);
        ontModel.read(URL);
    }

    public RDFSentenceGraph(OntModel model)
    {
        ontModel = model;
    }

    public RDFSentenceGraph(InputStream inputStream)
    {
        OntDocumentManager ontDocumentManager = new OntDocumentManager();
        ontDocumentManager.setProcessImports(RDFSentenceGraph.processImports);
        OntModelSpec ontModelSpec = new OntModelSpec(OntModelSpec.RDFS_MEM);
        ontModelSpec.setDocumentManager(ontDocumentManager);
        ontModel = ModelFactory.createOntologyModel(ontModelSpec);
        ontModel.read(inputStream, null);
    }

    public void setConceptRecognition(IndividualRecognition cr)
    {
        conceptRecog = cr;
    }

    public void build()
    {
        init(ontModel.listStatements());
        buildRDFSentences(ontModel.listStatements());
    // ontModel.close();
    // ontModel = null;
    }

    private void init(Iterator statements)
    {
        RDFSentences = new ArrayList();
        vocabularies = new ArrayList();
        URIToVocabulary = new HashMap();
        ontologyURIs = new ArrayList();

        sIDToOIDs = new HashMap();
        oIDToIDs = new HashMap();
        sID_oIDToStmts = new HashMap();

        ArrayList rdfTypeStmts = new ArrayList();
        while (statements.hasNext()) {
            Statement statement = (Statement) statements.next();
            RDFNode subject = statement.getSubject();
            RDFNode predicate = statement.getPredicate();
            RDFNode object = statement.getObject();
            String subjectURI = subject.toString();
            String predicateURI = predicate.toString();
            String objectURI = object.toString();

            if (!URIToVocabulary.containsKey(subjectURI)) {
                Vocabulary vocabulary = new Vocabulary(subjectURI,
                        vocabularies.size());
                vocabularies.add(vocabulary);
                URIToVocabulary.put(subjectURI, vocabulary);
                if (subject.isURIResource()) {
                    if (Constants.isBuiltInNs(((Resource) subject.as(
                            Resource.class)).getNameSpace())) {
                        vocabulary.setType(Vocabulary.BuiltinVocabulary);
                    } else {
                        vocabulary.setType(Vocabulary.DomainVocabulary);
                    }
                } else {
                    vocabulary.setType(Vocabulary.AnonymousResource);
                }
            }
            if (!URIToVocabulary.containsKey(predicateURI)) {
                Vocabulary vocabulary = new Vocabulary(predicateURI,
                        vocabularies.size());
                vocabularies.add(vocabulary);
                URIToVocabulary.put(predicateURI, vocabulary);
                if (Constants.isBuiltInNs(((Resource) predicate.as(
                        Resource.class)).getNameSpace())) {
                    vocabulary.setType(Vocabulary.BuiltinVocabulary);
                } else {
                    vocabulary.setType(Vocabulary.DomainVocabulary);
                }
            }
            if (!URIToVocabulary.containsKey(objectURI)) {
                Vocabulary vocabulary = new Vocabulary(objectURI, 
                        vocabularies.size());
                vocabularies.add(vocabulary);
                URIToVocabulary.put(objectURI, vocabulary);
                if (object.isURIResource()) {
                    if (Constants.isBuiltInNs(((Resource) object.as(
                            Resource.class)).getNameSpace())) {
                        vocabulary.setType(Vocabulary.BuiltinVocabulary);
                    } else {
                        vocabulary.setType(Vocabulary.DomainVocabulary);
                    }
                } else if (object.isAnon()) {
                    vocabulary.setType(Vocabulary.AnonymousResource);
                } else {
                    vocabulary.setType(Vocabulary.Literal);
                }
            }

            if (subject.isAnon() || object.isAnon()) {
                String sID = String.valueOf(((Vocabulary) URIToVocabulary.get(
                        subjectURI)).getID());
                String oID = String.valueOf(((Vocabulary) URIToVocabulary.get(
                        objectURI)).getID());
                String s_o = sID + "_" + oID;
                ArrayList s_oStatements = (ArrayList) sID_oIDToStmts.get(s_o);
                if (s_oStatements == null) {
                    s_oStatements = new ArrayList(1);
                    sID_oIDToStmts.put(s_o, s_oStatements);
                }
                s_oStatements.add(statement);

                if (subject.isAnon()) {
                    ArrayList oIDs = (ArrayList) sIDToOIDs.get(sID);
                    if (oIDs == null) {
                        oIDs = new ArrayList(1);
                        sIDToOIDs.put(sID, oIDs);
                    }
                    if (!oIDs.contains(oID)) {
                        oIDs.add(oID);
                    }
                }
                if (object.isAnon()) {
                    ArrayList sIDs = (ArrayList) oIDToIDs.get(oID);
                    if (sIDs == null) {
                        sIDs = new ArrayList(1);
                        oIDToIDs.put(oID, sIDs);
                    }
                    if (!sIDs.contains(sID)) {
                        sIDs.add(sID);
                    }
                }
            }

            if (predicateURI.equals(Constants.RDF_NS + "type")) {
                String ns = ((Resource) object.as(Resource.class)).getNameSpace();
                if (conceptRecog != null) {
                    rdfTypeStmts.add(statement);
                } else if (ns != null && !Constants.isBuiltInNs(ns) 
                        || objectURI.equals(Constants.OWL_NS + "Thing")) {
                    ((Vocabulary) URIToVocabulary.get(subjectURI)).setIndividual();
                }
                if (objectURI.equals(Constants.OWL_NS + "Ontology")) {
                    ontologyURIs.add(subjectURI);
                }
            }
        }

        if (conceptRecog != null) {
            for (Iterator conceptURIs = conceptRecog.getIndividualURIs(
                    rdfTypeStmts); conceptURIs.hasNext();) {
                ((Vocabulary) URIToVocabulary.get(
                        conceptURIs.next())).setIndividual();
            }
            rdfTypeStmts = null;
            conceptRecog = null;
        }
    }

    private void buildRDFSentences(Iterator statements)
    {
        nextStatement:
        while (statements.hasNext()) {
            Statement statement = (Statement) statements.next();
            RDFNode subject = statement.getSubject();
            RDFNode object = statement.getObject();
            String subjectURI = subject.toString();

            if (object.isLiteral()) {
                Vocabulary vocabulary = (Vocabulary) URIToVocabulary.get(subjectURI);
                vocabulary.addLiteralStatement(statement);
            }
            if (subject.isURIResource() && !object.isAnon()) {
                RDFSentence sentence = new RDFSentence(RDFSentences.size());
                sentence.addStatement(statement);
                RDFSentences.add(sentence);
                continue nextStatement;
            }
        }

        while (sID_oIDToStmts.size() > 0) {
            RDFSentence sentence = new RDFSentence(RDFSentences.size());
            RDFSentences.add(sentence);
            ArrayList bnodeIDs = new ArrayList();
            String initSID_OID = (String) sID_oIDToStmts.keySet().iterator().next();
            int _Index = initSID_OID.indexOf('_');
            String initSID = initSID_OID.substring(0, _Index);
            if (((Vocabulary) vocabularies.get(
                    Integer.parseInt(initSID))).isAnonymous()) {
                bnodeIDs.add(initSID);
            }
            String initOID = initSID_OID.substring(_Index + 1);
            if (((Vocabulary) vocabularies.get(
                    Integer.parseInt(initOID))).isAnonymous()) {
                bnodeIDs.add(initOID);
            }
            while (bnodeIDs.size() > 0) {
                String bnodeID = (String) bnodeIDs.remove(0);
                ArrayList os = (ArrayList) sIDToOIDs.remove(bnodeID);
                if (os != null) {
                    for (Iterator oIDs = os.iterator(); oIDs.hasNext();) {
                        String oID = (String) oIDs.next();
                        if (!bnodeIDs.contains(oID) && (sIDToOIDs.containsKey(oID) 
                                || oIDToIDs.containsKey(oID))) {
                            bnodeIDs.add(oID);
                        }
                        String s_o = bnodeID + "_" + oID;
                        ArrayList statementsToBeAdded = (ArrayList) sID_oIDToStmts.remove(s_o);
                        if (statementsToBeAdded != null) {
                            sentence.addStatements(statementsToBeAdded);
                        }
                    }
                }
                ArrayList ss = (ArrayList) oIDToIDs.remove(bnodeID);
                if (ss != null) {
                    for (Iterator sIDs = ss.iterator(); sIDs.hasNext();) {
                        String sID = (String) sIDs.next();
                        if (!bnodeIDs.contains(sID) && (sIDToOIDs.containsKey(sID)
                                || oIDToIDs.containsKey(sID))) {
                            bnodeIDs.add(sID);
                        }
                        String s_o = sID + "_" + bnodeID;
                        ArrayList statementsToBeAdded = (ArrayList) sID_oIDToStmts.remove(s_o);
                        if (statementsToBeAdded != null) {
                            sentence.addStatements(statementsToBeAdded);
                        }
                    }
                }
            }
        }
        sIDToOIDs = null;
        oIDToIDs = null;
        sID_oIDToStmts = null;
    }

    public void filter(RDFSentenceFilter RDFSentenceFilter)
    {
        RDFSentences = RDFSentenceFilter.filter(RDFSentences);
        for (int i = 0; i < RDFSentences.size(); i++) {
            ((RDFSentence) RDFSentences.get(i)).setID(i);
        }
    }

    public ArrayList calculateRelatedRDFSentences()
    {
        ArrayList relatedRDFSentences = new ArrayList(vocabularies.size());
        for (int i = 0; i < vocabularies.size(); i++) {
            relatedRDFSentences.add(new ArrayList(0));
        }
        for (Iterator sentences = RDFSentences.iterator(); sentences.hasNext();) {
            RDFSentence sentence = (RDFSentence) sentences.next();
            for (Iterator uris = sentence
                    .getDomainVocabularyURIs().iterator(); uris.hasNext();) {
                String uri = (String) uris.next();
                int id = ((Vocabulary) URIToVocabulary.get(uri)).getID();
                ((ArrayList) relatedRDFSentences.get(id)).add(sentence);
            }
        }
        return relatedRDFSentences;
    }

    public ArrayList calculatePaths()
    {
        ArrayList result = new ArrayList(vocabularies.size());
        for (int i = 0; i < vocabularies.size(); i++) {
            result.add(new HashMap(1));
        }
        for (Iterator sentences = RDFSentences.iterator(); sentences.hasNext();) {
            RDFSentence sentence = (RDFSentence) sentences.next();
            for (Iterator subjectURIs = sentence
                    .getSubjectDomainVocabularyURIs()
                            .iterator(); subjectURIs.hasNext();) {
                String subjectURI = (String) subjectURIs.next();
                int subjectID = ((Vocabulary) URIToVocabulary.get(subjectURI)).getID();
                for (Iterator objectURIs = sentence
                        .getObjectDomainVocabularyURIs()
                                .iterator(); objectURIs.hasNext();) {
                    String objectURI = (String) objectURIs.next();
                    ArrayList newPaths = sentence.getPaths(subjectURI,
                            objectURI);
                    HashMap objectURIToPaths = (HashMap) result.get(subjectID);
                    ArrayList paths = (ArrayList) objectURIToPaths.get(objectURI);
                    if (paths == null) {
                        objectURIToPaths.put(objectURI, newPaths);
                    } else {
                        for (int i = 0; i < newPaths.size(); i++) {
                            paths.add((ArrayList) newPaths.get(i));
                        }
                    }
                }
            }
        }
        return result;
    }

    public ArrayList getRDFSentences()
    {
        return RDFSentences;
    }

    public int getNumberOfRDFSentences()
    {
        return RDFSentences.size();
    }

    public ArrayList getVocabularies()
    {
        return vocabularies;
    }

    public int getNumberOfVocabularies()
    {
        return vocabularies.size();
    }

    public RDFSentence getRDFSentence(int ID)
    {
        return (RDFSentence) RDFSentences.get(ID);
    }

    public Vocabulary getVocabulary(int ID)
    {
        return (Vocabulary) vocabularies.get(ID);
    }

    public Vocabulary getVocabulary(String vocabularyURI)
    {
        return (Vocabulary) URIToVocabulary.get(vocabularyURI);
    }

    public int getVocabularyID(String vocabularyURI)
    {
        return ((Vocabulary) URIToVocabulary.get(vocabularyURI)).getID();
    }

    public ArrayList getOntologyURIs()
    {
        return ontologyURIs;
    }

    public void printRDFSentences()
    {
        for (int i = 0; i < RDFSentences.size(); i++) {
            RDFSentence sentence = (RDFSentence) RDFSentences.get(i);
            System.out.println(i);
            System.out.println(sentence.toString());
        }
    }

    public void printRDFSentencesToFile(String fileURL)
    {
        PrintWriter sentencesWriter = null;
        try {
            sentencesWriter = new PrintWriter(fileURL);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        sentencesWriter.println("size#" + String.valueOf(RDFSentences.size()));
        for (int i = 0; i < RDFSentences.size(); i++) {
            RDFSentence sentence = (RDFSentence) RDFSentences.get(i);
            sentencesWriter.println(sentence.toString());
            sentencesWriter.println("------");
        }
        sentencesWriter.close();
    }

    public static ArrayList getRDFSentencesFromFile(String fileURL)
    {
        ArrayList sentences = null;
        try {
            BufferedReader sentencesFile = new BufferedReader(new FileReader(
                    fileURL));
            String line = sentencesFile.readLine();
            int size = Integer.parseInt(line.substring(line.indexOf('#') + 1));
            sentences = new ArrayList(size);
            line = sentencesFile.readLine();
            StringBuffer buffer = new StringBuffer();
            while (line != null) {
                if (line.equals("")) {
                } else if (line.equals("------")) {
                    sentences.add(buffer.toString());
                    buffer = new StringBuffer();
                } else {
                    buffer.append(line + "\n");
                }
                line = sentencesFile.readLine();
            }
            sentencesFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return sentences;
    }
}
