/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.kpodvolecky.less;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * A simple functional test for the 'io.github.kpodvolecky.gradle-less-mt-plugin' plugin.
 */
class GradleLessPluginFunctionalTest {
    @TempDir
    File projectDir;

    private File getBuildFile() {
        return new File(projectDir, "build.gradle");
    }

    private File getSettingsFile() {
        return new File(projectDir, "settings.gradle");
    }

    private File getPropertiesFile() {
        return new File(projectDir, "gradle.properties");
    }

    @Test
    void canRunTask() throws IOException {
        // need to copy less files from src/functionalTest/resources to /tmp/less
        String buildGradle =
                        "plugins {\n" +
                        "  id('io.github.kpodvolecky.gradle-less-mt-plugin')\n" +
                        "}\n" +
                        "lessCompiler {\n" +
                        "  name 'GLC'\n" +
                        "}\n" +
                        "tasks.getByName('lessCompile') {\n " +
                        "    from fileTree('/tmp/less') { \n" +
                        "        include 'client*.less' \n" +
                        "        include '*client.less' \n" +
                        "        include 'theme/**/theme.less' \n" +
                        "    }.files \n " +
                        "    into layout.buildDirectory.dir('/tmp/css') \n " +
                        "} \n";
        String gradleProperties =
                "org.gradle.jvmargs=-Xmx4g\n" +
                "org.gradle.workers.max=5";
        writeString(getSettingsFile(), "");
        writeString(getBuildFile(), buildGradle);
        writeString(getPropertiesFile(), gradleProperties);

        // Run the build
        GradleRunner runner = GradleRunner.create();
        runner.forwardOutput();
        runner.withPluginClasspath();
        runner.withArguments("lessCompile");
        runner.withProjectDir(projectDir);
        BuildResult result = runner.build();

        System.out.println(result.getOutput());

        // Verify the result
        // assertTrue(result.getOutput().contains("Hello, GLC"));
    }

    private void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(string);
        }
    }
}
