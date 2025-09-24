package fr.gillesphan.cirilgroup.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Configuration class for the application.
 * In future versions, this class will be a bean and configuration will be
 * stored in database.
 */
@Component
@ConfigurationProperties(prefix = "cg")
public class AppConfiguration {

    private int width;
    private int height;
    /**
     * To delete ?
     * 
     * @deprecated not used
     */
    private List<FirePosition> startFirePositions;
    private double contagionRate;

    public AppConfiguration() {
    }

    public AppConfiguration(int width, int height, List<FirePosition> startFirePositions, double contagionRate) {
        this.width = width;
        this.height = height;
        this.startFirePositions = startFirePositions;
        this.contagionRate = contagionRate;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<FirePosition> getStartFirePositions() {
        return startFirePositions;
    }

    public void setStartFirePositions(List<FirePosition> startFirePositions) {
        this.startFirePositions = startFirePositions;
    }

    public double getContagionRate() {
        return contagionRate;
    }

    public void setContagionRate(double contagionRate) {
        this.contagionRate = contagionRate;
    }

}
