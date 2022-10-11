package com.github.kpodvolecky.less;

import com.github.kpodvolecky.less.util.Utils;
import com.inet.lib.less.Less;
import org.gradle.workers.WorkAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public abstract class LessCompile implements WorkAction<LessCompilerParameters> {
    private final Logger log = LoggerFactory.getLogger(LessCompile.class);

    @Override
    public void execute() {
        File source = getParameters().getLessFileProperty().get();
        try {
            File base = getParameters().getBaseLessFileProperty().get();
            File destination = getParameters().getCssFileProperty().get();
            log.info("Compiling: " + source.getAbsolutePath() + " -> " + destination.getAbsolutePath());

            Utils.ensureDirectoryCreated(destination.getParentFile());

            String lessData = new String( Files.readAllBytes( source.toPath() ), StandardCharsets.UTF_8 );
            String output = Less.compile(source.getParentFile().toURI().toURL(), lessData, true);

            Files.write(destination.toPath(), output.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            /*
            LessCompilationEngine engine = LessCompilationEngineFactory.create(LessCompilationEngineFactory.RHINO);
            CompilationTask task = new CompilationTask(engine);
            CompilationUnit unit = new CompilationUnit(source, destination);
            task.getCompilationUnits().add(unit);

            Collection<CompilationUnit> results = task.execute();
*/
        } catch (Exception e) {
            throw new RuntimeException("Error compiling file: " + source.getAbsolutePath(), e);
        }
    }
}
