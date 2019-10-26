package engine.core.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

final class EventDispatcher implements IEventDispatcher {
    private Map<Class<? extends IEvent>, Vector<IEventListener>> eventListeners = new HashMap<>();

    public <T extends IEvent> void publish(T event) {
        Vector<IEventListener> listeners = eventListeners.get(event.getClass());

        if (listeners != null) {
            for (IEventListener listener : listeners) {
                if (listener.onEvent(event))
                    break;
            }
        }
    }

    public void listen(Class<? extends IEvent> eventClass, IEventListener listener) {
        eventListeners.computeIfAbsent(eventClass, X -> new Vector<>()).add(listener);
    }
}
