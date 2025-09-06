package org.innowise.javacore.skynet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PartType {
    HEAD(1), TORSO(1), HAND(2), FEET(1);

    private final int partsRequired;
}
