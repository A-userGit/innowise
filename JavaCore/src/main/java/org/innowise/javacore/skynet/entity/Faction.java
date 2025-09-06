package org.innowise.javacore.skynet.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.innowise.javacore.skynet.enums.CycleState;
import org.innowise.javacore.skynet.enums.FactionState;
import org.innowise.javacore.skynet.enums.PartType;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Faction implements Runnable{

    private final String name;
    private final int number;
    private int cycles;
    private LinkedList<Part> externalPartsStorage;
    private int[] partsAmount;
    private DayState cycleState;
    private ReentrantLock lock;
    private Condition cycleStateCondition;
    private Condition maxTakenCondition;
    @Getter
    private int botsConstructed;

    public Faction(String name, int cycles, LinkedList<Part> externalPartsStorage, DayState cycleState,
                   ReentrantLock lock, int number, Condition cycleStateCondition, Condition maxTakenCondition)
    {
        this.cycleState = cycleState;
        this.externalPartsStorage = externalPartsStorage;
        this.lock = lock;
        this.cycles = cycles;
        this.name = name;
        partsAmount = new int[PartType.values().length];
        botsConstructed = 0;
        this.cycleStateCondition = cycleStateCondition;
        this.maxTakenCondition = maxTakenCondition;
        this.number = number;
    }

    @Override
    public void run() {
        while(cycles>0)
        {
            try {
                lock.lock();
                cycleState.getFactionStates()[number] = FactionState.FINISHED;
                cycleStateCondition.signalAll();
                while (cycleState.getState() == CycleState.DAY){
                    cycleStateCondition.await();
                }
                cycleState.getFactionStates()[number] = FactionState.ACTIVE;
                for(int i = 0; i < 5; i++) {
                    getPart();
                }
                if(externalPartsStorage.isEmpty()){
                    cycleStateCondition.signalAll();
                }
                cycles--;
                if(externalPartsStorage.isEmpty()){
                    cycleState.setState(CycleState.DAY);
                    maxTakenCondition.signalAll();
                }
            }catch (InterruptedException e){
                log.error(e.getMessage());
            }finally {
                lock.unlock();
            }
            try{
                lock.lock();
                while (cycleState.getState() == CycleState.NIGHT) {
                    maxTakenCondition.await();
                }
            }catch (InterruptedException e) {
                log.error(e.getMessage());
            }finally {
                lock.unlock();
            }
        }
        botsConstructed = constructBots();
    }

    private int constructBots()
    {
        int minParts = 0;
        int minPos = -1;
        for(int i = 0; i <partsAmount.length; i++){
            if(partsAmount[i] == 0)
                return 0;
            if(minParts == 0|| minParts > partsAmount[i]){
                minParts = partsAmount[i];
                minPos = i;
            }
        }
        int botsToBuild = (int)Math.floor((double) minParts /PartType.values()[minPos].getPartsRequired());
        if(botsToBuild < 1)
            return 0;
        for (int i = 0; i < partsAmount.length; i++) {
            partsAmount[i] = partsAmount[i] - botsToBuild * PartType.values()[i].getPartsRequired();
        }
        return botsToBuild;
    }

    private void getPart() throws InterruptedException {
        Part first = externalPartsStorage.getFirst();
        log.info("Faction "+ name +" got detail "+ first.getType());
        externalPartsStorage.removeFirst();
        partsAmount[first.getType().ordinal()]++;
    }
}
