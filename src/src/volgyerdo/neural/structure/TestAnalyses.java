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
package volgyerdo.neural.structure;

import java.io.Serializable;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public class TestAnalyses implements Serializable{
    
    public static final long versionUID = 1L;

    public int sampleSize;
    public float errorGeometricMean;
    public float errorArithmeticMean;
    public float errorMedian;
    public float errorStandardDeviation;
    public float minError;
    public float maxError;
    public long runTime;
    public long runPeriod;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.sampleSize;
        hash = 47 * hash + Float.floatToIntBits(this.errorGeometricMean);
        hash = 47 * hash + Float.floatToIntBits(this.errorArithmeticMean);
        hash = 47 * hash + Float.floatToIntBits(this.errorMedian);
        hash = 47 * hash + Float.floatToIntBits(this.errorStandardDeviation);
        hash = 47 * hash + Float.floatToIntBits(this.minError);
        hash = 47 * hash + Float.floatToIntBits(this.maxError);
        hash = 47 * hash + (int) (this.runTime ^ (this.runTime >>> 32));
        hash = 47 * hash + (int) (this.runPeriod ^ (this.runPeriod >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TestAnalyses other = (TestAnalyses) obj;
        if (this.sampleSize != other.sampleSize) {
            return false;
        }
        if (Float.floatToIntBits(this.errorGeometricMean) != Float.floatToIntBits(other.errorGeometricMean)) {
            return false;
        }
        if (Float.floatToIntBits(this.errorArithmeticMean) != Float.floatToIntBits(other.errorArithmeticMean)) {
            return false;
        }
        if (Float.floatToIntBits(this.errorMedian) != Float.floatToIntBits(other.errorMedian)) {
            return false;
        }
        if (Float.floatToIntBits(this.errorStandardDeviation) != Float.floatToIntBits(other.errorStandardDeviation)) {
            return false;
        }
        if (Float.floatToIntBits(this.minError) != Float.floatToIntBits(other.minError)) {
            return false;
        }
        if (Float.floatToIntBits(this.maxError) != Float.floatToIntBits(other.maxError)) {
            return false;
        }
        if (this.runTime != other.runTime) {
            return false;
        }
        if (this.runPeriod != other.runPeriod) {
            return false;
        }
        return true;
    }
    
}
