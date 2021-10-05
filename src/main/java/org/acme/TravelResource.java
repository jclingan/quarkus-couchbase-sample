package org.acme;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.couchbase.client.java.Cluster;

import org.eclipse.microprofile.config.inject.ConfigProperty;

// @RolesAllowed("user")
@Path("/travel")
public class TravelResource {
  @Inject
  Cluster cluster;

  @ConfigProperty(name = "query")
  String query;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> run() {
    // Get a reference to a particular Couchbase bucket and its default collection
    var list = new ArrayList<String>();

    // Perform a N1QL query
    var queryResult = cluster.query(query);

    queryResult.rowsAsObject().forEach(row -> {
      list.add(row.getObject("travel-sample").getString("name"));
    });

    return list;
  }
}