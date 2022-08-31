package com.github.kpodvolecky.less;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.*;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.*;
import org.gradle.work.DisableCachingByDefault;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.gradle.api.tasks.PathSensitivity.RELATIVE;

@DisableCachingByDefault
public abstract class LessCompileTask extends DefaultTask {
    @IgnoreEmptyDirectories
    @InputFiles
    @PathSensitive(RELATIVE)
    abstract ConfigurableFileTree getSource();

    @OutputDirectory
    abstract public DirectoryProperty getDestinationDirectory();

    public LessCompileTask from(FileTree paths) {
        getSource().from(paths);
        return this;
    }

    public LessCompileTask into(Provider<Directory> output) {
        getDestinationDirectory().set(output);
        return this;
    }

    public LessCompileTask into(Directory output) {
        getDestinationDirectory().set(output);
        return this;
    }

    @Inject
    abstract public WorkerExecutor getWorkerExecutor();

    @TaskAction
    public void lessCompile() {
        WorkQueue workQueue = getWorkerExecutor().noIsolation();
        File baseDirectory = getSource().getDir();
        for (File sourceFile : getSource().getFiles()) {
            try {
                Path relativeDestPath = Paths.get(baseDirectory.getAbsolutePath()).relativize(Paths.get(sourceFile.getAbsolutePath()));
                File destFile = getDestinationDirectory().file(relativeDestPath.toString().replaceAll("\\.less$", ".css")).get().getAsFile();
                workQueue.submit(LessCompile.class, parameters -> {
                    parameters.getLessFile().set(sourceFile);
                    parameters.getCssFile().set(destFile);
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
