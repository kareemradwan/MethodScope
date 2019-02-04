/*
 * Copyright (C) 2017 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.Modifier;

@SuppressWarnings("WeakerAccess")
public class InitializeScopeAnnotationGenerator {

    private final MethodScopeAnnotatedClass annotatedClazz;
    private final String packageName;
    private final String scopeName;

    private static final String SCOPE_INITIALIZE = "Init";
    private static final String SCOPE_POSTFIX = "Scope";

    public InitializeScopeAnnotationGenerator(MethodScopeAnnotatedClass annotatedClass, String packageName, String scopeName) {
        this.annotatedClazz = annotatedClass;
        this.packageName = packageName;
        this.scopeName = scopeName;
    }

    public TypeSpec generate() {
        ClassName annotationClassName = ClassName.get(packageName, getAnnotationName());
        ClassName elementTypeClassName = ClassName.get(ElementType.class);
        ClassName retentionPolicyClassName = ClassName.get(RetentionPolicy.class);
        return TypeSpec.annotationBuilder(annotationClassName)
                .addJavadoc("Generated by MethodScope. (https://github.com/skydoves/MethodScope).\n")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Documented.class)
                .addAnnotation(AnnotationSpec.builder(Target.class)
                        .addMember("value", "$T.METHOD", elementTypeClassName)
                        .build())
                .addAnnotation(AnnotationSpec.builder(Retention.class)
                        .addMember("value", "$T.CLASS", retentionPolicyClassName)
                        .build())
                .build();
    }

    private String getAnnotationName() {
        return SCOPE_INITIALIZE + scopeName + SCOPE_POSTFIX;
    }
}
