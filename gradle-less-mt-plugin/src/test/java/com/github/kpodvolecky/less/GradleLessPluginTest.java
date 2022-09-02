/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.kpodvolecky.less;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * A simple unit test for the 'gradle.less.plugin.greeting' plugin.
 */
class GradleLessPluginTest {
    @Test void pluginRegistersATask() {
        // Create a test project and apply the plugin
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply(GradleLessPlugin.class);

        // Verify the result
        assertNotNull(project.getTasks().findByName("lessCompile"));
        assertNotNull(project.getExtensions().findByName("lessCompiler"));
    }
}
