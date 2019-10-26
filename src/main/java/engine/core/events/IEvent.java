package engine.core.events;

public interface IEvent {
    String getName();
    Class<? extends IEvent> getType();
}
