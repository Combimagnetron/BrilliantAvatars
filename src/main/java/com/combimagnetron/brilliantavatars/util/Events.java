package com.combimagnetron.brilliantavatars.util;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class Events<T extends Event> implements Listener, EventExecutor {

  private final Class<T> tClass;
  private EventPriority eventPriority;
  private Consumer<T> consumer;

  public Events(@NotNull Class<T> eventClass) {
    this.tClass = eventClass;
    this.eventPriority = EventPriority.NORMAL;
    this.register();
  }

  public Events<T> priority(@NotNull EventPriority priority) {
    this.eventPriority = priority;
    return this;
  }

  public Events<T> async(@NotNull Consumer<T> consumer) {
    this.consumer = consumer;
    return this;
  }

  public Events<T> register() {
    Bukkit.getPluginManager().registerEvent(this.tClass, this, this.eventPriority, this, Bukkit.getPluginManager().getPlugin("BrilliantAvatars"), false);
    return this;
  }

  @Override
  public void execute(Listener listener, Event event) {
    if (event.getClass().equals(this.tClass)) {
      T t = (T) event;
      if (this.consumer != null)
        Tasks.executeAsync(() -> this.consumer.accept(t));
    }
  }

  public static <T extends Event> Events<T> event(@NotNull Class<T> eventClass) {
    return new Events<>(eventClass);
  }
}
