package com.fhr.aptquickstart.simple;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author Fan Huaran
 * created on 2018/12/11
 * @description
 */
@SupportedAnnotationTypes({"com.fhr.aptquickstart.simple.PrintMe"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class PrinteMeAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        for(TypeElement typeElement : annotations){
            for(Element element : roundEnv.getElementsAnnotatedWith(typeElement)){
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing:" + element.toString());
            }
        }

        return true;
    }

}
