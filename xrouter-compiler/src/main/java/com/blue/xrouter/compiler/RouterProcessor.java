package com.blue.xrouter.compiler;

import com.blue.xrouter.annotation.Router;
import com.blue.xrouter.annotation.RouterApp;
import com.blue.xrouter.annotation.RouterInterceptor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by blue on 2018/10/2.
 */
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private static final boolean DEBUG = true;
    private static final String PACKAGE_NAME = "com.blue.xrouter";
    private static final String XROUTER_MODULES = "XRouterModules";
    private static final String XROUTER_MODULE_NAME = "XRouterModuleName";

    private static final String XROUTER_APP_INIT = "XRouterAppInit";
    private static final String XROUTER_MODULE_INIT = "XRouterModuleInit_";

    private Messager messager;
    private Filer filer;
    private String modules = "";
    private String moduleName = "";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        Map<String, String> map = processingEnv.getOptions();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (XROUTER_MODULES.equals(key)) {
                this.modules = map.get(key);
            }
            if (XROUTER_MODULE_NAME.equals(key)) {
                this.moduleName = map.get(key);
            }
        }
        debug("apt init");
        debug("modules:" + modules);
        debug("moduleName:" + moduleName);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> ret = new HashSet<>();
        ret.add(RouterApp.class.getCanonicalName());
        ret.add(Router.class.getCanonicalName());
        ret.add(RouterInterceptor.class.getCanonicalName());
        return ret;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.size() == 0) {
            return false;
        }
        debug("process apt with " + set.toString());
        generateRouterApp(roundEnvironment);
        generateRouterModule(roundEnvironment);

        return true;
    }

    private void generateRouterApp(RoundEnvironment roundEnvironment) {
        Set<? extends Element> routerAppList = roundEnvironment.getElementsAnnotatedWith(RouterApp.class);
        if (routerAppList != null && routerAppList.size() > 0) {
            debug("generateRouterApp");
            MethodSpec.Builder initMethod = MethodSpec.methodBuilder("init")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

            if (!modules.isEmpty()) {
                String[] moduleNames = modules.split("[,]");
                for (String module : moduleNames) {
                    debug("add module:" + module);
                    initMethod.addStatement("com.blue.xrouter.tools.Logger.INSTANCE.d($S, $S)", "XRouter", "------ init " + module + " ------");
                    initMethod.addStatement(XROUTER_MODULE_INIT + module + ".registerPage()");
                    initMethod.addStatement(XROUTER_MODULE_INIT + module + ".registerMethod()");
                    initMethod.addStatement(XROUTER_MODULE_INIT + module + ".registerInterceptor()");
                }
            }

            TypeSpec routerAppInit = TypeSpec.classBuilder(XROUTER_APP_INIT)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(initMethod.build())
                    .build();

            try {
                JavaFile.builder(PACKAGE_NAME, routerAppInit).build().writeTo(filer);
                debug("generateRouterApp success");
            } catch (Throwable e) {
                e.printStackTrace();
                error(e.getMessage());
            }
        }
    }

    private void generateRouterModule(RoundEnvironment roundEnvironment) {
        Set<? extends Element> routerList = roundEnvironment.getElementsAnnotatedWith(Router.class);
        Set<? extends Element> routerInterceptorList = roundEnvironment.getElementsAnnotatedWith(RouterInterceptor.class);
        if (!moduleName.isEmpty()) {
            debug("generateRouterModule");
            MethodSpec.Builder registerPage = MethodSpec.methodBuilder("registerPage")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

            MethodSpec.Builder registerMethod = MethodSpec.methodBuilder("registerMethod")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

            MethodSpec.Builder registerInterceptor = MethodSpec.methodBuilder("registerInterceptor")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

            if (routerList != null && routerList.size() > 0) {
                debug("generateRouter");
                for (Element element : routerList) {
                    Router router = element.getAnnotation(Router.class);
                    if (element.getKind() == ElementKind.CLASS) {
                        TypeElement typeElement = (TypeElement) element;
                        for (String target : router.value()) {
                            registerPage.addStatement("com.blue.xrouter.XRouter.INSTANCE.registerPage($S, $T.class)", target, typeElement.asType());
                        }
                    } else if (element.getKind() == ElementKind.METHOD) {
                        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                        for (String target : router.value()) {
                            registerMethod.addStatement("com.blue.xrouter.XRouter.INSTANCE.registerMethod($S, " +
                                    "new XRouterMethod() {\n" +
                                    "   public void invoke(com.blue.xrouter.XRouterParams routerParams) {\n" +
                                    "       $T.$N(routerParams);\n" +
                                    "   }\n" +
                                    "}) ", target, typeElement.asType(), element.getSimpleName());
                        }
                    } else {
                        error("process Router by unknown type");
                    }
                }
            }

            if (routerInterceptorList != null && routerInterceptorList.size() > 0) {
                debug("generateRouterInterceptor");
                for (Element element : routerInterceptorList) {
                    RouterInterceptor routerInterceptor = element.getAnnotation(RouterInterceptor.class);
                    if (element.getKind() == ElementKind.CLASS) {
                        TypeElement typeElement = (TypeElement) element;
                        int target = routerInterceptor.priority();
                        registerInterceptor.addStatement("com.blue.xrouter.XRouter.INSTANCE.registerInterceptor($L, new $T())", target, typeElement.asType());
                    } else {
                        error("process RouterInterceptor by unknown type");
                    }
                }
            }

            TypeSpec routerInit = TypeSpec.classBuilder(XROUTER_MODULE_INIT + moduleName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(registerPage.build())
                    .addMethod(registerMethod.build())
                    .addMethod(registerInterceptor.build())
                    .build();

            try {
                JavaFile.builder(PACKAGE_NAME, routerInit).build().writeTo(filer);
                debug("generateRouterModule success");
            } catch (Throwable e) {
                e.printStackTrace();
                error(e.getMessage());
            }
        }
    }

    private void error(String error) {
        messager.printMessage(Diagnostic.Kind.ERROR, error);
    }

    private void debug(String msg) {
        if (DEBUG) {
            messager.printMessage(Diagnostic.Kind.NOTE, msg);
        }
    }
}
