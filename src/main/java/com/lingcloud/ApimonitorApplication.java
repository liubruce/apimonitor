package com.lingcloud;

/*
@SpringBootApplication
public class ApimonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApimonitorApplication.class, args);
	}
}
*/



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.client.RestTemplate;

//import com.lingcloud.apimonitor.APIContent;

@SpringBootApplication
public class ApimonitorApplication  { //implements CommandLineRunner

   // private static final Logger log = LoggerFactory.getLogger(ApimonitorApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(ApimonitorApplication.class);
    }
/*
    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        APIContent quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", APIContent.class);
        log.info(quote.toString());
    }
    
    */
}