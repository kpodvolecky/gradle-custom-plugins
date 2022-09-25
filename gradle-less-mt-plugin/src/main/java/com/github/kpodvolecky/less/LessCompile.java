package com.github.kpodvolecky.less;

import com.github.houbie.lesscss.builder.CompilationTask;
import com.github.houbie.lesscss.builder.CompilationUnit;
import com.github.houbie.lesscss.engine.LessCompilationEngine;
import com.github.houbie.lesscss.engine.LessCompilationEngineFactory;
import org.gradle.workers.WorkAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public abstract class LessCompile implements WorkAction<LessCompilerParameters> {
    private final Logger log = LoggerFactory.getLogger(LessCompile.class);

    @Override
    public void execute() {
        try {
            File source = getParameters().getLessFileProperty().get();
            File destination = getParameters().getCssFileProperty().get();
            log.info("Compiling: " + source.getAbsolutePath() + " -> " + destination.getAbsolutePath());

            LessCompilationEngine engine = LessCompilationEngineFactory.create(LessCompilationEngineFactory.RHINO);
            CompilationTask task = new CompilationTask(engine);
            CompilationUnit unit = new CompilationUnit(source, destination);
            task.getCompilationUnits().add(unit);

            Collection<CompilationUnit> results = task.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
