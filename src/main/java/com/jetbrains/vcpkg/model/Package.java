package com.jetbrains.vcpkg.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Package {
    @SerializedName(value = "package_name")
    public String packageName;
    public String triplet;
    public String version;
    @SerializedName(value = "port_version")
    public String portVersion;
    public String[] features;
    @SerializedName(value = "desc")
    public String[] description;

    @Override
    public String toString() {
        return "Package{" +
                "packageName='" + packageName + '\'' +
                ", triplet='" + triplet + '\'' +
                ", version='" + version + '\'' +
                ", portVersion='" + portVersion + '\'' +
                ", features=" + Arrays.toString(features) +
                ", description=" + Arrays.toString(description) +
                '}';
    }
}
