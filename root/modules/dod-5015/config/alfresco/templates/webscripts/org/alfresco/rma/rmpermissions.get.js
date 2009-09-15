/**
 * Entry point for rmpermissions GET data webscript.
 * Queries the permissions from an RM node and constructs the data-model for the template.
 * 
 * @method main
 */
function main()
{
   // Get the node from the URL
   var pathSegments = url.match.split("/");
   var reference = [ url.templateArgs.store_type, url.templateArgs.store_id ].concat(url.templateArgs.id.split("/"));
   var node = search.findNode(pathSegments[2], reference);
   
   // 404 if the node is not found
   if (node == null)
   {
      status.setCode(status.STATUS_NOT_FOUND, "The node could not be found");
      return;
   }
   
   // retrieve permissions applied  to this node
   var permissions = node.getFullPermissions();
   
   // split tokens - results are in the format:
   // [ALLOWED|DENIED];[USERNAME|GROUPNAME];PERMISSION;[INHERITED|DIRECT]
   var result = [];
   for (var i=0; i<permissions.length; i++)
   {
      var tokens = permissions[i].split(";");
      if (tokens[0] == "ALLOWED")
      {
         // we are only interested in the RM specific object level permissions
         // Filing, ReadRecords, FileRecords
         var id = tokens[2];
         switch (id)
         {
            case "Filing":
            case "ReadRecords":
            case "FileRecords":
            {
               var group = groups.getGroupForFullAuthorityName(tokens[1]);
               // check for null group - this could happen if a group is removed
               // from the system or similar and should be handled
               if (group != null)
               {
                  result.push(
                     {
                        id: id,
                        authority:
                        {
                           id: tokens[1],
                           label: group.displayName
                        },
                        inherited: (tokens[3] == "INHERITED")
                     }
                  );
               }
            }
         }
      }
   }
   
   // apply result data-model
   model.permissions = result;
   model.inherited = node.inheritsPermissions();
}

main();