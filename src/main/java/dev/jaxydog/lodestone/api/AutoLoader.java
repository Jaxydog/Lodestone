/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Copyright © 2024 Jaxydog
 *
 * This file is part of Lodestone.
 *
 * Lodestone is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Lodestone is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with Lodestone. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.jaxydog.lodestone.api;

import dev.jaxydog.lodestone.Lodestone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * A class that automatically loads all of its registered static constants.
 * <p>
 * Extending classes should typically be marked as {@code final}, and only contain private inner classes and
 * {@code public static final} fields. They should always be singletons, with their only instance being loaded either
 * directly during initialization or indirectly through another {@link AutoLoader}.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
public abstract class AutoLoader implements Loaded {

    /**
     * A comparator that sorts fields by their annotated loading priority.
     *
     * @since 1.0.0
     */
    private static final Comparator<Field> PRIORITY_ORDER = Comparator.comparingInt((Field field) -> {
        if (field.isAnnotationPresent(LoadingPriority.class)) {
            return field.getAnnotation(LoadingPriority.class).value();
        } else if (field.getType().isAnnotationPresent(LoadingPriority.class)) {
            return field.getType().getAnnotation(LoadingPriority.class).value();
        } else {
            return LoadingPriority.DEFAULT;
        }
    }).reversed();

    /**
     * This loader's logger instance.
     *
     * @since 1.0.0
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getLoaderId().toString().replaceAll(":", "/"));

    /**
     * Iterates over all valid fields within this class, providing them to the given consumer.
     *
     * @param type The {@link Loaded} interface.
     * @param consumer The field consumer.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    private <T extends Loaded> void iterate(Class<? extends T> type, BiConsumer<Field, ? super T> consumer) {
        for (final Field field : Arrays.stream(this.getClass().getFields()).sorted(PRIORITY_ORDER).toList()) {
            // Ensure the field is public, static, and final.
            if (!field.accessFlags().contains(AccessFlag.PUBLIC)) continue;
            if (!field.accessFlags().contains(AccessFlag.STATIC)) continue;
            if (!field.accessFlags().contains(AccessFlag.FINAL)) continue;

            // Ensure the field should not be ignored.
            if (field.isAnnotationPresent(IgnoreLoading.class)) {
                final IgnoreLoading annotation = field.getAnnotation(IgnoreLoading.class);
                final Set<Class<? extends Loaded>> types = Set.of(annotation.value());

                if (types.isEmpty() || types.contains(type)) continue;
            }

            // Ensure the field is an instance of the given type.
            if (!type.isAssignableFrom(field.getType())) {
                // Make sure we invoke internal autoloader instances.
                if (AutoLoader.class.isAssignableFrom(field.getType())) {
                    try {
                        ((AutoLoader) field.get(null)).iterate(type, consumer);
                    } catch (IllegalAccessException | IllegalArgumentException exception) {
                        final String className = this.getClass().getSimpleName();
                        final String fieldName = field.getName();
                        final String message = exception.getLocalizedMessage();

                        this.logger.error("Unable to access loader '{}#{}': {}", className, fieldName, message);
                    }
                }

                continue;
            }

            try {
                consumer.accept(field, (T) field.get(null));
            } catch (IllegalAccessException | IllegalArgumentException exception) {
                final String className = this.getClass().getSimpleName();
                final String fieldName = field.getName();
                final String message = exception.getLocalizedMessage();

                this.logger.error("Unable to access field '{}#{}': {}", className, fieldName, message);
            }
        }
    }

    /**
     * Registers all defined values that extend the given {@link Loaded} interface for future loading.
     *
     * @param type The type to register.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    public <T extends Loaded> void register(Class<? extends T> type) {
        this.iterate(type, (field, value) -> Lodestone.register(type, value));
    }

    /**
     * Registers all defined values that extend a {@link Loaded} interface for future loading.
     *
     * @since 1.0.0
     */
    public void register() {
        Lodestone.getInterfaces().forEach(this::register);
    }

}
