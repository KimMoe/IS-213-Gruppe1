/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is211.connect4.strategy;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;



/**
 *
 * @author evenal
 */
public class StrategyManager
{

    public static final String STRATEGY_DIR = "strategies";
    private static StrategyManager instance;

    private HashMap<String, Class<? extends GameAI>> strategies;

    /**
     * Get the one and only instance of this class.
     *
     * @return
     */
    public static StrategyManager getInstance() {
        if (null == instance)
            instance = new StrategyManager();
        return instance;
    }

    /**
     * Private constructor to prevent additional instances
     * to be created.
     */
    private StrategyManager() {
        strategies = new HashMap<>();
        autoLoadStrategies();
        register("Human", HumanStrategy.class);
    }

    /**
     * Load strategies from jar files. The jar files
     * must be located in STRATEG_YDIR
     */
    private void autoLoadStrategies() {
        URL[] jarUrls = getJarUrls(STRATEGY_DIR);
        if (null == jarUrls) return;

        URLClassLoader classLoader
                = new URLClassLoader(jarUrls);
        for (URL url : jarUrls) loadStrategy(classLoader, url);
    }

    /**
     * Convert the file listing of the strategy dir
     * to an array of urls (which is what the classloader
     * will accept)
     *
     * @param dirname
     * @return
     */
    private URL[] getJarUrls(String dirname) {
        File dir = new File(dirname);
        File[] jarFiles = dir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".jar");
            }
        });
        if (null == jarFiles) return null;
        URL[] urls = new URL[jarFiles.length];
        for (int i = 0; i < jarFiles.length; i++) {
            try {
                urls[i] = jarFiles[i].toURI().toURL();
            }
            catch (MalformedURLException mue) {
                mue.printStackTrace();
            }
        }
        return urls;
    }

    /**
     * Load a jar file with a single strategy class
     *
     * @param loader the classloader
     * @param url the url of the strategy jar file
     */
    private void loadStrategy(ClassLoader loader, URL url) {
        try {
            JarFile jf = new JarFile(url.getFile());
            Manifest manifest = jf.getManifest();
            Attributes attr = manifest.getMainAttributes();
            String strategyName = attr.getValue("Strategy-Name");
            if (strategyName == null) {
                // use basename of jar file, if missing in jar file
                File path = new File(url.getPath());
                String fileName = path.getName();
                // strip '.jar' extension
                strategyName = fileName.substring(0,
                                                  fileName.length() - 4);
            }
            //load the class
            String className = attr.getValue("Strategy-Class");
            Class cls = Class.forName(className, true, loader);
            GameAI strat = (GameAI) cls.newInstance();
            register(strategyName, cls);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public final void register(String name, Class<? extends GameAI> strategy) {
        strategies.put(name, strategy);
    }

    public GameAI getStrategy(String name) throws InstantiationException, IllegalAccessException {
        Class<? extends GameAI> strategyClass = strategies.get(name);

        if (null == strategyClass) return null;
        GameAI strat = strategyClass.newInstance();
        return strat;
    }

    public String[] getStrategyNames() {
        Set<String> names = strategies.keySet();
        return names.toArray(new String[0]);
    }
}
