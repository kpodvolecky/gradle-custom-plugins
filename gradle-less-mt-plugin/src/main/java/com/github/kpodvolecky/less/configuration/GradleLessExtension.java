package com.github.kpodvolecky.less.configuration;

import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

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
        setSourceTree(project.getObjects().fileTree());
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

    public Property<Directory> getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(DirectoryProperty destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public void setDestinationDirectory(Directory destinationDirectory) {
        this.destinationDirectory = project.getObjects().directoryProperty().value(destinationDirectory);
    }

    public ConfigurableFileTree getSourceTree() {
        return sourceTree;
    }

    public void setSourceTree(ConfigurableFileTree sourceTree) {
        this.sourceTree = sourceTree;
    }
}
