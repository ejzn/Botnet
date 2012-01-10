/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package servicebus;


import java.util.Timer;

import common.SnortConfig;

import configuration.LoggerConfiguration;

public class SWAClient {

	 public static AlertPluginFramework SenderObj ;

	
	public static void main(String args[]) throws Exception { 

		String loggerConfigFile ="";
		String snortConfigFile = "";
		
		if (args.length ==2)
		{
			 loggerConfigFile = args[0];
			 snortConfigFile = args[1];
			
		}
		
		if (loggerConfigFile !="")
			LoggerConfiguration.configurationFileName = loggerConfigFile;
		
		if (snortConfigFile !="")
			SnortConfig.configurationFileName = snortConfigFile;
		
	    LoggerConfiguration configObj = new LoggerConfiguration();
	  	Long Timer_Period =   Long.parseLong(configObj.period);   
		Timer timer = new Timer();
		//SenderObj = new AlertPluginFramework(configObj.TargetEPR, configObj.LoggerIdentity,Long.parseLong("7000"));
		SenderObj = new AlertPluginFramework(Long.parseLong("7000"));
		timer.schedule( new UploadAlerts() , 0, Timer_Period);
	  
		 
     
	}
	
	
	 
	

}

