package org.innowise.javacore.skynet.entity;

import lombok.extern.slf4j.Slf4j;
import org.innowise.javacore.skynet.enums.CycleState;
import org.innowise.javacore.skynet.enums.PartType;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Factory implements Runnable{

    private DayState cycleState;
    private LinkedList<Part> parts;
    private ReentrantLock lock;
    private int cycles;
    private int days;
    private Condition nightWait;

    public Factory(DayState cycleState, LinkedList<Part> parts, ReentrantLock lock, int cycles, Condition nightWait)
    {
        this.cycleState = cycleState;
        this.lock = lock;
        this.parts = parts;
        this.cycles = cycles;
        this.nightWait = nightWait;
        days = 0;
    }

    public void produceParts(int amount) {
        try {
            lock.lock();
            while (!cycleState.checkReadiness()) {
                nightWait.await();
            }
            days++;
            log.info("production started on day "+ days);
            for(int i = 0; i < amount; i++) {
                int type = (int) (Math.random() * 10) % 4;
                Part part = new Part(PartType.values()[type]);
                parts.add(part);
            }
            cycles--;
            log.info("production finished on day "+(days));
            cycleState.setState(CycleState.NIGHT);
            nightWait.signalAll();
        }
        catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while (cycles > 0){
            produceParts(10);
        }
    }
}
