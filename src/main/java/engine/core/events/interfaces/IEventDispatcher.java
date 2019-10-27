package engine.core.events.interfaces;

public interface IEventDispatcher {
    <T extends IEvent> void publish(T event);

    void addListener(Class<? extends IEvent> eventClass, IEventListener listener);
    void removeListener(Class<? extends IEvent> eventClass, IEventListener listener);
}
