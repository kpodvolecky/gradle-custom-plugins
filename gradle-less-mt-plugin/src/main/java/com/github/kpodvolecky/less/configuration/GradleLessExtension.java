package com.github.kpodvolecky.less.configuration;

/**
 * Plugin configuration.
 */
public class GradleLessExtension {
    private String name = "Gradle Less compilation plugin";

    private String srcDir;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
