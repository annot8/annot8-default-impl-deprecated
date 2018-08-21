open module io.annot8.defaultimpl {
  requires io.annot8.core;
  requires transitive io.annot8.common.implementations;
  requires transitive io.annot8.common.utils;
  requires transitive io.annot8.common.data;

  exports io.annot8.defaultimpl.annotations;
  exports io.annot8.defaultimpl.content;
  exports io.annot8.defaultimpl.context;
  exports io.annot8.defaultimpl.data;
  exports io.annot8.defaultimpl.factories;
  exports io.annot8.defaultimpl.pipeline;
  exports io.annot8.defaultimpl.references;
  exports io.annot8.defaultimpl.stores;
  exports io.annot8.defaultimpl.properties;

  requires slf4j.api;
}
