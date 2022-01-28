package com.arcenium.speedruntimer.service;

import com.arcenium.speedruntimer.model.Split;
import com.arcenium.speedruntimer.repositories.SplitRepository;

import java.util.List;

public class SplitService {
    //----------Fields / Attributes----------//
    private SplitRepository splitRepository;
    private List<Split> splits;
    private int numberOfSplits = 0;
    private int currentSplitIndex = 0;

    //----------Constructors----------//
    public SplitService() {
        splitRepository = new SplitRepository();
        splits = splitRepository.findAllById(1L);
    }

    //----------Class Specific Methods----------//

    //----------Default Methods----------//
    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }

    public int getNumberOfSplits() {
        return splits.size();
    }

    public int getCurrentSplitIndex() {
        return currentSplitIndex;
    }

    public void setCurrentSplitIndex(int currentSplitIndex) {
        this.currentSplitIndex = currentSplitIndex;
    }
}//End of SplitService Class
