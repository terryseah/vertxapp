package com.example.vertxapp.first;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.ServerSocket;

import static org.junit.Assert.*;

@RunWith(VertxUnitRunner.class)
public class MyFirstVerticleTest {

	private Vertx vertx;
	private int port = 8081;

	@Before
	public void setUp(TestContext testContext) throws Exception {
		vertx = Vertx.vertx();

		ServerSocket socket = new ServerSocket(0);
		port = socket.getLocalPort();
		socket.close();

		DeploymentOptions options = new DeploymentOptions()
				.setConfig(new JsonObject().put("http.port", port));

		vertx.deployVerticle(MyFirstVerticle.class.getName(), options,
				testContext.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext testContext) {
		vertx.close(testContext.asyncAssertSuccess());
	}

	@Test
	public void testMyApplication(TestContext testContext) {
		final Async async = testContext.async();

		vertx.createHttpClient().getNow(port, "localhost", "/", response -> {
			response.handler(body -> {
				testContext.assertTrue(body.toString().contains("Hello"));
				async.complete();
			});
		});
	}
}