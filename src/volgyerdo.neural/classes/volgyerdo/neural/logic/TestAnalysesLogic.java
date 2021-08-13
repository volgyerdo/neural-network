/*
 * Copyright 2021 Volgyerdo Nonprofit Kft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package volgyerdo.neural.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import volgyerdo.math.fast.FastMath;
import volgyerdo.neural.structure.TestAnalyses;
import volgyerdo.neural.structure.TestRecord;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class TestAnalysesLogic {

    public static List<TestAnalyses> analyzeRow(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }
        int dataSize = data.size();
        List<TestAnalyses> analyses = new ArrayList<>(dataSize);
        for (int i = 1; i <= dataSize; i++) {
            List<TestRecord> subList = data.subList(Math.max(0, i - NetworkConstants.ANALYSES_ROW_LENGTH), i);
            analyses.add(analyze(subList));
        }
        return analyses;
    }

    public static TestAnalyses analyze(List<TestRecord> data) {
        TestAnalyses analyses = new TestAnalyses();
        if (data == null || data.isEmpty()) {
            return analyses;
        }
        int dataSize = data.size();
        double sum = 0;
        double product = 1;
        long runTimeSum = 0;
        List<TestRecord> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData, new ErrorComparator());
        for (TestRecord record : data) {
            sum += record.error;
            product *= record.error;
            runTimeSum += record.runTime;
        }
        double errorArithmeticMean = sum / dataSize;
        double errorGeometricMean = Math.pow(product, 1. / dataSize);
        double errorMedian = sortedData.get(dataSize / 2).error;
        double deviationSum = 0;
        for (TestRecord record : data) {
            deviationSum += FastMath.pow2(record.error - errorArithmeticMean);
        }
        double standardDeviation = Math.pow(deviationSum / dataSize, 0.5);
        analyses.errorArithmeticMean = (float) errorArithmeticMean;
        analyses.errorGeometricMean = (float) errorGeometricMean;
        analyses.errorMedian = (float) errorMedian;
        analyses.errorStandardDeviation = (float) standardDeviation;
        analyses.minError = data.get(0).error;
        analyses.maxError = data.get(dataSize - 1).error;
        analyses.runTime = runTimeSum;
        analyses.runPeriod = data.get(dataSize - 1).timestamp - data.get(0).timestamp;
        return analyses;
    }

    private static class ErrorComparator implements Comparator<TestRecord> {

        @Override
        public int compare(TestRecord r1, TestRecord r2) {
            return Float.compare(r1.error, r2.error);
        }
    }

}
