package net.orbyfied.coldlib.plugin;

import net.orbyfied.coldlib.ColdLib;
import net.orbyfied.coldlib.ColdLibProvider;
import net.orbyfied.j8.util.logging.EventLogHandler;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BukkitColdLibProvider extends ColdLibProvider {

    //////////////////////////////////////

    // the plugin
    final ColdLibPlugin plugin;

    BukkitColdLibProvider(ColdLibPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void load() {
        // initialize bukkit based logger
        logGroup.withInitializer(eventLog -> {
            final Logger logger = plugin.getLogger();
            eventLog.withHandler(new EventLogHandler("logger", event -> {
                Level level = BukkitLoggerUtil.getLogLevel(event.getLevel());
                logger.log(level, Objects.toString(event.getMessage()));
                event.getErrors().forEach(Throwable::printStackTrace);
            }));
        });
    }

    @Override
    protected void enable() {

    }

    @Override
    protected void unload() {

    }

}
