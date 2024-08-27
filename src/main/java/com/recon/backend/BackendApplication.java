package com.recon.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recon.backend.services.CommunicationsService;
import com.recon.backend.services.FirebaseService;
import com.recon.backend.utils.E;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.logging.Logger;

@SpringBootApplication
@EnableWebMvc

public class BackendApplication  implements ApplicationListener<ApplicationReadyEvent> {
	static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	static final Logger logger = Logger.getLogger(BackendApplication.class.getSimpleName());
	static final String MM = "\uD83D\uDD35\uD83D\uDC26\uD83D\uDD35\uD83D\uDC26\uD83D\uDD35\uD83D\uDC26 ";
   	private static final  String tag = "\uD83E\uDD5D\uD83E\uDD5D\uD83E\uDD5D ReconBackendApp \uD83E\uDD5D";

	   private final FirebaseService firebaseService;
	   private final CommunicationsService communicationsService;

    public BackendApplication(FirebaseService firebaseService, CommunicationsService communicationsService) {
        this.firebaseService = firebaseService;
        this.communicationsService = communicationsService;
    }


    public static void main(String[] args) {
		logger.info(MM+tag+" Starting Backend Application .....");
		SpringApplication.run(BackendApplication.class, args);
		logger.info(MM+tag+" Backend Application started OK \uD83E\uDD6C\uD83E\uDD6C\uD83E\uDD6C");

	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
				.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
				.getHandlerMethods();

		logger.info(tag+E.PEAR + E.PEAR + E.PEAR + E.PEAR +
				" Total Backend Endpoints: \uD83C\uDF4E " + map.size() + "\n");
		map.forEach((key, value) -> logger.info( " \uD83C\uDF50\uD83C\uDF50 " + key));
		try {
			firebaseService.initializeFirebase();
			var s = communicationsService.connectWithGet("https://jsonplaceholder.typicode.com/todos");
		} catch (Exception e) {
			logger.info(tag + " "
					+ e.getMessage());
		}
	}

}
