package com.gliesestudio.spawnprotect;

import lombok.Data;

import java.io.Serializable;

@Data
public class Config implements Serializable {
    private SpawnPosition spawnPosition;
}
