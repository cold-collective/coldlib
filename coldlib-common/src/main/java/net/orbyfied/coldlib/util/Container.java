package net.orbyfied.coldlib.util;

/**
 * A class responsible for holding a
 * value and providing access to that
 * value under certain conditions.
 *
 * @param <V> The value type.
 */
public interface Container<V> {

    /**
     * Create a new immutable container
     * with the value already set.
     *
     * @param value The final value.
     * @param <V> The value type.
     * @return The container instance.
     */
    static <V> Container<V> finalImmutable(final V value) {
        return new Container<>() {
            @Override
            public V get() {
                return value;
            }

            @Override
            public boolean isSet() {
                return true;
            }

            @Override
            public Container<V> set(V val) {
                throw new UnsupportedOperationException("Container is immutable");
            }

            @Override
            public boolean isMutable() {
                return false;
            }
        };
    }

    /**
     * Create a new container whose value can
     * only be set once.
     *
     * @param <V> The value type.
     * @return The container instance.
     */
    static <V> Container<V> futureImmutable() {
        return new Container<>() {
            // the current value
            V value;
            // if it has been set
            boolean set;

            @Override
            public V get() {
                return value;
            }

            @Override
            public boolean isSet() {
                return set;
            }

            @Override
            public Container<V> set(V val) {
                if (set)
                    throw new UnsupportedOperationException("This container already has" +
                            " a value set");
                this.value = val;
                this.set = true;
                return this;
            }

            @Override
            public boolean isMutable() {
                return !set;
            }
        };
    }

    /**
     * Create a new mutable container instance
     * with a value optionally pre-set. Providing
     * null to the value argument is essentially
     * the same as providing nothing at all.
     *
     * @param val The value to pre-set.
     * @param <V> The value type.
     * @return The container instance.
     */
    static <V> Container<V> mutable(final V val) {
        return new Container<>() {
            // the value currently stored
            V value = val;

            @Override
            public V get() {
                return value;
            }

            @Override
            public boolean isSet() {
                return true;
            }

            @Override
            public Container<V> set(V val) {
                value = val;
                return this;
            }

            @Override
            public boolean isMutable() {
                return true;
            }
        };
    }

    /**
     * Create a new mutable container instance
     * without a value pre-set. Just calls
     * {@link Container#mutable(V)} with null
     * provided as the pre-set value.
     *
     * @param <V> The value type.
     * @return The container instance.
     */
    static <V> Container<V> mutable() {
        return mutable(null);
    }

    ////////////////////////////////////////////

    /**
     * Get the value currently stored.
     *
     * @return The value of type {@code V}
     */
    V get();

    /**
     * Get if a value is currently set.
     *
     * @return If a value is currently set.
     */
    boolean isSet();

    /**
     * Set the value stored to {@code val}.
     * This might not affect the current
     * instance and instead return a new
     * {@link Container} based on the implementation.
     *
     * @param val The value to set.
     * @return This or a new instance with the new value.
     * @throws UnsupportedOperationException If setting is unsupported.
     */
    Container<V> set(V val);

    /**
     * Get if this container is mutable.
     * This may depend on the state and not
     * the type, so caching this value might
     * not always work as expected.
     *
     * @return If it can be mutated.
     */
    boolean isMutable();

    /**
     * Clones this container (copies the value reference)
     * into a new mutable container instance.
     *
     * @return The new instance.
     */
    default Container<V> cloneMutable() {
        return mutable(get());
    }

}