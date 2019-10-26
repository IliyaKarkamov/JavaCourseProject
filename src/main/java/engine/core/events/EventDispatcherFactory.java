package engine.core.events;

public class EventDispatcherFactory {
    public static IEventDispatcher create() {
        return new EventDispatcher();
    }
}
