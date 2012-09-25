org.vertx.indexer-v1.0
======================

Vert.x module to modify and search a lucene index.

Configuration
=============

The following JSON structure shows an example configuration.

{
  "base_address": "indexer",    # the base address of the module
  "storage": "filesystem",      # can be "memory" or "filesystem"
  "max_results": 10,            # maximal number of search results
  "default_field": "id"         # the default field to search in
  "fields": {                   # field type definitions
    "id": "numeric",            # fields can have the types "numeric", "string" and "text".
    "name": "text",             # while "numeric" and "string" allows only exact matches,
    "email": "string"           # "text" will match against sub-strings of it's content.
  }
}

Messages
========

The indexer will respond to the following addresses.

<base_address>.add      # will take any JSON object and adds it to the index
<base_address>.update   # will update the entry that matches the given field and value parameters
                        # and replaces it with the given JSON object
<base_address>.remove   # will removes all entries from the index that matches to given query
<base_address>.search   # returns all matches to the given query
<base_address>.clear    # drops all entries in the index.
