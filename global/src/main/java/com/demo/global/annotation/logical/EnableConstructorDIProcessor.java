package com.demo.global.annotation.logical;

import com.demo.global.annotation.EnableConstructorDI;
import org.springframework.context.annotation.Primary;
import org.springframework.javapoet.*;
import org.springframework.stereotype.Component;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("EnableConstructorDI")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class EnableConstructorDIProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv == null) return false;

        for (Element element : roundEnv.getElementsAnnotatedWith(EnableConstructorDI.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Annotation chỉ áp dụng cho lớp!", element);
                continue;
            }

            var typeElement = (TypeElement) element;
            List<VariableElement> fields = new ArrayList<>();

            // Thu thập tất cả các trường
            for (Element enclosed : typeElement.getEnclosedElements()) {
                if (enclosed.getKind() == ElementKind.FIELD) {
                    fields.add((VariableElement) enclosed);
                }
            }

            if (fields.isEmpty()) {
                messager.printMessage(Diagnostic.Kind.WARNING, "Không tìm thấy trường nào để xử lý!", element);
                continue;
            }

            // Tạo constructor
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC);

            // Tạo danh sách trường với private final
            List<FieldSpec> fieldSpecs = new ArrayList<>();
            for (VariableElement field : fields) {
                String fieldName = field.getSimpleName().toString();
                TypeName fieldType = TypeName.get(field.asType());

                // Bỏ qua kiểu nguyên thủy và gói java.*
                if (field.asType().getKind().isPrimitive() || field.asType().toString().startsWith("java.")) continue;

                // Thêm trường private final
                fieldSpecs.add(FieldSpec.builder(fieldType, fieldName)
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                        .build());

                // Thêm tham số và gán giá trị trong constructor
                constructorBuilder.addParameter(fieldType, fieldName)
                        .addStatement("this.$N = $N", fieldName, fieldName);
            }

            if (fieldSpecs.isEmpty()) {
                messager.printMessage(Diagnostic.Kind.WARNING, "Không có trường hợp lệ để tạo constructor!", element);
                continue;
            }

            // Tạo lớp mới
            TypeSpec generatedClass = TypeSpec.classBuilder(typeElement.getSimpleName() + "Generated")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(TypeName.get(typeElement.asType()))
                    .addAnnotation(Component.class)
                    .addAnnotation(Primary.class)
                    .addFields(fieldSpecs)
                    .addMethod(constructorBuilder.build())
                    .build();

            // Tạo file Java
            JavaFile javaFile = JavaFile.builder(
                    elementUtils.getPackageOf(typeElement).getQualifiedName().toString(),
                    generatedClass
            ).build();

            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Lỗi khi tạo lớp: " + e.getLocalizedMessage(), element);
                return false;
            }
        }
        return true;
    }
}
