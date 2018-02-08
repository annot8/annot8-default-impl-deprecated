@SuppressWarnings("module")
    module io.annot8.defaultimpl {
  requires io.annot8.core;
  requires io.annot8.common;

  exports io.annot8.defaultimpl.annotations;
  exports io.annot8.defaultimpl.content;
  exports io.annot8.defaultimpl.context;
  exports io.annot8.defaultimpl.data;
  exports io.annot8.defaultimpl.factories;
  exports io.annot8.defaultimpl.pipeline;
  exports io.annot8.defaultimpl.stores;
  exports io.annot8.defaultimpl.properties;
}