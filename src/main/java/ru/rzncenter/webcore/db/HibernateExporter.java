package ru.rzncenter.webcore.db;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Генерация DDL схемы базы данных
 * @author john.thompson
 * @author Churkin Anton
 */
public class HibernateExporter {

    private MetadataSources metadata;

    public HibernateExporter(String[] packageNames) throws Exception {

        metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", Dialect.POSTGRESQL.getDialectClass())
                        .build());

        for (String packageName : packageNames) {

            for (Class<Object> clazz : getClasses(packageName)) {
                metadata.addAnnotatedClass(clazz);
            }

        }
    }

    /**
     * Method that actually creates the file.
     *
     */
    private void generate() {

        SchemaExport export = new SchemaExport((MetadataImplementor) metadata.buildMetadata());
        export.setDelimiter(";");
        export.setOutputFile("ddl_" + Dialect.POSTGRESQL.name().toLowerCase() + ".sql");
        export.execute(true, false, false, false);
    }

    /**
     * Запуск генерации
     * @param args имена пакетов
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        HibernateExporter gen = new HibernateExporter(args);
        gen.generate();
    }

    /**
     * Utility method used to fetch Class list based on a package name.
     *
     * @param packageName (should be the package containing your annotated beans.
     */
    private List<Class> getClasses(String packageName) throws Exception {
        List<Class> classes = new ArrayList<>();
        File directory = null;
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = packageName.replace('.', '/');
            URL resource = cld.getResource(path);
            if (resource == null) {
                throw new ClassNotFoundException("No resource for " + path);
            }
            directory = new File(resource.getFile());
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(packageName + " (" + directory
                    + ") does not appear to be a valid package");
        }
        if (directory.exists()) {
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(packageName + "."
                            + files[i].substring(0, files[i].length() - 6)));
                }
            }
        } else {
            throw new ClassNotFoundException(packageName
                    + " is not a valid package");
        }

        return classes;
    }

    /**
     * Holds the classnames of hibernate dialects for easy reference.
     */
    private static enum Dialect {
        ORACLE("org.hibernate.dialect.Oracle10gDialect"),
        MYSQL("org.hibernate.dialect.MySQLDialect"),
        POSTGRESQL("org.hibernate.dialect.PostgreSQL9Dialect"),
        HSQL("org.hibernate.dialect.HSQLDialect");

        private String dialectClass;

        private Dialect(String dialectClass) {
            this.dialectClass = dialectClass;
        }

        public String getDialectClass() {
            return dialectClass;
        }
    }
}
