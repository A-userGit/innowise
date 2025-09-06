package org.innowise.javacore.skynet.entity;

import lombok.Getter;
import lombok.Setter;
import org.innowise.javacore.skynet.enums.CycleState;
import org.innowise.javacore.skynet.enums.FactionState;

@Getter
@Setter
public class DayState {
    private CycleState state;
    private FactionState[] factionStates;

    public DayState(int number)
    {
        factionStates = new FactionState[number];
        for(int i = 0; i < number; i++){
            factionStates[i] = FactionState.FINISHED;
        }
    }

    public boolean checkReadiness()
    {
        for(int i = 0; i < factionStates.length; i++) {
            if(state == CycleState.NIGHT){
                return false;
            }
            if(factionStates[i] == FactionState.ACTIVE) {
                return false;
            }
        }
        return true;
    }
}
