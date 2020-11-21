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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * @author Wei Hu
 */
public class AlignmentWriter
{
    private AlignmentSet alignmentSet = null;
    private RandomAccessFile raf = null;
    private String filepath = null;
    private ArrayList writeList = null;

    public AlignmentWriter(AlignmentSet as, String fp)
    {
        alignmentSet = as;
        filepath = fp;
        try {
            File file = new File(fp);
            if (file.exists()) {
                file.delete();
            }
            raf = new RandomAccessFile(filepath, "rw");
            writeList = new ArrayList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(String onto1, String onto2, String uri1, String uri2)
    {
        writeNS();
        writeStart("yes", "0", "11", onto1, onto2, uri1, uri2);
        for (int i = 0, n = alignmentSet.size(); i < n; i++) {
            Alignment alignment = (Alignment) alignmentSet.getAlignment(i);
            String e1 = alignment.getEntity1().toString();
            String e2 = alignment.getEntity2().toString();
            String measure = Double.toString(alignment.getSimilarity());
            writeElement(e1, e2, measure);
        }
        writeEnd();
        writeToFile();
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeNS()
    {
        String temp = "<?xml version='1.0' encoding='utf-8'?>\n" 
                + "<rdf:RDF xmlns='http://knowledgeweb.semanticweb.org/heterogeneity/alignment' \n" 
                + "xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#' \n" 
                + "xmlns:xsd='http://www.w3.org/2001/XMLSchema#'>\n";
        writeList.add(temp);
    }

    public void writeStart(String xml, String level, String type, String onto1,
            String onto2, String uri1, String uri2)
    {
        String temp = "<Alignment>\n" 
                + "  <xml>" + xml + "</xml>\n" 
                + "  <level>" + level + "</level>\n" 
                + "  <type>" + type + "</type>\n" 
                + "  <onto1>" + onto1 + "</onto1>\n" 
                + "  <onto2>" + onto2 + "</onto2>\n" 
                + "  <uri1>" + uri1 + "</uri1>\n" 
                + "  <uri2>" + uri2 + "</uri2>\n" 
                + "  <map>";
        writeList.add(temp);
    }

    public void writeEnd()
    {
        String temp = "  </map>\n</Alignment>\n</rdf:RDF>";
        writeList.add(temp);
    }

    public void writeElement(String res1, String res2, String measure)
    {
        String temp = "    <Cell>\n" 
                + "      <entity1 rdf:resource=\"" + res1 + "\"/>\n" 
                + "      <entity2 rdf:resource=\"" + res2 + "\"/>\n" 
                + "      <measure rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\">" 
                + measure + "</measure>\n" 
                + "      <relation>=</relation>\n" 
                + "    </Cell>";
        writeList.add(temp);
    }

    public void writeElement(String res1, String res2, String measure, String r)
    {
        String temp = "    <Cell>\n"
                + "      <entity1 rdf:resource=\"" + res1 + "\"/>\n" 
                + "      <entity2 rdf:resource=\"" + res2 + "\"/>\n" 
                + "      <measure rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\">" 
                + measure + "</measure>\n" 
                + "      <relation>" + r + "</relation>\n" 
                + "    </Cell>";
        writeList.add(temp);
    }

    public void writeToFile()
    {
        try {
            for (int i = 0, n = writeList.size(); i < n; i++) {
                raf.seek(raf.length());
                raf.writeBytes((String) writeList.get(i) + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
