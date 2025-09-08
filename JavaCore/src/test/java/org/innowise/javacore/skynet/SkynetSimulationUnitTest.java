package org.innowise.javacore.skynet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SkynetSimulationUnitTest {

  @Test
  @DisplayName("Test skynet simulation")
  public void testSimulate() {
    SkynetSimulation simulation = new SkynetSimulation();
    simulation.simulate();
  }
}
