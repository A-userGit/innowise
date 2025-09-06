package org.innowise.javacore.skynet;

import lombok.extern.slf4j.Slf4j;
import org.innowise.javacore.skynet.entity.DayState;
import org.innowise.javacore.skynet.entity.Faction;
import org.innowise.javacore.skynet.entity.Factory;
import org.innowise.javacore.skynet.entity.Part;
import org.innowise.javacore.skynet.enums.CycleState;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SkynetSimulation {

    private volatile CycleState cycleState;

    public void simulate(){
        int daysCount = 100;
        LinkedList<Part> partsStorage = new LinkedList<>();
        ReentrantLock lock = new ReentrantLock();
        DayState state = new DayState(2);
        state.setState(CycleState.DAY);
        Condition cycleCondition = lock.newCondition();
        Condition maxDetailsTaken = lock.newCondition();
        Factory factory = new Factory(state, partsStorage, lock, daysCount, cycleCondition);
        Faction world = new Faction("World", daysCount, partsStorage, state, lock, 0, cycleCondition, maxDetailsTaken);
        Faction wednesday = new Faction("Wednesday", daysCount, partsStorage, state, lock, 1, cycleCondition, maxDetailsTaken);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(factory);
        executorService.submit(world);
        executorService.submit(wednesday);
        executorService.close();
        if(world.getBotsConstructed() > wednesday.getBotsConstructed()) {
            log.info("World won");
        } else if (wednesday.getBotsConstructed() > world.getBotsConstructed()) {
            log.info("Wednesday won");
        }else {
            log.info("Draw");
        }
    }

}
