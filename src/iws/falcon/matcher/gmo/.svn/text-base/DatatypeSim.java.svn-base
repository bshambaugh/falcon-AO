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
package iws.falcon.matcher.gmo;

import iws.falcon.model.Constants;
import iws.falcon.model.Node;

/**
 * @author Wei Hu & Ningsheng Jian
 */
public class DatatypeSim
{
    public static double[][] strArray = {{0, 1, 2, 3, 3, 3, 4},
        {1, 0, 1, 2, 2, 2, 3}, {2, 1, 0, 1, 1, 1, 2},
        {3, 2, 1, 0, 2, 2, 3}, {3, 2, 1, 2, 0, 2, 1},
        {3, 2, 1, 2, 2, 0, 3}, {4, 3, 2, 3, 1, 3, 0}
    };
    public static double[][] intArray = {
        {0, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5, 5, 6},
        {1, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4, 4, 5},
        {2, 1, 0, 2, 2, 1, 3, 3, 3, 4, 4, 5, 5, 6},
        {2, 1, 2, 0, 2, 3, 1, 3, 3, 2, 4, 3, 5, 6},
        {2, 1, 2, 2, 0, 3, 3, 1, 1, 4, 2, 5, 3, 4},
        {3, 2, 1, 3, 3, 0, 4, 4, 4, 5, 5, 6, 6, 7},
        {3, 2, 3, 1, 3, 4, 0, 4, 4, 1, 5, 2, 6, 7},
        {3, 2, 3, 3, 1, 4, 4, 0, 2, 5, 1, 6, 2, 3},
        {3, 2, 3, 3, 1, 4, 4, 2, 0, 5, 3, 6, 4, 5},
        {4, 3, 4, 2, 4, 5, 1, 5, 5, 0, 6, 1, 7, 8},
        {4, 3, 4, 4, 2, 5, 5, 1, 3, 6, 0, 7, 1, 2},
        {5, 4, 5, 3, 5, 6, 2, 6, 6, 1, 7, 0, 8, 9},
        {5, 4, 5, 5, 3, 6, 6, 2, 4, 7, 1, 8, 0, 1},
        {6, 5, 6, 6, 4, 7, 7, 3, 5, 7, 2, 9, 1, 0}
    };
    public static double[][] floatArray = {{0, 1}, {1, 0}};
    public static String xsdString = Constants.XSD_NS + "string";
    public static String xsdNormalizedString = Constants.XSD_NS + "normalizedString";
    public static String xsdToken = Constants.XSD_NS + "token";
    public static String xsdLanguage = Constants.XSD_NS + "language";
    public static String xsdName = Constants.XSD_NS + "Name";
    public static String xsdNMTOKEN = Constants.XSD_NS + "NMTOKEN";
    public static String xsdNCName = Constants.XSD_NS + "NCName";
    public static String StringDis[] = {xsdString, xsdNormalizedString,
        xsdToken, xsdLanguage, xsdName, xsdNMTOKEN, xsdNCName
    };
    public static String xsdDecimal = Constants.XSD_NS + "decimal";
    public static String xsdInteger = Constants.XSD_NS + "integer";
    public static String xsdNonPositiveInteger = Constants.XSD_NS + "nonPositiveInteger";
    public static String xsdLong = Constants.XSD_NS + "long";
    public static String xsdNonNegativeInteger = Constants.XSD_NS + "nonNegativeInteger";
    public static String xsdNegativeInteger = Constants.XSD_NS + "negativeInteger";
    public static String xsdInt = Constants.XSD_NS + "int";
    public static String xsdUnsignedLong = Constants.XSD_NS + "unsignedLong";
    public static String xsdPositiveInteger = Constants.XSD_NS + "positiveInteger";
    public static String xsdShort = Constants.XSD_NS + "short";
    public static String xsdUnsignedInt = Constants.XSD_NS + "unsignedInt";
    public static String xsdByte = Constants.XSD_NS + "byte";
    public static String xsdUnsignedShort = Constants.XSD_NS + "unsignedShort";
    public static String xsdUnsignedByte = Constants.XSD_NS + "unsignedByte";
    public static String IntDis[] = {xsdDecimal, xsdInteger,
        xsdNonPositiveInteger, xsdLong, xsdNonNegativeInteger,
        xsdNegativeInteger, xsdInt, xsdUnsignedLong, xsdPositiveInteger,
        xsdShort, xsdUnsignedInt, xsdByte, xsdUnsignedShort,
        xsdUnsignedByte
    };
    public static String xsdFloat = Constants.XSD_NS + "float";
    public static String xsdDouble = Constants.XSD_NS + "double";
    public static String FloatDis[] = {xsdFloat, xsdDouble};

    public static double getSimilarity(String left, String right)
    {
        double dis = 1E20;
        if (left.equals(right)) {
            return 1;
        }
        int index1 = -1, index2 = -1;
        for (int i = 0; i < StringDis.length; i++) {
            if (StringDis[i].equals(left)) {
                index1 = i;
            }
            if (StringDis[i].equals(right)) {
                index2 = i;
            }
        }
        if (index1 >= 0 && index2 >= 0) {
            dis = strArray[index1][index2];
        }
        index1 = -1;
        index2 = -1;
        for (int i = 0; i < FloatDis.length; i++) {
            if (FloatDis[i].equals(left)) {
                index1 = i;
            }
            if (FloatDis[i].equals(right)) {
                index2 = i;
            }
        }
        if (index1 >= 0 && index2 >= 0) {
            dis = floatArray[index1][index2];
        }
        index1 = -1;
        index2 = -1;
        for (int i = 0; i < IntDis.length; i++) {
            if (IntDis[i].equals(left)) {
                index1 = i;
            }
            if (IntDis[i].equals(right)) {
                index2 = i;
            }
        }
        if (index1 >= 0 && index2 >= 0) {
            dis = intArray[index1][index2];
        }
        double sim = 1 / (dis * 2 + 1);
        if (sim < 1E-10) {
            sim = 0;
        }
        return sim;
    }

    public double getSimilarity(Node left, Node right)
    {
        return DatatypeSim.getSimilarity(left.toString(), right.toString());
    }
}
