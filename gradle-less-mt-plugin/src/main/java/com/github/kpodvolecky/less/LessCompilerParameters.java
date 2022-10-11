package com.github.kpodvolecky.less;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.workers.WorkParameters;

import java.io.File;

public interface LessCompilerParameters extends WorkParameters {
    Property<File> getLessFileProperty();

    Property<File> getBaseLessFileProperty();

    Property<File> getCssFileProperty();
}
