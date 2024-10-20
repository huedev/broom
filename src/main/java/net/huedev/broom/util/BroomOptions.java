package net.huedev.broom.util;

import net.minecraft.client.option.Option;

/**
 * @author DanyGames2014
 */
public class BroomOptions {
    public static Option fovOption;
    public static Option cloudsOption;
    public static Option cloudHeightOption;
    public static Option fpsLimitOption;

    // FOV
    public static float fov;

    public static int getFovInDegrees() {
        return Math.round(70.0F + fov * 40.0F);
    }

    // Clouds
    public static boolean clouds = true;
    public static float cloudHeight;

    public static int getCloudHeight() {
        return Math.round(108.0F + cloudHeight * 24.0F);
    }

    // FPS Limit
    public static float fpsLimit = 0.4F;

    public static int getFpsLimitValue() {
        return (int) (Math.floor(fpsLimit * 59.0F) * 5.0F) + 5;
    }

    public static int getPerformanceLevel() {
        if (getFpsLimitValue() >= 300) {
            return 0;
        } else {
            return 2;
        }
    }
}
