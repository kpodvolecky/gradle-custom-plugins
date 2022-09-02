package com.github.kpodvolecky.less.configuration;

import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.Directory;

/**
 * Plugin configuration.
 */
public class GradleLessExtension {
    public static final String NAME = "lessCompiler";
    private ConfigurableFileCollection source;
    private Directory destinationDirectory;

    public ConfigurableFileCollection getSource() {
        return source;
    }

    public void setSource(ConfigurableFileCollection source) {
        this.source = source;
    }

    public Directory getDestinationDirectory() {
        return destinationDirectory;
    }

    public void setDestinationDirectory(Directory destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }
}
