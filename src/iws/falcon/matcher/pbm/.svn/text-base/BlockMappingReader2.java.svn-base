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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Wei Hu
 */
public class BlockMappingReader2
{
    private String alignpath = null;

    public BlockMappingReader2(String ap)
    {
        alignpath = ap;
    }

    public ArrayList read()
    {
        try {
            File file = new File(alignpath);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            Element align = root.element("BlockMapping");
            Iterator map = align.elementIterator("map");
            if (!map.hasNext()) {
                return null;
            }
            ArrayList list = new ArrayList();
            while (map.hasNext()) {
                Element cell = ((Element) map.next()).element("Cell");
                String s1 = cell.element("block1").attributeValue("resource");
                String s2 = cell.element("block2").attributeValue("resource");
                double sim = Double.parseDouble(cell.elementText("measure"));
                BlockMapping bm = new BlockMapping(s1, s2, sim);
                list.add(bm);
            }
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
