package com.jiang.simpleokhttp;

/**
 * Created by knowing on 2017/11/22.
 */

public abstract class EventListener {

    public static final EventListener NONE = new EventListener() {
    };

    static EventListener.Factory factory(final EventListener listener) {
        return new EventListener.Factory() {
            public EventListener create(Call call) {
                return listener;
            }
        };
    }

    public interface Factory {

        EventListener create(Call call);
    }
}
