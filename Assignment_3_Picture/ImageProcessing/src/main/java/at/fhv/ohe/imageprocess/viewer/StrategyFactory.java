package at.fhv.ohe.imageprocess.viewer;

import at.fhv.ohe.imageprocess.strategy.IStrategy;
import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Assignment_3_Picture
 * at.fhv.ohe.imageprocess.viewer
 * null.java
 * 12/11/2018 OliverHeil
 * <p>
 * Enter Description here
 */
public class StrategyFactory {

    private static final Object lock = new Object();
    private static StrategyFactory instance;

    private Map<String, ? extends IStrategy> loadedStrategies;

    private StrategyFactory() {
        Reflections reflections = new Reflections("at.fhv.ohe.imageprocess");
        Set<Class<? extends IStrategy>> classes = reflections.getSubTypesOf(IStrategy.class);

        loadedStrategies = classes.stream().map(clazz -> {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                return null;
            }
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(IStrategy::getStrategyName, v -> v));
    }

    public static StrategyFactory getInstance() {
        if (instance != null) return instance;
        synchronized (lock) {
            if (instance == null) {
                instance = new StrategyFactory();
            }
        }
        return instance;
    }

    public List<String> getAllStrategies() {
        return new ArrayList<>(loadedStrategies.keySet());
    }

    public IStrategy getStrategyByName(String strategyName) {
        return loadedStrategies.getOrDefault(strategyName, null);
    }
}
