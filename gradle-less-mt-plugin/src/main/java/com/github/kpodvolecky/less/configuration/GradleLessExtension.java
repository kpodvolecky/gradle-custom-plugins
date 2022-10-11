package com.github.kpodvolecky.less.configuration;

import com.github.kpodvolecky.less.LessCompileTask;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;

/**
 * Plugin configuration.
 */
public class GradleLessExtension {
    public static final String NAME = "lessCompiler";
    private Project project;
    private ConfigurableFileTree sourceTree;
    private DirectoryProperty destinationDirectory;

    public GradleLessExtension(Project project) {
        this.project = project;
        // set default values
        setSourceTree(project.getObjects().fileTree().from(project.getLayout().getProjectDirectory()));
        setDestinationDirectory(project.getLayout().getBuildDirectory());
    }

    public void from(ConfigurableFileTree treeSpec) {
        try {
            getSourceTree().setBuiltBy(treeSpec.getBuiltBy());
            getSourceTree().setDir(treeSpec.getDir());
            getSourceTree().setIncludes(treeSpec.getIncludes());
            getSourceTree().setIncludes(treeSpec.getExcludes());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void into(Provider<Directory> output) {
        getDestinationDirectory().set(output.get());
    }

    public void into(Directory output) {
        getDestinationDirectory().set(output);
    }

    public DirectoryProperty getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(DirectoryProperty destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public ConfigurableFileTree getSourceTree() {
        return sourceTree;
    }

    public void setSourceTree(ConfigurableFileTree sourceTree) {
        this.sourceTree = sourceTree;
    }
}
