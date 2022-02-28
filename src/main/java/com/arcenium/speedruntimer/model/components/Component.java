package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameInfo;

public interface Component {
    void update(GameInfo gameInfo, int currentSplitIndex);
    String getName();
    String getValue();
}//End of Component Interface
