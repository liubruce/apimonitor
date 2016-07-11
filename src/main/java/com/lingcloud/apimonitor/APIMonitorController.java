package com.lingcloud.apimonitor;


//import java.util.concurrent.atomic.AtomicLong;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.lingcloud.apiservice.Greeting;
import com.lingcloud.models.TaskApiInfo;
import com.lingcloud.models.TaskApiInfoDao;


import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;


@Controller
public class APIMonitorController {
	
	@Autowired
	private TaskApiInfoDao taskApiInfoDao;
	

    //private static final String template = "Hello, %s!";
    //private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value="/apiconfig", method=RequestMethod.GET) 
    public String apiconfig(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
       /* return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));*/
    	model.addAttribute("apicontent", new APIContent());
    	return "apiconfig";
    }

    @RequestMapping(value="/apiconfig", method=RequestMethod.POST)
    public String apiconfigSummit(@ModelAttribute APIContent apicontent, Model model) {
    	
    	restapiSummit( apicontent,  model);
    	soapapiSummit(apicontent,model);
    	
    	return "result";
    }
    
    public String restapiSummit(APIContent apicontent, Model model){
    	RestTemplate restTemplate = new RestTemplate();
/*    	APIContent quote = restTemplate.getForObject(
    			//"http://gturnquist-quoters.cfapps.io/api/random"
    			apicontent.getApiurl()
    			, APIContent.class);*/
    			//"http://gturnquist-quoters.cfapps.io/api/random"
    	//case 
    	String resultJson = restTemplate.getForObject(
    			//"http://gturnquist-quoters.cfapps.io/api/random"
    			apicontent.getApiurl()
    			, String.class);
    	//quote.setType(resultJson);
    	model.addAttribute("result", resultJson + " " + apicontent.getType());
    	return "result";
    }

    public String soapapiSummit(APIContent apicontent, Model model){
    
		//"http://136.64.50.199:9201/BssCrmWebServiceTj/services/CustomerService_B?wsdl";
		//GetCityForecastByZIPResponse response = weatherClient.getCityForecastByZip(zipCode);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            HttpGet httpget = new HttpGet(apicontent.getApiurl());
            //http://wsf.cdyne.com/WeatherWS/Weather.asmx?wsdl");
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .setExpectContinueEnabled(true)
                    .build();

            RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
                    .setSocketTimeout(1000)
                    .setConnectTimeout(1000)
                    .setConnectionRequestTimeout(1000)
                    .build();
            httpget.setConfig(requestConfig);
            System.out.println("Executing request " + httpget.getRequestLine());
            long a=System.currentTimeMillis();
            int status =0;
            try{
            	 CloseableHttpResponse response = httpclient.execute(httpget); //, responseHandler);
            	 status = response.getStatusLine().getStatusCode();
            	 if (status >= 200 && status < 300) {
            		 ///
            		 System.out.println("----------------------------------------成功:" + status);
            	 }else

            		 System.out.println("---------------------------------------- 错误码：" + status);
		            //System.out.println(responseBody);
            } catch (ClientProtocolException e) {  
            	System.out.println("status: " + status);
                e.printStackTrace();  
            } catch (IOException e) { 
            	System.out.println("status: " + status);
            	// status = response.getStatusLine().getStatusCode();
                e.printStackTrace(); 
            }finally{
        
	            System.out.println("\r<br>执行耗时 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
	            
	            
	        }
        } finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return "result";
        
    }    
    @RequestMapping("/test")
    public String test(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        //model.addAttribute("name", name);
    	model.addAttribute("greeting", new Greeting());
        return "test";
    } 
    
    @RequestMapping("/getTaskname")
    @ResponseBody
    public String getByEmail(String email) {
      long taskId;
      String alltasks = "";
      //taskApiInfoDao.save(new TaskApiInfo("Jack", "Bauer"));
      //TaskApiInfo taskApiInfo = TaskApiInfoDao.findByTaskName(email);
      //if (taskApiInfo != null) {
      for (TaskApiInfo taskApiInfo : (List<TaskApiInfo>)taskApiInfoDao.findByTaskName("Bauer")) {
    	  //log.info(bauer.toString());
    	  taskId = taskApiInfo.getTaskId();
    	  alltasks = alltasks + taskId;
      }
    	  
        return "The user id is: " + alltasks;
     // }
     // return "user " + email + " is not exist.";
    }
    
    /*
    @RequestMapping("/")
    public String index() {
        return "forward:/index";
    } */
}