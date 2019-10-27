package engine.core.events;

import engine.core.events.interfaces.IEvent;
import engine.core.events.interfaces.IEventDispatcher;
import engine.core.events.interfaces.IEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public final class EventDispatcher implements IEventDispatcher {
    private Map<Class<? extends IEvent>, Vector<IEventListener>> eventListeners = new HashMap<>();

    @Override
    public <T extends IEvent> void publish(T event) {
        Vector<IEventListener> listeners = eventListeners.get(event.getClass());

        if (listeners != null) {
            for (IEventListener listener : listeners) {
                if (listener.onEvent(event))
                    break;
            }
        }
    }

    @Override
    public void addListener(Class<? extends IEvent> eventClass, IEventListener listener) {
        eventListeners.computeIfAbsent(eventClass, X -> new Vector<>()).add(listener);
    }

    @Override
    public void removeListener(Class<? extends IEvent> eventClass, IEventListener listener) {
        Vector<IEventListener> listeners = eventListeners.get(eventClass);

        if (listeners != null) {
            listeners.remove(listener);
        }
    }
}
