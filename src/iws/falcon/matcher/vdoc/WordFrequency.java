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

/**
 * @author Wei Hu
 */
public class WordFrequency
{
    private String word = "";
    private double frequency = 0;

    public WordFrequency(String w, double f)
    {
        if (w != null) {
            word = w;
        }
        if (f > 0) {
            frequency = f;
        }
    }

    public void setWord(String w)
    {
        if (w != null) {
            word = w;
        }
    }

    public void setFrequency(double f)
    {
        if (f > 0) {
            frequency = f;
        }
    }

    public String getWord()
    {
        return word;
    }

    public double getFrequency()
    {
        return frequency;
    }
}
