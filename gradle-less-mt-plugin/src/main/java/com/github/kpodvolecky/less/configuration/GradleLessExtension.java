package com.github.kpodvolecky.less.configuration;

import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.Directory;
import org.gradle.api.internal.file.FileCollectionFactory;

/**
 * Plugin configuration.
 */
public class GradleLessExtension {
    public static final String NAME = "lessCompiler";
    private FileCollectionFactory fileCollectionFactory;
    private ConfigurableFileTree sourceTree;
    private Directory destinationDirectory;

    public GradleLessExtension(FileCollectionFactory fileCollectionFactory) {
        this.fileCollectionFactory=fileCollectionFactory;
        this.sourceTree = this.fileCollectionFactory.fileTree();
    }

    public void from(ConfigurableFileTree treeSpec) {
        try {
            System.err.println("ext.from tree: "+treeSpec);
            getSourceTree().from(treeSpec);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Directory getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(Directory destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    public ConfigurableFileTree getSourceTree() {
        return sourceTree;
    }

    public void setSourceTree(ConfigurableFileTree sourceTree) {
        this.sourceTree = sourceTree;
    }
}
