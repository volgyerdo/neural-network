package javacnn.layers;

import javacnn.data.BackPropResult;
import javacnn.data.DataBlock;
import java.util.List;

/**
 * A convolution neural network is built of layers that the data traverses
 * back and forth in order to predict what the network sees in the data.
 *
 * @author Daniel Persson (mailto.woden@gmail.com)
 */
public interface Layer {
    DataBlock forward(DataBlock db, boolean training);
    void backward();
    List<BackPropResult> getBackPropagationResult();
}
