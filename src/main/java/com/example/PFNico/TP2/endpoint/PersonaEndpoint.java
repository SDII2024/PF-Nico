package com.example.PFNico.TP2.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import com.example.PFNico.TP2.service.PersonaService;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.webservicesoap.persona.PersonaResponse;
import org.webservicesoap.persona.PersonaRequest;

@Endpoint
public class PersonaEndpoint {
    private static final String NAMESPACE = "http://www.webservicesoap.org/persona";

    @Autowired
    private PersonaService service = new PersonaService();

    @PayloadRoot(localPart = "PersonaRequest", namespace = NAMESPACE)
    @ResponsePayload
    public PersonaResponse PersonaRequest(@RequestPayload PersonaRequest request) {
        return service.consultar(request);
    }

}
