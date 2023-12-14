package de.t1dev.springdemo.common;

import java.util.UUID;

public abstract class Entity {

    public Entity(UUID id) {
        this.id = id;
    }

    protected UUID id;

    @Override
    public int hashCode() {
        return (this.getClass().getCanonicalName() + this.id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entity)) {
            return false;
        }
        if (!this.getClass().isInstance(obj)) {
            return false;
        }

        return this.id.equals(((Entity) obj).id);
    }

    public UUID getId() {
        return this.id;
    }
}
