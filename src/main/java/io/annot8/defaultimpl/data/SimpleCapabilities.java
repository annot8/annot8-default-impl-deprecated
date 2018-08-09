package io.annot8.defaultimpl.data;

import io.annot8.core.bounds.Bounds;
import io.annot8.core.capabilities.Capabilities;
import io.annot8.core.components.Resource;
import io.annot8.core.data.Content;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class SimpleCapabilities implements Capabilities {

  private final Set<String> requiredInputAnnotations;
  private final Set<String> optionalInputAnnotations;
  private final Set<String> outputAnnotations;
  
  private final Set<String> requiredInputGroups;
  private final Set<String> optionalInputGroups;
  private final Set<String> outputGroups;
  
  private final Set<Class<? extends Content<?>>> createdContent;
  private final Set<Class<? extends Content<?>>> requiredContent;

  private final Set<Class<? extends Resource>> requiredResources;
  private final Set<Class<? extends Bounds>> outputBounds;


  private SimpleCapabilities(Set<String> requiredInputAnnotations,
      Set<String> optionalInputAnnotations, Set<String> outputAnnotations,
      Set<String> requiredInputGroups,
      Set<String> optionalInputGroups, Set<String> outputGroups,
      Set<Class<? extends Content<?>>> createdContent,
      Set<Class<? extends Content<?>>> requiredContent,
      Set<Class<? extends Resource>> requiredResources,
      Set<Class<? extends Bounds>> outputBounds) {
    this.requiredInputAnnotations = requiredInputAnnotations;
    this.optionalInputAnnotations = optionalInputAnnotations;
    this.outputAnnotations = outputAnnotations;
    this.requiredInputGroups = requiredInputGroups;
    this.optionalInputGroups = optionalInputGroups;
    this.outputGroups = outputGroups;
    this.createdContent = createdContent;
    this.requiredContent = requiredContent;
    this.requiredResources = requiredResources;
    this.outputBounds = outputBounds;
  }

  @Override
  public Stream<String> getRequiredAnnotations() {
    return requiredInputAnnotations.stream();
  }

  @Override
  public Stream<String> getOptionalAnnotations() {
    return optionalInputAnnotations.stream();
  }

  @Override
  public Stream<String> getCreatedAnnotations() {
    return outputAnnotations.stream();
  }
  
  @Override
  public Stream<String> getRequiredGroups() {
    return requiredInputGroups.stream();
  }

  @Override
  public Stream<String> getOptionalGroups() {
    return optionalInputGroups.stream();
  }

  @Override
  public Stream<String> getCreatedGroups() {
    return outputGroups.stream();
  }

  @Override
  public Stream<Class<? extends Content<?>>> getRequiredContent() {
    return requiredContent.stream();
  }

  @Override
  public Stream<Class<? extends Content<?>>> getCreatedContent() {
    return createdContent.stream();
  }

  @Override
  public Stream<Class<? extends Resource>> getUsedResources() {
    return requiredResources.stream();
  }

  @Override
  public Stream<Class<? extends Bounds>> getCreatedBounds() {
    return outputBounds.stream();
  }

  public static class Builder implements Capabilities.Builder {

    private final Set<String> requiredInputAnnotations = new HashSet<>();
    private final Set<String> optionalInputAnnotations = new HashSet<>();
    private final Set<String> outputAnnotations = new HashSet<>();
    
    private final Set<String> requiredInputGroups = new HashSet<>();
    private final Set<String> optionalInputGroups = new HashSet<>();
    private final Set<String> outputGroups = new HashSet<>();
    
    private final Set<Class<? extends Content<?>>> requiredContent = new HashSet<>();
    private final Set<Class<? extends Content<?>>> createdContent = new HashSet<>();
    private final Set<Class<? extends Resource>> requiredResources = new HashSet<>();
    private final Set<Class<? extends Bounds>> outputBounds = new HashSet<>();

    public Builder requiresAnnotation(String type) {
      requiredInputAnnotations.add(type);
      return this;
    }

    public Builder optionalAnnotation(String type) {
      optionalInputAnnotations.add(type);
      return this;
    }

    public Builder createsAnnotation(String type) {
      outputAnnotations.add(type);
      return this;
    }
    
	public Builder requiresGroup(String type) {
		requiredInputGroups.add(type);
		return this;
	}

	public Builder optionalGroup(String type) {
		optionalInputGroups.add(type);
		return this;
	}

	public Builder createsGroup(String type) {
		outputGroups.add(type);
		return this;
	}

    public Builder requiresContent(Class<? extends Content<?>> clazz) {
      requiredContent.add(clazz);
      return this;
    }

    public Builder createsContent(Class<? extends Content<?>> clazz) {
      createdContent.add(clazz);
      return this;
    }

    public Builder usesResource(Class<? extends Resource> clazz) {
      requiredResources.add(clazz);
      return this;
    }

    public Builder createsBounds(Class<? extends Bounds> clazz) {
      outputBounds.add(clazz);
      return this;
    }

    public Capabilities save() {
      return new SimpleCapabilities(
          Collections.unmodifiableSet(requiredInputAnnotations),
          Collections.unmodifiableSet(optionalInputAnnotations),
          Collections.unmodifiableSet(outputAnnotations),
          Collections.unmodifiableSet(requiredInputGroups),
          Collections.unmodifiableSet(optionalInputGroups),
          Collections.unmodifiableSet(outputGroups),
          Collections.unmodifiableSet(createdContent),
          Collections.unmodifiableSet(requiredContent),
          Collections.unmodifiableSet(requiredResources),
          Collections.unmodifiableSet(outputBounds));
    }

  }

}
