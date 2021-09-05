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
import volgyerdo.commons.math.fast.FastMath;
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
        analyses.sampleSize = data.size();
        analyses.errorArithmeticMean = (float) getErrorArithmeticMean(data);
        analyses.errorGeometricMean = (float) getErrorGeometricMean(data);
        analyses.errorMedian = (float) getErrorMedian(data);
        analyses.errorStandardDeviation = (float) getErrorStandardDeviation(data);
        analyses.minError = (float) getMinError(data);
        analyses.maxError = (float) getMaxError(data);
        analyses.runTime = getRunTime(data);
        analyses.runPeriod = getRunPeriod(data);
        return analyses;
    }

    public static double getErrorArithmeticMean(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (TestRecord record : data) {
            sum += record.error;
        }
        return sum / data.size();
    }

    public static double getErrorGeometricMean(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        double product = 1;
        for (TestRecord record : data) {
            product *= record.error;
        }
        return Math.pow(product, 1. / data.size());
    }

    public static double getErrorMedian(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        List<TestRecord> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData, new ErrorComparator());
        return sortedData.get(data.size() / 2).error;
    }

    public static double getErrorStandardDeviation(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        double errorArithmeticMean = getErrorArithmeticMean(data);
        double deviationSum = 0;
        for (TestRecord record : data) {
            deviationSum += FastMath.pow2(record.error - errorArithmeticMean);
        }
        return Math.pow(deviationSum / data.size(), 0.5);
    }

    public static double getMinError(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        double min = Double.MAX_VALUE;
        for (TestRecord record : data) {
            if (min > record.error) {
                min = record.error;
            }
        }
        return min;
    }

    public static double getMaxError(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        double max = -Double.MAX_VALUE;
        for (TestRecord record : data) {
            if (max < record.error) {
                max = record.error;
            }
        }
        return max;
    }

    public static long getRunTime(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        long runTimeSum = 0;
        for (TestRecord record : data) {
            runTimeSum += record.runTime;
        }
        return runTimeSum;
    }

    public static long getRunPeriod(List<TestRecord> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for (TestRecord record : data) {
            if (max < record.timestamp) {
                max = record.timestamp;
            }
            if (min > record.timestamp) {
                min = record.timestamp;
            }
        }
        return max - min;
    }

    private static class ErrorComparator implements Comparator<TestRecord> {

        @Override
        public int compare(TestRecord r1, TestRecord r2) {
            return Float.compare(r1.error, r2.error);
        }
    }

}
