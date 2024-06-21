/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package volgyerdo.neural.structure;

import java.util.List;

/**
 *
 * @author Volgyerdo Nonprofit Kft.
 */
public interface TrainAction {
    
    public void action(Network network, List<TestRecord> testData);
    
}
