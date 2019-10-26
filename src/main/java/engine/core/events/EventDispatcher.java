package engine.core.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class EventDispatcher {
    Map<Class<? extends Event>, Vector<EventListener>> eventListeners = new HashMap<>();

    public void publish(Class<? extends Event> eventClass, Event event) {
        Vector<EventListener> listeners = eventListeners.get(eventClass);

        if (listeners != null) {
            for (EventListener listener : listeners) {
                if (listener.onEvent(event))
                    break;
            }
        }
    }

    public void listen(Class<? extends Event> eventClass, EventListener listener) {
        Vector<EventListener> defaultValue = new Vector<>();

        Vector<EventListener> listeners = eventListeners.getOrDefault(eventClass, defaultValue);
        listeners.add(listener);
    }
}
