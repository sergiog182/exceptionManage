package com.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import com.jackson.classes.*;
import com.processors.*;

public class Builder extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		DataFormat jackson = new JacksonDataFormat(User.class);
		
		errorHandler(deadLetterChannel("file:files/outgoing/errors/generalError"));
		
		onException(com.fasterxml.jackson.databind.exc.InvalidFormatException.class).handled(true).to("file:files/outgoing/errors/numberFormatException?fileExist=append");
		
		from("file:files/incoming?noop=true")
		.unmarshal(jackson)
		.process(new UserProcessor())
		.marshal(jackson)
		.doTry()
			.choice()
				.when(simple("${headers.gender} == 'Male'"))
					.toD("file:files/outgoing/Males?fileExist=fail&fileName=${headers.FinalFileName}.json")
				.otherwise()
					.toD("file:files/outgoing/Females?fileExist=fail&fileName=${headers.FinalFileName}.json")
			.end()
		.endDoTry()
		.doCatch(Exception.class)
			.toD("file:files/outgoing/errors/fileAlreadyExist?fileExist=append&fileName=${headers.FinalFileName}.json")
		.end();
	}

}
