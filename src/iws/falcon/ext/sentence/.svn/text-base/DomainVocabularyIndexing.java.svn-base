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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Gong Cheng
 */
public class DomainVocabularyIndexing
{
    private ArrayList domainVocURIs = null;
    private HashMap URIToDomainVocID = null;

    public DomainVocabularyIndexing(RDFSentenceGraph sg)
    {
        domainVocURIs = new ArrayList(sg.getNumberOfVocabularies());
        URIToDomainVocID = new HashMap(2 * sg.getNumberOfVocabularies());
        for (Iterator i = sg.getRDFSentences().iterator(); i.hasNext();) {
            RDFSentence sentence = (RDFSentence) i.next();
            ArrayList vocabularies = sentence.getDomainVocabularyURIs();
            for (Iterator j = vocabularies.iterator(); j.hasNext();) {
                String vocURI = sg.getVocabulary((String) j.next()).getURI();
                if (!URIToDomainVocID.containsKey(vocURI)) {
                    URIToDomainVocID.put(vocURI, domainVocURIs.size());
                    domainVocURIs.add(vocURI);
                }
            }
        }
    }

    public DomainVocabularyIndexing(String indexFileURL)
    {
        try {
            BufferedReader indexFile = new BufferedReader(new FileReader(
                    indexFileURL));
            String line = indexFile.readLine();
            int size = Integer.parseInt(line.substring(line.indexOf('#') + 1));
            domainVocURIs = new ArrayList(size);
            URIToDomainVocID = new HashMap(size * 2);
            line = indexFile.readLine();
            while (line != null && !line.equals("")) {
                URIToDomainVocID.put(line, domainVocURIs.size());
                domainVocURIs.add(line);
                line = indexFile.readLine();
            }
            indexFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ArrayList getDomainVocabularies()
    {
        return domainVocURIs;
    }

    public int getDomainVocabularyID(String URI)
    {
        Integer ID = (Integer) URIToDomainVocID.get(URI);
        if (ID != null) {
            return ID.intValue();
        }
        return -1;
    }

    public String getDomainVocabularyURI(int ID)
    {
        return (String) domainVocURIs.get(ID);
    }

    public int getNumberOfDomainVocabularies()
    {
        return domainVocURIs.size();
    }

    public void printIndexToFile(String indexFileURL)
    {
        PrintWriter indexWriter = null;
        try {
            indexWriter = new PrintWriter(indexFileURL);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        indexWriter.println("size#" + String.valueOf(domainVocURIs.size()));
        for (int i = 0; i < domainVocURIs.size(); i++) {
            indexWriter.println((String) domainVocURIs.get(i));
        }
        indexWriter.close();
    }
}
