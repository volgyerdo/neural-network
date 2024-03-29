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
public class TestRecord implements Serializable{
    
    public static final long versionUID = 1L;
    
    public float error;
    public long timestamp;
    public int runTime;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Float.floatToIntBits(this.error);
        hash = 67 * hash + (int) (this.timestamp ^ (this.timestamp >>> 32));
        hash = 67 * hash + this.runTime;
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
        final TestRecord other = (TestRecord) obj;
        if (Float.floatToIntBits(this.error) != Float.floatToIntBits(other.error)) {
            return false;
        }
        if (this.timestamp != other.timestamp) {
            return false;
        }
        if (this.runTime != other.runTime) {
            return false;
        }
        return true;
    }
    
}
