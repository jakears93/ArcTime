package com.arcenium.speedruntimer.repositories;

import com.arcenium.speedruntimer.model.Split;

import java.util.ArrayList;
import java.util.List;

//TODO link to db
public class SplitRepository implements Repository<Split, Long> {
    @Override
    public List<Split> findAll() {
        return null;
    }

    @Override
    public List<Split> findAllById(Long id) {
        //TODO remove temp list
        List<Split> splits = new ArrayList<>();
        Split split = new Split("BombBattleField", 10.00, 9.00);
        Split split1 = new Split("Whomps", 9.12, 8.45);
        Split split2 = new Split("CCM", 500.48, 498.00);
        splits.add(split);
        splits.add(split1);
        splits.add(split2);

        return splits;
    }

    @Override
    public boolean remove(Split split) {
        return false;
    }
    //----------Fields / Attributes----------//

    //----------Constructors----------//

    //----------Class Specific Methods----------//

    //----------Default Methods----------//

}//End of SplitRepository Class
