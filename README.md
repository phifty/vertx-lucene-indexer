# me.phifty.indexer-v1.0

Vert.x module to modify and search a lucene index.

## Configuration

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

## Messages

The indexer will respond to the following addresses.

### Adding an entry to the index
    <base_address>.add
Example:

    {
      "id": 1,
      "name": "Dummy"
    }

Example response:

    { "done": true }

### Updating an entry in the index
    <base_address>.update
Example:

    {
      "field": "id",
      "value": "1",
      "document": {
        "id": 1,
        "name: "Another dummy"
      }
    }

Example response:

    { "done": true }

### Removing an entry from the index
    <base_address>.remove
Example:

    {
      "query": "id:1"
    }

Example response:

    { "done": true }

### Searching in the index
    <base_address>.search

Example:
    
    {
      "query": "Dum*"
    }

Example response:

    {
      "results": [
        { "id": 1, "name": "Dummy" }
      ]
    }

### Clear the index
    <base_address>.clear
    
Example:

    { }

Example response:

    { "done": true }
