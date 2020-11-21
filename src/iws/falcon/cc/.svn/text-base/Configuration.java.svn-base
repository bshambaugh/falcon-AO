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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Wei Hu
 */
public class Configuration
{
    public Properties getProperties()
    {
        try {
            File file = new File("./falcon.properties");
            FileInputStream fis = new FileInputStream(file);
            Properties p = new Properties();
            p.load(fis);
            fis.close();
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void init()
    {
        Properties properties = getProperties();
        setFilepaths(properties);
        setEvaluation(properties);
        setControlCenter(properties);
        setPbmMatcher(properties);
        setStringMatcher(properties);
        setVDocMatcher(properties);
        setGmoMatcher(properties);
    }

    private void setFilepaths(Properties properties)
    {
        String s1 = properties.getProperty("onto1").trim();
        if (s1 != null && s1.length() != 0) {
            Parameters.onto1 = s1;
        }
        String s2 = properties.getProperty("onto2").trim();
        if (s2 != null && s2.length() != 0) {
            Parameters.onto2 = s2;
        }
        String s3 = properties.getProperty("output").trim();
        if (s3 != null && s3.length() != 0) {
            Parameters.output = s3;
        }
        String s4 = properties.getProperty("reference").trim();
        if (s4 != null && s4.length() != 0) {
            Parameters.reference = s4;
        }
    }

    public void setEvaluation(Properties properties)
    {
        String s = properties.getProperty("alpha");
        if (s != null && s.length() != 0) {
            iws.falcon.output.Parameters.alpha = Double.parseDouble(s);
        }
    }

    private void setControlCenter(Properties properties)
    {
        String s1 = properties.getProperty("lowComp").trim();
        if (s1 != null && s1.length() != 0) {
            Parameters.lowComp = Integer.parseInt(s1);
        }
        String s2 = properties.getProperty("mediumComp").trim();
        if (s2 != null && s2.length() != 0) {
            Parameters.mediumComp = Integer.parseInt(s2);
        }
        String s3 = properties.getProperty("highComp").trim();
        if (s3 != null && s3.length() != 0) {
            Parameters.highComp = Integer.parseInt(s3);
        }
        String s4 = properties.getProperty("lingHighSim").trim();
        if (s4 != null && s4.length() != 0) {
            Parameters.lingHighSim = Double.parseDouble(s4);
        }
        String s5 = properties.getProperty("lingLowSim").trim();
        if (s5 != null && s5.length() != 0) {
            Parameters.lingLowSim = Double.parseDouble(s5);
        }
        String s6 = properties.getProperty("lingHighValue").trim();
        if (s6 != null && s6.length() != 0) {
            Parameters.lingHighValue = Double.parseDouble(s6);
        }
        String s7 = properties.getProperty("lingLowValue").trim();
        if (s7 != null && s7.length() != 0) {
            Parameters.lingLowValue = Double.parseDouble(s7);
        }
        String s8 = properties.getProperty("structPercent").trim();
        if (s8 != null && s8.length() != 0) {
            Parameters.structPercent = Double.parseDouble(s8);
        }
        String s9 = properties.getProperty("structHighValue").trim();
        if (s9 != null && s9.length() != 0) {
            Parameters.structHighValue = Double.parseDouble(s9);
        }
        String s10 = properties.getProperty("structLowValue").trim();
        if (s10 != null && s10.length() != 0) {
            Parameters.structLowValue = Double.parseDouble(s10);
        }
        String s11 = properties.getProperty("structHighRate").trim();
        if (s11 != null && s11.length() != 0) {
            Parameters.structHighRate = Double.parseDouble(s11);
        }
        String s12 = properties.getProperty("structLowRate").trim();
        if (s12 != null && s12.length() != 0) {
            Parameters.structLowRate = Double.parseDouble(s12);
        }
        String s13 = properties.getProperty("combWeight").trim();
        if (s13 != null && s13.length() != 0) {
            Parameters.combWeight = Double.parseDouble(s13);
        }
        String s14 = properties.getProperty("inclInstMatch").trim();
        if (s14 != null && s14.length() != 0) {
            Parameters.inclInstMatch = Boolean.parseBoolean(s14);
        }
        String s15 = properties.getProperty("largeOnto").trim();
        if (s15 != null && s15.length() != 0) {
            Parameters.largeOnto = Integer.parseInt(s15);
        }
    }

    private void setPbmMatcher(Properties properties)
    {
        String s1 = properties.getProperty("anchorCutoff").trim();
        if (s1 != null && s1.length() != 0) {
            iws.falcon.matcher.pbm.Parameters.anchorCutoff = Double.parseDouble(s1);
        }
        String s3 = properties.getProperty("structCompCutoff").trim();
        if (s3 != null && s3.length() != 0) {
            iws.falcon.matcher.pbm.Parameters.structCompCutoff = Double.parseDouble(s3);
        }
        String s7 = properties.getProperty("hierWeight").trim();
        if (s7 != null && s7.length() != 0) {
            iws.falcon.matcher.pbm.Parameters.hierWeight = Double.parseDouble(s7);
        }
        String s8 = properties.getProperty("threshold").trim();
        if (s8 != null && s8.length() != 0) {
            iws.falcon.matcher.pbm.Parameters.threshold = Double.parseDouble(s8);
        }
    }

    private void setStringMatcher(Properties properties)
    {
        String s1 = properties.getProperty("method").trim();
        if (s1 != null && s1.length() != 0) {
            iws.falcon.matcher.string.Parameters.method = Integer.parseInt(s1);
        }
        String s2 = properties.getProperty("stringLocalnameWeight").trim();
        if (s2 != null && s2.length() != 0) {
            iws.falcon.matcher.string.Parameters.localnameWeight = Double.parseDouble(s2);
        }
        String s3 = properties.getProperty("stringLabelWeight").trim();
        if (s3 != null && s3.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.labelWeight = Double.parseDouble(s3);
        }
        String s4 = properties.getProperty("stringCommentWeight").trim();
        if (s4 != null && s4.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.commentWeight = Double.parseDouble(s4);
        }
    }

    private void setVDocMatcher(Properties properties)
    {
        String s1 = properties.getProperty("basicWeight").trim();
        if (s1 != null && s1.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.basicWeight = Double.parseDouble(s1);
        }
        String s2 = properties.getProperty("subjectWeight").trim();
        if (s2 != null && s2.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.subjectWeight = Double.parseDouble(s2);
        }
        String s3 = properties.getProperty("predicateWeight").trim();
        if (s3 != null && s3.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.predicateWeight = Double.parseDouble(s3);
        }
        String s4 = properties.getProperty("objectWeight").trim();
        if (s4 != null && s4.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.objectWeight = Double.parseDouble(s4);
        }
        String s5 = properties.getProperty("vdocLocalnameWeight").trim();
        if (s5 != null && s5.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.localnameWeight = Double.parseDouble(s5);
        }
        String s6 = properties.getProperty("vdocLabelWeight").trim();
        if (s6 != null && s6.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.labelWeight = Double.parseDouble(s6);
        }
        String s7 = properties.getProperty("vdocCommentWeight").trim();
        if (s7 != null && s7.length() != 0) {
            iws.falcon.matcher.vdoc.Parameters.commentWeight = Double.parseDouble(s7);
        }
    }

    private void setGmoMatcher(Properties properties)
    {
        String s1 = properties.getProperty("iterTimes").trim();
        if (s1 != null && s1.length() != 0) {
            iws.falcon.matcher.gmo.Parameters.iterTimes = Integer.parseInt(s1);
        }
        String s2 = properties.getProperty("convergence").trim();
        if (s2 != null && s2.length() != 0) {
            iws.falcon.matcher.gmo.Parameters.convergence = Double.parseDouble(s2);
        }
    }
}
