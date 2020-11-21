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

import iws.falcon.matcher.AbstractMatcher;
import iws.falcon.model.RBGModel;
import iws.falcon.output.AlignmentSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Wei Hu
 */
public class PbmMatcher implements AbstractMatcher
{
    private RBGModel rbgModelA = null,  rbgModelB = null;
    private String name1 = null,  name2 = null;
    private int maxSize = 500;
    private String tempDir = null;
    private AlignmentSet alignSet = null;
    private ArrayList bmSet = null;

    public PbmMatcher(RBGModel modelA, RBGModel modelB,
            String n1, String n2, String dir)
    {
        rbgModelA = modelA;
        rbgModelB = modelB;
        name1 = n1;
        name2 = n2;
        maxSize = Parameters.maxSize;
        tempDir = dir;
    }

    public void match()
    {
        Partitioner p1 = new Partitioner(rbgModelA, name1, maxSize, tempDir);
        p1.partition();
        HashMap clusters1 = p1.getClusters();
        Partitioner p2 = new Partitioner(rbgModelB, name2, maxSize, tempDir);
        p2.partition();
        HashMap clusters2 = p2.getClusters();
        BlockMatcher bm = new BlockMatcher(rbgModelA, rbgModelB, name1, name2,
                clusters1, clusters2, tempDir);
        bm.blockMatch();

        alignSet = bm.getAnchors();
        bmSet = bm.getBlockMappings();
    }

    public AlignmentSet getAnchors()
    {
        return alignSet;
    }

    public ArrayList getBlockMappings()
    {
        return bmSet;
    }

    public AlignmentSet getAlignmentSet()
    {
        return null;
    }

    public AlignmentSet getClassAlignmentSet()
    {
        return null;
    }

    public AlignmentSet getPropertyAlignmentSet()
    {
        return null;
    }

    public AlignmentSet getInstanceAlignmentSet()
    {
        return null;
    }
}
