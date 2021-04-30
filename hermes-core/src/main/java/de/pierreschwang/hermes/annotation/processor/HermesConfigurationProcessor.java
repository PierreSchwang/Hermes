package de.pierreschwang.hermes.annotation.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import de.pierreschwang.hermes.annotation.HermesConfiguration;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class HermesConfigurationProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(
                HermesConfiguration.class.getCanonicalName()
        );
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(HermesConfiguration.class);
        if (elements.size() > 1) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Exactly one class must be annotated with @HermesConfiguration");
            return false;
        }
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            HermesConfiguration configuration = element.getAnnotation(HermesConfiguration.class);
            try {
                new HermesConfigurationMapper(typeElement.getQualifiedName())
                        .generate(filer.createResource(StandardLocation.CLASS_OUTPUT, "", "plugin.yml"), configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
