package com.example.vertxapp.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class MyFirstVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		vertx.createHttpServer()
				.requestHandler(r -> {
					r.response().end("<h1>Hello from my first Vert.x 3 application</h1>");
				})
				.listen(config().getInteger("http.port", 8080), result -> {
					if (result.succeeded()) {
						startFuture.complete();
					} else {
						startFuture.fail(result.cause());
					}
				});
	}
}
