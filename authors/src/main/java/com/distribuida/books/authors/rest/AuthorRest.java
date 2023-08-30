package com.distribuida.books.authors.rest;

import com.distribuida.books.authors.db.Author;
import com.distribuida.books.authors.repo.AuthorRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthorRest {

    @Inject
    AuthorRepository rep;

    //books GET
    @GET
    @Operation(summary = "Lista autores",
            description = "el siguente metodo lista todos los authores")
    @Timeout(4000)
    @Retry(maxRetries = 4)
    @Counted(name = "findAll", description = "How many times findAll method has been invoked", absolute = true)
    public List<Author> findAll() {
        return rep.findAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "obtiene el authors por el id")
    @APIResponse(responseCode = "200",description = "encontro el autor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "400", description = "autor no encontrado")
    @Timeout(4000)
    @Retry(maxRetries = 4)
    @Counted(name = "findAllById", description = "How many times findAllyId method has been invoked", absolute = true)
    public Response getById(  @Parameter(description = "id requerido ", required = true) @PathParam("id") Integer id) {
        var book = rep.findById(id);
        if (book==null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @POST
    @Operation(summary = "Crear autor",
            description = "Se crea un autor")
    @Timeout(4000)
    @Retry(maxRetries = 4)
    @Timed(name = "create", description = "A measure of how long it takes to create an author", absolute = true)
    public Response create(@RequestBody(description = "Crear autor ", required = true,
            content = @Content(schema = @Schema(implementation = Author.class)))Author p) {
        rep.create(p);

        return Response.status(Response.Status.CREATED.getStatusCode(), "author created").build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualiza el autor por el id")
    @APIResponse(responseCode = "200",description = "se actualizo el autor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "400", description = "Autor no auctualizado")
    @Timeout(4000)
    @Retry(maxRetries = 4)
    @Timed(name = "updateAuthors", description = "A measure of how long it takes to update an author", absolute = true)
    public Response update( @Parameter(description = "id requerido", required = true)@PathParam("id") Integer id, @RequestBody(description = "actualizar author ", required = true,
            content = @Content(schema = @Schema(implementation = Author.class)))Author authorObj) {
        Author author = rep.findById(id);
        author.setFirstName(authorObj.getFirstName());
        author.setLastName(authorObj.getLastName());

        //rep.persistAndFlush(author);

        return Response.ok().build();
    }

    //books/{id} DELETE
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Borra el autor por el id")
    @APIResponse(responseCode = "200",description = "borro el autor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "400", description = "autor no borrado")
    @Timeout(4000)
    @Retry(maxRetries = 4)
    @Timed(name = "deleteAuthors", description = "A measure of how long it takes to delete an authors", absolute = true)
    public Response delete(@Parameter(description = " id requrido", required = true) @PathParam("id") Integer id) {
        rep.delete(id);

        return Response.ok( )
                .build();
    }

}
