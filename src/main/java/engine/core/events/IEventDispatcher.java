package engine.core.events;

public interface IEventDispatcher {
    <T extends IEvent> void publish(T event);
    void listen(Class<? extends IEvent> eventClass, IEventListener listener);
}
