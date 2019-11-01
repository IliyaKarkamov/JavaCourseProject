package engine.core.events.interfaces;

public interface IEvent {
    String getName();

    Class<? extends IEvent> getType();
}
