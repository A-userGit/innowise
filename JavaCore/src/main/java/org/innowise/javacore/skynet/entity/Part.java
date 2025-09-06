package org.innowise.javacore.skynet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.innowise.javacore.skynet.enums.PartType;

@Setter
@Getter
@AllArgsConstructor
public class Part {
    private PartType type;
}
