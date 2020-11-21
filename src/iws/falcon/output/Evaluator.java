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
public class Evaluator
{
    public ResultData compare(AlignmentSet as1, AlignmentSet as2)
    {
        if (as1 == null || as2 == null) {
            return null;
        }
        int found = as1.size();
        int exist = as2.size();
        int correct = 0;

        if (found == 0 || exist == 0) {
            return null;
        }
        ResultData result = new ResultData();
        AlignmentSet errorAlignments = new AlignmentSet();
        AlignmentSet correctAlignments = new AlignmentSet();
        AlignmentSet lostAlignments = new AlignmentSet();
        for (int i = 0; i < found; i++) {
            Alignment alignment1 = (Alignment) as1.getAlignment(i);
            boolean flag = true;
            for (int j = 0; j < exist; j++) {
                Alignment alignment2 = (Alignment) as2.getAlignment(j);
                if (alignment1.equals(alignment2)) {
                    correct++;
                    correctAlignments.addAlignment(alignment1);
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                errorAlignments.addAlignment(alignment1);
            }
        }
        for (int i = 0; i < exist; i++) {
            Alignment alignment1 = as2.getAlignment(i);
            boolean flag = true;
            for (int j = 0; j < found; j++) {
                Alignment alignment2 = as1.getAlignment(j);
                if (alignment1.equals(alignment2)) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                lostAlignments.addAlignment(alignment1);
            }
        }
        System.out.println("Found: " + found + ", Exist: " + exist + ", Correct: " + correct);

        double prec = (double) correct / found;
        double rec = (double) correct / exist;
        System.out.println("Precision: " + prec + ", Recall: " + rec);
        // F-measure
        double fm = (1 + Parameters.alpha) * (prec * rec) / (Parameters.alpha * prec + rec);
        System.out.println("F-Measure: " + fm);

        result.setFound(found);
        result.setExist(exist);
        result.setCorrect(correct);
        result.setPrecision(prec);
        result.setRecall(rec);
        result.setFmeasure(fm);
        result.setCorrectAlignments(correctAlignments);
        result.setErrorAlignments(errorAlignments);
        result.setLostAlignments(lostAlignments);
        return result;
    }
}
