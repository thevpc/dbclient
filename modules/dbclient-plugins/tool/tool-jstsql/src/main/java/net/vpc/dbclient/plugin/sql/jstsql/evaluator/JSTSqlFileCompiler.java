///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.vpc.dbclient.plugin.sql.jstsql.evaluator;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.io.Reader;
//import java.util.Date;
//import net.vpc.util.IOUtils;
//
///**
// *
// * @author vpc
// */
//public class JSTSqlFileCompiler implements JSTSqlCompiler {
//
//    private File defaultRootFolder;
//    private File binFolder;
//    private File srcFolder;
//    private JSTSqlTemplateCacheManager cache;
//
//    public JSTSqlFileCompiler(File defaultRootFolder) {
//        this.defaultRootFolder = defaultRootFolder;
//        if (this.defaultRootFolder != null && !this.defaultRootFolder.exists()) {
//            this.defaultRootFolder.mkdirs();
//        }
//        binFolder = new File(defaultRootFolder, "bin");
//        binFolder.mkdirs();
//        File srcFolder = new File(defaultRootFolder, "src");
//        srcFolder.mkdirs();
//        cache = new JSTSqlTemplateCacheManagerImpl(defaultRootFolder);
//    }
//
//    @Override
//    public Class compile(String classPackage, String className, Reader javaSource) throws IOException {
//        File sourceFile = new File(srcFolder, className + ".java");
//        PrintStream out=new PrintStream(sourceFile);
//        out.print(javaSource);
//        Integer compilationStatus = (Integer) javacMainClass.getMethod("compile", String[].class).invoke(null,
//                new Object[]{
//                    new String[]{
//                        "-classpath", classPath,
//                        "-d", binFolder.getCanonicalPath(),
//                        sourceFile.getCanonicalPath()
//                    }
//                });
//
//        File f = new File(binFolder.getCanonicalPath() + "/" + path + "/" + className + ".class");
//
//        JSTSqlTemplateCache newCache = new JSTSqlTemplateCache(context.getHashString(), nextClassName, new Date(f.lastModified()), classPath, IOUtils.loadStreamAsByteArray(f.toURI().toURL()));
//        if (context.getContextId() != null) {
//            cache.saveCache(newCache);
//        }
//        if (!Boolean.getBoolean("keep-sources")) {
//            sourceFile.delete();
//        }
//        f.delete();
//        return newCache.getImplClass(context);
//
//    }
//}
