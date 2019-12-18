package com.monirul.deputy.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Role {

    @SerializedName("Id")
    private int id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Parent")
    private int parentId;
}
