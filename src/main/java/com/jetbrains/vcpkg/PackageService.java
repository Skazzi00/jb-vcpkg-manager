package com.jetbrains.vcpkg;

import com.jetbrains.vcpkg.model.Package;

public interface PackageService {
    Package[] listInstalledPackages() throws CommandFailureException;
    void removePackage(String packageName) throws CommandFailureException;
    void installPackage(String packageName) throws CommandFailureException;
}
