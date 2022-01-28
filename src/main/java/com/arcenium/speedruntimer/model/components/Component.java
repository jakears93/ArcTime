package com.arcenium.speedruntimer.model.components;

import com.arcenium.speedruntimer.model.GameSplits;

public interface Component {
    void update(GameSplits splits, int currentSplitIndex);
    String getName();
    String getValue();
}
