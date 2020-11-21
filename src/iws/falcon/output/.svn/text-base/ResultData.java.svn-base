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

/**
 * @author Wei Hu
 */
public class ResultData
{
    private int found = 0;
    private int exist = 0;
    private int correct = 0;
    private double precision = 0;
    private double recall = 0;
    private double fmeasure = 0;
    private AlignmentSet errorAlignments = null;
    private AlignmentSet correctAlignments = null;
    private AlignmentSet lostAlignments = null;

    public AlignmentSet getCorrectAlignments()
    {
        return correctAlignments;
    }

    public void setCorrectAlignments(AlignmentSet ca)
    {
        correctAlignments = ca;
    }

    public AlignmentSet getLostAlignments()
    {
        return lostAlignments;
    }

    public void setLostAlignments(AlignmentSet la)
    {
        lostAlignments = la;
    }

    public AlignmentSet getErrorAlignments()
    {
        return errorAlignments;
    }

    public void setErrorAlignments(AlignmentSet ea)
    {
        errorAlignments = ea;
    }

    public int getCorrect()
    {
        return correct;
    }

    public void setCorrect(int c)
    {
        correct = c;
    }

    public int getFound()
    {
        return found;
    }

    public void setFound(int f)
    {
        found = f;
    }

    public int getExist()
    {
        return exist;
    }

    public void setExist(int e)
    {
        exist = e;
    }

    public double getFmeasure()
    {
        return fmeasure;
    }

    public void setFmeasure(double fm)
    {
        fmeasure = fm;
    }

    public double getPrecision()
    {
        return precision;
    }

    public void setPrecision(double prec)
    {
        precision = prec;
    }

    public double getRecall()
    {
        return recall;
    }

    public void setRecall(double rec)
    {
        recall = rec;
    }
}
