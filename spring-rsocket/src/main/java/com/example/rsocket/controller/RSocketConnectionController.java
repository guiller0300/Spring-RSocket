package com.example.rsocket.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class RSocketConnectionController {
	

	private Logger log = LoggerFactory.getLogger(RSocketConnectionController.class);

	// private Map<String, RSocketRequester> requesterMap = new HashMap<>();
	private final List<RSocketRequester> requesterList = new ArrayList<>();
	 @PreDestroy
	    void shutdown() {
	        log.info("Eliminando los clientes restantes...");
	        requesterList.stream().forEach(requester -> requester.rsocket().dispose());
	        log.info("Shutting down.");
	    }
	/*
	 * @Synchronized private Map<String, RSocketRequester> getRequesterMap(){ return
	 * requesterMap; }
	 * 
	 * @Synchronized private void addRequester(RSocketRequester rSocketRequester,
	 * String clientId) { log.info("adding requester %s", clientId);
	 * requesterMap.put(clientId, rSocketRequester); }
	 * 
	 * @Synchronized private void removeRequester(String clientId) {
	 * log.info(clientId); requesterMap.remove(clientId); }
	 */

	@ConnectMapping("client-id")
	void onConnect(RSocketRequester requester, String clientId) {
		// String clientIdFixed = clientId.replace("\"", "");
		log.info("Entro");
		requester.rsocket().onClose().doFirst(() -> {
			log.info("%s just disconnected", clientId);
			// removeRequester(clientIdFixed);
			log.info("Client: {} CONNECTED.", clientId);
			requesterList.add(requester);
		}).doOnError(error -> {
			// Warn when channels are closed by clients
			log.warn("Channel to client {} CLOSED", clientId);
		}).doFinally(consumer -> {
			// Remove disconnected clients from the client list
			requesterList.remove(requester);
			log.info("Client {} DISCONNECTED", clientId);
		}).subscribe();

		// addRequester(requester, clientIdFixed);

		// Callback to client, confirming connection
		requester.route("client-status").data("OPEN").retrieveFlux(String.class)
				.doOnNext(s -> log.info("Client: {} Free Memory: {}.", clientId, s)).subscribe();
	}
	
    @MessageMapping("request-response")
    Mono<Message> requestResponse(final Message request) {
        log.info("Received request-response request: {}", request);
        log.info("Request-response initiated by '{}' in the role '{}'");
        // create a single Message and return it
        return Mono.just(new Message("Server", "Response"));
    }

}
