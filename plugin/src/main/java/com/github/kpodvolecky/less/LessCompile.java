package com.github.kpodvolecky.less;

import com.github.houbie.lesscss.builder.CompilationTask;
import com.github.houbie.lesscss.builder.CompilationUnit;
import com.github.houbie.lesscss.engine.LessCompilationEngine;
import com.github.houbie.lesscss.engine.LessCompilationEngineFactory;
import org.gradle.workers.WorkAction;

import java.io.File;
import java.util.Collection;

public abstract class LessCompile implements WorkAction<LessCompilerParameters> {
    @Override
    public void execute() {
        try {
            File source = getParameters().getLessFile().get().getAsFile();
            File destination = getParameters().getCssFile().get().getAsFile();
            System.out.println("Compiling: " + source.getAbsolutePath() + " -> " + destination.getAbsolutePath());

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
