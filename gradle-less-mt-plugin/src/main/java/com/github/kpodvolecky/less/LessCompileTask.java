package com.github.kpodvolecky.less;

import com.github.kpodvolecky.less.util.Utils;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
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

public abstract class LessCompileTask extends DefaultTask {
    private DirectoryProperty destinationDirectory;

    public LessCompileTask() {
        ObjectFactory objectFactory = getProject().getObjects();
        this.destinationDirectory = objectFactory.directoryProperty();
    }

    @SkipWhenEmpty
    @InputFiles
    @PathSensitive(RELATIVE)
    abstract Property<ConfigurableFileTree> getSource();

    @OutputDirectory
    public DirectoryProperty getDestinationDirectory() {
        return this.destinationDirectory;
    }

    public LessCompileTask into(DirectoryProperty output) {
        getDestinationDirectory().set(output.get());
        return this;
    }

    public LessCompileTask into(Directory output) {
        getDestinationDirectory().set(output);
        return this;
    }

    public LessCompileTask from(ConfigurableFileTree sourceTree) {
        getSource().set(sourceTree);
        return this;
    }

    @Inject
    abstract public WorkerExecutor getWorkerExecutor();

    @TaskAction
    public void lessCompile() {
        getLogger().debug("Executing lessCompile task, destination directory: "+getDestinationDirectory().get().toString());
        WorkQueue workQueue = getWorkerExecutor().noIsolation();

        File baseDirectory = getSource().get().getDir();
        for (File sourceFile : getSource().get().getFiles()) {
            try {
                String destString = Utils.getDestinationPath(baseDirectory.getAbsolutePath(), sourceFile.getAbsolutePath(), getDestinationDirectory().get().toString());
                File destFile = new File(destString);
                workQueue.submit(LessCompile.class, parameters -> {
                    getLogger().debug("Setting base file: "+baseDirectory);
                    parameters.getBaseLessFileProperty().set(baseDirectory);
                    getLogger().debug("Setting source file: "+sourceFile);
                    parameters.getLessFileProperty().set(sourceFile);
                    getLogger().debug("Setting destination file: "+destFile);
                    parameters.getCssFileProperty().set(destFile);
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
