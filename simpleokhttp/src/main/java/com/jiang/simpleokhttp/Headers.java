package com.jiang.simpleokhttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by knowing on 2017/11/22.
 */

public class Headers {

    private final String[] namesAndValues;

    Headers(Builder builder) {
        this.namesAndValues = builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    public static final class Builder {

        final List<String> namesAndValues = new ArrayList<>(20);

        private Builder addLenient(String name, String value) {
            namesAndValues.add(name);
            namesAndValues.add(value.trim());
            return this;
        }

        public Builder add(String name, String value) {
            checkNameAndValue(name, value);
            return addLenient(name, value);
        }


        /**
         * 对key，value进行验证 保证不出现ascii码外的字符
         *
         * @param name
         * @param value
         */
        private void checkNameAndValue(String name, String value) {
            if (name == null) throw new NullPointerException("name == null");
            if (name.isEmpty()) throw new IllegalArgumentException("name is empty");
            for (int i = 0, length = name.length(); i < length; i++) {
                char c = name.charAt(i);
                if (c <= '\u0020' || c >= '\u007f') {
                    throw new IllegalArgumentException(String.format(
                            "Unexpected char %#04x at %d in header name: %s", (int) c, i, name));
                }
            }
            if (value == null)
                throw new NullPointerException("value for name " + name + " == null");
            for (int i = 0, length = value.length(); i < length; i++) {
                char c = value.charAt(i);
                if ((c <= '\u001f' && c != '\t') || c >= '\u007f') {
                    throw new IllegalArgumentException(String.format(
                            "Unexpected char %#04x at %d in %s value: %s", (int) c, i, name, value));
                }
            }
        }


        public Headers build() {
            return new Headers(this);
        }
    }
}
