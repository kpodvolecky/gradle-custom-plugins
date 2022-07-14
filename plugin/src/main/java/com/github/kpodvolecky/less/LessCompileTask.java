package com.github.kpodvolecky.less;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import javax.inject.Inject;
import java.io.File;

import static org.gradle.api.tasks.PathSensitivity.RELATIVE;

@DisableCachingByDefault
public abstract class LessCompileTask extends DefaultTask {
    @SkipWhenEmpty
    @InputFiles
    @PathSensitive(RELATIVE)
    abstract ConfigurableFileCollection getSource();

    @OutputDirectory
    abstract public DirectoryProperty getDestinationDirectory();

    public LessCompileTask from(Object... paths) {
        getSource().from(paths);
        return this;
    }

    public LessCompileTask into(Provider<Directory> output) {
        getDestinationDirectory().set(output);
        return this;
    }

    @Inject
    abstract public WorkerExecutor getWorkerExecutor();

    @TaskAction
    public void lessCompile() {
        WorkQueue workQueue = getWorkerExecutor().noIsolation();
        for (File sourceFile : getSource().getFiles()) {
            try {
                File destFile = getDestinationDirectory().file(sourceFile.getName().replace(".less", ".css")).get().getAsFile();
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
