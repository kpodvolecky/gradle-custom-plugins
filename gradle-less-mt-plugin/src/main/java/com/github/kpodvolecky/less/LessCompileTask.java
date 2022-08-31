package com.github.kpodvolecky.less;

import com.github.kpodvolecky.less.util.Utils;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileTree;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.IgnoreEmptyDirectories;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.TaskAction;
import org.gradle.work.DisableCachingByDefault;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import javax.inject.Inject;
import java.io.File;

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
                String destString = Utils.getDestinationPath(baseDirectory.getAbsolutePath(), sourceFile.getAbsolutePath(), getDestinationDirectory().toString());
                File destFile = new File(destString);
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
