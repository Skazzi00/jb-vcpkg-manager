package com.jetbrains.vcpkg;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jetbrains.vcpkg.model.Package;

import java.io.IOException;
import java.util.Map;

public class VcpkgService implements PackageService {
    enum Commands {
        INSTALL,
        REMOVE,
        LIST
    }

    private static final Map<Commands, String>
            commands = Map.of(
            Commands.INSTALL, "vcpkg --x-json install",
            Commands.REMOVE, "vcpkg --x-json remove",
            Commands.LIST, "vcpkg --x-json list"
    );

    private final Runtime runtime;

    public VcpkgService(Runtime runtime) {
        this.runtime = runtime;
    }


    @Override
    public Package[] listInstalledPackages() throws CommandFailureException {
        String jsonResult;
        try {
            Process listProcess = runtime.exec(commands.get(Commands.LIST));
            listProcess.waitFor();
            if (listProcess.exitValue() != 0) {
                throw new CommandFailureException(
                        String.format("List command returned %d error code", listProcess.exitValue()));
            }

            if (listProcess.exitValue() != 0)
                throw new CommandFailureException(new String(listProcess.getInputStream().readAllBytes()));

            jsonResult = new String(listProcess.getInputStream().readAllBytes());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new CommandFailureException(e);
        }

        Gson gson = new Gson();
        Map<String, Package> packages = gson.fromJson(jsonResult,
                new TypeToken<Map<String, Package>>() {
                }.getType());

        return packages.values().toArray(new Package[0]);
    }

    @Override
    public void removePackage(String packageName) throws CommandFailureException {
        try {
            Process removeProcess = runtime.exec(String.format("%s %s", commands.get(Commands.REMOVE), packageName));
            removeProcess.waitFor();
            if (removeProcess.exitValue() != 0)
                throw new CommandFailureException(new String(removeProcess.getInputStream().readAllBytes()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new CommandFailureException(e);
        }
    }

    @Override
    public void installPackage(String packageName) throws CommandFailureException {
        try {
            Process installProcess = runtime.exec(String.format("%s %s", commands.get(Commands.INSTALL), packageName));
            installProcess.waitFor();
            if (installProcess.exitValue() != 0)
                throw new CommandFailureException(new String(installProcess.getInputStream().readAllBytes()));
        } catch (IOException |
                InterruptedException e) {
            e.printStackTrace();
            throw new CommandFailureException(e);
        }
    }
}
