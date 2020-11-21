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
package iws.falcon.matcher.vdoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Wei Hu
 */
public class Stopword
{
    private ArrayList stopwordlist = new ArrayList();

    public Stopword()
    {
        try {
            FileReader fr = new FileReader("./stopwords.txt");
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while (s != null) {
                stopwordlist.add(s.trim());
                s = br.readLine();
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] removeStopword(String src[])
    {
        int length = src.length;
        if (length == 1) {
            return src;
        } else {
            ArrayList temp = new ArrayList();
            for (int i = 0; i < src.length; i++) {
                if (!stopwordlist.contains(src[i])) {
                    temp.add(src[i]);
                }
            }
            String des[] = new String[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                des[i] = (String) temp.get(i);
            }
            return des;
        }
    }

    public ArrayList getStopwordList()
    {
        Stopword sw = new Stopword();
        return sw.stopwordlist;
    }
}
