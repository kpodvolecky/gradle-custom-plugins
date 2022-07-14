package com.github.kpodvolecky.less;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.workers.WorkParameters;

public interface LessCompilerParameters extends WorkParameters {
    RegularFileProperty getLessFile();
    RegularFileProperty getCssFile();
}
