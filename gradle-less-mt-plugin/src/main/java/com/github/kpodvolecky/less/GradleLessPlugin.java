/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.kpodvolecky.less;

import com.github.kpodvolecky.less.configuration.GradleLessExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Gradle less compiler plugin
 */
public class GradleLessPlugin implements Plugin<Project> {
    public void apply(Project project) {
        // FIXME extension configuration not working
        // FIXME extension is populated after task is created, so task doesnt contain correct values
        // register extension
        GradleLessExtension extension = project.getExtensions().create(GradleLessExtension.NAME, GradleLessExtension.class, project);

        // Register a task
        project.getTasks().register("lessCompile", LessCompileTask.class)
                    .configure( task -> {
                        task.setGroup("build");
                        task.from(extension.getSourceTree());
                        task.into(extension.getDestinationDirectory());
                    });

    }
}
