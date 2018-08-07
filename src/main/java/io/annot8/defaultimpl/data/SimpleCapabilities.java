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
  public Stream<String> getRequiredInputAnnotations() {
    return requiredInputAnnotations.stream();
  }

  @Override
  public Stream<String> getOptionalInputAnnotations() {
    return optionalInputAnnotations.stream();
  }

  @Override
  public Stream<String> getOutputAnnotations() {
    return outputAnnotations.stream();
  }
  
  @Override
  public Stream<String> getRequiredInputGroups() {
    return requiredInputGroups.stream();
  }

  @Override
  public Stream<String> getOptionalInputGroups() {
    return optionalInputGroups.stream();
  }

  @Override
  public Stream<String> getOutputGroups() {
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
  public Stream<Class<? extends Resource>> getRequiredResources() {
    return requiredResources.stream();
  }

  @Override
  public Stream<Class<? extends Bounds>> getOutputBounds() {
    return outputBounds.stream();
  }

  public static class Builder {

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

    public Builder requiresInputAnnotation(String type) {
      requiredInputAnnotations.add(type);
      return this;
    }

    public Builder optionalInputAnnotation(String type) {
      optionalInputAnnotations.add(type);
      return this;
    }

    public Builder outputsAnnotation(String type) {
      outputAnnotations.add(type);
      return this;
    }
    
	public Builder requiresInputGroup(String type) {
		requiredInputGroups.add(type);
		return this;
	}

	public Builder optionalInputGroup(String type) {
		optionalInputGroups.add(type);
		return this;
	}

	public Builder outputsGroup(String type) {
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

    public Builder requiresResource(Class<? extends Resource> clazz) {
      requiredResources.add(clazz);
      return this;
    }

    public Builder outputsBounds(Class<? extends Bounds> clazz) {
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
